package repository.person.setting.copysetting;

import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.bs.person.dom.person.info.setting.copysetting.EmpCopySettingItem;
import nts.uk.ctx.bs.person.dom.person.setting.copysetting.EmpCopySettingItemRepository;

@Stateless
public class JpaEmpCopySettingItemRepository implements EmpCopySettingItemRepository {

	@Override
	public List<EmpCopySettingItem> getAll(String ctgId) {
		// TODO Auto-generated method stub
		return null;
	}

}
