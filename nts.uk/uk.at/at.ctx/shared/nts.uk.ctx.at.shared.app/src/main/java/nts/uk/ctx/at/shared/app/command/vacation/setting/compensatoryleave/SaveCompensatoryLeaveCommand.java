/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.vacation.setting.compensatoryleave;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.command.vacation.setting.compensatoryleave.dto.CompensatoryAcquisitionUseDto;
import nts.uk.ctx.at.shared.app.command.vacation.setting.compensatoryleave.dto.CompensatoryDigestiveTimeUnitDto;
import nts.uk.ctx.at.shared.app.command.vacation.setting.compensatoryleave.dto.CompensatoryOccurrenceSettingDto;
import nts.uk.ctx.at.shared.app.command.vacation.setting.compensatoryleave.dto.SubstituteHolidaySettingDto;
import nts.uk.ctx.at.shared.dom.common.TimeOfDay;
import nts.uk.ctx.at.shared.dom.vacation.setting.ApplyPermission;
import nts.uk.ctx.at.shared.dom.vacation.setting.ExpirationTime;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CertainPeriodOfTime;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryAcquisitionUse;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryDigestiveTimeUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComGetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryOccurrenceSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.DeadlCheckMonth;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EnumTimeDivision;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.HolidayWorkHourRequired;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.OvertimeHourRequired;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.SubstituteHolidaySetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.TermManagement;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.TimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.DesignatedTime;
import nts.uk.ctx.at.shared.dom.worktime.common.OneDayTime;

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
    			this.companyId, 
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
		public CompensatoryDigestiveTimeUnit getCompensatoryDigestiveTimeUnit() {
			return this.command.compensatoryDigestiveTimeUnit.toDomain();
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
			// TODO Auto-generated method stub
			return null;
		}
	}
}
