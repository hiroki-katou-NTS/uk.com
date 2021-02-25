package nts.uk.screen.at.app.query.kmk004.common;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.wkp.WkpFlexMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.wkp.WkpFlexMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.wkp.WkpDeforLaborMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.wkp.WkpDeforLaborMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.wkp.WkpRegulaMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.wkp.WkpRegulaMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.GeSettingStatusForEachWorkplace;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetWkp;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeWkp;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeWkpRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeWkp;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeWkpRepo;
import nts.uk.shr.com.context.AppContexts;

/**
 * 職場リストを表示する
 * UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.メニュー別OCD（共通）.職場リストを表示する.職場リストを表示する
 * @author chungnt
 *
 */

@Stateless
public class WorkplaceList {

	@Inject
	private MonthlyWorkTimeSetRepo timeSetRepo;
	
	@Inject
	private RegularLaborTimeWkpRepo regularLaborTimeWkpRepo;
	
	@Inject
	private WkpRegulaMonthActCalSetRepo wkpRegulaMonthActCalSetRepo;
	
	@Inject
	private DeforLaborTimeWkpRepo deforLaborTimeWkpRepo;
	
	@Inject
	private WkpDeforLaborMonthActCalSetRepo wkpDeforLaborMonthActCalSetRepo;
	
	@Inject
	private WkpFlexMonthActCalSetRepo wkpFlexMonthActCalSetRepo;

	/**
	 * 
	 * @param laborWorkTypeAttr 勤務区分
	 * @return
	 */
	public List<WorkplaceIdDto> get(LaborWorkTypeAttr laborWorkTypeAttr) {

		List<WorkplaceIdDto> resutl = new ArrayList<>();
		String cid = AppContexts.user().companyId();

		// 1 Call DS 職場別の設定状態を取得
		Require require = new Require(timeSetRepo,
				regularLaborTimeWkpRepo,
				wkpRegulaMonthActCalSetRepo,
				deforLaborTimeWkpRepo,
				wkpDeforLaborMonthActCalSetRepo,
				wkpFlexMonthActCalSetRepo);
		
		List<String> workplaceIds = GeSettingStatusForEachWorkplace.geSettingStatusForEachWorkplace(require, cid, laborWorkTypeAttr);
		
		resutl = workplaceIds.stream().map(m -> {
			return new WorkplaceIdDto(m);
		}).collect(Collectors.toList());

		return resutl;
	}

	@AllArgsConstructor
	public static class Require implements GeSettingStatusForEachWorkplace.Require {

		private MonthlyWorkTimeSetRepo timeSetRepo;
		private RegularLaborTimeWkpRepo regularLaborTimeWkpRepo;
		private WkpRegulaMonthActCalSetRepo wkpRegulaMonthActCalSetRepo;
		private DeforLaborTimeWkpRepo deforLaborTimeWkpRepo;
		private WkpDeforLaborMonthActCalSetRepo wkpDeforLaborMonthActCalSetRepo;
		private WkpFlexMonthActCalSetRepo wkpFlexMonthActCalSetRepo;

		@Override
		public List<MonthlyWorkTimeSetWkp> findWorkplace(String cid, LaborWorkTypeAttr laborAttr) {
			return timeSetRepo.findWorkplace(AppContexts.user().companyId(), laborAttr);
		}

		@Override
		public List<RegularLaborTimeWkp> findAll(String cid) {
			return regularLaborTimeWkpRepo.findAll(AppContexts.user().companyId());
		}

		@Override
		public List<WkpRegulaMonthActCalSet> findWkpRegulaMonthAll(String cid) {
			return wkpRegulaMonthActCalSetRepo.findWkpRegulaMonthAll(AppContexts.user().companyId());
		}

		@Override
		public List<DeforLaborTimeWkp> findDeforLaborTimeWkpByCid(String cid) {
			return deforLaborTimeWkpRepo.findDeforLaborTimeWkpByCid(AppContexts.user().companyId());
		}

		@Override
		public List<WkpDeforLaborMonthActCalSet> findAllByCid(String cid) {
			return wkpDeforLaborMonthActCalSetRepo.findAllByCid(AppContexts.user().companyId());
		}

		@Override
		public List<WkpFlexMonthActCalSet> findByCid(String cid) {
			return wkpFlexMonthActCalSetRepo.findByCid(AppContexts.user().companyId());
		}
	}

}
