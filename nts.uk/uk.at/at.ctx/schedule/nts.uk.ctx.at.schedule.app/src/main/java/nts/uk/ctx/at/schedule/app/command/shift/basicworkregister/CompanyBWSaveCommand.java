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
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.CompanyBasicWork;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.CompanyBasicWorkGetMemento;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class CompanyBWSaveCommand.
 */
@Getter
@Setter
public class CompanyBWSaveCommand extends BaseBWSaveCommand {

	/**
	 * To domain.
	 *
	 * @param companyId
	 *            the company id
	 * @return the daily pattern
	 */
	public CompanyBasicWork toDomain() {
		return new CompanyBasicWork(new GetMemento(this));
	}

	/**
	 * The Class JpaPatternCalendarSettingGetMemento.
	 */
	private class GetMemento implements CompanyBasicWorkGetMemento {

		/** The setting. */
		private CompanyBWSaveCommand command;

		/**
		 * Instantiates a new jpa pattern calendar setting get memento.
		 *
		 * @param companyId
		 *            the company id
		 * @param setting
		 *            the setting
		 */
		public GetMemento(CompanyBWSaveCommand command) {
			this.command = command;
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

		@Override
		public String getCompanyId() {
			return AppContexts.user().companyId();
		}
	}
}
