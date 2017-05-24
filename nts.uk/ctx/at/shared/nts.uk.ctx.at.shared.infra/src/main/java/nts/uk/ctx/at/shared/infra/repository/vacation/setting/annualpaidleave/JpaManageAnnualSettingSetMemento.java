/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.annualpaidleave;

import javax.inject.Inject;

import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AcquisitionVacationSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.DisplaySetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.ManageAnnualSettingSetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.RemainingNumberSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.TimeVacationSetting;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.annualpaidleave.KmfmtMngAnnualSet;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.annualpaidleave.KmfmtMngAnnualSetPK;

/**
 * The Class JpaManageAnnualSettingSetMemento.
 */
public class JpaManageAnnualSettingSetMemento implements ManageAnnualSettingSetMemento {

	/** The manage annual. */
	@Inject
	private KmfmtMngAnnualSet manageAnnual;

	/**
	 * Instantiates a new jpa manage annual setting set memento.
	 *
	 * @param manageAnnual
	 *            the manage annual
	 */
	public JpaManageAnnualSettingSetMemento(KmfmtMngAnnualSet manageAnnual) {
		this.manageAnnual = manageAnnual;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.vacation.setting.annualpaidleave.
	 * ManageAnnualSettingSetMemento#setCompanyId(java.lang.String)
	 */
	@Override
	public void setCompanyId(String companyId) {
		KmfmtMngAnnualSetPK pk = new KmfmtMngAnnualSetPK();
		pk.setCid(companyId);
		pk.setMngId(IdentifierUtil.randomUniqueId());
		this.manageAnnual.setKmfmtMngAnnualSetPK(pk);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.vacation.setting.annualpaidleave.
	 * ManageAnnualSettingSetMemento#setRemainingNumberSetting(nts.uk.ctx.pr.
	 * core.dom.vacation.setting.annualpaidleave.RemainingNumberSetting)
	 */
	@Override
	public void setRemainingNumberSetting(RemainingNumberSetting remaining) {
		this.manageAnnual.setWorkDayCal(remaining.getWorkDayCalculate().value);
		this.manageAnnual.setHalfDayMngAtr(remaining.getHalfDayManage().manageType.value);
		this.manageAnnual.setMngReference(remaining.getHalfDayManage().reference.value);
		this.manageAnnual
				.setCUniformMaxNumber(remaining.getHalfDayManage().maxNumberUniformCompany.v());
		this.manageAnnual.setMaxDayOneYear(remaining.getMaximumDayVacation().v());
		this.manageAnnual.setRemainDayMaxNum(remaining.getRemainingDayMaxNumber().intValue());
		this.manageAnnual.setRetentionYear(remaining.getRetentionYear().v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.vacation.setting.annualpaidleave.
	 * ManageAnnualSettingSetMemento#setAcquisitionSetting(nts.uk.ctx.pr.core.
	 * dom.vacation.setting.annualpaidleave.AcquisitionVacationSetting)
	 */
	@Override
	public void setAcquisitionSetting(AcquisitionVacationSetting acquisitionSetting) {
		this.manageAnnual.setYearVacaPriority(acquisitionSetting.yearVacationPriority.value);
		this.manageAnnual.setPermitType(acquisitionSetting.permitType.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.vacation.setting.annualpaidleave.
	 * ManageAnnualSettingSetMemento#setDisplaySetting(nts.uk.ctx.pr.core.dom.
	 * vacation.setting.annualpaidleave.DisplaySetting)
	 */
	@Override
	public void setDisplaySetting(DisplaySetting displaySetting) {
		this.manageAnnual.setRemainNumDisplay(displaySetting.remainingNumberDisplay.value);
		this.manageAnnual.setNextGrantDayDisplay(displaySetting.nextGrantDayDisplay.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.vacation.setting.annualpaidleave.
	 * ManageAnnualSettingSetMemento#setTimeSetting(nts.uk.ctx.pr.core.dom.
	 * vacation.setting.annualpaidleave.TimeVacationSetting)
	 */
	@Override
	public void setTimeSetting(TimeVacationSetting timeSetting) {
		this.manageAnnual.setTimeMngAtr(timeSetting.getTimeManageType().value);
		this.manageAnnual.setTimeUnit(timeSetting.getTimeUnit().value);
		this.manageAnnual.setTimeMaxDayMng(timeSetting.getMaxDay().manageMaxDayVacation.value);
		this.manageAnnual.setTimeMngReference(timeSetting.getMaxDay().reference.value);
		this.manageAnnual.setTimeMngMaxDay(timeSetting.getMaxDay().maxTimeDay.v());
		this.manageAnnual.setEnoughOneDay(timeSetting.isEnoughTimeOneDay() == true ? 1 : 0);
	}
}
