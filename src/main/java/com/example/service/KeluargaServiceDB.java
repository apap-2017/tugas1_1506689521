package com.example.service;

import com.example.model.KeluargaModel;
import com.example.model.PendudukModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.KeluargaMapper;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KeluargaServiceDB implements KeluargaService{

	@Autowired
	private KeluargaMapper keluargaMapper;
	
	@Override
	public KeluargaModel selectKeluargaByID(String id_keluarga) {
		log.info ("select keluarga with ID ", id_keluarga);
		return keluargaMapper.selectKeluargaByID(id_keluarga);
	}
	
	@Override
	public KeluargaModel selectKeluargaByNKK(String nkk) {
		log.info ("select keluarga with ", nkk);
		return keluargaMapper.selectKeluargaByNKK(nkk);
	}
	
	@Override
	public void tambahKeluarga(KeluargaModel keluarga) {
		keluargaMapper.tambahKeluarga(keluarga);
	}

	@Override
	public int generateNKK(String nomor_kk) {
		log.info("generate keluarga dengan", nomor_kk);
		return keluargaMapper.generateNKK(nomor_kk);
	}

	@Override
	public void updateKeluarga(KeluargaModel keluarga) {
		keluargaMapper.updateKeluarga(keluarga);
	}
}
