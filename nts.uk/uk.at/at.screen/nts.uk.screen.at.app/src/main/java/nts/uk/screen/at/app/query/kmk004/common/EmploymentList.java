package nts.uk.screen.at.app.query.kmk004.common;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.emp.EmpFlexMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.emp.EmpFlexMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.emp.EmpDeforLaborMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.emp.EmpDeforLaborMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.emp.EmpRegulaMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.emp.EmpRegulaMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.GetSettingStatusEmployment;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetEmp;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeEmp;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeEmpRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeEmp;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeEmpRepo;
import nts.uk.shr.com.context.AppContexts;

/**
 * 雇用リストを表示する
 * UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.メニュー別OCD（共通）.雇用リストを表示する.雇用リストを表示する
 * @author chungnt
 *
 */

@Stateless
public class EmploymentList {

	@Inject
	private MonthlyWorkTimeSetRepo monthlyWorkTimeSetEmpRepo;
	
	@Inject
	private RegularLaborTimeEmpRepo regularLaborTimeEmpRepo;
	
	@Inject
	private EmpRegulaMonthActCalSetRepo empRegulaMonthActCalSetRepo;
	
	@Inject
	private DeforLaborTimeEmpRepo deforLaborTimeEmpRepo;
	
	@Inject
	private EmpDeforLaborMonthActCalSetRepo empDeforLaborMonthActCalSetRepo;
	
	@Inject
	private EmpFlexMonthActCalSetRepo empFlexMonthActCalSetRepo;
	
	/**
	 * 
	 * @param laborWorkTypeAttr 勤務区分
	 * @return
	 */
	public List<EmploymentCodeDto> get(LaborWorkTypeAttr laborWorkTypeAttr) {
		
		List<EmploymentCodeDto> resutl = new ArrayList<>();
		String cid = AppContexts.user().companyId();
		
		//1 Call Ds 雇用別の設定状態を取得
		
		Require require = new Require(monthlyWorkTimeSetEmpRepo,
				regularLaborTimeEmpRepo,
				empRegulaMonthActCalSetRepo,
				deforLaborTimeEmpRepo,
				empDeforLaborMonthActCalSetRepo,
				empFlexMonthActCalSetRepo);
		
		List<String> employmentIds = GetSettingStatusEmployment.getSettingEmployment(require, cid, laborWorkTypeAttr);
		
		resutl = employmentIds.stream().map(m -> {
			return new EmploymentCodeDto(m); 
		}).collect(Collectors.toList());
		
		return resutl;
	}
	
	@AllArgsConstructor
	public static class Require implements GetSettingStatusEmployment.Require {
		
		private MonthlyWorkTimeSetRepo monthlyWorkTimeSetEmpRepo;
		private RegularLaborTimeEmpRepo regularLaborTimeEmpRepo;
		private EmpRegulaMonthActCalSetRepo empRegulaMonthActCalSetRepo;
		private DeforLaborTimeEmpRepo deforLaborTimeEmpRepo;
		private EmpDeforLaborMonthActCalSetRepo empDeforLaborMonthActCalSetRepo;
		private EmpFlexMonthActCalSetRepo empFlexMonthActCalSetRepo;
		
		@Override
		public List<MonthlyWorkTimeSetEmp> findEmploymentbyCid(String cid, LaborWorkTypeAttr laborAttr) {
			return monthlyWorkTimeSetEmpRepo.findEmploymentbyCid(AppContexts.user().companyId(), laborAttr);
		}

		@Override
		public List<RegularLaborTimeEmp> findListByCid(String cid) {
			return regularLaborTimeEmpRepo.findListByCid(AppContexts.user().companyId());
		}

		@Override
		public List<EmpRegulaMonthActCalSet> findByCid(String cid) {
			return empRegulaMonthActCalSetRepo.findByCid(AppContexts.user().companyId());
		}

		@Override
		public List<DeforLaborTimeEmp> findDeforLaborByCid(String cid) {
			return deforLaborTimeEmpRepo.findDeforLaborByCid(AppContexts.user().companyId());
		}

		@Override
		public List<EmpDeforLaborMonthActCalSet> findEmpDeforLabor(String cid) {
			return empDeforLaborMonthActCalSetRepo.findEmpDeforLabor(AppContexts.user().companyId());
		}

		@Override
		public List<EmpFlexMonthActCalSet> findEmpFlexMonthByCid(String cid) {
			return empFlexMonthActCalSetRepo.findEmpFlexMonthByCid(AppContexts.user().companyId());
		}

	}
}
