package nts.uk.ctx.at.record.dom.adapter.personnelcostsetting;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.PersonnelCostSettingImport;

public interface PersonnelCostSettingAdapter {
	
	List<PersonnelCostSettingImport> findAll(String companyId, GeneralDate baseDate);

	public List<PersonnelCostSettingImport> findAll(String companyId, DatePeriod period);
}
