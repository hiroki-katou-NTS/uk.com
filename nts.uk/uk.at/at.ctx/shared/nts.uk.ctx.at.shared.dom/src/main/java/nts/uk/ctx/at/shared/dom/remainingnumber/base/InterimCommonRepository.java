package nts.uk.ctx.at.shared.dom.remainingnumber.base;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;

public interface InterimCommonRepository {

	void deleteInterim(String sid, DatePeriod period, RemainType type);
	
}
