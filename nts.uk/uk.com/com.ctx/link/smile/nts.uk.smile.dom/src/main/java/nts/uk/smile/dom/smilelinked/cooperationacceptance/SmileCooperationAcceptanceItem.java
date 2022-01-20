package nts.uk.smile.dom.smilelinked.cooperationacceptance;

import java.util.ArrayList;
import java.util.List;

/**
 * Smile連携受入項目
 * 
 */
public enum SmileCooperationAcceptanceItem {
	
	/**
	 * SMILE_組織情報（職場）
	 */
	ORGANIZATION_INFORMATION(1, "組織情報"),

	/**
	 * SMILE_人事基本情報
	 */
	BASIC_PERSONNEL_INFORMATION(2, "人事基本情報"),

	/**
	 * SMILE_職制情報
	 */
	JOB_STRUCTURE_INFORMATION(3, "職制情報"),

	/**
	 * SMILE_住所情報
	 */
	ADDRESS_INFORMATION(4, "住所情報"),

	/**
	 * SMILE_休職情報
	 */
	LEAVE_INFORMATION(5, "休職情報"),

	/**
	 * SMILE_所属マスター
	 */
	AFFILIATED_MASTER(6, "所属マスター"),

	/**
	 * SMILE_社員マスター
	 */
	EMPLOYEE_MASTER(7, "社員マスター");

	public int value;
	public String nameId;

	private SmileCooperationAcceptanceItem(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
	
    public int getValue() {
    	return this.value;
    }
	
    public static final List<SmileCooperationAcceptanceItem> lookup = new ArrayList<SmileCooperationAcceptanceItem>();
    static {
        for (SmileCooperationAcceptanceItem e : SmileCooperationAcceptanceItem.values()) {
            lookup.add(e);
        }
    }
}
