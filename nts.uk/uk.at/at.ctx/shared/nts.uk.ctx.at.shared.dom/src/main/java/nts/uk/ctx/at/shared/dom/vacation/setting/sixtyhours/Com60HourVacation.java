/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.SixtyHourExtra;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeVacationDigestUnit;

/**
 * The Class CompanyCompensatoryLeave.
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.就業規則.休暇.60H超休.60H超休.60H超休管理設定
 */
@Getter
public class Com60HourVacation extends DomainObject {

	/** The company id. */
	// 会社ID
	private String companyId;

	// 時間休暇消化単位
	private TimeVacationDigestUnit timeVacationDigestUnit;
	
	// 60H超休使用期限
	private SixtyHourExtra sixtyHourExtra;

	/**
	 * Checks if is managed.
	 *
	 * @return true, if is managed
	 */
	public boolean isManaged() {
		return this.timeVacationDigestUnit.getManage().equals(ManageDistinct.YES);
	}

	/**
	 * Instantiates a new com 60 hour vacation.
	 *
	 * @param companyId the company id
	 * @param setting the setting
	 */
	public Com60HourVacation(String companyId, TimeVacationDigestUnit digestiveUnit, SixtyHourExtra sixtyHourExtra) {
		super();
		this.companyId = companyId;
		this.timeVacationDigestUnit = digestiveUnit;
		this.sixtyHourExtra = sixtyHourExtra;
	}

	// =================== Memento State Support Method ===================
	/**
	 * Instantiates a new labor insurance office.
	 *
	 * @param memento
	 *            the memento
	 */
	public Com60HourVacation(Com60HourVacationGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.timeVacationDigestUnit = memento.getDigestiveUnit();
		this.sixtyHourExtra = memento.getSixtyHourExtra();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(Com60HourVacationSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setTimeVacationDigestUnit(this.timeVacationDigestUnit);
		memento.setSixtyHourExtra(this.sixtyHourExtra);
	}
	
	
	/**
	 * [1] 利用する休暇時間の消化単位をチェックする
	 * @param require
	 * @param time
	 */
	public boolean checkVacationTimeUnitUsed(TimeVacationDigestUnit.Require require, AttendanceTime time) {
		return this.timeVacationDigestUnit.checkDigestUnit(require, time);
	}
	
	/**
	 * [2] 時間60H超休を管理するか
	 * @param require
	 */
	public boolean isVacationTimeManage(TimeVacationDigestUnit.Require require) {
		return this.timeVacationDigestUnit.isVacationTimeManage(require);
	}

}
