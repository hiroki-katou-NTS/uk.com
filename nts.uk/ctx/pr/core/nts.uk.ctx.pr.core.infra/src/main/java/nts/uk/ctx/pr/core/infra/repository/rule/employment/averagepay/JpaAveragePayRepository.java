package nts.uk.ctx.pr.core.infra.repository.rule.employment.averagepay;

import java.util.List;
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
	//private final String SELECT_ALL_BY_COMPANY = SELECT_NO_WHERE + " WHERE a.companyCode = :companyCode";
	private final String DELETE = "DELETE FROM QapmtAvePay a WHERE a.companyCode = :companyCode";
	@Override
	public void add(AveragePay averagePay) {
		this.commandProxy().insert(convertToEntity(averagePay));
	}
	
	/*@Override
	public Optional<AvePay> find(String companyCode) {
		return this.queryProxy().find(companyCode, QapmtAvePay.class).
				map(x -> AvePay.createFromJavaType(x.attendDayGettingSet, x.exceptionPayRate, x.roundTimingSet, x.roundDigitSet));
	}*/
	
	@Override
	public List<AveragePay> findAll() {
		
		
		return this.queryProxy().query(SELECT_NO_WHERE, QapmtAvePay.class)
				.getList(x -> AveragePay.createFromJavaType(x.companyCode, x.attendDayGettingSet, x.exceptionPayRate, x.roundTimingSet, x.roundDigitSet));
	}
	
	@Override
	public void update(AveragePay averagePay) {
		this.commandProxy().update(convertToEntity(averagePay));
	}
	
	@Override
	public void remove(AveragePay averagePay) {
		//this.commandProxy().remove(convertToEntity(averagePay));
		//this.queryProxy().query(DELETE, QapmtAvePay.class).setParameter("companyCode", averagePay.getCompanyCode().v());
		this.commandProxy().remove(QapmtAvePay.class, averagePay.getCompanyCode());
		
	}
	
	
	private QapmtAvePay convertToEntity(AveragePay averagePay) {
		QapmtAvePay entity = new QapmtAvePay(
				averagePay.getCompanyCode().v(), 
				averagePay.getRoundDigitSet().value, 
				averagePay.getAttendDayGettingSet().value, 
				averagePay.getRoundTimingSet().value, 
				averagePay.getExceptionPayRate().v());
		
		/*
		QapmtAveragePay entity = new QapmtAveragePay();
		entity.setCompanyCode(companyCode);
		entity.setAttendDayGettingSet(averagePay.getAttendDayGettingSet().value);
		entity.setExceptionPayRate(averagePay.getExceptionPayRate().v());
		entity.setRoundDigitSet(averagePay.getRoundDigitSet().value);
		entity.setRoundTimingSet(averagePay.getRoundTimingSet().value);
		*/
		
		return entity;
	}
	

	
}
