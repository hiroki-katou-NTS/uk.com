package nts.uk.ctx.at.record.dom.adapter.personnelcostsetting;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface PersonnelCostSettingAdapter {
	
	List<PersonnelCostSettingImport> findAll(String companyId, GeneralDate baseDate);

}
