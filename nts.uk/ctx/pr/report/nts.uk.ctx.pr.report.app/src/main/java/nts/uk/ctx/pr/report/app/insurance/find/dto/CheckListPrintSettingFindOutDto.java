/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.app.insurance.find.dto;

import lombok.Data;
import nts.uk.ctx.pr.report.dom.company.CompanyCode;
import nts.uk.ctx.pr.report.dom.insurance.ChecklistPrintSettingSetMemento;

/**
 * The Class CheckListPrintSettingDto.
 */
@Data
public class CheckListPrintSettingFindOutDto implements ChecklistPrintSettingSetMemento {

//    private HealthInsuranceType healthInsuranceType;
    
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

	@Override
	public void setCompanyCode(CompanyCode companyCode) {
		// Do nothing.
	}

	@Override
	public void setShowCategoryInsuranceItem(Boolean showCategoryInsuranceItem) {
		this.showCategoryInsuranceItem = showCategoryInsuranceItem;
	}

	@Override
	public void setShowDeliveryNoticeAmount(Boolean showDeliveryNoticeAmount) {
		this.showDeliveryNoticeAmount = showDeliveryNoticeAmount;
	}

	@Override
	public void setShowDetail(Boolean showDetail) {
		this.showDetail = showDetail;
	}

	@Override
	public void setShowOffice(Boolean showOffice) {
		this.showOffice = showOffice;
	}

    @Override
    public void setShowTotal(Boolean showTotal) {
        this.showTotal = showTotal;
    }
}
