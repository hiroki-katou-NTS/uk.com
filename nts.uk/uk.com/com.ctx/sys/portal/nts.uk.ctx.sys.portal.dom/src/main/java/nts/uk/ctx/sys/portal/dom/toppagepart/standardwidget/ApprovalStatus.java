package nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.月別実績.36協定特別条項の適用申請.承認状態
 * 
 * @author tutt
 *
 */
public enum ApprovalStatus {

	// 未承認
	UNAPPROVED(1),

	// 承認
	APPROVAL(2),

	// 否認
	DENY(3);

	ApprovalStatus(int status) {
		this.value = status;
	}

	public final int value;

}
