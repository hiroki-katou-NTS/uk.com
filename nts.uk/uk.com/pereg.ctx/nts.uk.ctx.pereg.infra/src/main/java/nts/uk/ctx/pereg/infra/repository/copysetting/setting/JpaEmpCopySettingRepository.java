package nts.uk.ctx.pereg.infra.repository.copysetting.setting;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pereg.dom.copysetting.setting.EmpCopySetting;
import nts.uk.ctx.pereg.dom.copysetting.setting.EmpCopySettingRepository;
import nts.uk.ctx.pereg.dom.copysetting.setting.EmployeeCopyCategory;
import nts.uk.ctx.pereg.dom.copysetting.setting.EmployeeCopySetting;
import nts.uk.ctx.pereg.infra.entity.copysetting.item.PpestEmployeeCopySettingItem;
import nts.uk.ctx.pereg.infra.entity.copysetting.setting.PpestEmployeeCopySetting;
import nts.uk.ctx.pereg.infra.entity.copysetting.setting.PpestEmployeeCopySettingPk;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JpaEmpCopySettingRepository extends JpaRepository implements EmpCopySettingRepository {

	private static final String SELECT_EMP_COPY_SETTING_BY_COMID_QUERY_STRING = "SELECT cs "
			+ "FROM PpestEmployeeCopySetting cs " + "WHERE cs.companyId =:companyId";

	private static final String SELECT_EMP_COPY_SETTING = "SELECT ci FROM PpestEmployeeCopySettingItem ci"
			+ " INNER JOIN PpestEmployeeCopySetting cs ON cs.ppestEmployeeCopySettingPk.categoryId = ci.categoryId"
			+ " WHERE cs.companyId = :companyId";
	
	private static final String SELECT_EMP_COPY_CATEGORY = "SELECT ci FROM PpestEmployeeCopySettingItem ci"
			+ " WHERE ci.categoryId = :categoryId";
	
	private final static String COUNT_PERINFOCTGIN_COPYSETING = "SELECT COUNT(i) FROM PpestEmployeeCopySetting i "
			+ "WHERE i.ppestEmployeeCopySettingPk.categoryId = :categoryId AND i.companyId = :companyId";

	@Override
	public List<EmpCopySetting> find(String companyId) {
		return this.queryProxy().query(SELECT_EMP_COPY_SETTING_BY_COMID_QUERY_STRING, PpestEmployeeCopySetting.class)
				.setParameter("companyId", companyId).getList().stream().map(x -> toDomain(x))
				.collect(Collectors.toList());

	}
	
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

	private EmpCopySetting toDomain(PpestEmployeeCopySetting entity) {

		return EmpCopySetting.createFromJavaType(entity.ppestEmployeeCopySettingPk.categoryId, entity.companyId);
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
	
	private PpestEmployeeCopySetting toEntity(EmpCopySetting domain) {
		PpestEmployeeCopySettingPk key = new PpestEmployeeCopySettingPk(domain.getCategoryId());
		
		return new PpestEmployeeCopySetting(key, domain.getCompanyId());
	}

	@Override
	public void addCtgCopySetting(EmpCopySetting newCtg) {
		this.commandProxy().insert(toEntity(newCtg));

	}

	@Override
	public void removeCtgCopySetting(String perInfoCtgId) {
		if (this.queryProxy().find(new PpestEmployeeCopySettingPk(perInfoCtgId), PpestEmployeeCopySetting.class)
				.isPresent()) {
			this.commandProxy().remove(PpestEmployeeCopySetting.class, new PpestEmployeeCopySettingPk(perInfoCtgId));

		}

	}


}
