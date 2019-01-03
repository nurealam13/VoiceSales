package alam.dbz.voicesales.models;

public class UnitType {
	private String unitTypeId;
	private String code;
	private String name;
	private String companyId;
	private String dateOfEntry;
	private String entryby;

	public UnitType(String unitTypeId, String code, String name,
                    String companyId, String dateOfEntry, String entryby) {
		super();
		this.unitTypeId = unitTypeId;
		this.code = code;
		this.name = name;
		this.companyId = companyId;
		this.dateOfEntry = dateOfEntry;
		this.entryby = entryby;
	}

	public String getUnitTypeId() {
		return unitTypeId;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public String getCompanyId() {
		return companyId;
	}

	public String getDateOfEntry() {
		return dateOfEntry;
	}

	public String getEntryby() {
		return entryby;
	}

}
