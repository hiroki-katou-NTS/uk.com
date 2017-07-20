/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.find.shift.basicworkregister.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.BasicWorkSetting;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.CompanyBasicWorkSetMemento;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.CompanyId;


@Getter
@Setter
public class CompanyBasicWorkFindDto implements CompanyBasicWorkSetMemento{

	/** The company id. */
	private String companyId;

	/** The basic work setting. */
	private List<BasicWorkSettingFindDto> basicWorkSetting;

	@Override
	public void setCompanyId(CompanyId companyId) {
		this.companyId = companyId.v();
	}

	@Override
	public void setBasicWorkSetting(List<BasicWorkSetting> basicWorkSetting) {
		// TODO Auto-generated method stub
		
	}
}
