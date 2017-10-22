package com.example.dao;


import org.apache.ibatis.annotations.*;
import com.example.model.PendudukModel;

@Mapper
public interface PendudukMapper {
	
	@Select("select * from penduduk where nik = #{nik}")
	@Results(value = {
    		@Result(property="nik", column="nik"),
    		@Result(property="nama", column="nama"), 
    		@Result(property="tempat_lahir", column="tempat_lahir"), 
    		@Result(property="tanggal_lahir", column="tanggal_lahir"),
    		@Result(property="is_wni", column="is_wni"),
    		@Result(property="agama", column="agama"),
    		@Result(property="pekerjaan", column="pekerjaan"),
    		@Result(property="status_perkawinan", column="status_perkawinan"),
    		@Result(property="golongan_darah", column="golongan_darah"),
    		@Result(property="is_wafat", column="is_wafat"),
    		@Result(property="id_keluarga", column="id_keluarga"),
    		@Result(property="tanggal_lahir", column="tanggal_lahir")
    		
    })
    PendudukModel selectPendudukByNIK (@Param("nik") String nik);
	
	//tambah penduduk untuk fitur 3
	@Select("select count(*) from penduduk where nik like #{nik}")
	int generateNIK(String nik);
	
	@Insert("INSERT INTO penduduk (nik, nama, tempat_lahir, tanggal_lahir, "
			+ "jenis_kelamin, is_wni, id_keluarga, "
			+ "agama, pekerjaan, status_perkawinan,"
			+ "status_dalam_keluarga, golongan_darah, is_wafat)"
			+ " VALUES (#{nik}, #{nama}, #{tempat_lahir}, #{tanggal_lahir}, "
			+ "#{jenis_kelamin}, #{is_wni}, #{id_keluarga}, "
			+ "#{agama}, #{pekerjaan}, #{status_perkawinan}, "
			+ "#{status_dalam_keluarga}, #{golongan_darah},  #{is_wafat})")
	void tambahPenduduk(PendudukModel penduduk);
	
	
	//update penduduk untuk fitur 5
	@Update("UPDATE penduduk SET nama = #{nama}, tempat_lahir = #{tempat_lahir}, tanggal_lahir = #{tanggal_lahir}, jenis_kelamin = #{jenis_kelamin}, "
			+ "is_wni = #{is_wni}, id_keluarga = #{id_keluarga}, agama = #{agama}, pekerjaan = #{pekerjaan}, status_perkawinan = #{status_perkawinan}, "
			+ "status_dalam_keluarga = #{status_dalam_keluarga}, golongan_darah = #{golongan_darah}, is_wafat = #{is_wafat} WHERE nik = #{nik}")
	void updatePenduduk(PendudukModel penduduk);
	
	@Select("select id from penduduk where nik = #{nik}")
	int selectIdPendudukByNik(String nik);

	@Update("UPDATE penduduk SET nik = #{nik} where id = #{id}")
	void updatePendudukNIK(@Param("nik") String nik,@Param("id") int id);
}
