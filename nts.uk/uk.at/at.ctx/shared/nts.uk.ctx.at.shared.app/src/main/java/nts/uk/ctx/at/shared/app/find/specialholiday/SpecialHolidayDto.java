package nts.uk.ctx.at.shared.app.find.specialholiday;

import java.util.List;

import lombok.Data;

@Data
public class SpecialHolidayDto {
	
	private String companyId;

	private int specialHolidayCode;

	private String specialHolidayName;

	private int grantPeriodicCls;

	private String memo;
	
	private List<String> workTypeList;
}
