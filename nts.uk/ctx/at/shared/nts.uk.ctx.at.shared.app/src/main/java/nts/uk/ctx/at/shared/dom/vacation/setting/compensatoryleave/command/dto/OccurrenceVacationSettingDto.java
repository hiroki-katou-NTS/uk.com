/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.command.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryOccurrenceDivision;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryTransferSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.OccurrenceVacationGetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.OccurrenceVacationSetting;

@Getter
@Setter
public class OccurrenceVacationSettingDto {

	/** The transfer setting. */
	private CompensatoryTransferSettingDto transferSetting;

	/** The occurrence division. */
	private CompensatoryOccurrenceDivision occurrenceDivision;

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
		public CompensatoryTransferSetting getTransferSetting() {
			return this.occurrenceVacationSettingDto.transferSetting.toDomain();
		}

		@Override
		public CompensatoryOccurrenceDivision getOccurrenceDivision() {
			return CompensatoryOccurrenceDivision.valueOf(this.occurrenceVacationSettingDto.occurrenceDivision.value);
		}

	}
}
