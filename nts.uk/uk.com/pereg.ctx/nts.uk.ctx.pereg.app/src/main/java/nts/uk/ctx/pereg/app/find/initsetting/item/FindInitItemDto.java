package nts.uk.ctx.pereg.app.find.initsetting.item;

import lombok.Getter;
import lombok.Value;
import nts.arc.time.GeneralDate;

@Value
@Getter
public class FindInitItemDto {

	private String initSettingId;

	private GeneralDate baseDate;

	private String categoryCd;

	private String employeeName;

	private String employeeCode;

	private GeneralDate hireDate;
}
