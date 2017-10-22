package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.example.dao.LokasiMapper;
import com.example.model.KecamatanModel;
import com.example.model.KelurahanModel;
import com.example.model.KotaModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LokasiServiceDB implements LokasiService{
	@Autowired
	private LokasiMapper lokasiMapper;
	
	@Override
	public KelurahanModel selectKelurahanById(String id_kelurahan) {
		log.info("select kelurahan by id ", id_kelurahan);
		return lokasiMapper.selectKelurahanById(id_kelurahan);
	}

	@Override
	public KecamatanModel selectKecamatanById(String id_kecamatan) {
		log.info("select kecamatan by id ", id_kecamatan);
		return lokasiMapper.selectKecamatanById(id_kecamatan);
	}

	@Override
	public KotaModel selectKotaById(String id_kota) {
		log.info("select kota by id ", id_kota);
		return lokasiMapper.selectKotaById(id_kota);
	}

	@Override
	public KelurahanModel selectKelurahanByNama(String nama_kelurahan) {
		log.info("select kelurahan by name", nama_kelurahan);
		return lokasiMapper.selectKelurahanByName(nama_kelurahan);
	}

}
