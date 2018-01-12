/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.shift.basicworkregister;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.BasicWorkSetting;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkplaceBasicWork;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkplaceBasicWorkGetMemento;

/**
 * The Class WorkplaceBWSaveCommand.
 */
@Getter
@Setter
public class WorkplaceBWSaveCommand extends BaseBWSaveCommand {

	/** The workplace id. */
	private String workplaceId;

	/**
	 * To domain.
	 *
	 * @param companyId
	 *            the company id
	 * @return the daily pattern
	 */
	public WorkplaceBasicWork toDomain() {
		return new WorkplaceBasicWork(new GetMemento(this));
	}

	/**
	 * The Class JpaPatternCalendarSettingGetMemento.
	 */
	private class GetMemento implements WorkplaceBasicWorkGetMemento {

		/** The setting. */
		private WorkplaceBWSaveCommand command;

		/**
		 * Instantiates a new jpa pattern calendar setting get memento.
		 *
		 * @param companyId
		 *            the company id
		 * @param setting
		 *            the setting
		 */
		public GetMemento(WorkplaceBWSaveCommand command) {
			this.command = command;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.schedule.dom.shift.basicworkregister.
		 * WorkplaceBasicWorkGetMemento#getWorkPlaceId()
		 */
		@Override
		public String getWorkPlaceId() {
			return this.command.workplaceId;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.schedule.dom.shift.basicworkregister.
		 * WorkplaceBasicWorkGetMemento#getBasicWorkSetting()
		 */
		@Override
		public List<BasicWorkSetting> getBasicWorkSetting() {
			return this.command.basicWorkSetting.stream().map(item -> item.toDomain())
					.collect(Collectors.toList());
		}
	}

}
