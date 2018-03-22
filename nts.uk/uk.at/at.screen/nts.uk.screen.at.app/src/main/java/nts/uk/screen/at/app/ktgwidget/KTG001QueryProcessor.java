package nts.uk.screen.at.app.ktgwidget;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;

@Stateless
public class KTG001QueryProcessor {
	@Inject 
	private ClosureEmploymentRepository closureEmploymentRepo;
	
	/**
	 * 日別実績確認すべきデータ有無表示
	 * @return
	 */
	public boolean init() {
		String employmentCode = "";
		Optional<ClosureEmployment> closureEmploymentOpt = closureEmploymentRepo.findByEmploymentCD("", employmentCode);
		
		return false;
	}
}
