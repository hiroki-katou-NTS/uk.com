package nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.月別実績.36協定特別条項の適用申請.３６協定申請種類
 * 
 * @author tutt
 *
 */
public enum Agreement36ApplicationType {

	// 1ヶ月
	ONE_MONTH(1),

	// 1年間
	ONE_YEAR(2);

	public final int value;

	Agreement36ApplicationType(int type) {
		this.value = type;
	}
}
