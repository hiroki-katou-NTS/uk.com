package nts.uk.ctx.at.record.infra.repository.workrecord.workrecord;

import java.util.List;
import java.util.Optional;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.workrecord.workrecord.EmploymentConfirmed;
import nts.uk.ctx.at.record.dom.workrecord.workrecord.EmploymentConfirmedRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

/**
 * 就業確定Repository	
 * @author chungnt
 *
 */

public class JpaEmploymentConfirmedRepository implements EmploymentConfirmedRepository {

	@Override
	public void insert(EmploymentConfirmed domain) {
		
	}

	@Override
	public void delete(EmploymentConfirmed domain) {
		
	}

	@Override
	public Optional<EmploymentConfirmed> get(String companyId, String workplaceId, ClosureId closureId,
			YearMonth processYM) {
		return null;
	}

	@Override
	public List<EmploymentConfirmed> get(String companyId, List<String> workplaceId, ClosureId closureId,
			YearMonth processYM) {
		return null;
	}

}
