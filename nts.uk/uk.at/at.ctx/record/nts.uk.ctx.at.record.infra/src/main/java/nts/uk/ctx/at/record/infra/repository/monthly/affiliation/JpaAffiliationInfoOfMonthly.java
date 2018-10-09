package nts.uk.ctx.at.record.infra.repository.monthly.affiliation;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.TimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.TimeOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.monthly.affiliation.AffiliationInfoOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.affiliation.AffiliationInfoOfMonthlyRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

/**
 * リポジトリ実装：月別実績の所属情報
 * @author shuichu_ishida
 */
@Stateless
public class JpaAffiliationInfoOfMonthly extends JpaRepository implements AffiliationInfoOfMonthlyRepository {
	
	@Inject
	private TimeOfMonthlyRepository mergeRepo;
	
	/** 検索 */
	@Override
	public Optional<AffiliationInfoOfMonthly> find(String employeeId, YearMonth yearMonth, ClosureId closureId,
			ClosureDate closureDate) {
		
		return mergeRepo.find(employeeId, yearMonth, closureId, closureDate).map(c -> c.getAffiliation().orElse(null));
	}
	
	/** 検索　（社員IDと年月） */
	@Override
	public List<AffiliationInfoOfMonthly> findBySidAndYearMonth(String employeeId, YearMonth yearMonth) {
		
		return mergeRepo.findByYearMonthOrderByStartYmd(employeeId, yearMonth)
				.stream().map(c -> c.getAffiliation().orElse(null))
				.filter(c -> c != null).collect(Collectors.toList());
	}
	
	/** 検索　（社員IDリスト） */
	@Override
	public List<AffiliationInfoOfMonthly> findByEmployees(List<String> employeeIds, YearMonth yearMonth, ClosureId closureId,
			ClosureDate closureDate) {
		
		return mergeRepo.findByEmployees(employeeIds, yearMonth, closureId, closureDate)
				.stream().map(c -> c.getAffiliation().orElse(null))
				.filter(c -> c != null).collect(Collectors.toList());
	}
	
	/** 検索　（社員IDリストと年月リスト） */
	@Override
	public List<AffiliationInfoOfMonthly> findBySidsAndYearMonths(List<String> employeeIds,
			List<YearMonth> yearMonths) {
		
		return mergeRepo.findBySidsAndYearMonths(employeeIds, yearMonths)
				.stream().map(c -> c.getAffiliation().orElse(null))
				.filter(c -> c != null).collect(Collectors.toList());
	}
	
	/** 登録および更新 */
	@Override
	public void persistAndUpdate(AffiliationInfoOfMonthly domain) {
		
		mergeRepo.persistAndUpdate(new TimeOfMonthly(null, domain));
	}
	
	/** 削除 */
	@Override
	public void remove(String employeeId, YearMonth yearMonth, ClosureId closureId, ClosureDate closureDate) {

		mergeRepo.removeAffiliation(employeeId, yearMonth, closureId, closureDate);
	}
	
	/** 削除　（社員IDと年月） */
	@Override
	public void removeBySidAndYearMonth(String employeeId, YearMonth yearMonth) {
		
		mergeRepo.removeAffiliation(employeeId, yearMonth);
	}
}
