/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.vacation.setting.compensatoryleave.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.common.DesignatedTime;
import nts.uk.ctx.at.shared.dom.worktime.common.OneDayTime;
import nts.uk.ctx.at.shared.dom.worktime.common.SubHolTransferSet;
import nts.uk.ctx.at.shared.dom.worktime.common.SubHolTransferSetAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.SubHolTransferSetGetMemento;

/**
 * The Class CompensatoryTransferSettingDto.
 */

/**
 * Gets the transfer division.
 *
 * @return the transfer division
 */
@Getter

/**
 * Sets the transfer division.
 *
 * @param transferDivision
 *            the new transfer division
 */
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
	 * @return the sub hol transfer set
	 */
	public SubHolTransferSet toDomain() {
		return new SubHolTransferSet(new TransferSettingGetMementoImpl(this));
	}

	/**
	 * The Class TransferSettingGetMementoImpl.
	 */
	public class TransferSettingGetMementoImpl implements SubHolTransferSetGetMemento {

		/** The compensatory transfer setting dto. */
		private CompensatoryTransferSettingDto compensatoryTransferSettingDto;

		/**
		 * Instantiates a new transfer setting get memento impl.
		 *
		 * @param compensatoryTransferSettingDto
		 *            the compensatory transfer setting dto
		 */
		public TransferSettingGetMementoImpl(
				CompensatoryTransferSettingDto compensatoryTransferSettingDto) {
			this.compensatoryTransferSettingDto = compensatoryTransferSettingDto;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave2.
		 * TransferSettingGetMemento#getCertainTime()
		 */
		@Override
		public OneDayTime getCertainTime() {
			return new OneDayTime(this.compensatoryTransferSettingDto.certainTime);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * nts.uk.ctx.at.shared.dom.worktime.common.SubHolTransferSetGetMemento#
		 * getUseDivision()
		 */
		@Override
		public boolean getUseDivision() {
			return this.compensatoryTransferSettingDto.useDivision;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * nts.uk.ctx.at.shared.dom.worktime.common.SubHolTransferSetGetMemento#
		 * getDesignatedTime()
		 */
		@Override
		public DesignatedTime getDesignatedTime() {
			return new DesignatedTime(
					new OneDayTime(this.compensatoryTransferSettingDto.oneDayTime),
					new OneDayTime(this.compensatoryTransferSettingDto.halfDayTime));
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * nts.uk.ctx.at.shared.dom.worktime.common.SubHolTransferSetGetMemento#
		 * getSubHolTransferSetAtr()
		 */
		@Override
		public SubHolTransferSetAtr getSubHolTransferSetAtr() {
			return SubHolTransferSetAtr
					.valueOf(this.compensatoryTransferSettingDto.transferDivision);
		}

	}
}
