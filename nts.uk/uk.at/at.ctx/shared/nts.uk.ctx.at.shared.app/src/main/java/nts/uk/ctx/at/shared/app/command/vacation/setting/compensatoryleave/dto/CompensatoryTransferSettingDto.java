/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.vacation.setting.compensatoryleave.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.OneDayTime;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.TransferSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.TransferSettingDivision;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.TransferSettingGetMemento;

/**
 * The Class CompensatoryTransferSettingDto.
 */
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

	/**
	 * To domain.
	 *
	 * @return the transfer setting
	 */
	public TransferSetting toDomain() {
		return new TransferSetting(new TransferSettingGetMementoImpl(this));
	}

	/**
	 * The Class TransferSettingGetMementoImpl.
	 */
	public class TransferSettingGetMementoImpl implements TransferSettingGetMemento {

		/** The compensatory transfer setting dto. */
		private CompensatoryTransferSettingDto compensatoryTransferSettingDto;

		/**
		 * Instantiates a new transfer setting get memento impl.
		 *
		 * @param compensatoryTransferSettingDto the compensatory transfer setting dto
		 */
		public TransferSettingGetMementoImpl(CompensatoryTransferSettingDto compensatoryTransferSettingDto) {
			this.compensatoryTransferSettingDto = compensatoryTransferSettingDto;
		}

		/* (non-Javadoc)
		 * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave2.TransferSettingGetMemento#getCertainTime()
		 */
		@Override
		public OneDayTime getCertainTime() {
			return new OneDayTime(this.compensatoryTransferSettingDto.certainTime);
		}

		/* (non-Javadoc)
		 * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave2.TransferSettingGetMemento#isUseDivision()
		 */
		@Override
		public boolean isUseDivision() {
			return this.compensatoryTransferSettingDto.useDivision;
		}

		/* (non-Javadoc)
		 * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave2.TransferSettingGetMemento#getOneDayTime()
		 */
		@Override
		public OneDayTime getOneDayTime() {
			return new OneDayTime(this.compensatoryTransferSettingDto.oneDayTime);
		}

		/* (non-Javadoc)
		 * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave2.TransferSettingGetMemento#getHalfDayTime()
		 */
		@Override
		public OneDayTime getHalfDayTime() {
			return new OneDayTime(this.compensatoryTransferSettingDto.halfDayTime);
		}

		/* (non-Javadoc)
		 * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave2.TransferSettingGetMemento#getTransferDivision()
		 */
		@Override
		public TransferSettingDivision getTransferDivision() {
			return TransferSettingDivision.valueOf(this.compensatoryTransferSettingDto.transferDivision);
		}

	}
}
