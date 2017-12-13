/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.worktimeset.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.uk.ctx.at.shared.dom.common.timerounding.Rounding;
import nts.uk.ctx.at.shared.dom.common.timerounding.Unit;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDailyAtr;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeMethodSet;
import nts.uk.shr.infra.i18n.resource.I18NResourcesForUK;

/**
 * The Class WorkTimeSettingEnumDto.
 */
@Getter
@Setter
public class WorkTimeSettingEnumDto {
	
	/** The work time daily atr. */
	private List<EnumConstant> workTimeDailyAtr;

	/** The work time method set. */
	private List<EnumConstant> workTimeMethodSet;
	
	/** The rounding time. */
	private List<EnumConstant> roundingTime;
	
	/** The rounding. */
	private List<EnumConstant> rounding;
	
	/**
	 * Inits the.
	 *
	 * @param i18n the i 18 n
	 * @return the work time setting enum dto
	 */
	public static WorkTimeSettingEnumDto init(I18NResourcesForUK i18n) {
		WorkTimeSettingEnumDto dto = new WorkTimeSettingEnumDto();
		dto.setWorkTimeDailyAtr(EnumAdaptor.convertToValueNameList(WorkTimeDailyAtr.class, i18n));
		dto.setWorkTimeMethodSet(EnumAdaptor.convertToValueNameList(WorkTimeMethodSet.class, i18n));
		dto.setRoundingTime(EnumAdaptor.convertToValueNameList(Unit.class, i18n));
		dto.setRounding(EnumAdaptor.convertToValueNameList(Rounding.class, i18n));
		return dto;
		
	}

}
