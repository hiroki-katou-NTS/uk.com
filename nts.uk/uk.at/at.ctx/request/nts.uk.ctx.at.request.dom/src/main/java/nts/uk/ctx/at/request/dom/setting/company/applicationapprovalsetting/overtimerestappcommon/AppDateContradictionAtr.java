package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon;

/**
 * @author loivt
 *	登録可否チェック区分
 */
public enum AppDateContradictionAtr{
	
	/**
	 * 0: チェックしない
	 */
	 NOTCHECK(0),
	 
	 /**
	 * 1: チェックする（登録可）
	 */
	CHECKREGISTER(1),
	 
	 /**
	 * 2: チェックする（登録不可）
	 */
	CHECKNOTREGISTER(2);
	
	public final int value;
	
	AppDateContradictionAtr(int value){
		this.value = value;
	}
}
