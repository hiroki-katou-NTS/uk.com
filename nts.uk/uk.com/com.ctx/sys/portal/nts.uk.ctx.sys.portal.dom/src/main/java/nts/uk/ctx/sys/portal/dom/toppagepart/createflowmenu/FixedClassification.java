package nts.uk.ctx.sys.portal.dom.toppagepart.createflowmenu;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.システム.ポータル.トップページの部品.フローメニュー作成.既定区分
 */
public enum FixedClassification {
	FIXED(0),
	RANDOM(1);
	
	public int value;

	private FixedClassification(int value) {
		this.value = value;
	}
}
