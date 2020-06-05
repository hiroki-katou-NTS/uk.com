package nts.uk.ctx.hr.shared.dom.personalinfo.laborcontracthistory;

/**
 * @author laitv
 * 就業区分
 * path: UKDesign.ドメインモデル.NittsuSystem.UniversalK.人事.shared.個人情報（人事）.労働契約履歴.就業区分*/
public enum EmploymentDivision {

	StatutoryWorkingHoursSystem (0,"法定労働時間制"),
	
	FlextimeSystem (1,"フレックスタイム制"),
	
	ModifiedWorkingHoursSystem (2,"変形労働時間制"),
	
	NotAppliEmploymentCalculation (3,"就業計算対象外");
	
	public int value;
	
	public String nameId;

	EmploymentDivision(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
	
}
