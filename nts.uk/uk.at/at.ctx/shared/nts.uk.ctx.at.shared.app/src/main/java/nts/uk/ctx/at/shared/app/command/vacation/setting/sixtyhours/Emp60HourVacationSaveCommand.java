/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.vacation.setting.sixtyhours;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.SixtyHourExtra;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.Emp60HourVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.Emp60HourVacationGetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.SixtyHourVacationSetting;

/**
 * The Class Emp60HourVacationSaveCommand.
 */
@Getter
@Setter
public class Emp60HourVacationSaveCommand extends SixtyHourVacationSaveBaseCommand {

	/** The is manage. */
	private String contractTypeCode;

	/**
	 * To domain.
	 *
	 * @param companyId
	 *            the company id
	 * @return the com subst vacation
	 */
	public Emp60HourVacation toDomain(String companyId) {
		return new Emp60HourVacation(new EmpSvGetMementoImpl(companyId, this));
	}

	/**
	 * The Class ComSvGetMementoImpl.
	 */
	private class EmpSvGetMementoImpl implements Emp60HourVacationGetMemento {

		/** The company id. */
		private String companyId;

		/** The dto. */
		private Emp60HourVacationSaveCommand dto;

		/**
		 * Instantiates a new cg set memento.
		 *
		 * @param dto
		 *            the dto
		 */
		public EmpSvGetMementoImpl(String companyId, Emp60HourVacationSaveCommand dto) {
			this.companyId = companyId;
			this.dto = dto;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.shared.dom.vacation.setting.subst.
		 * Com60HourVacationGetMemento#getCompanyId()
		 */
		@Override
		public String getCompanyId() {
			return this.companyId;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.shared.dom.vacation.setting.subst.
		 * Emp60HourVacationGetMemento#getEmpContractTypeCode()
		 */
		@Override
		public String getEmpContractTypeCode() {
			return this.dto.contractTypeCode;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.shared.dom.vacation.setting.subst.
		 * Com60HourVacationGetMemento#getSetting()
		 */
		@Override
		public SixtyHourVacationSetting getSetting() {
			return new SixtyHourVacationSetting(ManageDistinct.valueOf(this.dto.getIsManage()),
					SixtyHourExtra.valueOf(this.dto.getSixtyHourExtra()),
					TimeDigestiveUnit.valueOf(this.dto.getDigestiveUnit()));
		}

	}

}
