/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.vacation.setting.compensatoryleave.find.dto;

import nts.uk.ctx.at.shared.app.vacation.setting.compensatoryleave.find.dto.NormalVacationSettingDto;
import nts.uk.ctx.at.shared.app.vacation.setting.compensatoryleave.find.dto.OccurrenceVacationSettingDto;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComSetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.NormalVacationSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.OccurrenceVacationSetting;

public class CompensatoryLeaveComSettingDto implements CompensatoryLeaveComSetMemento {
	/** The company id. */
	public String companyId;

	/** The is managed. */
	public ManageDistinct isManaged;

	/** The normal vacation setting. */
	public NormalVacationSettingDto normalVacationSetting;

	/** The occurrence vacation setting. */
	public OccurrenceVacationSettingDto occurrenceVacationSetting;

	@Override
	public void setCompanyId(String companyId) {
	}

	@Override
	public void setIsManaged(ManageDistinct isManaged) {
		this.isManaged = isManaged;
	}

	@Override
	public void setNormalVacationSetting(NormalVacationSetting normalVacationSetting) {
		NormalVacationSettingDto normal = new NormalVacationSettingDto();
		normalVacationSetting.saveToMemento(normal);
		this.normalVacationSetting = normal;
	}

	@Override
	public void setOccurrenceVacationSetting(OccurrenceVacationSetting occurrenceVacationSetting) {
		OccurrenceVacationSettingDto occ = new OccurrenceVacationSettingDto();
		occurrenceVacationSetting.saveToMemento(occ);
		this.occurrenceVacationSetting = occ;
	}
}
