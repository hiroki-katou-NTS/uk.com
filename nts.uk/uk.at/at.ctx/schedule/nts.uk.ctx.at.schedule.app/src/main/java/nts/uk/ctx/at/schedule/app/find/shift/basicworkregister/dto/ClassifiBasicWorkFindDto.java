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
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.ClassifiBasicWorkSetMemento;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.ClassificationCode;

/**
 * The Class ClassifiBasicWorkFindDto.
 */
@Getter
@Setter
public class ClassifiBasicWorkFindDto implements ClassifiBasicWorkSetMemento {

	/** The company id. */
	private String companyId;

	/** The classification code. */
	private String classificationCode;

	/** The basic work setting. */
	private List<BasicWorkSettingFindDto> basicWorkSetting;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.basicworkregister.
	 * ClassifiBasicWorkSetMemento#setCompanyId(nts.uk.ctx.at.schedule.dom.shift
	 * .basicworkregister.CompanyId)
	 */
	@Override
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.basicworkregister.
	 * ClassifiBasicWorkSetMemento#setClassificationCode(nts.uk.ctx.at.schedule.
	 * dom.shift.basicworkregister.ClassificationCode)
	 */
	@Override
	public void setClassificationCode(ClassificationCode classificationCode) {
		this.classificationCode = classificationCode.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.basicworkregister.
	 * ClassifiBasicWorkSetMemento#setBasicWorkSetting(java.util.List)
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
