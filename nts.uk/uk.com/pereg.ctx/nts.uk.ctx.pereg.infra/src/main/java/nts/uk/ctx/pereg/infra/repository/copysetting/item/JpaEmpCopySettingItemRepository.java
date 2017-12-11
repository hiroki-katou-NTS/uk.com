package nts.uk.ctx.pereg.infra.repository.copysetting.item;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pereg.dom.copysetting.item.EmpCopySettingItem;
import nts.uk.ctx.pereg.dom.copysetting.item.EmpCopySettingItemRepository;
import nts.uk.ctx.pereg.infra.entity.copysetting.item.PpestEmployeeCopySettingItem;
import nts.uk.ctx.pereg.infra.entity.copysetting.item.PpestEmployeeCopySettingItemPk;

@Stateless
public class JpaEmpCopySettingItemRepository extends JpaRepository implements EmpCopySettingItemRepository {

	private static final String SELECT_EMP_COPY_SETTING_ITEM_BY_CTG_ID_QUERY_STRING = "SELECT DISTINCT pi.perInfoCtgId,pc.categoryCd,pi.ppemtPerInfoItemPK.perInfoItemDefId,pi.itemCd,pi.itemName,pi.requiredAtr,pm.dataType"
			+ " FROM PpestEmployeeCopySettingItem ci" + " INNER JOIN PpestEmployeeCopySetting cs "
			+ " ON ci.categoryId = cs.ppestEmployeeCopySettingPk.categoryId" + " INNER JOIN PpemtPerInfoCtg pc"
			+ " ON ci.categoryId = pc.ppemtPerInfoCtgPK.perInfoCtgId" + " INNER JOIN PpemtPerInfoItem pi"
			+ " ON ci.ppestEmployeeCopySettingItemPk.perInfoItemDefId=pi.ppemtPerInfoItemPK.perInfoItemDefId"
			+ " INNER JOIN PpemtPerInfoItemCm pm"
			+ " ON pi.itemCd = pm.ppemtPerInfoItemCmPK.itemCd AND pc.categoryCd = pm.ppemtPerInfoItemCmPK.categoryCd"
			+ " INNER JOIN PpemtPersonItemAuth pa"
			+ " ON ci.ppestEmployeeCopySettingItemPk.perInfoItemDefId=pa.ppemtPersonItemAuthPk.personItemDefId"
			+ " WHERE pc.categoryCd =:categoryCd AND pc.cid=:companyId";
	private static final String CHECK_OTHER_AUTH = " AND pa.otherPersonAuthType!=1";

	private static final String CHECK_SELF_AUTH = " AND pa.selfAuthType!=1";

	private final static String SELECT_PERINFOITEM_BYCTGID = "SELECT i.ppemtPerInfoItemPK.perInfoItemDefId, i.itemName,"
			+ " CASE WHEN (ci.ppestEmployeeCopySettingItemPk.perInfoItemDefId) IS NOT NULL  THEN 'True' ELSE 'False' END AS alreadyCopy "
			+ " FROM PpemtPerInfoItem i INNER JOIN PpemtPerInfoCtg c ON i.perInfoCtgId = c.ppemtPerInfoCtgPK.perInfoCtgId"
			+ " LEFT JOIN PpestEmployeeCopySettingItem ci ON i.ppemtPerInfoItemPK.perInfoItemDefId = ci.ppestEmployeeCopySettingItemPk.perInfoItemDefId"
			+ " WHERE c.cid = :companyId AND i.perInfoCtgId = :perInfoCtgId";

	private final static String COUNT_ITEMS_IN_COPYITEM = "SELECT COUNT(s) FROM PpestEmployeeCopySettingItem s "
			+ " JOIN PpemtPerInfoItem i ON i.ppemtPerInfoItemPK.perInfoItemDefId = s.ppestEmployeeCopySettingItemPk.perInfoItemDefId "
			+ " JOIN PpemtPerInfoCtg c ON c.ppemtPerInfoCtgPK.perInfoCtgId = i.perInfoCtgId "
			+ " WHERE s.ppestEmployeeCopySettingItemPk.perInfoItemDefId = :perInfoItemDefId AND c.cid = :companyId";

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
		int dataType = entity[6] != null ? Integer.parseInt(entity[6].toString()) : 0;

		return EmpCopySettingItem.createFromJavaType(perInfoCtgId, categoryCd, perInfoItemDefId, itemCode, itemName,
				isRequired, dataType);

	}

	@Override
	public void removePerInfoItemInCopySetting(String itemId) {
		this.commandProxy().remove(PpestEmployeeCopySettingItem.class, new PpestEmployeeCopySettingItemPk(itemId));
	}

	@Override
	public void updatePerInfoItemInCopySetting(String perInforCtgId, List<String> perInfoItemDefIds) {
		for (String perInfoItemDefId : perInfoItemDefIds) {
			val entity = new PpestEmployeeCopySettingItem(new PpestEmployeeCopySettingItemPk(perInfoItemDefId),
					perInforCtgId);
			this.commandProxy().insert(entity);
		}
	}

	@Override
	public List<EmpCopySettingItem> getPerInfoItemByCtgId(String perInfoCategoryId, String companyId,
			String contractCd) {
		return this.queryProxy().query(SELECT_PERINFOITEM_BYCTGID, Object[].class).setParameter("companyId", companyId)
				.setParameter("perInfoCtgId", perInfoCategoryId).getList(i -> {
					EmpCopySettingItem newCopyItem = EmpCopySettingItem.createFromJavaType(perInfoCategoryId, "",
							String.valueOf(i[0]), "", String.valueOf(i[1]), 0, 0);
					newCopyItem.setAlreadyCopy(Boolean.valueOf(i[2].toString()));

					return newCopyItem;
				});
	}

	@Override
	public int countPerInfoItemDefInCopySetting(String perInfoItemDefId, String companyId) {
		Optional<Long> a = this.queryProxy().query(COUNT_ITEMS_IN_COPYITEM, Long.class)
				.setParameter("companyId", companyId).setParameter("perInfoItemDefId", perInfoItemDefId).getSingle();
		return a.isPresent() ? a.get().intValue() : 0;
	}

}
