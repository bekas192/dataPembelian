/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kurasi.dataPembelian.Controller;

import com.kurasi.dataPembelian.Model.Barang;
import com.kurasi.dataPembelian.Model.Supplier;
import com.kurasi.dataPembelian.Repository.BarangRepository;
import com.kurasi.dataPembelian.Repository.SupplierRepository;
import com.kurasi.dataPembelian.exception.ResourceNotFoundException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
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
 * @author 300
 */
@RestController
@RequestMapping("/barang")
public class BarangController {

    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private BarangRepository barangRepository;

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Barang> getAllBarang(Pageable pageable) {
        return barangRepository.findAll(pageable);
    }

    @PostMapping(value = "/{supplierId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Barang createBarang(@PathVariable(value = "supplierId") Long supplierId, @RequestBody Barang barang) {

        if (!supplierRepository.existsById(supplierId)) {
            throw new ResourceNotFoundException("Supplier Id " + supplierId + " not found");
        }
        
        Supplier supplier = supplierRepository.findById(supplierId).get();
        
        barang.setSupplier(supplier);

        return barangRepository.save(barang);
    }

    @PutMapping(value = "/{barangId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Barang updateBarang(@PathVariable Long barangId, @Valid @RequestBody Barang barangRequest) {
        return barangRepository.findById(barangId).map(barang -> {
            barang.setNama(barangRequest.getNama());
            barang.setHargaBeli(barangRequest.getHargaBeli());
            barang.setHargaJual(barangRequest.getHargaJual());
            return barangRepository.save(barang);
        }).orElseThrow(() -> new ResourceNotFoundException("Barang " + barangId + " not found"));
    }

    @DeleteMapping("/{barangId}")
    public ResponseEntity<?> deleteBarang(@PathVariable Long barangId) {
        return barangRepository.findById(barangId).map(barang -> {
            barangRepository.delete(barang);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("BarangId " + barangId + " not found"));
    }
}
