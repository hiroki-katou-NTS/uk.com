package nts.uk.screen.at.app.query.kmk004.p;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.app.command.workrecord.monthcal.common.DeforWorkTimeAggrSetDto;
import nts.uk.ctx.at.shared.app.command.statutory.worktime.common.WorkingTimeSettingDto;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.emp.EmpDeforLaborMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.emp.EmpDeforLaborMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeEmp;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeEmpRepo;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.P：基本設定（変形労働）.メニュー別OCD.雇用別基本設定（変形労働）の初期画面を表示する
 * 
 * @author tutt
 *
 */
@Stateless
public class GetEmpBasicSetting {

	@Inject
	private DeforLaborTimeEmpRepo timeEmpRepo;

	@Inject
	private EmpDeforLaborMonthActCalSetRepo monthActCalSetRepo;

	public DeforLaborComDto get(String empCode) {

		String cid = AppContexts.user().companyId();
		
		DeforLaborComDto deforLaborcomDto = new DeforLaborComDto();

		// 労働時間設定
		Optional<DeforLaborTimeEmp> optTimeEmp = timeEmpRepo.find(cid, empCode);

		if (optTimeEmp.isPresent()) {
			deforLaborcomDto.setDeforLaborTimeComDto(WorkingTimeSettingDto.fromDomain(optTimeEmp.get()));
		}

		// 変形労働時間勤務の法定内集計設定
		Optional<EmpDeforLaborMonthActCalSet> optMonthActCalSet = monthActCalSetRepo.find(cid, empCode);

		if (optMonthActCalSet.isPresent()) {
			deforLaborcomDto.setSettingDto(DeforWorkTimeAggrSetDto.fromDomain(optMonthActCalSet.get()));
		}

		return deforLaborcomDto;
	}
}
