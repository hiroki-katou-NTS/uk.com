package nts.uk.ctx.at.function.app.find.attendancerecord.export.setting;


import lombok.Data;

@Data
public class ItemSelectedTypeSettingDto {

	private int selectionType;
	
	private String companyId;
	
	private String employeeId;
	
	private String selectedOutputLayoutId;
	
	private String selectedCode;
}
