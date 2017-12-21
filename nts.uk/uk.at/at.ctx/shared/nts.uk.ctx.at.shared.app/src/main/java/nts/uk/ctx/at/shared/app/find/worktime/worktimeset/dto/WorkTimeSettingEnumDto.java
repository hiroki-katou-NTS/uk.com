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
import nts.uk.ctx.at.shared.dom.worktime.common.CompensatoryOccurrenceDivision;
import nts.uk.ctx.at.shared.dom.worktime.common.GoOutTimeRoundingMethod;
import nts.uk.ctx.at.shared.dom.worktime.common.LateEarlyAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkSystemAtr;
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
	
	/** The rounding break timezone. */
	private List<EnumConstant> roundingBreakTimezone;
	
	/** The rounding break time. */
	private List<EnumConstant> roundingBreakTime;
	
	/** The rounding time. */
	private List<EnumConstant> roundingTime;
	
	/** The rounding. */
	private List<EnumConstant> rounding;
	
	/** The rounding simple. */
	private List<EnumConstant> roundingSimple;
	
	/** The lst late early atr. */
	private List<EnumConstant> lstLateEarlyAtr;
	
	/** The work system atr. */
	private List<EnumConstant> workSystemAtr;
	
	/** The compensatory occurrence division. */
	private List<EnumConstant> compensatoryOccurrenceDivision;
	
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
		//TODO confirm duplicate enum
		dto.setRoundingBreakTimezone(EnumAdaptor.convertToValueNameList(GoOutTimeRoundingMethod.class, i18n));
		dto.setRoundingBreakTime(EnumAdaptor.convertToValueNameList(GoOutTimeRoundingMethod.class, i18n));
		//TODO confirm duplicate enum
		dto.setRoundingTime(EnumAdaptor.convertToValueNameList(Unit.class, i18n));
		dto.setRounding(EnumAdaptor.convertToValueNameList(Rounding.class, i18n));
		List<EnumConstant> roundingSimple = EnumAdaptor.convertToValueNameList(Rounding.class, i18n);
		roundingSimple.removeIf(enumConstant -> enumConstant.getValue() == Rounding.ROUNDING_DOWN_OVER.value);
		dto.setRoundingSimple(roundingSimple);
		dto.setLstLateEarlyAtr(EnumAdaptor.convertToValueNameList(LateEarlyAtr.class, i18n));
		dto.setWorkSystemAtr(EnumAdaptor.convertToValueNameList(WorkSystemAtr.class, i18n));
		dto.setCompensatoryOccurrenceDivision(EnumAdaptor.convertToValueNameList(CompensatoryOccurrenceDivision.class, i18n));
		return dto;
		
	}

}
