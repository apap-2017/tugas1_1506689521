package com.example.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KelurahanModel {
	private String id;
	private String kode_kelurahan;
	private String id_kecamatan;
	private String nama_kelurahan;
	private String kode_pos;
}
