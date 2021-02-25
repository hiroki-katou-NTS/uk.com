/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.ot.autocalsetting.wkp;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.command.ot.autocalsetting.AutoCalRaisingSalarySettingDto;
import nts.uk.ctx.at.shared.app.command.ot.autocalsetting.AutoCalcOfLeaveEarlySettingDto;
import nts.uk.ctx.at.shared.dom.calculationattribute.AutoCalcSetOfDivergenceTime;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalFlexOvertimeSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalOvertimeSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalRestTimeSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalcOfLeaveEarlySetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.wkp.WkpAutoCalSettingSetMemento;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.AutoCalRaisingSalarySetting;

/**
 * The Class WkpAutoCalSettingDto.
 */

@Getter
@Setter
public class WkpAutoCalSettingDto implements WkpAutoCalSettingSetMemento{
	
	/** The wkp id. */
	private String wkpId;

	/** The normal OT time. */
	// 残業時間
	private AutoCalOvertimeSettingDto normalOTTime;

	/** The flex OT time. */
	// フレックス超過時間
	private AutoCalFlexOvertimeSettingDto flexOTTime;

	/** The rest time. */
	// 休出時間
	private AutoCalRestTimeSettingDto restTime;

	/** The leave early. */
	// 遅刻早退
	private AutoCalcOfLeaveEarlySettingDto leaveEarly;

	/** The raising salary. */
	// 加給
	private AutoCalRaisingSalarySettingDto raisingSalary;

	/** The set of divergence time. */
	// 乖離時間
	private Integer divergenceTime;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.ComAutoCalSettingSetMemento#setCompanyId(nts.uk.ctx.at.shared.dom.common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		// do nothing
		
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.WkpAutoCalSettingSetMemento#setWkpId(nts.uk.ctx.at.shared.dom.common.WorkplaceId)
	 */
	@Override
	public void setWkpId(WorkplaceId workplaceId) {
		this.wkpId = workplaceId.v();
		
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.ComAutoCalSettingSetMemento#setNormalOTTime(nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalOvertimeSetting)
	 */
	@Override
	public void setNormalOTTime(AutoCalOvertimeSetting normalOTTime) {
		AutoCalOvertimeSettingDto dto = new AutoCalOvertimeSettingDto();
		normalOTTime.saveToMemento(dto);
		this.normalOTTime = dto;
		
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.ComAutoCalSettingSetMemento#setFlexOTTime(nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalFlexOvertimeSetting)
	 */
	@Override
	public void setFlexOTTime(AutoCalFlexOvertimeSetting flexOTTime) {
		AutoCalFlexOvertimeSettingDto dto = new AutoCalFlexOvertimeSettingDto();
		flexOTTime.saveToMemento(dto);
		this.flexOTTime = dto;
		
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.ComAutoCalSettingSetMemento#setRestTime(nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalRestTimeSetting)
	 */
	@Override
	public void setRestTime(AutoCalRestTimeSetting restTime) {
		AutoCalRestTimeSettingDto dto = new AutoCalRestTimeSettingDto();
		restTime.saveToMemento(dto);
		this.restTime = dto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.ot.autocalsetting.com.
	 * ComAutoCalSettingSetMemento#setLeaveEarly(nts.uk.ctx.at.shared.dom.ot.
	 * autocalsetting.AutoCalcOfLeaveEarlySetting)
	 */
	@Override
	public void setLeaveEarly(AutoCalcOfLeaveEarlySetting leaveEarly) {
		AutoCalcOfLeaveEarlySettingDto dto = new AutoCalcOfLeaveEarlySettingDto();
		dto.setLate(leaveEarly.isLate());
		dto.setLeaveEarly(leaveEarly.isLeaveEarly());
		this.leaveEarly = dto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.ot.autocalsetting.com.
	 * ComAutoCalSettingSetMemento#setRaisingSalary(nts.uk.ctx.at.shared.dom.
	 * workrule.outsideworktime.AutoCalRaisingSalarySetting)
	 */
	@Override
	public void setRaisingSalary(AutoCalRaisingSalarySetting raisingSalary) {
		AutoCalRaisingSalarySettingDto dto = new AutoCalRaisingSalarySettingDto();
		dto.setRaisingSalaryCalcAtr(raisingSalary.isRaisingSalaryCalcAtr());
		dto.setSpecificRaisingSalaryCalcAtr(raisingSalary.isSpecificRaisingSalaryCalcAtr());
		this.raisingSalary = dto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.ot.autocalsetting.com.
	 * ComAutoCalSettingSetMemento#setDivergenceTime(nts.uk.ctx.at.shared.dom.
	 * calculationattribute.AutoCalcSetOfDivergenceTime)
	 */
	@Override
	public void setDivergenceTime(AutoCalcSetOfDivergenceTime divergenceTime) {
		this.divergenceTime = divergenceTime.getDivergenceTime().value;
	}
}
