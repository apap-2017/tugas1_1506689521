package com.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.*;
import com.example.model.KeluargaModel;
import com.example.model.PendudukModel;

@Mapper
public interface KeluargaMapper {

	//select penduduk by id untuk fitur 1
	@Select("select * from keluarga where id = #{id_keluarga}")
	@Results(value = {
			@Result(property="nomor_kk", column="nomor_kk"), 
			@Result(property="alamat", column="alamat"), 
			@Result(property="RT", column="RT"),
			@Result(property="RW", column="RW"),
			@Result(property="id_kelurahan", column="id_kelurahan"),
			@Result(property="is_tidak_berlaku", column="is_tidak_berlaku")

	})
	KeluargaModel selectKeluargaByID(String id_keluarga);

	//select keluarga by nkk untuk fitur 2
	@Select("select * from keluarga where nomor_kk = #{nkk}")
	@Results(value = {

			@Result(property="nomor_kk", column="nomor_kk"), 
			@Result(property="alamat", column="alamat"), 
			@Result(property="RT", column="RT"),
			@Result(property="RW", column="RW"),
			@Result(property="id_kelurahan", column="id_kelurahan"),
			@Result(property="is_tidak_berlaku", column="is_tidak_berlaku"),
			@Result(property="pendudukKeluarga",column="id", 
			javaType = List.class, 
			many=@Many(select="selectPenduduk"))

	})
	KeluargaModel selectKeluargaByNKK(@Param("nkk") String nkk);

	@Select("select nik, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, "
			+ "is_wni, id_keluarga, agama, pekerjaan, status_perkawinan, "
			+ "status_dalam_keluarga, golongan_darah, is_wafat from penduduk where id_keluarga = #{id}")
	List<PendudukModel> selectPenduduk (@Param("id") String id);

	//tambah keluarga untuk fitur 4
	@Insert("insert into penduduk(nomor_kk, alamat, RT, RW, id_kelurahan "
			+ "values(#{nomor_kk}, #{alamat}, #{RT}, #{RW}, #{id_kelurahan}))")
	void tambahKeluarga(KeluargaModel keluarga);

	@Select("select count(*) from keluarga where nomor_kk like #{nomor_kk}")
	int generateNKK(String nomor_kk);


	//update keluarga untuk fitur 6
	@Update("UPDATE keluarga SET nomor_kk = #{nkk}, alamat = #{alamat}, rt = #{rt}, rw = #{rw}, "
			+ "id_kelurahan = #{id_kelurahan}, is_tidak_berlaku = #{is_tidak_berlaku} WHERE id = #{id}")
	void updateKeluarga(KeluargaModel keluarga);
}
