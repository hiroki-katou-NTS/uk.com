package nts.uk.ctx.pr.core.infra.repository.rule.employment.averagepay;

import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.rule.employment.averagepay.AveragePay;
import nts.uk.ctx.pr.core.dom.rule.employment.averagepay.AveragePayRepository;
import nts.uk.ctx.pr.core.infra.entity.rule.employment.averagepay.QapmtAvePay;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@RequestScoped
@Transactional
public class JpaAveragePayRepository extends JpaRepository implements AveragePayRepository {
	
	private final String SELECT_NO_WHERE = "SELECT a FROM QapmtAvePay a";
	private final String SELECT_ALL_BY_COMPANY = SELECT_NO_WHERE + " WHERE a.companyCode = :companyCode";
	@Override
	public void register(AveragePay averagePay) {
		this.commandProxy().insert(convertToEntity(averagePay));
	}
	
	@Override
	public Optional<AveragePay> findByCompanyCode(String companyCode) {
		return this.queryProxy().query(SELECT_ALL_BY_COMPANY, QapmtAvePay.class).setParameter("companyCode", companyCode)
				.getSingle(x -> AveragePay.createFromJavaType(x.companyCode, x.attendDayGettingSet, x.exceptionPayRate, x.roundTimingSet, x.roundDigitSet));
	}
	
	@Override
	public void update(AveragePay averagePay) {
		this.commandProxy().update(convertToEntity(averagePay));
	}
	
	private QapmtAvePay convertToEntity(AveragePay averagePay) {
		QapmtAvePay entity = new QapmtAvePay(
				averagePay.getCompanyCode().v(), 
				averagePay.getRoundDigitSet().value, 
				averagePay.getAttendDayGettingSet().value, 
				averagePay.getRoundTimingSet().value, 
				averagePay.getExceptionPayRate().v());
		return entity;
	}
	

	
}
