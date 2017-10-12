package repository.person.setting.copysetting;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import entity.person.info.setting.copysetting.PpestEmployeeCopySettingItem;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.person.dom.person.info.setting.copysetting.EmpCopySettingItem;
import nts.uk.ctx.bs.person.dom.person.setting.copysetting.EmpCopySettingItemRepository;

@Stateless
public class JpaEmpCopySettingItemRepository extends JpaRepository implements EmpCopySettingItemRepository {

	private static final String SELECT_EMP_COPY_SETTING_ITEM_BY_CTG_ID_QUERY_STRING = "SELECT ci "
			+ "FROM PpestEmployeeCopySettingItem ci " + "WHERE ci.categoryId =:categoryId";

	@Override
	public List<EmpCopySettingItem> getAllItemFromCategoryId(String categoryId) {

		return this.queryProxy()
				.query(SELECT_EMP_COPY_SETTING_ITEM_BY_CTG_ID_QUERY_STRING, PpestEmployeeCopySettingItem.class)
				.getList().stream().map(x -> toDomain(x)).collect(Collectors.toList());
	}

	private EmpCopySettingItem toDomain(PpestEmployeeCopySettingItem entity) {

		return EmpCopySettingItem.createFromJavaType(entity.PpestEmployeeCopySettingItemPk.perInfoItemDefId,
				entity.categoryId);

	}

}
