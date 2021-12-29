/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.vacation.setting.compensatoryleave;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.command.vacation.setting.compensatoryleave.dto.CompensatoryAcquisitionUseDto;
import nts.uk.ctx.at.shared.app.command.vacation.setting.compensatoryleave.dto.CompensatoryDigestiveTimeUnitDto;
import nts.uk.ctx.at.shared.app.command.vacation.setting.compensatoryleave.dto.SubstituteHolidaySettingDto;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeVacationDigestUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryAcquisitionUse;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComGetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryOccurrenceSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.SubstituteHolidaySetting;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class SaveCompensatoryLeaveCommand.
 */
@Getter
@Setter
public class SaveCompensatoryLeaveCommand {

	/** The company id. */
	private String companyId;
	
	/** The employment code */
	private String employmentCode;

	/** The is managed. */
	private Integer isManaged;

	/** The normal vacation setting. */
	private CompensatoryAcquisitionUseDto compensatoryAcquisitionUse;

	// 発生設定
	private SubstituteHolidaySettingDto substituteHolidaySetting;
	
	// 時間代休の消化単位
	private CompensatoryDigestiveTimeUnitDto compensatoryDigestiveTimeUnit;
   
	// 紐付け管理区分
	private int linkingManagementATR;
	
	
	public CompensatoryLeaveComSetting toDomain() {
    	
    	return new CompensatoryLeaveComSetting(
    			AppContexts.user().companyId(), 
    			ManageDistinct.valueOf(this.isManaged), 
    			compensatoryAcquisitionUse.toDomainNew(),
    			substituteHolidaySetting.toDomain(), 
    			compensatoryDigestiveTimeUnit.toDomainNew(), 
    			ManageDistinct.valueOf(this.linkingManagementATR));
	}
	
//	/** The occurrence vacation setting. */
//	private CompensatoryDigestiveTimeUnitDto compensatoryDigestiveTimeUnit;
//	
//	/** The compensatory occurrence setting. */
//	private List<CompensatoryOccurrenceSettingDto> compensatoryOccurrenceSetting; 

	public CompensatoryLeaveComSetting toDomain(String companyId) {
		return new CompensatoryLeaveComSetting(new CompensatoryLeaveComGetMementoImpl(companyId, this));
	}
	
	/**
	 * To domain CompensatoryOccurrenceSetting.
	 * @return CompensatoryOccurrenceSetting
	 */
	public List<CompensatoryOccurrenceSetting> toDomainCompensatoryOccurrenceSetting(){
		List<CompensatoryOccurrenceSetting> list = new ArrayList<CompensatoryOccurrenceSetting>();

//		this.compensatoryOccurrenceSetting.forEach(c -> list.add(c.toDomain()));
		
		return list;
	}	

	public class CompensatoryLeaveComGetMementoImpl implements CompensatoryLeaveComGetMemento {

		/** The company id. */
		private String companyId;

		/** The command. */
		private SaveCompensatoryLeaveCommand command;

		/**
		 * @param companyId
		 * @param command
		 */
		public CompensatoryLeaveComGetMementoImpl(String companyId, SaveCompensatoryLeaveCommand command) {
			this.companyId = companyId;
			this.command = command;
		}

		@Override
		public String getCompanyId() {
			return this.companyId;
		}

		@Override
		public ManageDistinct getIsManaged() {
			return ManageDistinct.valueOf(this.command.isManaged);
		}

		@Override
		public CompensatoryAcquisitionUse getCompensatoryAcquisitionUse() {
			return this.command.compensatoryAcquisitionUse.toDomain();
		}

		@Override
		public List<CompensatoryOccurrenceSetting> getCompensatoryOccurrenceSetting() {
//			return this.command.compensatoryOccurrenceSetting.stream().map(item -> {
//				return item.toDomain();
//			}).collect(Collectors.toList());
			return new ArrayList<>();
		}

		@Override
		public SubstituteHolidaySetting getSubstituteHolidaySetting() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ManageDistinct getLinkingManagementATR() {
			
			return ManageDistinct.valueOf(this.command.linkingManagementATR);
		}

		@Override
		public TimeVacationDigestUnit getTimeVacationDigestUnit() {
			return this.command.compensatoryDigestiveTimeUnit.toDomain();
		}
	}
}
