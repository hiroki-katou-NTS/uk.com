package nts.uk.shr.pereg.app.find;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@Getter
@Setter
@NoArgsConstructor
public class PeregQuery {


	private String categoryId;

	private String categoryCode;

	private String employeeId;

	private String personId;

	private GeneralDate standardDate;

	private String infoId;
	
	private int ctgType;

	// layout case
	public PeregQuery(String categoryCode, String employeeId, String personId, GeneralDate standardDate) {
		this.categoryCode = categoryCode;
		this.employeeId = employeeId;
		this.personId = personId;
		this.standardDate = standardDate;
	}
	
	// category case
	public PeregQuery(String infoId, String categoryCode, String employeeId, String personId) {
		this.infoId = infoId;
		this.categoryCode = categoryCode;
		this.employeeId = employeeId;
		this.personId = personId;
	}
	
}
