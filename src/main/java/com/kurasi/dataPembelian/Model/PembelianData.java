/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kurasi.dataPembelian.Model;

import java.util.List;

/**
 *
 * @author 300
 */
public class PembelianData {
     private List<Pembelian> pembelian;
    private Boolean hasPrevious;
    private Boolean hasNext;
    private Integer numberOfElements;

    public List<Pembelian> getPembelian() {
        return pembelian;
    }

    public void setPembelian(List<Pembelian> pembelian) {
        this.pembelian = pembelian;
    }


    public Boolean getHasPrevious() {
        return hasPrevious;
    }

    public void setHasPrevious(Boolean hasPrevious) {
        this.hasPrevious = hasPrevious;
    }

    public Boolean getHasNext() {
        return hasNext;
    }

    public void setHasNext(Boolean hasNext) {
        this.hasNext = hasNext;
    }

    public Integer getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(Integer numberOfElements) {
        this.numberOfElements = numberOfElements;
    }
    
}
