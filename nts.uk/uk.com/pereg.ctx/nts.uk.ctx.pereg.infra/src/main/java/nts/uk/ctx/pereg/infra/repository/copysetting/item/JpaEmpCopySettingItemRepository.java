package nts.uk.ctx.pereg.infra.repository.copysetting.item;

import java.util.List;
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

	private static final String SELECT_EMP_COPY_SETTING_ITEM_BY_CTG_ID_QUERY_STRING = "SELECT DISTINCT pi.perInfoCtgId,pc.categoryCd,pi.ppemtPerInfoItemPK.perInfoItemDefId,pi.itemCd,pi.itemName,pi.requiredAtr,"
			+ " pm.dataType,pm.selectionItemRefType,pm.itemParentCd ,pm.dateItemType,pm.selectionItemRefCode"
			+ " FROM PpestEmployeeCopySettingItem ci" + " INNER JOIN PpestEmployeeCopySetting cs "
			+ " ON ci.categoryId = cs.ppestEmployeeCopySettingPk.categoryId" + " INNER JOIN PpemtPerInfoCtg pc"
			+ " ON ci.categoryId = pc.ppemtPerInfoCtgPK.perInfoCtgId" + " INNER JOIN PpemtPerInfoItem pi"
			+ " ON ci.ppestEmployeeCopySettingItemPk.perInfoItemDefId=pi.ppemtPerInfoItemPK.perInfoItemDefId"
			+ " INNER JOIN PpemtPerInfoItemOrder po ON pi.ppemtPerInfoItemPK.perInfoItemDefId= po.ppemtPerInfoItemPK.perInfoItemDefId"
			+ " INNER JOIN PpemtPersonItemAuth pa"
			+ " ON ci.ppestEmployeeCopySettingItemPk.perInfoItemDefId=pa.ppemtPersonItemAuthPk.personItemDefId"
			+ " INNER JOIN PpemtPerInfoItemCm pm"
			+ " ON pi.itemCd = pm.ppemtPerInfoItemCmPK.itemCd AND pc.categoryCd = pm.ppemtPerInfoItemCmPK.categoryCd"
			+ " WHERE pc.categoryCd =:categoryCd AND pc.cid=:companyId";
	private static final String CHECK_OTHER_AUTH = " AND pa.otherPersonAuthType!=1 ORDER BY po.displayOrder ASC";

	private static final String CHECK_SELF_AUTH = " AND pa.selfAuthType!=1 ORDER BY po.displayOrder ASC";

	private final static String SELECT_PERINFOITEM_BYCTGID = "SELECT i.ppemtPerInfoItemPK.perInfoItemDefId, i.itemName,i.itemCd,"
			+ " CASE WHEN (ci.ppestEmployeeCopySettingItemPk.perInfoItemDefId) IS NOT NULL THEN 'True' ELSE 'False' END AS alreadyCopy ,ic.itemParentCd,ic.dateItemType"
			+ " FROM PpemtPerInfoItem i INNER JOIN PpemtPerInfoCtg c ON i.perInfoCtgId = c.ppemtPerInfoCtgPK.perInfoCtgId"
			+ " INNER JOIN PpemtPerInfoItemOrder io ON i.ppemtPerInfoItemPK.perInfoItemDefId= io.ppemtPerInfoItemPK.perInfoItemDefId"
			+ " INNER JOIN PpemtPerInfoItemCm ic"
			+ " ON i.itemCd = ic.ppemtPerInfoItemCmPK.itemCd AND c.categoryCd = ic.ppemtPerInfoItemCmPK.categoryCd"
			+ " LEFT JOIN PpestEmployeeCopySettingItem ci ON i.ppemtPerInfoItemPK.perInfoItemDefId = ci.ppestEmployeeCopySettingItemPk.perInfoItemDefId"
			+ " WHERE c.cid = :companyId AND i.perInfoCtgId = :perInfoCtgId AND i.abolitionAtr = 0 ORDER BY io.displayOrder ASC";
	
	private final static String SELECT_PERINFOITEM_BYCTGID_NO812 = "SELECT i.ppemtPerInfoItemPK.perInfoItemDefId, i.itemName,i.itemCd,"
			+ " CASE WHEN (ci.ppestEmployeeCopySettingItemPk.perInfoItemDefId) IS NOT NULL THEN 'True' ELSE 'False' END AS alreadyCopy ,ic.itemParentCd,ic.dateItemType"
			+ " FROM PpemtPerInfoItem i INNER JOIN PpemtPerInfoCtg c ON i.perInfoCtgId = c.ppemtPerInfoCtgPK.perInfoCtgId"
			+ " INNER JOIN PpemtPerInfoItemOrder io ON i.ppemtPerInfoItemPK.perInfoItemDefId= io.ppemtPerInfoItemPK.perInfoItemDefId"
			+ " INNER JOIN PpemtPerInfoItemCm ic ON i.itemCd = ic.ppemtPerInfoItemCmPK.itemCd AND c.categoryCd = ic.ppemtPerInfoItemCmPK.categoryCd"
			+ " LEFT JOIN PpestEmployeeCopySettingItem ci ON i.ppemtPerInfoItemPK.perInfoItemDefId = ci.ppestEmployeeCopySettingItemPk.perInfoItemDefId"
			+ " WHERE c.cid = :companyId AND i.perInfoCtgId = :perInfoCtgId AND i.abolitionAtr = 0"
			+ " AND ic.dataType != 9 AND ic.dataType != 10 AND ic.itemParentCd IS NULL ORDER BY io.displayOrder ASC";

	@Override
	public List<EmpCopySettingItem> getAllItemFromCategoryCd(String categoryCd, String companyId, boolean isSelf) {

		return this.queryProxy()
				.query(SELECT_EMP_COPY_SETTING_ITEM_BY_CTG_ID_QUERY_STRING
						+ (isSelf ? CHECK_SELF_AUTH : CHECK_OTHER_AUTH), Object[].class)
				.setParameter("categoryCd", categoryCd).setParameter("companyId", companyId).getList().stream()
				.map(x -> toDomain(x)).collect(Collectors.toList());
	}

	private EmpCopySettingItem toDomain(Object[] entity) {

		String perInfoCtgId = entity[0] != null ? entity[0].toString() : null;
		String categoryCd = entity[1] != null ? entity[1].toString() : null;
		String perInfoItemDefId = entity[2] != null ? entity[2].toString() : null;
		String itemCode = entity[3] != null ? entity[3].toString() : null;
		String itemName = entity[4] != null ? entity[4].toString() : null;
		int isRequired = entity[5] != null ? Integer.parseInt(entity[5].toString()) : 0;
		int dataType = entity[6] != null ? Integer.parseInt(entity[6].toString()) : 1;
		int selectionItemRefType = entity[7] != null ? Integer.parseInt(entity[7].toString()) : 1;
		String itemParentCd = entity[8] != null ? entity[8].toString() : null;
		int dateType = entity[9] != null ? Integer.parseInt(entity[9].toString()) : 1;
		String selectionItemRefCd = entity[10] != null ? entity[10].toString() : null;

		return EmpCopySettingItem.createFromJavaType(perInfoCtgId, categoryCd, perInfoItemDefId, itemCode, itemName,
				isRequired, dataType, selectionItemRefType, itemParentCd, dateType, selectionItemRefCd);

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
		List<EmpCopySettingItem> itemList = this.queryProxy().query(SELECT_PERINFOITEM_BYCTGID, Object[].class)
				.setParameter("companyId", companyId).setParameter("perInfoCtgId", perInfoCategoryId).getList(i -> {
					return EmpCopySettingItem.createFromJavaType(perInfoCategoryId, String.valueOf(i[0]),
							String.valueOf(i[1]), String.valueOf(i[2]), Boolean.valueOf(i[3].toString()),
							i[4] != null ? i[4].toString() : null);
				});

		return itemList;

	}
	
	@Override
	public List<EmpCopySettingItem> getPerInfoItemByCtgIdNo812(String perInfoCategoryId, String companyId,
			String contractCd) {
		List<EmpCopySettingItem> itemList = this.queryProxy().query(SELECT_PERINFOITEM_BYCTGID_NO812, Object[].class)
				.setParameter("companyId", companyId).setParameter("perInfoCtgId", perInfoCategoryId).getList(i -> {
					return EmpCopySettingItem.createFromJavaType(perInfoCategoryId, String.valueOf(i[0]),
							String.valueOf(i[1]), String.valueOf(i[2]), Boolean.valueOf(i[3].toString()),
							i[4] != null ? i[4].toString() : null);
				});

		return itemList;

	}

}
