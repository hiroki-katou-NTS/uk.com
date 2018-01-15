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
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.ClassifiBasicWorkGetMemento;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.ClassificationBasicWork;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.ClassificationCode;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class ClassifiBWSaveCommand.
 */
@Getter
@Setter
public class ClassifiBWSaveCommand extends BaseBWSaveCommand {

	/** The classification code. */
	private String classificationCode;

	/**
	 * To domain.
	 *
	 * @param companyId
	 *            the company id
	 * @return the daily pattern
	 */
	public ClassificationBasicWork toDomain() {
		return new ClassificationBasicWork(new GetMemento(this));
	}

	/**
	 * The Class JpaPatternCalendarSettingGetMemento.
	 */
	private class GetMemento implements ClassifiBasicWorkGetMemento {

		/** The setting. */
		private ClassifiBWSaveCommand command;

		/**
		 * Instantiates a new jpa pattern calendar setting get memento.
		 *
		 * @param companyId
		 *            the company id
		 * @param setting
		 *            the setting
		 */
		public GetMemento(ClassifiBWSaveCommand command) {
			this.command = command;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.schedule.dom.shift.basicworkregister.
		 * ClassifiBasicWorkGetMemento#getCompanyId()
		 */
		@Override
		public String getCompanyId() {
			return AppContexts.user().companyId();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.schedule.dom.shift.basicworkregister.
		 * ClassifiBasicWorkGetMemento#getClassificationCode()
		 */
		@Override
		public ClassificationCode getClassificationCode() {
			return new ClassificationCode(this.command.classificationCode);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.schedule.dom.shift.basicworkregister.
		 * ClassifiBasicWorkGetMemento#getBasicWorkSetting()
		 */
		@Override
		public List<BasicWorkSetting> getBasicWorkSetting() {
			return this.command.basicWorkSetting.stream().map(dto -> dto.toDomain())
					.collect(Collectors.toList());
		}
	}

}
