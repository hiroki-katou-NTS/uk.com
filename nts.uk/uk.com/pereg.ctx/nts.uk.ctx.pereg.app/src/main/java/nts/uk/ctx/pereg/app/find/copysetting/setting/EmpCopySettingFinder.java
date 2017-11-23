package nts.uk.ctx.pereg.app.find.copysetting.setting;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import find.person.setting.init.category.SettingCtgDto;
import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.uk.ctx.bs.person.dom.person.info.setting.copysetting.EmpCopySetting;
import nts.uk.ctx.bs.person.dom.person.role.auth.category.PersonInfoCategoryAuthRepository;
import nts.uk.ctx.bs.person.dom.person.setting.copysetting.EmpCopySettingRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class EmpCopySettingFinder {

	@Inject
	EmpCopySettingRepository empCopyRepo;

	@Inject
	PersonInfoCategoryAuthRepository PerInfoCtgRepo;

	public List<SettingCtgDto> getEmpCopySetting() {

		String companyId = AppContexts.user().companyId();
		List<EmpCopySetting> copyList = this.empCopyRepo.find(companyId);

		if (copyList.isEmpty()) {
			// check permision
			boolean isPerRep = true;
			if (isPerRep) {
				throw new BusinessException(new RawErrorMessage("Msg_347"));
			} else {
				throw new BusinessException(new RawErrorMessage("Msg_348"));
			}
		}

		List<String> categoryList = new ArrayList<String>();

		copyList.stream().forEach(i -> categoryList.add(i.getCategoryId()));

		return this.PerInfoCtgRepo.getAllCategoryByCtgIdList(companyId, categoryList).stream().map(p -> {
			return new SettingCtgDto(p.getCategoryCode(), p.getCategoryName());
		}).collect(Collectors.toList());

	}
}
