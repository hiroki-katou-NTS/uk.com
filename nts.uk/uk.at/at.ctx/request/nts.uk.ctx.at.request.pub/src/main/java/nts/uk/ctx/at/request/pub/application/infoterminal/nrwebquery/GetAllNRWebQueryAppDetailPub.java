package nts.uk.ctx.at.request.pub.application.infoterminal.nrwebquery;

import java.util.List;

import nts.arc.time.calendar.period.DatePeriod;

public interface GetAllNRWebQueryAppDetailPub {
	// S-1] プロセス
	public List<? extends NRQueryAppExport> getAll(NRWebQuerySidDateParameterExport param, DatePeriod period);
}
