package com.techathon2014.paypermeter;

import com.ibm.mobile.services.data.IBMDataObject;
import com.ibm.mobile.services.data.IBMDataObjectSpecialization;

@IBMDataObjectSpecialization("TripDetails")
public class TripDetails extends IBMDataObject {
	public final String CLASS_NAME = "TripDetails";
	private static final String driverLic = "driverLic";
	private static final String regNo= "regNo";
	private static final String polSN= "policeSL";
	private static final String badge= "badge";
	
	/**
	 * @return the driverlic
	 */
	public static String getDriverlic() {
		return driverLic;
	}
	
	public void setDriverlic(String lic) {
		setObject(driverLic, ((lic != null) ? lic : ""));
	}
	/**
	 * @return the regno
	 */
	public static String getRegno() {
		return regNo;
	}
	
	public void setRegNo(String reg) {
		setObject(regNo, ((reg != null) ? reg : ""));
	}
	/**
	 * @return the polsn
	 */
	public static String getPolsn() {
		return polSN;
	}
	public void setPolsn(String pol) {
		setObject(polSN, ((pol != null) ? pol : ""));
	}
	/**
	 * @return the badge
	 */
	public static String getBadge() {
		return badge;
	}
	public void setBadge(String bad) {
		setObject(badge, ((bad != null) ? bad : ""));
	}
}