/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.app.insurance.command.dto;

import lombok.Data;
import nts.uk.ctx.pr.report.dom.company.CompanyCode;
import nts.uk.ctx.pr.report.dom.insurance.ChecklistPrintSetting;
import nts.uk.ctx.pr.report.dom.insurance.ChecklistPrintSettingGetMemento;

/**
 * The Class CheckListPrintSettingDto.
 */
@Data
public class CheckListPrintSettingDto {

	/** The show category insurance item. */
	private Boolean showCategoryInsuranceItem;

	/** The show delivery notice amount. */
	private Boolean showDeliveryNoticeAmount;

	/** The show detail. */
	private Boolean showDetail;

	/** The show office. */
	private Boolean showOffice;

	/** The show total. */
	private Boolean showTotal;

	/**
	 * To domain.
	 *
	 * @param companyCode
	 *            the company code
	 * @return the checklist print setting
	 */
	public ChecklistPrintSetting toDomain(String companyCode) {
		CheckListPrintSettingDto dto = this;
		return new ChecklistPrintSetting(
			new ChecklistPrintSettingGetMemento() {

				@Override
				public Boolean getShowOffice() {
					return dto.showOffice;
				}

				@Override
				public Boolean getShowDetail() {
					return dto.showDetail;
				}

				@Override
				public Boolean getShowDeliveryNoticeAmount() {
					return dto.showDeliveryNoticeAmount;
				}

				@Override
				public Boolean getShowCategoryInsuranceItem() {
					return dto.showCategoryInsuranceItem;
				}

				@Override
				public CompanyCode getCompanyCode() {
					return new CompanyCode(companyCode);
				}

				@Override
				public Boolean getShowTotal() {
					return dto.showTotal;
				}
			});
	}

}
