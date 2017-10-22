package com.example.service;

import com.example.model.KecamatanModel;

import com.example.model.KelurahanModel;
import com.example.model.KotaModel;

public interface LokasiService {
	
	KelurahanModel selectKelurahanById(String id_kelurahan);
	KecamatanModel selectKecamatanById(String id_kecamatan);
	KotaModel selectKotaById(String id_kota);
	KelurahanModel selectKelurahanByNama(String nama_kelurahan);
	
}
