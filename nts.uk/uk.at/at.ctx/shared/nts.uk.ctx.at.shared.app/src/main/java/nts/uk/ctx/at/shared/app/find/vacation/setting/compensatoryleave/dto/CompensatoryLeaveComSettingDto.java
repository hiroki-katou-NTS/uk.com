/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.vacation.setting.compensatoryleave.dto;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryAcquisitionUse;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryDigestiveTimeUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComSetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryOccurrenceSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.SubstituteHolidaySetting;

/**
 * The Class CompensatoryLeaveComSettingDto.
 */
public class CompensatoryLeaveComSettingDto implements CompensatoryLeaveComSetMemento {
	
	/** The company id. */
	public String companyId;

	/** The is managed. */
	public Integer isManaged;

	/** The compensatory acquisition use. */
	public CompensatoryAcquisitionUseDto compensatoryAcquisitionUse;

	/** The compensatory digestive time unit. */
	public CompensatoryDigestiveTimeUnitDto compensatoryDigestiveTimeUnit;
	
	/** The compensatory occurrence setting. */
	public List<CompensatoryOccurrenceSettingDto> compensatoryOccurrenceSetting; 

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave2.CompensatoryLeaveComSetMemento#setCompanyId(java.lang.String)
	 */
	@Override
	public void setCompanyId(String companyId) {
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave2.CompensatoryLeaveComSetMemento#setIsManaged(nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct)
	 */
	@Override
	public void setIsManaged(ManageDistinct isManaged) {
		this.isManaged = isManaged.value;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave2.CompensatoryLeaveComSetMemento#setCompensatoryAcquisitionUse(nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave2.CompensatoryAcquisitionUse)
	 */
	@Override
	public void setCompensatoryAcquisitionUse(CompensatoryAcquisitionUse compensatoryAcquisitionUse) {
		CompensatoryAcquisitionUseDto acquisitionUse = new CompensatoryAcquisitionUseDto();
		compensatoryAcquisitionUse.saveToMemento(acquisitionUse);
		this.compensatoryAcquisitionUse = acquisitionUse;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave2.CompensatoryLeaveComSetMemento#setCompensatoryDigestiveTimeUnit(nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave2.CompensatoryDigestiveTimeUnit)
	 */
	@Override
	public void setCompensatoryDigestiveTimeUnit(CompensatoryDigestiveTimeUnit compensatoryDigestiveTimeUnit) {
		CompensatoryDigestiveTimeUnitDto  digestiveTimeUnit = new CompensatoryDigestiveTimeUnitDto();
		compensatoryDigestiveTimeUnit.saveToMemento(digestiveTimeUnit);
		this.compensatoryDigestiveTimeUnit = digestiveTimeUnit;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave2.CompensatoryLeaveComSetMemento#setCompensatoryOccurrenceSetting(java.util.List)
	 */
	@Override
	public void setCompensatoryOccurrenceSetting(List<CompensatoryOccurrenceSetting> compensatoryOccurrenceSetting) {
		this.compensatoryOccurrenceSetting = compensatoryOccurrenceSetting.stream().map(item -> {
			CompensatoryOccurrenceSettingDto dto = new CompensatoryOccurrenceSettingDto();
			item.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());
	}

	@Override
	public void setSubstituteHolidaySetting(SubstituteHolidaySetting substituteHolidaySetting) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLinkingManagementATR(ManageDistinct linkingManagementATR) {
		// TODO Auto-generated method stub
		
	}
}
