/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.command.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryTransferSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.OccurrenceVacationGetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.OccurrenceVacationSetting;

@Getter
@Setter
public class OccurrenceVacationSettingDto {

	/** The transfer setting over time. */
	private CompensatoryTransferSettingDto transferSettingOverTime;

	/** The transfer setting day of time. */
	private CompensatoryTransferSettingDto transferSettingDayOffTime;

	public OccurrenceVacationSetting toDomain() {
		return new OccurrenceVacationSetting(
				new OccurrenceVacationSettingGetMementoImpl(this));
	}

	public class OccurrenceVacationSettingGetMementoImpl implements OccurrenceVacationGetMemento {
		
		/** The normal vacation setting. */
		private OccurrenceVacationSettingDto occurrenceVacationSettingDto;

		/**
		 * @param companyId
		 * @param isManaged
		 * @param normalVacationSetting
		 * @param occurrenceVacationSetting
		 */
		public OccurrenceVacationSettingGetMementoImpl(OccurrenceVacationSettingDto occurrenceVacationSettingDto) {
			this.occurrenceVacationSettingDto = occurrenceVacationSettingDto;
		}
		
		@Override
		public CompensatoryTransferSetting getTransferSettingOverTime() {
			return this.occurrenceVacationSettingDto.transferSettingOverTime.toDomain();
		}

		@Override
		public CompensatoryTransferSetting getTransferSettingDayOffTime() {
			return this.occurrenceVacationSettingDto.transferSettingDayOffTime.toDomain();
		}

	}
}
