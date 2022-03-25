/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeVacationDigestUnit;
import nts.uk.ctx.at.shared.dom.worktime.common.OneDayTime;
import nts.uk.ctx.at.shared.dom.worktime.common.SubHolTransferSet;
import nts.uk.ctx.at.shared.dom.worktime.common.SubHolTransferSetAtr;

/**
 * 代休管理設定
 * The Class CompensatoryLeaveComSetting.
 */
// 
@Getter
public class CompensatoryLeaveComSetting extends AggregateRoot {

	// 会社ID
	/** The company id. */
	private String companyId;

	// 管理区分
	/** The is managed. */
	private ManageDistinct isManaged;

	// 取得と使用方法
	/** The normal vacation setting. */
	private CompensatoryAcquisitionUse compensatoryAcquisitionUse;

	// 発生設定
	private SubstituteHolidaySetting substituteHolidaySetting;
		
	// 時間代休の消化単位
	/** The compensatory digestive time unit. */
	private TimeVacationDigestUnit timeVacationDigestUnit;
   
	// 紐付け管理区分
	private ManageDistinct linkingManagementATR;
	
	
	public List<CompensatoryOccurrenceSetting> getCompensatoryOccurrenceSetting(){
		List<CompensatoryOccurrenceSetting> result = new ArrayList<>();
		CompensatoryOccurrenceSetting holidayWorkTime = new CompensatoryOccurrenceSetting(
				CompensatoryOccurrenceDivision.WorkDayOffTime,
				new SubHolTransferSet(
						new OneDayTime(this.substituteHolidaySetting.getHolidayWorkHourRequired().getTimeSetting().getCertainPeriodofTime().getCertainPeriodofTime().v()), 
						this.substituteHolidaySetting.getHolidayWorkHourRequired().isUseAtr(), 
						this.substituteHolidaySetting.getHolidayWorkHourRequired().getTimeSetting().getDesignatedTime(),
						SubHolTransferSetAtr.valueOf(this.substituteHolidaySetting.getHolidayWorkHourRequired().getTimeSetting().getEnumTimeDivision().value)
						)
				);
		result.add(holidayWorkTime);
		CompensatoryOccurrenceSetting overTime = new CompensatoryOccurrenceSetting(
				CompensatoryOccurrenceDivision.FromOverTime,
				new SubHolTransferSet(
						new OneDayTime(this.substituteHolidaySetting.getOvertimeHourRequired().getTimeSetting().getCertainPeriodofTime().getCertainPeriodofTime().v()), 
						this.substituteHolidaySetting.getOvertimeHourRequired().isUseAtr(), 
						this.substituteHolidaySetting.getOvertimeHourRequired().getTimeSetting().getDesignatedTime(),
						SubHolTransferSetAtr.valueOf(this.substituteHolidaySetting.getOvertimeHourRequired().getTimeSetting().getEnumTimeDivision().value)
						)
				);
		result.add(overTime);
		return result;
	}
	
	/**
	 * Checks if is managed.
	 *
	 * @return true, if is managed
	 */
	//代休を管理するかどうか判断する
	public boolean isManaged() {
		return this.isManaged.equals(ManageDistinct.YES);
	}
	
	/**
	 * [2] 時間代休を管理するかどうか判断する
	 * @param require
	 * @return
	 */
	public boolean isManagedTime(TimeVacationDigestUnit.Require require) {
		return this.timeVacationDigestUnit.isVacationTimeManage(require, this.isManaged);
	}

	/**
	 * Instantiates a new compensatory leave com setting.
	 *
	 * @param memento
	 *            the memento
	 */
	public CompensatoryLeaveComSetting(CompensatoryLeaveComGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.isManaged = memento.getIsManaged();
		this.compensatoryAcquisitionUse = memento.getCompensatoryAcquisitionUse();
		this.timeVacationDigestUnit = memento.getTimeVacationDigestUnit();
//		this.compensatoryOccurrenceSetting = memento.getCompensatoryOccurrenceSetting();
		this.substituteHolidaySetting = memento.getSubstituteHolidaySetting();
		this.linkingManagementATR = memento.getLinkingManagementATR();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(CompensatoryLeaveComSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setIsManaged(this.isManaged);
		memento.setCompensatoryAcquisitionUse(this.compensatoryAcquisitionUse);
		memento.setTimeVacationDigestUnit(this.timeVacationDigestUnit);
//		memento.setCompensatoryOccurrenceSetting(this.compensatoryOccurrenceSetting);
		memento.setSubstituteHolidaySetting(this.substituteHolidaySetting);
		memento.setLinkingManagementATR(this.linkingManagementATR);
	}

	public CompensatoryLeaveComSetting(String companyId, ManageDistinct isManaged,
			CompensatoryAcquisitionUse compensatoryAcquisitionUse, SubstituteHolidaySetting substituteHolidaySetting,
			TimeVacationDigestUnit compensatoryDigestiveTimeUnit, ManageDistinct linkingManagementATR) {
		super();
		this.companyId = companyId;
		this.isManaged = isManaged;
		this.compensatoryAcquisitionUse = compensatoryAcquisitionUse;
		this.substituteHolidaySetting = substituteHolidaySetting;
		this.timeVacationDigestUnit = compensatoryDigestiveTimeUnit;
		this.linkingManagementATR = linkingManagementATR;
	}
	
	public static interface Require {
		Optional<CompensatoryLeaveComSetting> compensatoryLeaveComSetting(String companyId);
	}
	
	/**
	 * [7] 利用する休暇時間の消化単位をチェックする
	 * @param require
	 * @param cid 会社ID
	 * @param time 休暇使用時間
	 * @param employeeId 社員ID
	 * @param ymd 基準日
	 */
	public boolean checkVacationTimeUnitUsed(RequireM7 require, String cid, AttendanceTime time, String employeeId, GeneralDate ymd) {
		boolean isManage = CheckDateForManageCmpLeaveService.check(require, cid, employeeId, ymd);
    	return this.timeVacationDigestUnit.checkDigestUnit(require, time, ManageDistinct.valueOf(isManage ? 1 : 0));
    }
	
	/**
	 * [8]雇用設定に従う時間代休を管理するかどうか判断する
	 * @param require
	 * @param cid 会社ID
	 * @param employeeId 社員ID
	 * @param ymd 基準日
	 */
	public boolean manageTimeOffAccordingEmpSettings(RequireM7 require, String cid, String employeeId, GeneralDate ymd) {
		boolean isManage = CheckDateForManageCmpLeaveService.check(require, cid, employeeId, ymd);
		return this.timeVacationDigestUnit.isVacationTimeManage(require, ManageDistinct.valueOf(isManage ? 1 : 0));
	}

	public static interface RequireM7 extends CheckDateForManageCmpLeaveService.Require, TimeVacationDigestUnit.Require {}
}
