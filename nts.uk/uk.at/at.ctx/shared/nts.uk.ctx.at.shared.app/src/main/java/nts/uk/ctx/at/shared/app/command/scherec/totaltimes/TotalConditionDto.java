/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.scherec.totaltimes;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.ConditionThresholdLimit;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalCondition;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.UseAtr;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.memento.TotalConditionGetMemento;

/**
 * The Class TotalConditionDto.
 */
@Getter
@Setter
public class TotalConditionDto {

	/** The upper limit setting atr. */
	private Integer upperLimitSettingAtr;

	/** The lower limit setting atr. */
	private Integer lowerLimitSettingAtr;

	/** The thresold upper limit. */
	private Long thresoldUpperLimit;

	/** The thresold lower limit. */
	private Long thresoldLowerLimit;
	
	/** The attendance item id. */
	private Integer attendanceItemId;

	/**
	 * To domain.
	 *
	 * @return the total condition
	 */
	public TotalCondition toDomain() {
		return new TotalCondition(new DtoGetMemento(this));
	}

	/**
	 * The Class DtoGetMemento.
	 */
	private class DtoGetMemento implements TotalConditionGetMemento {

		/** The command. */
		private TotalConditionDto command;

		/**
		 * Instantiates a new dto get memento.
		 *
		 * @param command
		 *            the command
		 */
		public DtoGetMemento(TotalConditionDto command) {
			this.command = command;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalConditionGetMemento#
		 * getUpperLimitSettingAtr()
		 */
		@Override
		public UseAtr getUpperLimitSettingAtr() {
			return UseAtr.valueOf(this.command.getUpperLimitSettingAtr());
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalConditionGetMemento#
		 * getLowerLimitSettingAtr()
		 */
		@Override
		public UseAtr getLowerLimitSettingAtr() {
			return UseAtr.valueOf(this.command.getLowerLimitSettingAtr());
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalConditionGetMemento#
		 * getThresoldUpperLimit()
		 */
		@Override
		public Optional<ConditionThresholdLimit> getThresoldUpperLimit() {
			if (this.command.getThresoldUpperLimit() == null) {
				return Optional.empty();
			}
			
			return Optional.of(new ConditionThresholdLimit(this.command.getThresoldUpperLimit().intValue()));
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalConditionGetMemento#
		 * getThresoldLowerLimit()
		 */
		@Override
		public Optional<ConditionThresholdLimit> getThresoldLowerLimit() {
			if (this.command.getThresoldLowerLimit() == null) {
				return Optional.empty();
			}
			
			return Optional.of(new ConditionThresholdLimit(this.command.getThresoldLowerLimit().intValue()));
		}

		
		/* (non-Javadoc)
		 * @see nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalConditionGetMemento#getAttendanceItemId()
		 */
		@Override
		public Optional<Integer> getAttendanceItemId() {
			return Optional.ofNullable(this.command.getAttendanceItemId());
		}

	}

}
