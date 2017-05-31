/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.command.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryOccurrenceDivision;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryTransferGetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryTransferSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.OneDayTime;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.TransferSettingDivision;

@Getter
@Setter
public class CompensatoryTransferSettingDto {

	/** The certain time. */
	private Integer certainTime;

	/** The use division. */
	private boolean useDivision;

	/** The one day time. */
	private Integer oneDayTime;

	/** The half day time. */
	private Integer halfDayTime;

	/** The transfer division. */
	private Integer transferDivision;

	/** The compensatory occurrence division. */
	private Integer compensatoryOccurrenceDivision;

	public CompensatoryTransferSetting toDomain() {
		return new CompensatoryTransferSetting(new CompensatoryTransferGetMementoImpl(this));
	}

	public class CompensatoryTransferGetMementoImpl implements CompensatoryTransferGetMemento {

		/** The compensatory transfer setting dto. */
		private CompensatoryTransferSettingDto compensatoryTransferSettingDto;

		/**
		 * @param compensatoryTransferSettingDto
		 */
		public CompensatoryTransferGetMementoImpl(CompensatoryTransferSettingDto compensatoryTransferSettingDto) {
			this.compensatoryTransferSettingDto = compensatoryTransferSettingDto;
		}

		@Override
		public OneDayTime getCertainTime() {
			return new OneDayTime(this.compensatoryTransferSettingDto.certainTime.longValue());
		}

		@Override
		public boolean getUseDivision() {
			return this.compensatoryTransferSettingDto.useDivision;
		}

		@Override
		public OneDayTime getOneDayTime() {
			return new OneDayTime(this.compensatoryTransferSettingDto.oneDayTime.longValue());
		}

		@Override
		public OneDayTime getHalfDayTime() {
			return new OneDayTime(this.compensatoryTransferSettingDto.halfDayTime.longValue());
		}

		@Override
		public TransferSettingDivision getTransferDivision() {
			return TransferSettingDivision.valueOf(this.compensatoryTransferSettingDto.transferDivision);
		}

		@Override
		public CompensatoryOccurrenceDivision getCompensatoryOccurrenceDivision() {
			return CompensatoryOccurrenceDivision
					.valueOf(this.compensatoryTransferSettingDto.compensatoryOccurrenceDivision);
		}

	}
}
