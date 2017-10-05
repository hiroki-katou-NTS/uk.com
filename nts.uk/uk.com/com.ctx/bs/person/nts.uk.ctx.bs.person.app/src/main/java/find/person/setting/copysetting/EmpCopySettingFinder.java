package find.person.setting.copysetting;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.person.dom.person.info.setting.copySetting.EmpCopySetting;
import nts.uk.ctx.bs.person.dom.person.setting.copysetting.EmpCopySettingRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class EmpCopySettingFinder {

	@Inject
	EmpCopySettingRepository empCopyRepo;

	public EmpCopySettingDto getEmpCopySetting() {
		Optional<EmpCopySetting> opt = this.empCopyRepo.find(AppContexts.user().companyId());
		if (!opt.isPresent()) {
			return null;
		}

		return EmpCopySettingDto.fromDomain(opt.get());

	}
}
