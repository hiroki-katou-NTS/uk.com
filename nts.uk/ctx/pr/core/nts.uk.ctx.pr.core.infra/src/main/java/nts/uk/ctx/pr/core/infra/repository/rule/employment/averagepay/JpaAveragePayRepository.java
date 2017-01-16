package nts.uk.ctx.pr.core.infra.repository.rule.employment.averagepay;

import java.util.List;

import javax.enterprise.context.RequestScoped;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.rule.employment.averagepay.AveragePay;
import nts.uk.ctx.pr.core.dom.rule.employment.averagepay.AveragePayRepository;
import nts.uk.ctx.pr.core.infra.entity.rule.employment.averagepay.QapmtAveragePay;

@RequestScoped
public class JpaAveragePayRepository extends JpaRepository implements AveragePayRepository {
	
	@Override
	public void add(String companyCode, AveragePay averagePay) {
		this.commandProxy().insert(convertToEntity(companyCode, averagePay));
	}

	private QapmtAveragePay convertToEntity(String companyCode, AveragePay averagePay) {
		QapmtAveragePay entity = new QapmtAveragePay();
		entity.setCompanyCode(companyCode);
		entity.setAttendDayGettingSet(averagePay.getAttendDayGettingSet().value);
		entity.setExceptionPayRate(averagePay.getExceptionPayRate().v());
		entity.setRoundDigitSet(averagePay.getRoundDigitSet().value);
		entity.setRoundTimingSet(averagePay.getRoundTimingSet().value);
		
		return entity;
	}
	
}
