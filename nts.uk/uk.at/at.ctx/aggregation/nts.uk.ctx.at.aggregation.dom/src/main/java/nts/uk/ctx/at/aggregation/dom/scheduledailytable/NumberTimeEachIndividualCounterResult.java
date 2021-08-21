package nts.uk.ctx.at.aggregation.dom.scheduledailytable;


import java.math.BigDecimal;

import lombok.Value;
import nts.uk.ctx.at.aggregation.dom.common.ScheRecAtr;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
/**
 * 個人別の回数集計結果
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.勤務計画実施表.勤務計画実施表の個人計を集計する.個人別の回数集計結果
 * @author lan_lt
 *
 */
@Value
public class NumberTimeEachIndividualCounterResult {
	/** 社員ID **/
	private EmployeeId sid;
	
	/** 回数集計NO **/
	private Integer totalCountNo;
	
	/** 予実区分  **/
	private ScheRecAtr scheRecAtr;
	
	/** 値  */
	private BigDecimal value;

}
