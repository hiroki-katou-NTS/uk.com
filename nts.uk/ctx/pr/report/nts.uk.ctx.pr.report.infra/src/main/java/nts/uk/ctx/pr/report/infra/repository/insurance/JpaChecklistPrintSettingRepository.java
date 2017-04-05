/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.infra.repository.insurance;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.uk.ctx.pr.report.app.insurance.find.dto.CheckListPrintSettingFindOutDto;
import nts.uk.ctx.pr.report.dom.insurance.ChecklistPrintSetting;
import nts.uk.ctx.pr.report.dom.insurance.ChecklistPrintSettingGetMemento;
import nts.uk.ctx.pr.report.dom.insurance.ChecklistPrintSettingRepository;

/**
 * The Class JpaChecklistPrintSettingRepository.
 */
@Stateless
public class JpaChecklistPrintSettingRepository implements ChecklistPrintSettingRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.report.dom.insurance.ChecklistPrintSettingRepository#save(
	 * nts.uk.ctx.pr.report.dom.insurance.ChecklistPrintSetting)
	 */
	@Override
	public void save(ChecklistPrintSetting printSetting) {
		// Do nothing
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.insurance.ChecklistPrintSettingRepository#
	 * findByCompanyCode(java.lang.String)
	 */
	@Override
	public Optional<ChecklistPrintSetting> findByCompanyCode(String companyCode) {
		return null;
	}

	/**
	 * The Class JpaChecklistPrintSettingGetMemento.
	 */
	class JpaChecklistPrintSettingGetMemento implements ChecklistPrintSettingGetMemento {

		/**
		 * Instantiates a new jpa checklist print setting get memento.
		 *
		 * @param entity
		 *            the entity
		 */
		public JpaChecklistPrintSettingGetMemento(CheckListPrintSettingFindOutDto entity) {
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * nts.uk.ctx.pr.report.dom.insurance.ChecklistPrintSettingGetMemento#
		 * getCompanyCode()
		 */
		@Override
		public String getCompanyCode() {
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * nts.uk.ctx.pr.report.dom.insurance.ChecklistPrintSettingGetMemento#
		 * getShowCategoryInsuranceItem()
		 */
		@Override
		public Boolean getShowCategoryInsuranceItem() {
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * nts.uk.ctx.pr.report.dom.insurance.ChecklistPrintSettingGetMemento#
		 * getShowDeliveryNoticeAmount()
		 */
		@Override
		public Boolean getShowDeliveryNoticeAmount() {
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * nts.uk.ctx.pr.report.dom.insurance.ChecklistPrintSettingGetMemento#
		 * getShowDetail()
		 */
		@Override
		public Boolean getShowDetail() {
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * nts.uk.ctx.pr.report.dom.insurance.ChecklistPrintSettingGetMemento#
		 * getShowOffice()
		 */
		@Override
		public Boolean getShowOffice() {
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * nts.uk.ctx.pr.report.dom.insurance.ChecklistPrintSettingGetMemento#
		 * getShowTotal()
		 */
		@Override
		public Boolean getShowTotal() {
			return null;
		}

	}

}
