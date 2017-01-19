package nts.uk.ctx.pr.core.infra.repository.rule.employment.avepay;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.rule.employment.avepay.AvePay;
import nts.uk.ctx.pr.core.dom.rule.employment.avepay.AvePayRepository;
import nts.uk.ctx.pr.core.infra.entity.rule.employment.avepay.QapmtAvePay;

@RequestScoped
@Transactional
public class JpaAvePayRepository extends JpaRepository implements AvePayRepository {
	
	private final String SELECT_NO_WHERE = "SELECT a FROM QapmtAvePay a";
	//private final String SELECT_ALL_BY_COMPANY = SELECT_NO_WHERE + " WHERE a.companyCode = :companyCode";
	
	@Override
	public void add(String companyCode, AvePay avePay) {
		this.commandProxy().insert(convertToEntity(companyCode, avePay));
	}
	
	/*@Override
	public Optional<AvePay> find(String companyCode) {
		return this.queryProxy().find(companyCode, QapmtAvePay.class).
				map(x -> AvePay.createFromJavaType(x.attendDayGettingSet, x.exceptionPayRate, x.roundTimingSet, x.roundDigitSet));
	}*/
	
	@Override
	public List<AvePay> findAll() {
		return this.queryProxy().query(SELECT_NO_WHERE, QapmtAvePay.class)
				.getList(x -> AvePay.createFromJavaType(x.attendDayGettingSet, x.exceptionPayRate, x.roundTimingSet, x.roundDigitSet));
	}
	
	@Override
	public void update(String companyCode, AvePay avePay) {
		this.commandProxy().update(convertToEntity(companyCode, avePay));
	}
	
	@Override
	public void remove(String companyCode, AvePay avePay) {
		this.commandProxy().remove(convertToEntity(companyCode, avePay));
	}

	private QapmtAvePay convertToEntity(String companyCode, AvePay avePay) {
		QapmtAvePay entity = new QapmtAvePay(
				companyCode, 
				avePay.getRoundDigitSet().value, 
				avePay.getAttendDayGettingSet().value, 
				avePay.getRoundTimingSet().value, 
				avePay.getExceptionPayRate().v());
		entity.companyCode = companyCode;
		entity.roundDigitSet = avePay.getRoundDigitSet().value;
		entity.attendDayGettingSet = avePay.getAttendDayGettingSet().value;
		entity.roundTimingSet = avePay.getRoundTimingSet().value;
		entity.exceptionPayRate = avePay.getExceptionPayRate().v();
		
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
