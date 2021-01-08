package nts.uk.ctx.office.dom.favorite;

/*
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.在席照会.在席照会.対象選択
 */
public enum TargetSelection {
	// 職場
	WORKPLACE(0),

	// 所属職場
	AFFILIATION_WORKPLACE(1);

	public int value;

	TargetSelection(int val) {
		this.value = val;
	}
}
