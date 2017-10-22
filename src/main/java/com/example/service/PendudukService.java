package com.example.service;

import com.example.model.PendudukModel;

public interface PendudukService {
	PendudukModel selectPendudukByNIK(String nik);
	void tambahPenduduk (PendudukModel penduduk);
	int generateNIK(String nik);
	void updatePenduduk(PendudukModel Penduduk);
	int selectIdPendudukByNik(String nik);
	void updatePendudukNIK(String nik, int id);
}
