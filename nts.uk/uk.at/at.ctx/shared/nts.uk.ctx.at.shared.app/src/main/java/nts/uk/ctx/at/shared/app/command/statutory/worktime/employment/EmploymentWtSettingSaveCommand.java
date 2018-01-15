/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.employment;

import lombok.Data;
import nts.uk.ctx.at.shared.app.find.statutory.worktime.shared.DeformationLaborSettingDto;
import nts.uk.ctx.at.shared.app.find.statutory.worktime.shared.FlexSettingDto;
import nts.uk.ctx.at.shared.app.find.statutory.worktime.shared.NormalSettingDto;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employment.EmploymentWtSettingGetMemento;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.DeformationLaborSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.FlexSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.NormalSetting;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class EmploymentWtSettingSaveCommand.
 */

/**
 * Instantiates a new employment wt setting save command.
 */
@Data
public class EmploymentWtSettingSaveCommand implements EmploymentWtSettingGetMemento {

	/** The flex setting. */
	private FlexSettingDto flexSetting;

	/** The deformation labor setting. */
	private DeformationLaborSettingDto deformationLaborSetting;

	/** The year. */
	private int year;

	/** The normal setting. */
	private NormalSettingDto normalSetting;

	/** The employment code. */
	private String employmentCode;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.employment.statutory.worktime.
	 * CompanySettingGetMemento#getFlexSetting()
	 */
	@Override
	public FlexSetting getFlexSetting() {
		return FlexSettingDto.toDomain(this.flexSetting);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.employment.statutory.worktime.
	 * CompanySettingGetMemento#getDeformationLaborSetting()
	 */
	@Override
	public DeformationLaborSetting getDeformationLaborSetting() {
		return DeformationLaborSettingDto.toDomain(this.deformationLaborSetting);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.employment.statutory.worktime.
	 * CompanySettingGetMemento#getYear()
	 */
	@Override
	public Year getYear() {
		return new Year(this.year);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.employment.statutory.worktime.
	 * CompanySettingGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(AppContexts.user().companyId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.employment.statutory.worktime.
	 * CompanySettingGetMemento#getNormalSetting()
	 */
	@Override
	public NormalSetting getNormalSetting() {
		return NormalSettingDto.toDomain(this.normalSetting);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.employment.statutory.worktime.employment.
	 * EmploymentWtSettingGetMemento#getEmploymentCode()
	 */
	@Override
	public String getEmploymentCode() {
		return this.employmentCode;
	}

}
