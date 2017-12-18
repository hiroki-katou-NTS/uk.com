package nts.uk.ctx.bs.employee.dom.holidaymanagement;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;

@Getter
@Setter
// 月間公休日数設定
public class PublicHolidayMonthSetting extends DomainObject{

	// 公休管理年
	private Year publicHdManagementYear;
	
	// 月度
	private Integer month;
	
	// 法定内休日日数
	private MonthlyNumberOfDays inLegalHoliday;
	
	// 法定外休日日数
	private MonthlyNumberOfDays outLegalHoliday;
	
}
