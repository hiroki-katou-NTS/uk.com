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
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.CompanyBasicWorkSetMemento;


/**
 * The Class CompanyBasicWorkFindDto.
 */
@Getter
@Setter
public class CompanyBasicWorkFindDto implements CompanyBasicWorkSetMemento {

	/** The company id. */
	private String companyId;

	/** The basic work setting. */
	private List<BasicWorkSettingFindDto> basicWorkSetting;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.basicworkregister.
	 * CompanyBasicWorkSetMemento#setCompanyId(nts.uk.ctx.at.schedule.dom.shift.
	 * basicworkregister.CompanyId)
	 */
	@Override
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.basicworkregister.
	 * CompanyBasicWorkSetMemento#setBasicWorkSetting(java.util.List)
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
