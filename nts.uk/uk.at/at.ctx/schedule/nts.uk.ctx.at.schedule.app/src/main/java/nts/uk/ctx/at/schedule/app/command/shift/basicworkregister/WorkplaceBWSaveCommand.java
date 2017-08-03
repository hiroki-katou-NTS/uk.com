/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.shift.basicworkregister;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import nts.uk.ctx.at.schedule.app.command.shift.basicworkregister.dto.BasicWorkSettingDto;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.BasicWorkSetting;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkplaceBasicWorkGetMemento;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkplaceId;


/**
 * The Class WorkplaceBWSaveCommand.
 */
@Data
public class WorkplaceBWSaveCommand implements WorkplaceBasicWorkGetMemento {
	
	/** The workplace id. */
	private String workplaceId;
	
	/** The basic work setting. */
	private List<BasicWorkSettingDto> basicWorkSetting;
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkplaceBasicWorkGetMemento#getWorkPlaceId()
	 */
	@Override
	public WorkplaceId getWorkPlaceId() {
		return new WorkplaceId(this.workplaceId);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkplaceBasicWorkGetMemento#getBasicWorkSetting()
	 */
	@Override
	public List<BasicWorkSetting> getBasicWorkSetting() {
		return this.basicWorkSetting.stream().map(item -> item.toDomain())
				.collect(Collectors.toList());
	}
}
