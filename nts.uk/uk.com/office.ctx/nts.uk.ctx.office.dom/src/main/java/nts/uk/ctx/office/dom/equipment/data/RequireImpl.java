package nts.uk.ctx.office.dom.equipment.data;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.office.dom.equipment.achievement.EquipmentUsageRecordItemSetting;

@Stateless
public class RequireImpl implements ItemData.Require {
	
	@Inject
	// TODO

	@Override
	public EquipmentUsageRecordItemSetting getItemSetting(String cid, String itemNo) {
		// TODO Auto-generated method stub
		return null;
	}

}
