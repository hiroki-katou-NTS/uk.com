/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.shift.totaltimes;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.schedule.dom.shift.pattern.WorkTypeCode;
import nts.uk.ctx.at.schedule.dom.shift.totaltimes.TotalSubjects;
import nts.uk.ctx.at.schedule.dom.shift.totaltimes.TotalSubjectsGetMemento;
import nts.uk.ctx.at.schedule.dom.shift.totaltimes.WorkTypeAtr;

/**
 * The Class TotalSubjectsDto.
 */
@Getter
@Setter
public class TotalSubjectsDto {

	/** The work type code. */
	private String workTypeCode;

	/** The work type atr. */
	private Integer workTypeAtr;

	/**
	 * To domain.
	 *
	 * @return the total subjects
	 */
	public TotalSubjects toDomain() {
		return new TotalSubjects(new DtoGetMemento(this));
	}

	/**
	 * The Class DtoGetMemento.
	 */
	private class DtoGetMemento implements TotalSubjectsGetMemento {

		/** The command. */
		private TotalSubjectsDto command;

		/**
		 * Instantiates a new dto get memento.
		 *
		 * @param command
		 *            the command
		 */
		public DtoGetMemento(TotalSubjectsDto command) {
			this.command = command;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * nts.uk.ctx.at.schedule.dom.shift.totaltimes.TotalSubjectsGetMemento#
		 * getWorkTypeCode()
		 */
		@Override
		public WorkTypeCode getWorkTypeCode() {
			return new WorkTypeCode(this.command.getWorkTypeCode());
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * nts.uk.ctx.at.schedule.dom.shift.totaltimes.TotalSubjectsGetMemento#
		 * getWorkTypeAtr()
		 */
		@Override
		public WorkTypeAtr getWorkTypeAtr() {
			return WorkTypeAtr.valueOf(this.command.getWorkTypeAtr());
		}
	}
}
