package nts.uk.ctx.sys.env.pub.mailserver;

public interface MailServerPub {
	boolean findBy(String companyId);
	
	/**
	 * メールサーバを設定したかチェックする
	 * @param companyID
	 * @return
	 */
	public MailServerSetExport checkMailServerSet(String companyID); 
}
