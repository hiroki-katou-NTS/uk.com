package nts.uk.ctx.at.record.app.command.monthly.vtotalmethod;

//import java.util.Map;

import lombok.Data;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.SpecCountNotCalcSubject;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.SpecTotalCountMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.TADaysCountCondOfMonthlyAggr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.TADaysCountOfMonthlyAggr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.VerticalTotalMethodOfMonthly;

/**
 * The Class AddVerticalTotalMethodOfMonthlyCommand.
 *
 * @author HoangNDH
 */
@Data
public class AddVerticalTotalMethodOfMonthlyCommand {
	
	/** 振出日数 */
	private int attendanceItemCountingMethod;
	
	// 連続勤務の日でもカウントする
	private boolean continuousCount;
	// 勤務日ではない日でもカウントする
	private boolean notWorkCount;
	// 計算対象外のカウント条件
	private int specCount;
	
//	/** 計算対象外のカウント条件 */ 
//	private int specCountNotCalcSubject;
//	
//	/** 勤務種類のカウント条件 */
//	private Map<Integer, Boolean> workTypeSetting;
	
	public VerticalTotalMethodOfMonthly toDomain(String companyId) {
		return VerticalTotalMethodOfMonthly.of(
				companyId, 
				TADaysCountOfMonthlyAggr.of(EnumAdaptor.valueOf(attendanceItemCountingMethod, TADaysCountCondOfMonthlyAggr.class)),
				new SpecTotalCountMonthly(continuousCount, notWorkCount, EnumAdaptor.valueOf(specCount, SpecCountNotCalcSubject.class)));
	}
}
