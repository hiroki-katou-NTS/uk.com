package nts.uk.ctx.at.function.ac.employmentinfoterminal.infoterminal;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.ReqComStatusMonitoringAdapter;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal.ReqComStatusMonitoringPub;

@Stateless
public class ReqComStatusMonitoringAdapterImpl implements ReqComStatusMonitoringAdapter {

	@Inject
	private ReqComStatusMonitoringPub pub;

	@Override
	public void update(String contractCode, String terminalCode, boolean statusConnect) {
		pub.update(contractCode, terminalCode, statusConnect);
	}

}
