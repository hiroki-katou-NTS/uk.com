package nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SWkpHistImport;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveEmSetting;

/**
 * @author hiep.ld
 *
 */
@AllArgsConstructor
@Getter
public class SubstituteManagementOutput {
	SWkpHistImport sWkpHistImport;
	ExtraHolidayManagementOutput extraHolidayManagementOutput; 
	CompensatoryLeaveEmSetting compensatoryLeaveEmSetting;
	CompensatoryLeaveComSetting compensatoryLeaveComSetting;
	
}
