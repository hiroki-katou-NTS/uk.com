package nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.システム.ポータル.トップページの部品.標準ウィジェット.承認すべき申請状況の項目
 * 
 * @author tutt
 *
 */
public enum ApprovedApplicationStatusItem {
	/**
	 * 0 - 承認すべき申請データ
	 */
	APPLICATION_DATA(0),

	/**
	 * 1 - 日別実績承認すべきデータ
	 */
	DAILY_PERFORMANCE_DATA(1),

	/**
	 * 2 - 月別実績承認すべきデータ
	 */
	MONTHLY_RESULT_DATA(2),

	/**
	 * 3 - ３６協定承認すべき申請データ
	 */
	AGREEMENT_APPLICATION_DATA(3);

	ApprovedApplicationStatusItem(int type) {
		this.value = type;
	}

	public final int value;
}
