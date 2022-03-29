package nts.uk.ctx.at.schedule.dom.schedule.support;

/**
 * 応援予定の機能制御Repository
 * @author kumiko_otake
 */
public interface SupportFunctionControlRepository {

	/**
	 * get
	 * @param cid 会社ID
	 * @return 応援予定の機能制御
	 */
	public SupportFunctionControl get(String cid);

	/**
	 * update
	 * @param cid 会社ID
	 * @param domain 応援予定の機能制御
	 */
	public void update(String cid, SupportFunctionControl domain);

}
