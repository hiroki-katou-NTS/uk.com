/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.shift.basicworkregister.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.BasicWorkSetting;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.CompanyBasicWork;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.CompanyBasicWorkGetMemento;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.CompanyId;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyBasicWorkDto {

	/** The company id. */
	private String companyId;
	
	/** The basic work setting. */
	private BasicWorkSettingDto basicWorkSetting;

	/**
	 * To domain.
	 *
	 * @return the company basic work
	 */
	public CompanyBasicWork toDomain() {
		return new CompanyBasicWork(new GetMementoImpl(this));
	}

	/**
	 * The Class GetMementoImpl.
	 */
	private class GetMementoImpl implements CompanyBasicWorkGetMemento {
		
		/** The dto. */
		private CompanyBasicWorkDto dto;

		/**
		 * Instantiates a new gets the memento impl.
		 *
		 * @param dto the dto
		 */
		public GetMementoImpl(CompanyBasicWorkDto dto) {
			super();
			this.dto = dto;
		}

		/* (non-Javadoc)
		 * @see nts.uk.ctx.at.schedule.dom.shift.basicworkregister.CompanyBasicWorkGetMemento#getCompanyId()
		 */
		@Override
		public CompanyId getCompanyId() {
			return new CompanyId(dto.companyId);
		}

		/* (non-Javadoc)
		 * @see nts.uk.ctx.at.schedule.dom.shift.basicworkregister.CompanyBasicWorkGetMemento#getBasicWorkSetting()
		 */
		@Override
		public BasicWorkSetting getBasicWorkSetting() {
			return dto.basicWorkSetting.toDomain();
		}
	}
}
