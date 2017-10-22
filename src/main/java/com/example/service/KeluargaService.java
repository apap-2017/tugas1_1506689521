package com.example.service;

import com.example.model.KeluargaModel;
import com.example.model.PendudukModel;


public interface KeluargaService {
	KeluargaModel selectKeluargaByID(String id_keluarga);
	KeluargaModel selectKeluargaByNKK(String nkk);
	void tambahKeluarga(KeluargaModel keluarga);
	int generateNKK(String nomor_kk);
	void updateKeluarga(KeluargaModel Keluarga);
}
