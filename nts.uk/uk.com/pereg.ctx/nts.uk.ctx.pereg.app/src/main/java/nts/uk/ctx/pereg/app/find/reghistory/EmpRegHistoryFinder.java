package nts.uk.ctx.pereg.app.find.reghistory;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.person.dom.person.info.setting.reghistory.EmpRegHistory;
import nts.uk.ctx.bs.person.dom.person.setting.reghistory.EmpRegHistoryRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class EmpRegHistoryFinder {

	@Inject
	private EmpRegHistoryRepository empHisRepo;

	public EmpRegHistoryDto getLastRegHistory() {
		String companyId = AppContexts.user().companyId();
		Optional<EmpRegHistory> opt = this.empHisRepo.getLastRegHistory(companyId);
		if (!opt.isPresent()) {
			return null;
		}
		return EmpRegHistoryDto.fromDomain(opt.get());

	}
}
