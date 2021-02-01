package nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.sha.ShaFlexMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.sha.ShaDeforLaborMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.sha.ShaRegulaMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeSha;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeSha;

/**
 * 社員別の設定状態を取得
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).法定労働時間.法定労働時間（New）.月単位の法定労働時間.社員別の設定状態を取得
 * 
 * @author chungnt
 *
 */
public class GetSettingStatusForEachEmployee {

	/**
	 * [1]設定済みの社員IDを取得する
	 * 
	 * @param require
	 * @param cid
	 *            会社ID
	 * @param laborWorkTypeAttr
	 *            労働勤務区分
	 * @return List<社員ID>
	 */
	public static List<String> getSettingStatusForEachEmployee(Require require, String cid,
			LaborWorkTypeAttr laborWorkTypeAttr) {

		List<String> employeeIds1 = require.findEmployeeByCid(cid, laborWorkTypeAttr)
				.stream()
				.map(m -> m.getEmpId())
				.collect(Collectors.toList());
		List<String> employeeIds2 = new ArrayList<>();
		List<String> employeeIds3 = new ArrayList<>();

		switch (laborWorkTypeAttr) {
		case REGULAR_LABOR:
			employeeIds2 = require.findAll(cid)
				.stream()
				.map(m -> m.getEmpId())
				.collect(Collectors.toList());
			employeeIds3 = require.findRegulaMonthActCalSetByCid(cid)
					.stream()
					.map(m -> m.getEmployeeId())
					.collect(Collectors.toList());
			break;
		case DEFOR_LABOR:
			employeeIds2 = require.findDeforLaborTimeShaByCid(cid)
			.stream()
			.map(m -> m.getEmpId())
			.collect(Collectors.toList());
			employeeIds3 = require.findByCid(cid)
				.stream()
				.map(m -> m.getEmployeeId())
				.collect(Collectors.toList());
			break;
		case FLEX:
			employeeIds2 = require.findAllShaByCid(cid)
			.stream()
			.map(m -> m.getEmpId())
			.collect(Collectors.toList());
			break;
		}
		
		employeeIds1.addAll(employeeIds2);
		employeeIds1.addAll(employeeIds3);
		return employeeIds1.stream().distinct().collect(Collectors.toList());
	}

	public static interface Require {

		// [R-1] 社員別月単位労働時間を取得する(会社ID)
		List<MonthlyWorkTimeSetSha> findEmployeeByCid(String cid, LaborWorkTypeAttr laborAttr);

		// [R-2] 社員別通常勤務法定労働時間を取得する(会社ID)
		List<RegularLaborTimeSha> findAll(String cid);

		// [R-3] 社員別通常勤務集計設定を取得する(会社ID)
		List<ShaRegulaMonthActCalSet> findRegulaMonthActCalSetByCid(String cid);

		// [R-4] 社員別変形労働法定労働時間を取得する(会社ID)
		List<DeforLaborTimeSha> findDeforLaborTimeShaByCid(String cid);

		// [R-5]社員別変形労働集計設定を取得する(会社ID)
		List<ShaDeforLaborMonthActCalSet> findByCid(String cid);

		// [R-6] 社員別フレックス勤務集計方法を取得する(会社ID)
		List<ShaFlexMonthActCalSet> findAllShaByCid(String cid);
	}

}
