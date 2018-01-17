/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.find.shift.basicworkregister.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.BasicWorkSetting;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkplaceBasicWorkSetMemento;


/**
 * The Class WorkplaceBasicWorkFindDto.
 */
@Getter
@Setter
public class WorkplaceBasicWorkFindDto implements WorkplaceBasicWorkSetMemento {
	
	/** The workplace id. */
	private String workplaceId;
	
	/** The basic work setting. */
	private List<BasicWorkSettingFindDto> basicWorkSetting;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.basicworkregister.
	 * WorkplaceBasicWorkSetMemento#setWorkPlaceId(nts.uk.ctx.at.schedule.dom.
	 * shift.basicworkregister.WorkplaceId)
	 */
	@Override
	public void setWorkPlaceId(String workplaceId) {
		this.workplaceId = workplaceId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.basicworkregister.
	 * WorkplaceBasicWorkSetMemento#setBasicWorkSetting(java.util.List)
	 */
	@Override
	public void setBasicWorkSetting(List<BasicWorkSetting> basicWorkSetting) {
		this.basicWorkSetting = basicWorkSetting.stream().map(item -> {
			BasicWorkSettingFindDto basicWorkSettingFindDto = new BasicWorkSettingFindDto();
			item.saveToMemento(basicWorkSettingFindDto);
			return basicWorkSettingFindDto;
		}).collect(Collectors.toList());
	}

}
