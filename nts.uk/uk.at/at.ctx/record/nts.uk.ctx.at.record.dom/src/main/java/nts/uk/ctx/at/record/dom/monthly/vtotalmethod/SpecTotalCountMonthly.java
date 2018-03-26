package nts.uk.ctx.at.record.dom.monthly.vtotalmethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;

/**
 * 月別実績集計の特定日カウント
 * @author HoangNDH
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpecTotalCountMonthly {
	
	// 勤務種類のカウント条件
	private List<SpecDayMonthCountCon> specDayOfTotalMonCon;
	// 計算対象外のカウント条件
	private SpecCountNotCalcSubject specCount;
	
	public static SpecTotalCountMonthly createFromJavaType(Map<Integer, Boolean> specDayOfTotalMonCon, int specCount) {
		List<SpecDayMonthCountCon> specDay = new ArrayList<SpecDayMonthCountCon>();
		for (Map.Entry<Integer, Boolean> entry: specDayOfTotalMonCon.entrySet()) {
			specDay.add(SpecDayMonthCountCon.createFromJavaType(entry.getKey(), entry.getValue()));
		}
		return new SpecTotalCountMonthly(specDay, EnumAdaptor.valueOf(specCount, SpecCountNotCalcSubject.class));
	}
}
