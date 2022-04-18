package nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal;

/**
 * @author thanh_nx
 *
 *         リクエスト通信の状態監視を更新するPublish
 */
public interface ReqComStatusMonitoringPub {

	public void update(String contractCode, String terminalCode, boolean statusConnect);

}
