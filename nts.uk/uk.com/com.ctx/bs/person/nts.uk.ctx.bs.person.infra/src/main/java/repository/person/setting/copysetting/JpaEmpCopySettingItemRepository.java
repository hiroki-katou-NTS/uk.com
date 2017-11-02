package repository.person.setting.copysetting;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.person.dom.person.info.setting.copysetting.EmpCopySettingItem;
import nts.uk.ctx.bs.person.dom.person.setting.copysetting.EmpCopySettingItemRepository;

@Stateless
public class JpaEmpCopySettingItemRepository extends JpaRepository implements EmpCopySettingItemRepository {

	private static final String SELECT_EMP_COPY_SETTING_ITEM_BY_CTG_ID_QUERY_STRING = "SELECT DISTINCT pi.itemCd,pi.itemName,pi.requiredAtr"
			+ " FROM PpestEmployeeCopySettingItem ci" + " INNER JOIN PpestEmployeeCopySetting cs "
			+ " ON ci.categoryId = cs.PpestEmployeeCopySettingPk.categoryId" + " INNER JOIN PpemtPerInfoCtg pc"
			+ " ON ci.categoryId = pc.ppemtPerInfoCtgPK.perInfoCtgId" + " INNER JOIN PpemtPerInfoItem pi"
			+ " ON ci.PpestEmployeeCopySettingItemPk.perInfoItemDefId=pi.ppemtPerInfoItemPK.perInfoItemDefId"
			+ " WHERE pc.categoryCd =:categoryCd AND pc.cid= :companyId";

	@Override
	public List<EmpCopySettingItem> getAllItemFromCategoryCd(String categoryCd, String companyId) {

		return this.queryProxy().query(SELECT_EMP_COPY_SETTING_ITEM_BY_CTG_ID_QUERY_STRING, Object[].class)
				.setParameter("categoryCd", categoryCd).setParameter("companyId", companyId).getList().stream()
				.map(x -> toDomain(x)).collect(Collectors.toList());
	}

	private EmpCopySettingItem toDomain(Object[] entity) {

		String itemCode = entity[0] != null ? entity[0].toString() : "";
		String itemName = entity[1] != null ? entity[1].toString() : "";
		int isRequired = entity[0] != null ? Integer.parseInt(entity[0].toString()) : 0;

		return EmpCopySettingItem.createFromJavaType(itemCode, itemName, isRequired);

	}

}
