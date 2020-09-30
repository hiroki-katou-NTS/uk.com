package nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.システム.ポータル.トップページの部品.標準ウィジェット.申請状況ウィジェットの項目
 * 
 * @author tutt
 *
 */
public enum ApplicationStatusWidgetItem {
	/**
	 * 0 - 承認された件数
	 */
	APPROVED_NUMBER(0),

	/**
	 * 1 - 未承認件数
	 */
	UNAPPROVED_NUMBER(1),

	/**
	 * 2 - 否認された件数
	 */
	DENIED_NUMBER(2),

	/**
	 * 3 - 差し戻し件数
	 */
	REMAND_NUMBER(3),

	/**
	 * 4 - 今月の申請締め切り日
	 */
	MONTH_APP_DEADLINE(4);

	ApplicationStatusWidgetItem(int type) {
		this.value = type;
	}

	public final int value;
}
