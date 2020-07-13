package nts.uk.ctx.at.schedule.dom.employeeinfo.rank;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 社員ランク情報
 *  UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務予定.社員情報.ランク
 * @author HieuLT
 *
 */
@Getter
@RequiredArgsConstructor
public class EmpRankInfor {
	
	 /**社員ID **/
	private final String empId;
	/** ランクコード **/
	private final String rankCode;
	
	/**ランク記号 **/
	private final String rankName;

	public EmpRankInfor(String empId) {
		super();
		this.empId = empId;
		this.rankCode = null;
		this.rankName = null;
		
		
	}


	
	
	
	
	
	

}
