/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kurasi.dataPembelian.Repository;

import com.kurasi.dataPembelian.Model.Barang;
import com.kurasi.dataPembelian.Model.Supplier;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author 300
 */
@Repository
public interface BarangRepository extends JpaRepository<Barang, Long> {

    Page<Barang> findBySupplier_Id(Long supplierId, Pageable pageable);

    Optional<Barang> findByIdAndSupplier_Id(Long id, Long supplierId);
    
    
   
}
