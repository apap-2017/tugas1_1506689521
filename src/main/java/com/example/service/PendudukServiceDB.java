package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.PendudukMapper;
import com.example.model.KecamatanModel;
import com.example.model.KeluargaModel;
import com.example.model.KelurahanModel;
import com.example.model.KotaModel;
import com.example.model.PendudukModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PendudukServiceDB implements PendudukService{
	@Autowired
	private PendudukMapper pendudukMapper;
	
	@Override
	public PendudukModel selectPendudukByNIK(String nik) {
		log.info ("select penduduk with NIK", nik);
		return pendudukMapper.selectPendudukByNIK (nik);
	}
	
	@Override
	public void tambahPenduduk(PendudukModel penduduk) {
		pendudukMapper.tambahPenduduk(penduduk);
	}

	@Override
	public int generateNIK(String nik) {
		log.info("generate penduduk dengan", nik);
		return pendudukMapper.generateNIK(nik);
	}
	
	@Override
	public void updatePenduduk(PendudukModel penduduk) {
		pendudukMapper.updatePenduduk(penduduk);
	}

	@Override
	public int selectIdPendudukByNik(String nik) {
		log.info ("select id penduduk with nik {}", nik);
		return pendudukMapper.selectIdPendudukByNik(nik);
	}

	@Override
	public void updatePendudukNIK(String nik, int id) {
		pendudukMapper.updatePendudukNIK(nik,id);
		
	}
}
