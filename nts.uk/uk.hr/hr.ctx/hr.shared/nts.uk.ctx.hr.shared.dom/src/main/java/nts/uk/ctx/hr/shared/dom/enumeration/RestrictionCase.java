package nts.uk.ctx.hr.shared.dom.enumeration;

/**
 * @author laitv
 * 制限ケース
 * path: UKDesign.ドメインモデル.NittsuSystem.UniversalK.人事.shared.enumeration.制限ケース
 * 
 */
public enum RestrictionCase {

	LeaveOfAbsence (0,"休職"),
	
	SuspensionOfBusiness (1,"休業");
	
	public int value;
	
	public String nameId;

	RestrictionCase(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
	
}
