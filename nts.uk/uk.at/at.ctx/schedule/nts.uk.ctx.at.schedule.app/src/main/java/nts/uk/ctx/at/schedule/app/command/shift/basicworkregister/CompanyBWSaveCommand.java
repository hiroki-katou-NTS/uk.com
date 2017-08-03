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
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.CompanyBasicWorkGetMemento;
import nts.uk.shr.com.context.AppContexts;


/**
 * The Class CompanyBWSaveCommand.
 */
@Data
public class CompanyBWSaveCommand implements CompanyBasicWorkGetMemento {

	/** The basic work setting. */
	private List<BasicWorkSettingDto> basicWorkSetting;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.basicworkregister.
	 * CompanyBasicWorkGetMemento#getCompanyId()
	 */
	@Override
	public String getCompanyId() {
		return AppContexts.user().companyId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.basicworkregister.
	 * CompanyBasicWorkGetMemento#getBasicWorkSetting()
	 */
	@Override
	public List<BasicWorkSetting> getBasicWorkSetting() {
		return this.basicWorkSetting.stream().map(dto -> dto.toDomain()).collect(Collectors.toList());
	}
}
