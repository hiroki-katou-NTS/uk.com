package nts.uk.ctx.health.dom.sample;

import java.util.List;

import nts.arc.time.calendar.period.DatePeriod;

public interface SampleOshiraseAdapter {

	List<SampleOshirase> get(String companyId, DatePeriod period);
}
