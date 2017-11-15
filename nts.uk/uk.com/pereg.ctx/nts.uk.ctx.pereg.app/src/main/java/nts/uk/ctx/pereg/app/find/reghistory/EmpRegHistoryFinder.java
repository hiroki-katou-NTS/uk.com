package nts.uk.ctx.pereg.app.find.reghistory;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.person.dom.person.info.setting.reghistory.EmpRegHistory;
import nts.uk.ctx.bs.person.dom.person.setting.reghistory.EmpRegHistoryRepository;

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
