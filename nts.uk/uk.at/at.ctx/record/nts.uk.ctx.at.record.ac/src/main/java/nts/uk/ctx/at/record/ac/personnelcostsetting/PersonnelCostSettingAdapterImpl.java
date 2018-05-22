package nts.uk.ctx.at.record.ac.personnelcostsetting;

import java.util.ArrayList;
import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.personnelcostsetting.PersonnelCostSettingAdapter;
import nts.uk.ctx.at.record.dom.adapter.personnelcostsetting.PersonnelCostSettingImport;

public class PersonnelCostSettingAdapterImpl implements PersonnelCostSettingAdapter{
	
	@Override
	public List<PersonnelCostSettingImport> find(String companyId, GeneralDate baseDate){
		
		List<PersonnelCostSettingImport> result = new ArrayList<>();
		
		//この下にベトナムが製造したpublishのメソッドを基に取得ロジックを書く事
		
		
		return result;
	}
	
	
}
