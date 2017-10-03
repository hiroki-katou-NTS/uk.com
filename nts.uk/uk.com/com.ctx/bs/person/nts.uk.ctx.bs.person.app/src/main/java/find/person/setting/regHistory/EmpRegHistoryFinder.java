package find.person.setting.regHistory;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.person.dom.person.info.setting.regHistory.EmpRegHistory;
import nts.uk.ctx.bs.person.dom.person.setting.regHistory.EmpRegHistoryRepository;

@Stateless
public class EmpRegHistoryFinder {

	@Inject
	EmpRegHistoryRepository empHisRepo;

	public EmpRegHistoryDto getLastRegHistory() {

		Optional<EmpRegHistory> opt = this.empHisRepo.getLastRegHistory();
		if (!opt.isPresent()) {
			return null;
		}
		return EmpRegHistoryDto.fromDomain(opt.get());

	}
}
