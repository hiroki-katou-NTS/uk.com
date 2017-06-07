/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.SixtyHourExtra;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;

/**
 * The Class SubstVacationSetting.
 */
// 60H超休の設定
@Getter
public class SixtyHourVacationSetting extends DomainObject {

	/** The is manage. */
	// 管理区分
	private ManageDistinct isManage;

	/** The expiration date. */
	// 60H超休使用期限
	private SixtyHourExtra sixtyHourExtra;

	/** The allow prepaid leave. */
	// 時間休暇消化単位
	private TimeDigestiveUnit digestiveUnit;

	/**
	 * Instantiates a new subst vacation setting.
	 *
	 * @param isManage
	 *            the is manage
	 * @param expirationDate
	 *            the expiration date
	 * @param allowPrepaidLeave
	 *            the allow prepaid leave
	 */
	public SixtyHourVacationSetting(ManageDistinct isManage, SixtyHourExtra sixtyHourExtra,
			TimeDigestiveUnit digestiveUnit) {
		super();
		this.isManage = isManage;
		this.sixtyHourExtra = sixtyHourExtra;
		this.digestiveUnit = digestiveUnit;
	}

	// =================== Memento State Support Method ===================
	/**
	 * Instantiates a new subst vacation setting.
	 *
	 * @param memento
	 *            the memento
	 */
	public SixtyHourVacationSetting(SixtyHourVacationSettingGetMemento memento) {
		this.isManage = memento.getIsManage();
		this.sixtyHourExtra = memento.getSixtyHourExtra();
		this.digestiveUnit = memento.getDigestiveUnit();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(SixtyHourVacationSettingSetMemento memento) {
		memento.setIsManage(this.isManage);
		memento.setSixtyHourExtra(this.sixtyHourExtra);
		memento.setDigestiveUnit(this.digestiveUnit);
	}

}
