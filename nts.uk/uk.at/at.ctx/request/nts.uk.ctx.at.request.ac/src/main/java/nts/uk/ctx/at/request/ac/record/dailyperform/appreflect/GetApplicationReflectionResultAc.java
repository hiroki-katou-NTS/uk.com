package nts.uk.ctx.at.request.ac.record.dailyperform.appreflect;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.pub.appreflect.GetApplicationReflectionResultPub;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.reflect.GetApplicationReflectionResultAdapter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;

@Stateless
public class GetApplicationReflectionResultAc implements GetApplicationReflectionResultAdapter {

	@Inject
	private GetApplicationReflectionResultPub pub;

	@Override
	public Optional<IntegrationOfDaily> getApp(String companyId, Object application, GeneralDate baseDate,
			Optional<IntegrationOfDaily> dailyData) {
		return pub.getApp(companyId, application, baseDate, dailyData);
	}

}
