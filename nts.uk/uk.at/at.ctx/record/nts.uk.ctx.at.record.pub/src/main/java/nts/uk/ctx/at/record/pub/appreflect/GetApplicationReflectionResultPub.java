package nts.uk.ctx.at.record.pub.appreflect;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;

public interface GetApplicationReflectionResultPub {

	public Optional<IntegrationOfDaily> getApp(String companyId, Object application, GeneralDate baseDate,
			Optional<IntegrationOfDaily> dailyData);
}
