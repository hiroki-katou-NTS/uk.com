package nts.uk.ctx.bs.person.infra.repository.person.setting.copy;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.person.dom.person.info.setting.copysetting.EmpCopySettingItem;
import nts.uk.ctx.bs.person.dom.person.setting.copysetting.EmpCopySettingItemRepository;

@Stateless
public class JpaEmpCopySettingItemRepository extends JpaRepository implements EmpCopySettingItemRepository {

	private static final String SELECT_EMP_COPY_SETTING_ITEM_BY_CTG_ID_QUERY_STRING = "SELECT DISTINCT pi.perInfoCtgId,pc.categoryCd,pi.ppemtPerInfoItemPK.perInfoItemDefId,pi.itemCd,pi.itemName,pi.requiredAtr"
			+ " FROM PpestEmployeeCopySettingItem ci" 
			+ " INNER JOIN PpestEmployeeCopySetting cs "
			+ " ON ci.categoryId = cs.ppestEmployeeCopySettingPk.categoryId" 
			+ " INNER JOIN PpemtPerInfoCtg pc"
			+ " ON ci.categoryId = pc.ppemtPerInfoCtgPK.perInfoCtgId" 
			+ " INNER JOIN PpemtPerInfoItem pi"
			+ " ON ci.PpestEmployeeCopySettingItemPk.perInfoItemDefId=pi.ppemtPerInfoItemPK.perInfoItemDefId"
			+ " INNER JOIN PpemtPersonItemAuth pa"
			+ " ON ci.PpestEmployeeCopySettingItemPk.perInfoItemDefId=pa.ppemtPersonItemAuthPk.personItemDefId"
			+ " WHERE pc.categoryCd =:categoryCd AND pc.cid=:companyId";
	private static final String CHECK_OTHER_AUTH = " AND pa.otherPersonAuthType!=1";

	private static final String CHECK_SELF_AUTH = " AND pa.selfAuthType!=1";

	@Override
	public List<EmpCopySettingItem> getAllItemFromCategoryCd(String categoryCd, String companyId, boolean isSelf) {

		return this.queryProxy()
				.query(SELECT_EMP_COPY_SETTING_ITEM_BY_CTG_ID_QUERY_STRING
						+ (isSelf ? CHECK_SELF_AUTH : CHECK_OTHER_AUTH), Object[].class)
				.setParameter("categoryCd", categoryCd).setParameter("companyId", companyId).getList().stream()
				.map(x -> toDomain(x)).collect(Collectors.toList());
	}

	private EmpCopySettingItem toDomain(Object[] entity) {

		String perInfoCtgId = entity[0] != null ? entity[0].toString() : "";
		String categoryCd = entity[1] != null ? entity[1].toString() : "";
		String perInfoItemDefId = entity[2] != null ? entity[2].toString() : "";
		String itemCode = entity[3] != null ? entity[3].toString() : "";
		String itemName = entity[4] != null ? entity[4].toString() : "";
		int isRequired = entity[5] != null ? Integer.parseInt(entity[5].toString()) : 0;

		return EmpCopySettingItem.createFromJavaType(perInfoCtgId, categoryCd, perInfoItemDefId, itemCode, itemName,
				isRequired);

	}

}
