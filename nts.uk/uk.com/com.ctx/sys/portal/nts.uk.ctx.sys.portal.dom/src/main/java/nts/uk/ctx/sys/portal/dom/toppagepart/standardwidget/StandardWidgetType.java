package nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.システム.ポータル.トップページの部品.標準ウィジェット.標準ウィジェット種別
 * 
 * @author tutt
 *
 */
public enum StandardWidgetType {
	/**
	 * 1 - 申請状況
	 */
	APPLICATION_STATUS(1),

	/**
	 * 2 - 承認すべき状況
	 */
	APPROVE_STATUS(2),

	/**
	 * 3 - 勤務状況
	 */
	WORK_STATUS(3),

	/**
	 * 4 - 上長用の時間外労働時間
	 */
	OVERTIME_FOR_SUPERIOR(4),

	/**
	 * 5 - 打刻入力
	 */
	STAMPT_INPUT(5),

	/**
	 * 6 - トップぺージアラーム
	 */
	TOP_PAGE_ALARM(6),

	/**
	 * 7 - 従業員用の時間外勤務時間
	 */
	OVERTIME_FOR_EMPLOYEE(7);

	StandardWidgetType(int type) {
		this.value = type;
	}

	public final int value;
}
