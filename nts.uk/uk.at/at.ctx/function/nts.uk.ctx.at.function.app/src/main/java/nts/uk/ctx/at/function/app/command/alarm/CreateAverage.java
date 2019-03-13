package nts.uk.ctx.at.function.app.command.alarm;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.alarm.AlarmPatternSettingRepository;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.mutilmonth.AverageMonth;

@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Stateless
public class CreateAverage {

	@Inject
	private AlarmPatternSettingRepository repo;
	
	public void createAver(AverageMonth domain) {
		repo.createAver(domain);
	}

}
