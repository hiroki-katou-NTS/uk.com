package nts.uk.ctx.bs.employee.app.find.holidaysetting.configuration;

import lombok.Data;

@Data
public class HolidaySettingDto {
	private String companyID;
	
	private boolean isManage;

	public HolidaySettingDto(String companyID, boolean isManage) {
		super();
		this.companyID = companyID;
		this.isManage = isManage;
	}
}
