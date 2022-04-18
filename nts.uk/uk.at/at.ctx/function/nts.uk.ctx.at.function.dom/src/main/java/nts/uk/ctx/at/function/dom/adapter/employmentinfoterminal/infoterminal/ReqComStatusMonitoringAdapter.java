package nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal;

/**
 * @author thanh_nx
 *
 *         リクエスト通信の状態監視を更新するAdapter
 */
public interface ReqComStatusMonitoringAdapter {
	public void update(String contractCode, String terminalCode, boolean statusConnect);
}
