/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.vacation.setting.compensatoryleave.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.command.vacation.setting.compensatoryleave.dto.CompensatoryAcquisitionUseDto;
import nts.uk.ctx.at.shared.app.command.vacation.setting.compensatoryleave.dto.CompensatoryDigestiveTimeUnitDto;
import nts.uk.ctx.at.shared.app.command.vacation.setting.compensatoryleave.dto.SubstituteHolidaySettingDto;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComSetting;

/**
 * The Class CompensatoryLeaveComSettingDto.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompensatoryLeaveComSettingDto {
	
	/** The company id. */
	public String companyId;

	/** The is managed. */
	public Integer isManaged;

	/** The normal vacation setting. */
	private CompensatoryAcquisitionUseDto compensatoryAcquisitionUse;

	// 発生設定
	private SubstituteHolidaySettingDto substituteHolidaySetting;
	
	// 時間代休の消化単位
	private CompensatoryDigestiveTimeUnitDto compensatoryDigestiveTimeUnit;
   
	// 紐付け管理区分
	private int linkingManagementATR;
	
	public static CompensatoryLeaveComSettingDto toDto(CompensatoryLeaveComSetting domain) {
    	return new CompensatoryLeaveComSettingDto(
    			domain.getCompanyId(), 
    			domain.getIsManaged().value, 
    			CompensatoryAcquisitionUseDto.toDto(domain.getCompensatoryAcquisitionUse()),
    			SubstituteHolidaySettingDto.toDto(domain.getSubstituteHolidaySetting()), 
    			CompensatoryDigestiveTimeUnitDto.toDto(domain.getTimeVacationDigestUnit()) , 
    			domain.getLinkingManagementATR().value);
	}
	
	

//	/* (non-Javadoc)
//	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave2.CompensatoryLeaveComSetMemento#setCompanyId(java.lang.String)
//	 */
//	@Override
//	public void setCompanyId(String companyId) {
//	}
//
//	/* (non-Javadoc)
//	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave2.CompensatoryLeaveComSetMemento#setIsManaged(nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct)
//	 */
//	@Override
//	public void setIsManaged(ManageDistinct isManaged) {
//		this.isManaged = isManaged.value;
//	}
//
//	/* (non-Javadoc)
//	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave2.CompensatoryLeaveComSetMemento#setCompensatoryAcquisitionUse(nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave2.CompensatoryAcquisitionUse)
//	 */
//	@Override
//	public void setCompensatoryAcquisitionUse(CompensatoryAcquisitionUse compensatoryAcquisitionUse) {
//		CompensatoryAcquisitionUseDto acquisitionUse = new CompensatoryAcquisitionUseDto();
//		compensatoryAcquisitionUse.saveToMemento(acquisitionUse);
//		this.compensatoryAcquisitionUse = acquisitionUse;
//	}
//
//	/* (non-Javadoc)
//	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave2.CompensatoryLeaveComSetMemento#setCompensatoryDigestiveTimeUnit(nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave2.CompensatoryDigestiveTimeUnit)
//	 */
//	@Override
//	public void setCompensatoryDigestiveTimeUnit(CompensatoryDigestiveTimeUnit compensatoryDigestiveTimeUnit) {
//		CompensatoryDigestiveTimeUnitDto  digestiveTimeUnit = new CompensatoryDigestiveTimeUnitDto();
//		compensatoryDigestiveTimeUnit.saveToMemento(digestiveTimeUnit);
//		this.compensatoryDigestiveTimeUnit = digestiveTimeUnit;
//	}
//
//	/* (non-Javadoc)
//	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave2.CompensatoryLeaveComSetMemento#setCompensatoryOccurrenceSetting(java.util.List)
//	 */
//	@Override
//	public void setCompensatoryOccurrenceSetting(List<CompensatoryOccurrenceSetting> compensatoryOccurrenceSetting) {
//		this.compensatoryOccurrenceSetting = compensatoryOccurrenceSetting.stream().map(item -> {
//			CompensatoryOccurrenceSettingDto dto = new CompensatoryOccurrenceSettingDto();
//			item.saveToMemento(dto);
//			return dto;
//		}).collect(Collectors.toList());
//	}
//
//	@Override
//	public void setSubstituteHolidaySetting(SubstituteHolidaySetting substituteHolidaySetting) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void setLinkingManagementATR(ManageDistinct linkingManagementATR) {
//		// TODO Auto-generated method stub
//		
//	}
}
