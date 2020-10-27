package nts.uk.ctx.pereg.infra.repository.copysetting.setting;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pereg.dom.copysetting.setting.EmpCopySettingRepository;
import nts.uk.ctx.pereg.dom.copysetting.setting.EmployeeCopyCategory;
import nts.uk.ctx.pereg.dom.copysetting.setting.EmployeeCopySetting;
import nts.uk.ctx.pereg.dom.copysetting.setting.valueobject.CopySettingItemObject;
import nts.uk.ctx.pereg.infra.entity.copysetting.item.PpestEmployeeCopySettingItem;
import nts.uk.ctx.pereg.infra.entity.copysetting.setting.PpestEmployeeCopySetting;
import nts.uk.ctx.pereg.infra.entity.copysetting.setting.PpestEmployeeCopySettingPk;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JpaEmpCopySettingRepository extends JpaRepository implements EmpCopySettingRepository {

	private static final String SELECT_EMP_COPY_SETTING = "SELECT ci FROM PpestEmployeeCopySettingItem ci"
			+ " INNER JOIN PpestEmployeeCopySetting cs ON cs.ppestEmployeeCopySettingPk.categoryId = ci.categoryId"
			+ " WHERE cs.companyId = :companyId";
	
	private static final String SELECT_EMP_COPY_CATEGORY = "SELECT ci FROM PpestEmployeeCopySettingItem ci"
			+ " WHERE ci.categoryId = :categoryId";
	
	private final static String COUNT_PERINFOCTGIN_COPYSETING = "SELECT COUNT(i) FROM PpestEmployeeCopySetting i "
			+ "WHERE i.ppestEmployeeCopySettingPk.categoryId = :categoryId AND i.companyId = :companyId";
	
	private final static String SELECT_PERINFOITEM_BYCTGID = "SELECT i.ppemtItemPK.perInfoItemDefId, i.itemName,i.itemCd FROM PpemtItem i"
			+ " INNER JOIN PpemtCtg c ON i.perInfoCtgId = c.ppemtCtgPK.perInfoCtgId"
			+ " INNER JOIN PpemtItemSort io ON i.ppemtItemPK.perInfoItemDefId= io.ppemtItemPK.perInfoItemDefId"
			+ " INNER JOIN PpemtItemCommon ic ON i.itemCd = ic.ppemtItemCommonPK.itemCd AND c.categoryCd = ic.ppemtItemCommonPK.categoryCd"
			+ " WHERE c.cid = :companyId AND i.perInfoCtgId = :perInfoCtgId AND i.abolitionAtr = 0"
			+ " AND ((ic.dataType != 9 AND ic.dataType != 10) or ic.dataType is null)"
			+ " AND i.itemCd != 'IS00020' AND ic.itemParentCd IS NULL ORDER BY io.displayOrder ASC";
	
	@Override
	public Optional<EmployeeCopySetting> findSetting(String companyId) {
		List<PpestEmployeeCopySettingItem> copyItems = this.queryProxy()
				.query(SELECT_EMP_COPY_SETTING, PpestEmployeeCopySettingItem.class)
				.setParameter("companyId", companyId)
				.getList();
		Map<String, List<PpestEmployeeCopySettingItem>> entityMap = copyItems.stream()
				.collect(Collectors.groupingBy(PpestEmployeeCopySettingItem::getCategoryId));

		if (copyItems.isEmpty()) {
			return Optional.empty();
		}

		List<EmployeeCopyCategory> copyCategoryList = new ArrayList<>();
		entityMap.forEach((categoryId, v) -> {
			List<String> itemIdList = v.stream()
					.map(copyItem -> copyItem.ppestEmployeeCopySettingItemPk.perInfoItemDefId)
					.collect(Collectors.toList());
			copyCategoryList.add(new EmployeeCopyCategory(categoryId, itemIdList));
		});

		return Optional.of(new EmployeeCopySetting(companyId, copyCategoryList));
	}
	
	
	@Override
	public Optional<EmployeeCopyCategory> findCopyCategory(String companyId, String categoryId) {
		List<PpestEmployeeCopySettingItem> copyItems = this.queryProxy()
				.query(SELECT_EMP_COPY_CATEGORY, PpestEmployeeCopySettingItem.class)
				.setParameter("categoryId", categoryId).getList();
		if (copyItems.isEmpty()) {
			return Optional.empty();
		}
		List<String> itemIdList = copyItems.stream()
				.map(copyItem -> copyItem.ppestEmployeeCopySettingItemPk.perInfoItemDefId).collect(Collectors.toList());
		return Optional.of(new EmployeeCopyCategory(categoryId, itemIdList));
	}
	
	@Override
	public List<CopySettingItemObject> getPerInfoItemByCtgId(String companyId, String perInfoCategoryId) {

		List<Object[]> perDefItemList = this.queryProxy().query(SELECT_PERINFOITEM_BYCTGID, Object[].class)
				.setParameter("companyId", companyId)
				.setParameter("perInfoCtgId", perInfoCategoryId).getList();

		List<String> copyItemIdList = this.queryProxy()
				.query(SELECT_EMP_COPY_CATEGORY, PpestEmployeeCopySettingItem.class)
				.setParameter("categoryId", perInfoCategoryId).getList().stream()
				.map(item -> item.ppestEmployeeCopySettingItemPk.perInfoItemDefId).collect(Collectors.toList());

		return perDefItemList.stream()
				.map(i -> CopySettingItemObject.createFromJavaType(perInfoCategoryId, String.valueOf(i[0]),
						String.valueOf(i[1]), String.valueOf(i[2]), copyItemIdList.contains(String.valueOf(i[0]))))
				.collect(Collectors.toList());
	}

	@Override
	public void addCopyCategory(EmployeeCopyCategory copyCategory) {
		
		this.commandProxy().insert(toEntity(copyCategory));
		
		List<PpestEmployeeCopySettingItem> copyItems = copyCategory.getItemDefIdList().stream()
				.map(id -> new PpestEmployeeCopySettingItem(copyCategory.getCategoryId(), id))
				.collect(Collectors.toList());
		this.commandProxy().insertAll(copyItems);
	}
	
	@Override
	public void removeCopyCategory(String categoryId) {
		
		if (this.queryProxy().find(new PpestEmployeeCopySettingPk(categoryId), PpestEmployeeCopySetting.class)
				.isPresent()) {
			this.commandProxy().remove(PpestEmployeeCopySetting.class, new PpestEmployeeCopySettingPk(categoryId));
		}

		List<PpestEmployeeCopySettingItem> copyItems = this.queryProxy()
				.query(SELECT_EMP_COPY_CATEGORY, PpestEmployeeCopySettingItem.class)
				.setParameter("categoryId", categoryId).getList();
		
		this.commandProxy().removeAll(copyItems);
		
		this.getEntityManager().flush();
	}

	@Override
	public void updatePerInfoCtgInCopySetting(String perInfoCtgId, String companyId) {
		boolean alreadyExist = checkPerInfoCtgAlreadyCopy(perInfoCtgId, companyId);
		if (!alreadyExist) {

			PpestEmployeeCopySettingPk key = new PpestEmployeeCopySettingPk(perInfoCtgId);
			PpestEmployeeCopySetting entity = new PpestEmployeeCopySetting(key, companyId);
			this.commandProxy().insert(entity);
		}

	}

	@Override
	public boolean checkPerInfoCtgAlreadyCopy(String perInfoCtgId, String companyId) {
		Optional<Long> a = this.queryProxy().query(COUNT_PERINFOCTGIN_COPYSETING, Long.class)
				.setParameter("categoryId", perInfoCtgId).setParameter("companyId", companyId).getSingle();
		return a.isPresent() ? (a.get().intValue() > 0 ? true : false) : false;
	}

	
	private PpestEmployeeCopySetting toEntity(EmployeeCopyCategory domain) {
		PpestEmployeeCopySettingPk key = new PpestEmployeeCopySettingPk(domain.getCategoryId());

		return new PpestEmployeeCopySetting(key, AppContexts.user().companyId());
	}
	

}
