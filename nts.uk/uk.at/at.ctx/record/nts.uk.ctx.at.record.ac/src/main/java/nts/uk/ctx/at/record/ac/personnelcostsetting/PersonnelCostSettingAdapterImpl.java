package nts.uk.ctx.at.record.ac.personnelcostsetting;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.personnelcostsetting.PersonnelCostSettingAdapter;
import nts.uk.ctx.at.record.dom.adapter.personnelcostsetting.PersonnelCostSettingImport;
import nts.uk.ctx.at.schedule.pub.budget.premium.PersonCostSettingExport;
import nts.uk.ctx.at.schedule.pub.budget.premium.PremiumItemPub;

@Stateless
public class PersonnelCostSettingAdapterImpl implements PersonnelCostSettingAdapter{
	
	@Inject
	private PremiumItemPub premiumItemPub;
	
	@Override
	public List<PersonnelCostSettingImport> findAll(String companyId, GeneralDate baseDate){
		
		List<PersonCostSettingExport> export = premiumItemPub.getPersonCostSetting(companyId, baseDate);
		return export.stream().map(x -> {
			return new PersonnelCostSettingImport(x.getPremiumNo(), x.getAttendanceItems());
		}).collect(Collectors.toList());
		
	}
	
	
}
