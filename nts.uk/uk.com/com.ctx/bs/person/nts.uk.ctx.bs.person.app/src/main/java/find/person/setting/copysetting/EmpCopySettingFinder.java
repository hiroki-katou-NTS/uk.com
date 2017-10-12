package find.person.setting.copysetting;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import find.person.info.category.PerInfoCtgMapDto;
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

	public List<PerInfoCtgMapDto> getEmpCopySetting() {
		List<EmpCopySetting> copyList = this.empCopyRepo.find(AppContexts.user().companyId());

		if (copyList.isEmpty()) {

			throw new BusinessException(new RawErrorMessage("Msg_347"));

			// throw new BusinessException(new RawErrorMessage("Msg_348"));
		}

		List<String> categoryList = new ArrayList<String>();

		copyList.stream().forEach(i -> categoryList.add(i.getCategoryId()));

		return this.PerInfoCtgRepo.getAllCategoryByCtgIdList(AppContexts.user().contractCode(), categoryList).stream()
				.map(p -> {
					return new PerInfoCtgMapDto(p.getCategoryId(), p.getCategoryCode(), p.getCategoryName(), true);
				}).collect(Collectors.toList());

	}
}
