package nts.uk.ctx.at.request.dom.application.appabsence.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpecialLeaveInfor {
	/**
	 * 当てはまるフラグ
	 */
	private boolean applicableFlg;
	
	/**
	 * 続柄の表示フラグ
	 */
	private boolean relationFlg;
	/**
	 * 喪主の表示フラグ
	 */
	private boolean  mournerDisplayFlg;
	/**
	 * 続柄の理由の表示フラグ
	 */
	private boolean displayRelationReasonFlg;
	
	/**
	 * 付与方法(0: 単独付与, 1:定期付与 )
	 */
	private int methodOfGranting;
	
	/**
	 * 上限日数(固定)
	 */
	private int maxDayFixed;
	
	/**
	 * 上限日数(喪主)
	 */
	private int maxDayMourner;
	
	/**
	 * 上限日数(喪主ではない)  
	 */
	private int maxDayNotMourner;
	
	/**
	 * 休日除外区分( 0: 休日は除く、1: 休日を含める)
	 */
	private int holidayAtr;
	
	/**
	 * 付与種類(0:固定付与,1: 続柄参照)
	 */
	private int grantType;
}
