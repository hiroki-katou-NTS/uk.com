package nts.uk.screen.at.app.query.kmk004.common;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.sha.ShaFlexMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.sha.ShaFlexMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.sha.ShaDeforLaborMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.sha.ShaDeforLaborMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.sha.ShaRegulaMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.sha.ShaRegulaMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.GetSettingStatusForEachEmployee;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetSha;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeSha;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeShaRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeSha;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeShaRepo;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.メニュー別OCD（共通）.社員リストを表示する.社員リストを表示する
 * @author chungnt
 *
 */

@Stateless
public class EmployeeList {

	@Inject
	private MonthlyWorkTimeSetRepo monthlyWorkTimeSetShaRepo;
	
	@Inject
	private RegularLaborTimeShaRepo regularLaborTimeShaRepo;
	
	@Inject
	private ShaRegulaMonthActCalSetRepo shaRegulaMonthActCalSetRepo;
	
	@Inject
	private DeforLaborTimeShaRepo deforLaborTimeShaRepo;
	
	@Inject
	private ShaDeforLaborMonthActCalSetRepo shaDeforLaborMonthActCalSetRepo;
	
	@Inject
	private ShaFlexMonthActCalSetRepo shaFlexMonthActCalSetRepo;

	/**
	 * 
	 * @param laborWorkTypeAttr 　勤務区分
	 * @return
	 */
	public List<EmployeeIdDto> get(LaborWorkTypeAttr laborWorkTypeAttr) {

		List<EmployeeIdDto> result = new ArrayList<>();
		String cid = AppContexts.user().companyId();
		
		Require require = new Require(monthlyWorkTimeSetShaRepo,
				regularLaborTimeShaRepo,
				shaRegulaMonthActCalSetRepo,
				deforLaborTimeShaRepo,
				shaDeforLaborMonthActCalSetRepo,
				shaFlexMonthActCalSetRepo);

		List<String> sids = GetSettingStatusForEachEmployee.getSettingStatusForEachEmployee(require, cid, laborWorkTypeAttr);
		
		result = sids.stream().map(m -> {
			return new EmployeeIdDto(m);
		}).collect(Collectors.toList());
		
		return result;
	}

	@AllArgsConstructor
	public static class Require implements GetSettingStatusForEachEmployee.Require {

		private MonthlyWorkTimeSetRepo monthlyWorkTimeSetShaRepo;
		private RegularLaborTimeShaRepo regularLaborTimeShaRepo;
		private ShaRegulaMonthActCalSetRepo shaRegulaMonthActCalSetRepo;
		private DeforLaborTimeShaRepo deforLaborTimeShaRepo;
		private ShaDeforLaborMonthActCalSetRepo shaDeforLaborMonthActCalSetRepo;
		private ShaFlexMonthActCalSetRepo shaFlexMonthActCalSetRepo;

		@Override
		public List<MonthlyWorkTimeSetSha> findEmployeeByCid(String cid, LaborWorkTypeAttr laborAttr) {
			return monthlyWorkTimeSetShaRepo.findEmployeeByCid(AppContexts.user().companyId(), laborAttr);
		}

		@Override
		public List<RegularLaborTimeSha> findAll(String cid) {
			return regularLaborTimeShaRepo.findAll(AppContexts.user().companyId());
		}

		@Override
		public List<ShaRegulaMonthActCalSet> findRegulaMonthActCalSetByCid(String cid) {
			return shaRegulaMonthActCalSetRepo.findRegulaMonthActCalSetByCid(AppContexts.user().companyId());
		}

		@Override
		public List<DeforLaborTimeSha> findDeforLaborTimeShaByCid(String cid) {
			return deforLaborTimeShaRepo.findDeforLaborTimeShaByCid(AppContexts.user().companyId());
		}

		@Override
		public List<ShaDeforLaborMonthActCalSet> findByCid(String cid) {
			return shaDeforLaborMonthActCalSetRepo.findByCid(AppContexts.user().companyId());
		}

		@Override
		public List<ShaFlexMonthActCalSet> findAllShaByCid(String cid) {
			return shaFlexMonthActCalSetRepo.findAllShaByCid(AppContexts.user().companyId());
		}

	}
}
