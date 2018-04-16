/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.vacation.setting.compensatoryleave.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryOccurrenceDivision;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryOccurrenceSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryOccurrenceSettingGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.common.SubHolTransferSet;

/**
 * The Class CompensatoryOccurrenceSettingDto.
 */
@Getter
@Setter
public class CompensatoryOccurrenceSettingDto {

	/** The occurrence type. */
	private Integer occurrenceType;

	/** The transfer setting. */
	private CompensatoryTransferSettingDto transferSetting;

	/**
	 * To domain.
	 *
	 * @return the compensatory occurrence setting
	 */
	public CompensatoryOccurrenceSetting toDomain() {
		return new CompensatoryOccurrenceSetting(new CompensatoryOccurrenceSettingGetMementoImpl(this));
	}

	/**
	 * The Class CompensatoryOccurrenceSettingGetMementoImpl.
	 */
	public class CompensatoryOccurrenceSettingGetMementoImpl implements CompensatoryOccurrenceSettingGetMemento {

		/** The compensatory occurrence setting dto. */
		private CompensatoryOccurrenceSettingDto compensatoryOccurrenceSettingDto;

		/**
		 * Instantiates a new compensatory occurrence setting get memento impl.
		 *
		 * @param compensatoryOccurrenceSettingDto the compensatory occurrence setting dto
		 */
		public CompensatoryOccurrenceSettingGetMementoImpl(
				CompensatoryOccurrenceSettingDto compensatoryOccurrenceSettingDto) {
			this.compensatoryOccurrenceSettingDto = compensatoryOccurrenceSettingDto;
		}

		/* (non-Javadoc)
		 * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave2.CompensatoryOccurrenceSettingGetMemento#getOccurrenceType()
		 */
		@Override
		public CompensatoryOccurrenceDivision getOccurrenceType() {
			return CompensatoryOccurrenceDivision.valueOf(this.compensatoryOccurrenceSettingDto.occurrenceType);
		}

		/* (non-Javadoc)
		 * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave2.CompensatoryOccurrenceSettingGetMemento#getTransferSetting()
		 */
		@Override
		public SubHolTransferSet getTransferSetting() {
			return this.compensatoryOccurrenceSettingDto.transferSetting.toDomain();
		}

	}
}
