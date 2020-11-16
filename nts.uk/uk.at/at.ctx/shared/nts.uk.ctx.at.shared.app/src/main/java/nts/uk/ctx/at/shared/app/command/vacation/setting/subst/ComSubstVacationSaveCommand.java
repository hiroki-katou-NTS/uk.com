/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.vacation.setting.subst;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.vacation.setting.ApplyPermission;
import nts.uk.ctx.at.shared.dom.vacation.setting.ExpirationTime;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacationGetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.SubstVacationSetting;

/**
 * The Class ComSubstVacationSaveCommand.
 */
@Getter
@Setter
public class ComSubstVacationSaveCommand extends SubstVacationSaveBaseCommand {

	/**
	 * To domain.
	 *
	 * @param companyId
	 *            the company id
	 * @return the com subst vacation
	 */
	public ComSubstVacation toDomain(String companyId) {
		return new ComSubstVacation(new ComSvGetMementoImpl(companyId, this));
	}

	/**
	 * The Class ComSvGetMementoImpl.
	 */
	private class ComSvGetMementoImpl implements ComSubstVacationGetMemento {

		/** The company id. */
		private String companyId;

		/** The dto. */
		private SubstVacationSaveBaseCommand dto;

		/**
		 * Instantiates a new cg set memento.
		 *
		 * @param dto
		 *            the dto
		 */
		public ComSvGetMementoImpl(String companyId, SubstVacationSaveBaseCommand dto) {
			this.companyId = companyId;
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
		 * ComSubstVacationGetMemento#getSetting()
		 */
		@Override
		public SubstVacationSetting getSetting() {
			return new SubstVacationSetting(ManageDistinct.valueOf(this.dto.getIsManage()),
					ExpirationTime.valueOf(this.dto.getExpirationDate()),
					ApplyPermission.valueOf(this.dto.getAllowPrepaidLeave()));
		}

		@Override
		public ManageDistinct getManageDistinct() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ManageDistinct getLinkingManagementATR() {
			// TODO Auto-generated method stub
			return null;
		}
	}

}
