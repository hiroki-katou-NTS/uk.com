/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.vacation.setting.compensatoryleave.dto;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryAcquisitionUse;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryDigestiveTimeUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveEmSettingSetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

/**
 * The Class CompensatoryLeaveEmSettingDto.
 */
public class CompensatoryLeaveEmSettingDto implements CompensatoryLeaveEmSettingSetMemento {

	// 会社ID
	/** The company id. */
	public String companyId;

	// 雇用区分コード
	/** The employment code. */
	public EmploymentCode employmentCode;

	/** The is managed. */
	public Integer isManaged;

	// 設定
	/** The compensatory acquisition use. */
	public CompensatoryAcquisitionUseDto compensatoryAcquisitionUse;

	// 時間代休の消化単位
	/** The compensatory digestive time unit. */
	public CompensatoryDigestiveTimeUnitDto compensatoryDigestiveTimeUnit;

	@Override
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	@Override
	public void setEmploymentCode(EmploymentCode employmentCode) {
		this.employmentCode = employmentCode;
	}

	@Override
	public void setIsManaged(ManageDistinct isManaged) {
		this.isManaged = isManaged.value;
	}

	@Override
	public void setCompensatoryAcquisitionUse(CompensatoryAcquisitionUse compensatoryAcquisitionUse) {
		CompensatoryAcquisitionUseDto acquisitionUse = new CompensatoryAcquisitionUseDto();
		compensatoryAcquisitionUse.saveToMemento(acquisitionUse);
		this.compensatoryAcquisitionUse = acquisitionUse;
	}

	@Override
	public void setCompensatoryDigestiveTimeUnit(CompensatoryDigestiveTimeUnit compensatoryDigestiveTimeUnit) {
		CompensatoryDigestiveTimeUnitDto digestiveTimeUnit = new CompensatoryDigestiveTimeUnitDto();
		compensatoryDigestiveTimeUnit.saveToMemento(digestiveTimeUnit);
		this.compensatoryDigestiveTimeUnit = digestiveTimeUnit;
	}

}
