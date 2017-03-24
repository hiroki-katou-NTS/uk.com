package nts.uk.ctx.pr.report.infra.repository.insurance;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.uk.ctx.pr.report.app.insurance.find.dto.CheckListPrintSettingFindOutDto;
import nts.uk.ctx.pr.report.dom.company.CompanyCode;
import nts.uk.ctx.pr.report.dom.insurance.ChecklistPrintSetting;
import nts.uk.ctx.pr.report.dom.insurance.ChecklistPrintSettingGetMemento;
import nts.uk.ctx.pr.report.dom.insurance.ChecklistPrintSettingRepository;

@Stateless
public class JpaChecklistPrintSettingRepository implements ChecklistPrintSettingRepository {

	@Override
	public void save(ChecklistPrintSetting printSetting) {
		// Do nothing
	}

	@Override
	public Optional<ChecklistPrintSetting> findByCompanyCode(String companyCode) {
		return null;
	}

	private ChecklistPrintSetting toDomain() {
		ChecklistPrintSetting entity = new ChecklistPrintSetting(
			new JpaChecklistPrintSettingGetMemento(null));
		return entity;
	}

	class JpaChecklistPrintSettingGetMemento implements ChecklistPrintSettingGetMemento {

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
		public CompanyCode getCompanyCode() {
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
