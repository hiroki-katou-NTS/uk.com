package nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.emp.EmpFlexMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.emp.EmpDeforLaborMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.emp.EmpRegulaMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeEmp;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeEmp;

/**
 * DS: 雇用別の設定状態を取得
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).法定労働時間.法定労働時間（New）.月単位の法定労働時間.雇用別の設定状態を取得
 * 
 * @author chungnt
 *
 */

public class GetSettingStatusEmployment {

	/**
	 * [1]設定済みの雇用を取得する
	 * 
	 * @param require
	 * @param cid 					会社ID
	 * @param laborWorkTypeAttr 	労働勤務区分
	 * @return ListeploymentCode 	List<雇用コード>
	 */
	public static List<String> getSettingEmployment(Require require, String cid, LaborWorkTypeAttr laborWorkTypeAttr) {

		List<String> employmentCodes1 = require.findEmploymentbyCid(cid)
			.stream()
			.map(m -> m.getEmployment().v())
			.collect(Collectors.toList());
		List<String> employmentCodes2 = new ArrayList<>();
		List<String> employmentCodes3 = new ArrayList<>();

		switch (laborWorkTypeAttr) {
		case REGULAR_LABOR:
			employmentCodes2 = require.findListByCid(cid)
			.stream().
			map(m -> m.getEmploymentCode().v()).
			collect(Collectors.toList());
			employmentCodes3 = require.findByCid(cid)
				.stream()
				.map(m -> m.getEmploymentCode().v())
				.collect(Collectors.toList());
			break;
		case DEFOR_LABOR:
			employmentCodes2 = require.findDeforLaborByCid(cid)
				.stream()
				.map(m -> m.getEmploymentCode().v())
				.collect(Collectors.toList());
			employmentCodes3 = require.findEmpDeforLabor(cid)
				.stream()
				.map(m -> m.getEmploymentCode().v())
				.collect(Collectors.toList());
			break;
		case FLEX:
			employmentCodes2 = require.findEmpFlexMonthByCid(cid)
				.stream()
				.map(m -> m.getEmploymentCode().v())
				.collect(Collectors.toList());
			break;
		}

		employmentCodes1.addAll(employmentCodes2);
		employmentCodes1.addAll(employmentCodes3);
		
		return employmentCodes1.stream().distinct().collect(Collectors.toList());
	}

	public static interface Require {

		// [R-1] 雇用別月単位労働時間を取得する(会社ID)
		List<MonthlyWorkTimeSetEmp> findEmploymentbyCid(String cid);

		// [R-2] 雇用別通常勤務法定労働時間を取得する(会社ID)
		List<RegularLaborTimeEmp> findListByCid(String cid);

		// [R-3] 雇用別通常勤務集計設定を取得する(会社ID)
		List<EmpRegulaMonthActCalSet> findByCid(String cid);

		// [R-4] 雇用別変形労働法定労働時間を取得する(会社ID)
		List<DeforLaborTimeEmp> findDeforLaborByCid(String cid);

		// [R-5]雇用別変形労働集計設定を取得する(会社ID)
		List<EmpDeforLaborMonthActCalSet> findEmpDeforLabor(String cid);

		// [R-6] 雇用別フレックス勤務集計方法を取得する(会社ID)
		List<EmpFlexMonthActCalSet> findEmpFlexMonthByCid(String cid);

	}
}
