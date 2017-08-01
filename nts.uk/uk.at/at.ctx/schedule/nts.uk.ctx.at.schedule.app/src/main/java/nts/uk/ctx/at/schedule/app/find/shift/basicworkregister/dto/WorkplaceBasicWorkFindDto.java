package nts.uk.ctx.at.schedule.app.find.shift.basicworkregister.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.BasicWorkSetting;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkplaceBasicWorkSetMemento;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkplaceId;

@Getter
@Setter
public class WorkplaceBasicWorkFindDto implements WorkplaceBasicWorkSetMemento {
	
	/** The workplace id. */
	private String workplaceId;
	
	/** The basic work setting. */
	private List<BasicWorkSettingFindDto> basicWorkSetting;

	@Override
	public void setWorkPlaceId(WorkplaceId workplaceId) {
		this.workplaceId = workplaceId.v();
	}

	@Override
	public void setBasicWorkSetting(List<BasicWorkSetting> basicWorkSetting) {
		this.basicWorkSetting = basicWorkSetting.stream().map(item -> {
			BasicWorkSettingFindDto basicWorkSettingFindDto = new BasicWorkSettingFindDto();
			item.saveToMemento(basicWorkSettingFindDto);
			return basicWorkSettingFindDto;
		}).collect(Collectors.toList());
	}

}
