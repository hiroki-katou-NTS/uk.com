package repository.person.info.setting;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.bs.person.dom.person.setting.init.item.PerInfoInitValueSetItem;
import nts.uk.ctx.bs.person.dom.person.setting.init.item.PerInfoInitValueSetItemRepository;
@Stateless
public class JpaPerInfoInitValSetItem extends JpaRepository implements PerInfoInitValueSetItemRepository{
	
	private final String CHECK_ITEM_IS_EXITED = "SELECT a.ppemtPerInfoItemPK.perInfoItemDefId from PpemtPerInfoItem a"
			+ " INNER JOIN PpemtPerInfoItemOrder b"
			+ " ON a.ppemtPerInfoItemPK.perInfoItemDefId = b.ppemtPerInfoItemPK.perInfoItemDefId "
			+ " AND a.perInfoCtgId = b.perInfoCtgId "
			+ " WHERE a.abolitionAtr = 0"
			+ " AND a.perInfoCtgId =:perInfoCtgId"
			+ " ORDER BY b.disporder";
	
	private final String SEL_ALL_ITEM = " SELECT c FROM PpemtPerInfoItem c "
			+ " INNER JOIN PpemtPersonInitValueSettingItem b"
			+ " ON c.settingItemPk.perInfoItemDefId = b.ppemtPerInfoItemPK.perInfoItemDefId "
			+ " AND c.settingItemPk.perInfoCtgId = b.perInfoCtgId"
			+ " WHERE b.abolitionAtr = 0"
			+ " AND c.perInfoCtgId =:perInfoCtgId";

	
	
	
	@Override
	public List<PerInfoInitValueSetItem> getAllItem(String initValueSettingCtgId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PerInfoInitValueSetItem getDetailItem(String initValueSettingCtgId, String perInfoItemDefId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isExist(String perInfoCtgId) {
		List<Object[]> itemDefLst = this.queryProxy().query(CHECK_ITEM_IS_EXITED, Object[].class)
				.setParameter("perInfoCtgId", perInfoCtgId).getList();
		
		if(CollectionUtil.isEmpty(itemDefLst)) {
			return false;
		}
		return true;
	}

}
