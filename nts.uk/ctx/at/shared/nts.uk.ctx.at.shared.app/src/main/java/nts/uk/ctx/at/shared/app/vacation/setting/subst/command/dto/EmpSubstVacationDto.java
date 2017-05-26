/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.vacation.setting.subst.command.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.vacation.setting.ApplyPermission;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.EmpSubstVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.EmpSubstVacationGetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.SubstVacationSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.VacationExpiration;

/**
 * The Class EmpSubstVacationDto.
 */
@Getter
@Setter
public class EmpSubstVacationDto extends SubstVacationDto {

	/** The is manage. */
	private String contractTypeCode;

	/**
	 * To domain.
	 *
	 * @param companyId
	 *            the company id
	 * @return the com subst vacation
	 */
	public EmpSubstVacation toDomain(String companyId, String contractTypeCode) {
		return new EmpSubstVacation(new EmpSvGetMementoImpl(companyId, contractTypeCode, this));
	}

	/**
	 * The Class ComSvGetMementoImpl.
	 */
	private class EmpSvGetMementoImpl implements EmpSubstVacationGetMemento {

		/** The company id. */
		private String companyId;

		/** The contract type code. */
		private String contractTypeCode;

		/** The dto. */
		private EmpSubstVacationDto dto;

		/**
		 * Instantiates a new cg set memento.
		 *
		 * @param dto
		 *            the dto
		 */
		public EmpSvGetMementoImpl(String companyId, String contractTypeCode,
				EmpSubstVacationDto dto) {
			this.companyId = companyId;
			this.contractTypeCode = contractTypeCode;
			this.dto = dto;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.shared.dom.vacation.setting.subst.
		 * ComSubstVacationGetMemento#getCompanyId()
		 */
		@Override
		public String getCompanyId() {
			return this.companyId;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.shared.dom.vacation.setting.subst.
		 * EmpSubstVacationGetMemento#getEmpContractTypeCode()
		 */
		@Override
		public String getEmpContractTypeCode() {
			return this.contractTypeCode;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.shared.dom.vacation.setting.subst.
		 * ComSubstVacationGetMemento#getSetting()
		 */
		@Override
		public SubstVacationSetting getSetting() {
			return new SubstVacationSetting(ManageDistinct.valueOf(this.dto.getIsManage()),
					VacationExpiration.valueOf(this.dto.getExpirationDate()),
					ApplyPermission.valueOf(this.dto.getAllowPrepaidLeave()));
		}

	}

}
