/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.shift.basicworkregister.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.BasicWorkSetting;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkplaceBasicWork;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkplaceBasicWorkGetMemento;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkplaceId;

/**
 * The Class WorkplaceBasicWorkDto.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkplaceBasicWorkDto {

	/** The workplace id. */
	private String workplaceId;
	
	/** The basic work setting. */
	private List<BasicWorkSettingDto> basicWorkSetting;

	/**
	 * To domain.
	 *
	 * @return the workplace basic work
	 */
	public WorkplaceBasicWork toDomain() {
		return new WorkplaceBasicWork(new GetMementoImpl(this));
	}

	/**
	 * The Class GetMementoImpl.
	 */
	private class GetMementoImpl implements WorkplaceBasicWorkGetMemento {

		/** The dto. */
		WorkplaceBasicWorkDto dto;

		/**
		 * Instantiates a new gets the memento impl.
		 *
		 * @param dto the dto
		 */
		public GetMementoImpl(WorkplaceBasicWorkDto dto) {
			super();
			this.dto = dto;
		}

		/* (non-Javadoc)
		 * @see nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkplaceBasicWorkGetMemento#getWorkPlaceId()
		 */
		@Override
		public WorkplaceId getWorkPlaceId() {
			return new WorkplaceId(dto.workplaceId);
		}

		/* (non-Javadoc)
		 * @see nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkplaceBasicWorkGetMemento#getBasicWorkSetting()
		 */
		@Override
		public List<BasicWorkSetting> getBasicWorkSetting() {
			return dto.basicWorkSetting.stream().map(item -> item.toDomain())
					.collect(Collectors.toList());
		}

	}
}
