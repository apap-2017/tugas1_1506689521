package com.example.controller;

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
public class PendudukController {
	@Autowired
	PendudukService pendudukDAO;

	@Autowired
	KeluargaService keluargaDAO;

	@Autowired
	LokasiService lokasiDAO;

	@RequestMapping("/") //initial page
	public String index() {
		return "index";
	}

	@RequestMapping("/penduduk")
	public String selectPenduduk(@RequestParam(value = "nik", required = false, defaultValue = "0") String nik,
			Model model) {
		PendudukModel penduduk = pendudukDAO.selectPendudukByNIK(nik);

		if (penduduk != null) {
			String tanggalLahir = penduduk.getTanggal_lahir();
			String[] arrTglLahir = tanggalLahir.split("-");
			String bulan = arrTglLahir[1].toString();
			System.out.println(bulan);
			switch (bulan) {
			case "01":
				bulan = "Januari";
				break;
			case "02":
				bulan = "Februari"; 
				break;
			case "03":
				bulan = "Maret"; 
				break;
			case "04":
				bulan = "April"; 
				break;
			case "05":
				bulan = "Mei"; 
				break;
			case "06":
				bulan = "Juni";
				break;
			case "07": 
				bulan = "Juli"; 
				break;
			case "08":
				bulan = "Agustus"; 
				break;
			case "09":
				bulan = "September"; 
				break;
			case "10":
				bulan = "Oktober";
				break;
			case "11":
				bulan = "November"; 
				break;
			case "12":
				bulan = "Desember"; 
				break;
			default:
				break;
			}
			tanggalLahir = arrTglLahir[2] + " " + bulan + " " + arrTglLahir[0];
			penduduk.setTanggal_lahir(tanggalLahir);

			KeluargaModel keluarga = keluargaDAO.selectKeluargaByID(penduduk.getId_keluarga());
			KelurahanModel kelurahan = lokasiDAO.selectKelurahanById(keluarga.getId_kelurahan());
			KecamatanModel kecamatan = lokasiDAO.selectKecamatanById(kelurahan.getId_kecamatan());
			KotaModel kota = lokasiDAO.selectKotaById(kecamatan.getId_kota());

			model.addAttribute("penduduk", penduduk);
			model.addAttribute("keluarga", keluarga);
			model.addAttribute("kelurahan", kelurahan);
			model.addAttribute("kecamatan", kecamatan);
			model.addAttribute("kota", kota);

			if (penduduk.getIs_wni() == 1) {
				model.addAttribute("kewarganegaraan", "WNI");

			} else {
				model.addAttribute("kewarganegaraan", "WNA");
			}

			if (penduduk.getIs_wafat() == 0) {
				model.addAttribute("wafat", "Hidup");

			} else {
				model.addAttribute("wafat", "Wafat");
			}
			return "view-penduduk";
		} else {
			model.addAttribute("nik", "nik");
			return "notfound";
		}
	}


	@RequestMapping(method = RequestMethod.GET, value = "/penduduk/tambah")
	public String tambahPendudukForm() { //nampilin form nya aja
		return "tambah-penduduk";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/penduduk/tambah")
	public String tambahPendudukSubmit(@RequestParam(value = "nama", required = false) String nama,
			@RequestParam(value = "tempat_lahir", required = false) String tempat_lahir,
			@RequestParam(value = "tanggal_lahir", required = false) String tanggal_lahir,
			@RequestParam(value = "golongan_darah", required = false) String golongan_darah,
			@RequestParam(value = "jenis_kelamin", required = false) int jenis_kelamin,
			@RequestParam(value = "agama", required = false) String agama,
			@RequestParam(value = "status_perkawinan", required = false) String status_perkawinan,
			@RequestParam(value = "pekerjaan", required = false) String pekerjaan,
			@RequestParam(value = "is_wni", required = false) int is_wni,
			@RequestParam(value = "is_wafat", required = false) int is_wafat,
			@RequestParam(value = "id_keluarga", required = false) String id_keluarga,
			@RequestParam(value = "status_dalam_keluarga", required = false) String status_dalam_keluarga,
			Model model) {
		KeluargaModel keluarga = keluargaDAO.selectKeluargaByID(id_keluarga);
		KelurahanModel kelurahan = lokasiDAO.selectKelurahanById(keluarga.getId_kelurahan());
		KecamatanModel kecamatan = lokasiDAO.selectKecamatanById(kelurahan.getId_kecamatan());
		String kode_kecamatan = kecamatan.getKode_kecamatan().substring(0, kecamatan.getKode_kecamatan().length() - 1);
		String tahun = tanggal_lahir.substring(2, 4);
		String bulan = tanggal_lahir.substring(5, 7);

		String tanggal = tanggal_lahir.substring(8, tanggal_lahir.length());
		
		int angkaBulan = Integer.parseInt(bulan);
		System.out.println(angkaBulan);

		if (jenis_kelamin == 1) {

			Integer tglWanita = Integer.parseInt(tanggal) + 40; //untuk wanita
			tanggal = "" + tglWanita;
		}
		
		String tmpNik = kode_kecamatan + tanggal + bulan + tahun;
		int count = pendudukDAO.generateNIK("%" + tmpNik + "%");
		count += 1;
		String akhir = "" + count;
		int i = 4;
		while (i > akhir.length()) {
			akhir = "0" + akhir;
		}
		String nik = kode_kecamatan + tanggal + bulan + tahun + akhir;
		PendudukModel Newpenduduk = new PendudukModel(nik, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, is_wni,
				id_keluarga, agama, pekerjaan, status_perkawinan, status_dalam_keluarga, golongan_darah, is_wafat);
		pendudukDAO.tambahPenduduk(Newpenduduk);
		
		if(nama.equals("")||tempat_lahir.equals("")||(angkaBulan>12||angkaBulan<=0)||agama.equals("")||status_perkawinan.equals("")||pekerjaan.equals("")||status_dalam_keluarga.equals("")||id_keluarga.equals("")) {
			return "error";
		}else {
		model.addAttribute("penduduk", Newpenduduk);
		return "success-add";
		}
	}
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/penduduk/ubah/{nik}")
    public String updatePendudukForm(Model model, @PathVariable(value = "nik") String nik){
		if(pendudukDAO.selectPendudukByNIK(nik) != null){
			
    		model.addAttribute("penduduk", pendudukDAO.selectPendudukByNIK(nik));
    		return "update-penduduk";
    	} else{
    		return "notfound";
    	
    	}

	}

	@RequestMapping("/penduduk/ubah/submit")
	public String updatePenduduk(Model model, PendudukModel penduduk,
			@RequestParam(value = "id_keluarga_lama", required = false) String id_keluarga_lama,
			@RequestParam(value = "jenis_kelamin_lama", required = false) int jenis_kelamin_lama,
			@RequestParam(value = "tanggal_lahir_lama", required = false) String tanggal_lahir_lama) {

		if (!penduduk.getId_keluarga().equalsIgnoreCase(id_keluarga_lama)
				|| !penduduk.getTanggal_lahir().equalsIgnoreCase(tanggal_lahir_lama)
				|| jenis_kelamin_lama != penduduk.getJenis_kelamin()) {
			String newNIK = "";
			KeluargaModel keluarga = keluargaDAO.selectKeluargaByID(penduduk.getId_keluarga());
			KelurahanModel kelurahan = lokasiDAO.selectKelurahanById(keluarga.getId_kelurahan());
			KecamatanModel kecamatan = lokasiDAO.selectKecamatanById(kelurahan.getId_kecamatan());
			String kode_kecamatan = kecamatan.getKode_kecamatan().substring(0,
					kecamatan.getKode_kecamatan().length() - 1);
			String tanggal = penduduk.getTanggal_lahir().substring(penduduk.getTanggal_lahir().length() - 2,
					penduduk.getTanggal_lahir().length());
			String tahun = penduduk.getTanggal_lahir().substring(2, 4);
			String bulan = penduduk.getTanggal_lahir().substring(5, 7);
			String tanggal_lahir = "";
			if (penduduk.getJenis_kelamin() == 0) {
				tanggal_lahir = tanggal + bulan + tahun;
				
			} else {
				Integer tgl = Integer.parseInt(tanggal) + 40;
				tanggal_lahir = "" + tanggal + bulan + tahun;
			}
			
			newNIK = kode_kecamatan + tanggal_lahir;
			int count = pendudukDAO.generateNIK("%" + newNIK + "%");
			count += 1;
			String akhir = "" + count;
			int i = 4;
			while (i > akhir.length()) {
				akhir = "0" + akhir;
			}
			newNIK += akhir;
			int id = pendudukDAO.selectIdPendudukByNik(penduduk.getNik());
			model.addAttribute("nik", penduduk.getNik());
			pendudukDAO.updatePenduduk(penduduk);
			pendudukDAO.updatePendudukNIK(newNIK, id);
			return "success-update-penduduk";
		} else {
			model.addAttribute("nik", penduduk.getNik());
			pendudukDAO.updatePenduduk(penduduk);
			return "success-update-penduduk";
		}
	}
}
