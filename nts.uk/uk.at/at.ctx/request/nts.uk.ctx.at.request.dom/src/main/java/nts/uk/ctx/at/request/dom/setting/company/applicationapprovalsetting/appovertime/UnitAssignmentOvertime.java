package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.appovertime;

/**
 * @author loivt
 * 申請時間指定単位
 */
public enum UnitAssignmentOvertime {

		/**
		 * 1分
		 */
		ONEMIN(0),
		/**
		 * 5分
		 */
		FIVEMIN(1),
		/**
		 * 6分
		 */
		SIXMIN(2),
		/**
		 * 10分
		 */
		TENMIN(3),
		/**
		 * 15分
		 */
		FIFTEENMIN(4),
		/**
		 * 20分
		 */
		TWENTYMIN(5),
		/**
		 * 30分
		 */
		THIRTYMIN(6),
		/**
		 * 60分
		 */
		SIXTYMIN(7);
	
	public final int value;
	UnitAssignmentOvertime(int value){
		this.value = value;
	}

}
