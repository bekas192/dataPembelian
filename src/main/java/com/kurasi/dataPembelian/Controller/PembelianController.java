/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kurasi.dataPembelian.Controller;

import com.kurasi.dataPembelian.Model.Barang;
import com.kurasi.dataPembelian.Model.PembelianData;
import com.kurasi.dataPembelian.Model.Pembelian;
import com.kurasi.dataPembelian.Model.Supplier;
import com.kurasi.dataPembelian.Model.SupplierData;
import com.kurasi.dataPembelian.Repository.BarangRepository;
import com.kurasi.dataPembelian.Repository.PembelianRepository;
import com.kurasi.dataPembelian.Repository.SupplierRepository;
import com.kurasi.dataPembelian.exception.ResourceNotFoundException;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author arrif
 */
@RestController
@RequestMapping("/pembelian")
public class PembelianController {
    @Autowired
    private PembelianRepository pembelianRepository;
    @Autowired
    private BarangRepository barangRepository;
    @GetMapping("/all/{page}/{limit}")
    public PembelianData getAllPemebelianData(@PathVariable (value = "page") int page, 
                                                @PathVariable (value = "limit") int limit) {
        
        Pageable pageable = PageRequest.of(page, limit);
        Page<Pembelian> data = pembelianRepository.findAll(pageable);
        
        PembelianData dataResponse = new PembelianData();
        dataResponse.setPembelian(data.getContent());
        dataResponse.setHasNext(data.hasNext());
        dataResponse.setHasPrevious(data.hasPrevious());
        dataResponse.setNumberOfElements(data.getNumberOfElements());
        
        return dataResponse;
    }
    
    @GetMapping("/barang/{barangId}/{page}/{limit}")
    public PembelianData getAllPembelianData(@PathVariable (value = "page") int page, 
                                                @PathVariable (value = "limit") int limit) {

        Pageable pageable = PageRequest.of(page, limit);
        Page<Pembelian> data = pembelianRepository.findAll(pageable);

        PembelianData dataResponse = new PembelianData();
        dataResponse.setPembelian(data.getContent());
        dataResponse.setHasNext(data.hasNext());
        dataResponse.setHasPrevious(data.hasPrevious());
        dataResponse.setNumberOfElements(data.getNumberOfElements());
        
        return dataResponse;
    }
    
    @PostMapping("/{barangId}")
    public Pembelian createPembelian(@PathVariable(value = "barangId") Long barangId, @RequestBody Pembelian pembelian) {
        
        if (!barangRepository.existsById(barangId)) {
            throw new ResourceNotFoundException("Barang Id " + barangId + " not found");
        }
        
        Barang barang = barangRepository.findById(barangId).get();
        
        pembelian.setBarang(barang);

        return pembelianRepository.save(pembelian);
    }
    @PutMapping("/{pembelianId}")
    public Pembelian updatePembelian(@PathVariable (value = "pembelianId") Long pembelianId,
                                 @Valid @RequestBody Pembelian pembelianRequest) {
        if(!barangRepository.existsById(pembelianId)) {
            throw new ResourceNotFoundException("Pembelian Id " + pembelianId + " not found");
        }

        return pembelianRepository.findById(pembelianId).map(pembelian -> {
            pembelian.setTglPembelian(pembelianRequest.getTglPembelian());
            pembelian.setKeterangan(pembelianRequest.getKeterangan());
            pembelian.setNoOrder(pembelianRequest.getNoOrder());
            return pembelianRepository.save(pembelian);
        }).orElseThrow(() -> new ResourceNotFoundException("PembelianId " + pembelianId + "not found"));
    }
    
    @DeleteMapping("/{pembelianId}")
    public ResponseEntity<?> deleteSupplier(@PathVariable (value = "pembelianId") Long pembelianId) {
        return pembelianRepository.findById(pembelianId).map(pembelian -> {
            pembelianRepository.delete(pembelian);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Pembelian not found with id " + pembelianId));
    }
}

