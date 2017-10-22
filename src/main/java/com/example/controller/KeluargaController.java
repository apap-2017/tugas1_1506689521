package com.example.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.KecamatanModel;
import com.example.model.KeluargaModel;
import com.example.model.KelurahanModel;
import com.example.model.KotaModel;
import com.example.model.PendudukModel;
import com.example.service.KeluargaService;
import com.example.service.LokasiService;
import com.example.service.PendudukService;

@Controller
public class KeluargaController {
	@Autowired
	PendudukService pendudukDAO;

	@Autowired
	KeluargaService keluargaDAO;

	@Autowired
	LokasiService lokasiDAO;

	@RequestMapping("/keluarga")
	public String selectKeluargaByNKK(@RequestParam(value = "nkk", required = false, defaultValue = "0") String nkk,
			Model model) {
		KeluargaModel keluarga = keluargaDAO.selectKeluargaByNKK(nkk);

		if (keluarga != null) {
			KelurahanModel kelurahan = lokasiDAO.selectKelurahanById(keluarga.getId_kelurahan());
			KecamatanModel kecamatan = lokasiDAO.selectKecamatanById(kelurahan.getId_kecamatan());
			KotaModel kota = lokasiDAO.selectKotaById(kecamatan.getId_kota());
			model.addAttribute("keluarga", keluarga);
			model.addAttribute("kelurahan", kelurahan);
			model.addAttribute("kecamatan", kecamatan);
			model.addAttribute("kota", kota);
			return "view-keluarga";
		} else {
			return "notfound";
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/keluarga/tambah")
	public String tambahKeluargaForm() { //nampilin form aja
		return "tambah-keluarga";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/keluarga/tambah")
	public String tambahKeluargaSubmit(@RequestParam(value = "alamat", required = false) String alamat,
			@RequestParam(value = "RT", required = false) String RT,
			@RequestParam(value = "RW", required = false) String RW,
			@RequestParam(value = "nama_kelurahan", required = false) String nama_kelurahan,
			@RequestParam(value = "nama_kecamatan", required = false) String nama_kecamatan,
			@RequestParam(value = "nama_kota", required = false) String nama_kota, Model model) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date tmpDate = new Date();
		String tmpDate2 = dateFormat.format(tmpDate);
		KelurahanModel kelurahan = lokasiDAO.selectKelurahanByNama(nama_kelurahan);
		String id_kelurahan = kelurahan.getId();
		KecamatanModel kecamatan = lokasiDAO.selectKecamatanById(kelurahan.getId_kecamatan());
		String kode_kecamatan = kecamatan.getKode_kecamatan().substring(0, kecamatan.getKode_kecamatan().length() - 1);
		Integer tahun = Integer.parseInt(tmpDate2.substring(2, 4));
		Integer bulan = Integer.parseInt(tmpDate2.substring(5, 7));
		Integer tanggal = Integer.parseInt(tmpDate2.substring(8, tmpDate2.length()));

		String tmpNKK = kode_kecamatan + tanggal + bulan + tahun;
		int count = keluargaDAO.generateNKK("%" + tmpNKK + "%");
		count += 1;
		String akhir = "" + count;
		int i = 4;
		while (i > akhir.length()) {
			akhir = "0" + akhir;
		}
		String nkk = kode_kecamatan + tanggal + bulan + tahun + akhir;
		KeluargaModel Newkeluarga = new KeluargaModel(nkk, alamat, RT, RW, id_kelurahan, 0, null);
		model.addAttribute("keluarga", Newkeluarga);
		return "success-add-keluarga";
	}
	
//	@RequestMapping(method = RequestMethod.GET, value = "/keluarga/ubah/{nkk}")
//    public String updateKeluargaForm(Model model, @PathVariable(value = "nkk") String nomor_kk){
//		if(keluargaDAO.selectKeluargaByNKK(nomor_kk != null){
//			
//    		model.addAttribute("keluarga", keluargaDAO.selectKeluargaByNKK(nomor_kk));
//    		return "update-keluarga";
//    	} else{
//    		return "notfound";	
//    	}
//	}
	
}
