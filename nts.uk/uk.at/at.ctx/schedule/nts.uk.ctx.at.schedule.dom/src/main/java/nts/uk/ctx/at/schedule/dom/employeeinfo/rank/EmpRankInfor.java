package nts.uk.ctx.at.schedule.dom.employeeinfo.rank;

import java.util.Optional;

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
	private final Optional<RankCode> rankCode;
	
	/**ランク記号 **/
	private final Optional<RankSymbol> rankSymbol;

	/**
	 * [C-1] ランクなしで作る
	 * @param empId
	 * @return
	 */
	public static EmpRankInfor makeWithoutRank(String empId){
		return new EmpRankInfor(empId, Optional.empty(), Optional.empty());
	}
	/**
	 * [C-2] 作る
	 * @param empId
	 * @param rankCode
	 * @param rankName
	 * @return
	 */
	public static EmpRankInfor create(String empId, RankCode rankCode , RankSymbol rankSymbol){
		return new EmpRankInfor(empId, Optional.ofNullable(rankCode), Optional.ofNullable(rankSymbol));
	}
	
	

	
	
	
	
	
	

}
