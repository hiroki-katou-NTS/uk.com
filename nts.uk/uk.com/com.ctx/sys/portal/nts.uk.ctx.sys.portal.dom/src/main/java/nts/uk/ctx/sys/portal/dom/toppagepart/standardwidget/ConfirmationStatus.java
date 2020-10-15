package nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.月別実績.36協定特別条項の適用申請.確認状態
 * 
 * @author tutt
 *
 */
public enum ConfirmationStatus {

	// 未確認
	UNCONFIRMED(1),

	// 確認済
	CONFIRMED(2),

	// 否認
	DENY(3);

	ConfirmationStatus(int status) {
		this.value = status;
	}

	public final int value;
}
