package nts.uk.ctx.at.record.infra.entity.monthly.affiliation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.affiliation.AffiliationInfoOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.affiliation.AffiliationInfoOfMonthlyRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

@Stateless
public class AffiliationInfoOfMonthlyRepoImpl implements AffiliationInfoOfMonthlyRepository {

	@Override
	public Optional<AffiliationInfoOfMonthly> find(String employeeId, YearMonth yearMonth, ClosureId closureId,
			ClosureDate closureDate) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<AffiliationInfoOfMonthly> findBySidAndYearMonth(String employeeId, YearMonth yearMonth) {
		// TODO Auto-generated method stub
		return new ArrayList<>();
	}

	@Override
	public void persistAndUpdate(AffiliationInfoOfMonthly attendanceTimeOfMonthly) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(String employeeId, YearMonth yearMonth, ClosureId closureId, ClosureDate closureDate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeBySidAndYearMonth(String employeeId, YearMonth yearMonth) {
		// TODO Auto-generated method stub
		
	}

}
