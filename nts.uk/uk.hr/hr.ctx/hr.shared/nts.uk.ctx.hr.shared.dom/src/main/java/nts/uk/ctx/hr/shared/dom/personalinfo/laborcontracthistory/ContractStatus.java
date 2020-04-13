package nts.uk.ctx.hr.shared.dom.personalinfo.laborcontracthistory;

/**
 * @author laitv
 * 契約状況
 * path: UKDesign.ドメインモデル.NittsuSystem.UniversalK.人事.shared.個人情報（人事）.労働契約履歴.契約状況
 *
 */
public enum ContractStatus {

	Agreement (1,"契約"),
	
	NoContract (0,"無契約");
	
	public int value;
	
	public String nameId;

	ContractStatus(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
	
}
