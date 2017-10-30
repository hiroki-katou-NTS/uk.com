package nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.setting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 公休設定
 * 
 * @author sonnh1
 *
 */
@Getter
@AllArgsConstructor
public class PublicHolidaySetting extends AggregateRoot {
	private String companyId;
	private boolean publicHolidayManagement;
	private PublicHolidayManagementClassification publicHolidayManagementCls;
	private int transferPublicHolidayMinus;
	private PublicHolidayCarryOverDeadline carryOverDeadline;
}
