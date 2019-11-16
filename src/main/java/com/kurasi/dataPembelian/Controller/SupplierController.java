/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kurasi.dataPembelian.Controller;

import com.kurasi.dataPembelian.Model.Barang;
import com.kurasi.dataPembelian.Model.Pembelian;
import com.kurasi.dataPembelian.Model.PembelianData;
import com.kurasi.dataPembelian.Model.Supplier;
import com.kurasi.dataPembelian.Model.SupplierData;
import com.kurasi.dataPembelian.Repository.BarangRepository;
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
 * @author 300
 */
@RestController
@RequestMapping("/suppliers")
public class SupplierController {

    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private BarangRepository barangRepository;
    
    @GetMapping("/all/{page}/{limit}")
    public SupplierData getAllSupplierData(@PathVariable (value = "page") int page, 
                                                @PathVariable (value = "limit") int limit) {
        
        Pageable pageable = PageRequest.of(page, limit);
        Page<Supplier> data = supplierRepository.findAll(pageable);
        
        SupplierData dataResponse = new SupplierData();
        dataResponse.setSuppliers(data.getContent());
        dataResponse.setHasNext(data.hasNext());
        dataResponse.setHasPrevious(data.hasPrevious());
        dataResponse.setNumberOfElements(data.getNumberOfElements());
        
        return dataResponse;
    }

    @GetMapping("/barang/{barangId}")
    public Supplier getSupplierDataByBarangId(@PathVariable(value = "barangId") Long barangId) {

        Optional<Barang> barangOp = barangRepository.findById(barangId);

        if (!barangOp.isPresent()) {
            throw new ResourceNotFoundException("Barang Id " + barangId + " not found");
        }

        return barangOp.get().getSupplier();
    }

    @PostMapping("/")
    public Supplier createSupplier(@Valid @RequestBody Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    @PutMapping("/{supplierId}")
    public Supplier updateSupplier(
            @PathVariable(value = "supplierId") Long supplierId,
            @Valid @RequestBody Supplier supplierRequest) {
        if (!supplierRepository.existsById(supplierId)) {
            throw new ResourceNotFoundException("Supplier Id " + supplierId + " not found");
        }

        return supplierRepository.findById(supplierId).map(supplier -> {
            supplier.setNamaSupplier(supplierRequest.getNamaSupplier());
            supplier.setNoHp(supplierRequest.getNoHp());
            return supplierRepository.save(supplier);
        }).orElseThrow(() -> new ResourceNotFoundException("SupplierId " + supplierId + "not found"));
    }

    @DeleteMapping("/{supplierId}")
    public ResponseEntity<?> deleteSupplier(
            @PathVariable(value = "supplierId") Long supplierId) {
        return supplierRepository.findById(supplierId).map(supplier -> {
            supplierRepository.delete(supplier);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id " + supplierId));
    }
}
