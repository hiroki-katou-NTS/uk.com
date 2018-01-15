package nts.uk.ctx.pereg.app.find.layout;

import lombok.Getter;
import lombok.Value;
import nts.arc.time.GeneralDate;

@Value
@Getter
public class GetLayoutByCreateTypeDto {

	private int createType;

	private String initSettingId;

	private GeneralDate baseDate;

	private String copyEmployeeId;

	private String employeeName;

	private String employeeCode;

	private GeneralDate hireDate;

}
