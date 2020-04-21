package nts.uk.ctx.hr.shared.dom.personalinfo.laborcontracthistory;

/**
 * @author laitv
 * あり・なし区分
 * path: UKDesign.ドメインモデル.NittsuSystem.UniversalK.人事.shared.個人情報（人事）.労働契約履歴.あり・なし区分 */
public enum YesNoClassification {

	YES (0,"あり"),
	
	NONE (1,"なし");
	
	public int value;
	
	public String nameId;

	YesNoClassification(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
	
}
