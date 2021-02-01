package nts.uk.screen.at.app.kmk004.n;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.emp.EmpDeforLaborMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.emp.EmpDeforLaborMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeEmp;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeEmpRepo;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.N：雇用別法定労働時間の登録（変形労働）.メニュー別OCD.雇用別基本設定（変形労働）を表示する.雇用別基本設定（変形労働）を表示する
 * 
 * @author tutt
 *
 */
@Stateless
public class DisplayDeforBasicSettingByEmployment {

	@Inject
	private DeforLaborTimeEmpRepo timeEmpRepo;

	@Inject
	private EmpDeforLaborMonthActCalSetRepo monthActCalSetRepo;

	public DeforLaborMonthTimeEmpDto displayDeforBasicSettingByEmployment(String empCd) {

		String companyId = AppContexts.user().companyId();

		DeforLaborMonthTimeEmpDto deforLaborMonthTimeEmpDto = new DeforLaborMonthTimeEmpDto();

		// 1. 雇用別変形労働法定労働時間
		Optional<DeforLaborTimeEmp> optEmpDefor = timeEmpRepo.find(AppContexts.user().companyId(), empCd);

		if (optEmpDefor.isPresent()) {
			deforLaborMonthTimeEmpDto.setDeforLaborTimeEmpDto(DeforLaborTimeEmpDto.fromDomain(optEmpDefor.get()));
		}

		// 2. 雇用別変形労働集計設定
		Optional<EmpDeforLaborMonthActCalSet> optMonthActCalSet = monthActCalSetRepo.find(companyId, empCd);

		if (optMonthActCalSet.isPresent()) {
			deforLaborMonthTimeEmpDto.setEmpDeforLaborMonthActCalSetDto(
					EmpDeforLaborMonthActCalSetDto.fromDomain(optMonthActCalSet.get()));
		}

		return deforLaborMonthTimeEmpDto;
	}

}
