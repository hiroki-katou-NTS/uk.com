package nts.uk.ctx.at.function.app.find.annualworkschedule;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.adapter.standardtime.AgreementOperationSettingAdapter;

@Stateless
public class PeriodFinder {

	@Inject
	private AgreementOperationSettingAdapter agreementOperationSettingAdapter;

	public PeriodDto getPeriod(){
		int startMonth = agreementOperationSettingAdapter.find().getStartingMonth();
		return new PeriodDto("201704", "201803"); //TODO linh.nd
	}

}
