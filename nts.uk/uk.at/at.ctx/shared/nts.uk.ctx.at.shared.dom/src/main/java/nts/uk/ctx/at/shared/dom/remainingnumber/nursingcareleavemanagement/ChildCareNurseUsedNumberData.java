package nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement;

/**
 * 子の看護休暇使用数データ
 * @author yuri_tamakoshi
 */
public class ChildCareNurseUsedNumberData extends ChildCareNurseUsedNumber {
	/** 社員ID */
	private String employeeId;

	/**
	 * コンストラクタ
	 */
	public ChildCareNurseUsedNumberData(){
		this.employeeId = "";
	}

	/**
	 * ファクトリー
	 * @param employeeId　社員ID
	 * @return  子の看護休暇使用数データ
	*/
	public static ChildCareNurseUsedNumberData of(
			String employeeId){

		ChildCareNurseUsedNumberData domain = new ChildCareNurseUsedNumberData();
		domain.employeeId = employeeId;
		return domain;
	}

}
