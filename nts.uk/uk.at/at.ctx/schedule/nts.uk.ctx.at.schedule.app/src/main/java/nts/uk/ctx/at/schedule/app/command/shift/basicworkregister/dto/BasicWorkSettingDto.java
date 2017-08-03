/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.shift.basicworkregister.dto;

import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.BasicWorkSetting;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.BasicWorkSettingGetMemento;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkdayDivision;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkingCode;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorktypeCode;

/**
 * The Class BasicWorkSettingDto.
 */
@Getter
@Setter
public class BasicWorkSettingDto {

	/** The work type code. */
	private String workTypeCode;
	
	/** The sift code. */
	private String siftCode;
	
	/** The work day division. */
	private int workDayDivision;

	/**
	 * To domain.
	 *
	 * @return the basic work setting
	 */
	public BasicWorkSetting toDomain() {
		return new BasicWorkSetting(new GetMementoImpl(this));
	}

	
	/**
	 * Instantiates a new basic work setting dto.
	 */
	public BasicWorkSettingDto() {
	}


	/**
	 * The Class GetMementoImpl.
	 */
	private class GetMementoImpl implements BasicWorkSettingGetMemento {

		/** The dto. */
		private BasicWorkSettingDto dto;

		/**
		 * Instantiates a new gets the memento impl.
		 *
		 * @param dto the dto
		 */
		public GetMementoImpl(BasicWorkSettingDto dto) {
			super();
			this.dto = dto;
		}

		/* (non-Javadoc)
		 * @see nts.uk.ctx.at.schedule.dom.shift.basicworkregister.BasicWorkSettingGetMemento#getWorkTypecode()
		 */
		@Override
		public WorktypeCode getWorkTypecode() {
			return new WorktypeCode(dto.getWorkTypeCode());
		}

		/* (non-Javadoc)
		 * @see nts.uk.ctx.at.schedule.dom.shift.basicworkregister.BasicWorkSettingGetMemento#getSiftCode()
		 */
		@Override
		public WorkingCode getSiftCode() {
			return new WorkingCode(dto.getSiftCode());
		}

		/* (non-Javadoc)
		 * @see nts.uk.ctx.at.schedule.dom.shift.basicworkregister.BasicWorkSettingGetMemento#getWorkDayDivision()
		 */
		@Override
		public WorkdayDivision getWorkDayDivision() {
			return  EnumAdaptor.valueOf(dto.getWorkDayDivision(), WorkdayDivision.class);
		}
	}
}
