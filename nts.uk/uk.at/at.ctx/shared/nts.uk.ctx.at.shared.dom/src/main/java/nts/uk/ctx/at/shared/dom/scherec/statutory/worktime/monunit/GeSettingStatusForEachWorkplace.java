package nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.wkp.WkpFlexMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.wkp.WkpDeforLaborMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.wkp.WkpRegulaMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeWkp;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeWkp;

/**
 * 職場別の設定状態を取得
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).法定労働時間.法定労働時間（New）.月単位の法定労働時間.職場別の設定状態を取得
 * @author chungnt
 *
 */

public class GeSettingStatusForEachWorkplace {


	 /**
	  * 
	  * @param require
	  * @param cid					会社ID
	  * @param laborWorkTypeAttr	労働勤務区分	
	  * @return						List<職場ID>	
	  */
	public static List<String> geSettingStatusForEachWorkplace(Require require, String cid, LaborWorkTypeAttr laborWorkTypeAttr) {

		List<String> workplaceIds1 = require.findWorkplace(cid, laborWorkTypeAttr)
				.stream()
				.map(m -> m.getWorkplaceId())
				.collect(Collectors.toList());
		List<String> workplaceIds2 = new ArrayList<>();
		List<String> workplaceIds3 = new ArrayList<>();
		
		switch (laborWorkTypeAttr) {
		case REGULAR_LABOR:
			workplaceIds2 = require.findAll(cid)
				.stream()
				.map(m -> m.getWorkplaceId())
				.collect(Collectors.toList());
			workplaceIds3 = require.findWkpRegulaMonthAll(cid)
					.stream()
					.map(m -> m.getWorkplaceId())
					.collect(Collectors.toList());
			break;
		case DEFOR_LABOR:
			workplaceIds2 = require.findDeforLaborTimeWkpByCid(cid)
			.stream()
			.map(m -> m.getWorkplaceId())
			.collect(Collectors.toList());
			workplaceIds3 = require.findAllByCid(cid)
				.stream()
				.map(m -> m.getWorkplaceId())
				.collect(Collectors.toList());
			break;
		case FLEX:
			workplaceIds2 = require.findByCid(cid)
			.stream()
			.map(m -> m.getWorkplaceId())
			.collect(Collectors.toList());
			break;
		}
		
		workplaceIds1.addAll(workplaceIds2);
		workplaceIds1.addAll(workplaceIds3);
		
		return workplaceIds1.stream().distinct().collect(Collectors.toList());
	}

	public static interface Require {

		// [R-1] 職場別月単位労働時間を取得する(会社ID)
		List<MonthlyWorkTimeSetWkp> findWorkplace(String cid, LaborWorkTypeAttr laborAttr);
		
		// [R-2] 職場別通常勤務法定労働時間を取得する(会社ID)
		List<RegularLaborTimeWkp> findAll(String cid);
		
		// [R-3] 職場別通常勤務集計設定を取得する(会社ID)
		List<WkpRegulaMonthActCalSet> findWkpRegulaMonthAll(String cid);
		
		// [R-4] 職場別変形労働法定労働時間を取得する(会社ID)
		List<DeforLaborTimeWkp> findDeforLaborTimeWkpByCid(String cid);
		
		// [R-5]職場別変形労働集計設定を取得する(会社ID)
		List<WkpDeforLaborMonthActCalSet> findAllByCid(String cid);
		
		// [R-6] 職場別フレックス勤務集計方法を取得する(会社ID)
		List<WkpFlexMonthActCalSet> findByCid(String cid);

	}
	
}
