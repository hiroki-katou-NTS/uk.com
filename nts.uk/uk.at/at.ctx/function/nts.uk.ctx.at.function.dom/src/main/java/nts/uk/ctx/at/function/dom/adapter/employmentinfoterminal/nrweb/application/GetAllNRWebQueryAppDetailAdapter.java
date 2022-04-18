package nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.application;

import java.util.List;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.common.NRWebQuerySidDateParameter;

/**
 * @author thanh_nx
 *
 *         すべてのNRWeb照会申請を取得Adapter
 */
public interface GetAllNRWebQueryAppDetailAdapter {

	// S-1] プロセス
	public List<? extends NRQueryAppImport> getAll(NRWebQuerySidDateParameter param, DatePeriod period);
}
