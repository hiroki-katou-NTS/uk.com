package nts.uk.ctx.pr.core.infra.repository.rule.employment.avepay;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.rule.employment.avepay.AvePay;
import nts.uk.ctx.pr.core.dom.rule.employment.avepay.AvePayRepository;
import nts.uk.ctx.pr.core.infra.entity.rule.employment.avepay.QapmtAvePay;

@RequestScoped
public class JpaAvePayRepository extends JpaRepository implements AvePayRepository {
	
	private final String SELECT_NO_WHERE = "SELECT a FROM QapmtAvePay a";
	//private final String SELECT_ALL_BY_COMPANY = SELECT_NO_WHERE + " WHERE a.companyCode = :companyCode";
	
	@Override
	public void add(String companyCode, AvePay averagePay) {
		this.commandProxy().insert(convertToEntity(companyCode, averagePay));
	}
	@Override
	public Optional<AvePay> find(String companyCode) {
		return this.queryProxy().find(companyCode, QapmtAvePay.class).
				map(x -> AvePay.createFromJavaType(x.attendDayGettingSet, x.exceptionPayRate, x.roundTimingSet, x.roundDigitSet));
	}
	
	@Override
	public List<AvePay> findAll() {
		return this.queryProxy().query(SELECT_NO_WHERE, QapmtAvePay.class)
				.getList(x -> AvePay.createFromJavaType(x.attendDayGettingSet, x.exceptionPayRate, x.roundTimingSet, x.roundDigitSet));
	}
	
	@Override
	public void update(String companyCode, AvePay averagePay) {
		this.commandProxy().update(convertToEntity(companyCode, averagePay));
	}
	
	@Override
	public void remove(String companyCode, AvePay averagePay) {
		this.commandProxy().remove(convertToEntity(companyCode, averagePay));
	}

	private QapmtAvePay convertToEntity(String companyCode, AvePay averagePay) {
		QapmtAvePay entity = new QapmtAvePay(
				companyCode, 
				averagePay.getRoundDigitSet().value, 
				averagePay.getAttendDayGettingSet().value, 
				averagePay.getRoundTimingSet().value, 
				averagePay.getExceptionPayRate().v());
		entity.companyCode = companyCode;
		entity.roundDigitSet = averagePay.getRoundDigitSet().value;
		entity.attendDayGettingSet = averagePay.getAttendDayGettingSet().value;
		entity.roundTimingSet = averagePay.getRoundTimingSet().value;
		entity.exceptionPayRate = averagePay.getExceptionPayRate().v();
		
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
