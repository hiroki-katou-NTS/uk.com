package nts.uk.ctx.at.record.dom.adapter.personnelcostsetting;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface PersonnelCostSettingAdapter {
	
	List<PersonnelCostSettingImport> findAll(String companyId, GeneralDate baseDate);

	public List<PersonnelCostSettingImport> findAll(String companyId, DatePeriod period);
}
