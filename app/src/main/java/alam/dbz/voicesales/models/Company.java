package alam.dbz.voicesales.models;

public class Company {
	private String companyID;
	private String companyName;
	private String locationID;
	private String locationName;
	private String currDate;

	public Company(String companyID, String companyName, String locationID,
                   String locationName, String currDate) {
		super();
		this.companyID = companyID;
		this.companyName = companyName;
		this.locationID = locationID;
		this.locationName = locationName;
		this.currDate = currDate;
	}

	public Company(String locationID, String locationName) {
		super();
		this.locationID = locationID;
		this.locationName = locationName;
	}

	public Company(String companyID, String companyName, String currDate) {
		super();
		this.companyID = companyID;
		this.companyName = companyName;
		this.currDate = currDate;
	}

	public String getCompanyID() {
		return companyID;
	}

	public String getCompanyName() {
		return companyName;
	}

	public String getLocationID() {
		return locationID;
	}

	public String getLocationName() {
		return locationName;
	}

	public String getCurrDate() {
		return currDate;
	}
}
