package nts.uk.ctx.at.record.infra.repository.monthly.information.childnursing;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.information.childnursing.ChildcareRemNumEachMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.information.childnursing.MonChildHdRemainRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.remainmerge.RemainMergeRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

/**
 * 
 * @author phongtq
 */
@Stateless
public class JpaMonChildHdRemainRepository extends JpaRepository implements MonChildHdRemainRepository{

	@Inject
	private RemainMergeRepository remainRepo;

	@Override
	public Optional<ChildcareRemNumEachMonth> find(String employeeId, YearMonth yearMonth, ClosureId closureId,
			ClosureDate closureDate) {
		
		return this.remainRepo.find(employeeId, yearMonth, closureId, closureDate)
								.map(c -> c.getMonChildHdRemain());
	}

	@Override
	public List<ChildcareRemNumEachMonth> findByYearMonthOrderByStartYmd(String employeeId, YearMonth yearMonth) {
		
		return this.remainRepo.findByYearMonthOrderByStartYmd(employeeId, yearMonth)
				.stream().map(c -> c.getMonChildHdRemain()).collect(Collectors.toList());
	}

	@Override
	public List<ChildcareRemNumEachMonth> findByYMAndClosureIdOrderByStartYmd(String employeeId, YearMonth yearMonth,
			ClosureId closureId) {
		
		return this.remainRepo.findByYMAndClosureIdOrderByStartYmd(employeeId, yearMonth, closureId)
				.stream().map(c -> c.getMonChildHdRemain()).collect(Collectors.toList());
	}

	@Override
	public List<ChildcareRemNumEachMonth> findByEmployees(List<String> employeeIds, YearMonth yearMonth, ClosureId closureId,
			ClosureDate closureDate) {
		
		return this.remainRepo.findByEmployees(employeeIds, yearMonth, closureId, closureDate)
				.stream().map(c -> c.getMonChildHdRemain()).collect(Collectors.toList());
	}

	@Override
	public List<ChildcareRemNumEachMonth> findBySidsAndYearMonths(List<String> employeeIds, List<YearMonth> yearMonths) {
		
		return this.remainRepo.findBySidsAndYearMonths(employeeIds, yearMonths)
				.stream().map(c -> c.getMonChildHdRemain()).collect(Collectors.toList());
	}

	@Override
	public void persistAndUpdate(ChildcareRemNumEachMonth domain) {
		
		this.remainRepo.persistAndUpdate(domain);
	}

	@Override
	public void remove(String employeeId, YearMonth yearMonth, ClosureId closureId, ClosureDate closureDate) {
		
		this.remainRepo.removeMonChildHd(employeeId, yearMonth, closureId, closureDate);
	}

	@Override
	public void removeByYearMonth(String employeeId, YearMonth yearMonth) {
		
		this.remainRepo.removeMonChildHd(employeeId, yearMonth);
	}
}
