package model.vo;

import java.io.Serializable;

public class Hospital implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private static final String VACCINENAME = "화이자";
	private static final String HOSPITALNAME = "자바병원";
	private static final String HOSPITALADDRESS = "서울시 딩동구 딩동동";
	
	private int vaccineCount = 3;
	
	public Hospital() {
		super();
	}

	public Hospital(int vaccineCount) {
		super();
		this.vaccineCount = vaccineCount;
	}

	public static String getVaccinename() {
		return VACCINENAME;
	}

	public static String getHospitalname() {
		return HOSPITALNAME;
	}

	public static String getHospitaladdress() {
		return HOSPITALADDRESS;
	}

	public int getVaccineCount() {
		return vaccineCount;
	}

	public void setVaccineCount(int vaccineCount) {
		this.vaccineCount = vaccineCount;
	}

	@Override
	public String toString() {
		return "백신명: " + VACCINENAME + " | " + "병원명: " + HOSPITALNAME + " | "+ "병원 주소: " + HOSPITALADDRESS + " | " + "백신 잔여 수량: " + this.vaccineCount;
	}
	
	
}
