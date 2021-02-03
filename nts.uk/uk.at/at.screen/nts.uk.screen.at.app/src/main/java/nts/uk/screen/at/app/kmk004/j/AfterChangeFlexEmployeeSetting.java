package nts.uk.screen.at.app.kmk004.j;

import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.com.ComFlexMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.screen.at.app.kmk004.g.ComFlexMonthActCalSetDto;
import nts.uk.screen.at.app.query.kmk004.common.EmployeeList;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author sonnlb
 *
 *         UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.J：社員別法定労働時間の登録（フレックス勤務）.メニュー別OCD.社員別基本設定（フレックス勤務）を作成・変更・削除した時
 */
@Stateless
public class AfterChangeFlexEmployeeSetting {

	@Inject
	private DisplayFlexBasicSettingByEmployee displayFlexBasicSettingByEmployee;

	@Inject
	private EmployeeList employeeList;
	
	@Inject
	private ComFlexMonthActCalSetRepo comFlexRepo;

	public AfterChangeFlexEmployeeSettingDto afterChangeFlexEmployeeSetting(String sId) {
		AfterChangeFlexEmployeeSettingDto result = new AfterChangeFlexEmployeeSettingDto();
		// 社員別基本設定（フレックス勤務）を表示する
		result.setFlexMonthActCalSet(this.displayFlexBasicSettingByEmployee.displayFlexBasicSettingByEmployee(sId).getFlexMonthActCalSet());

		// 社員リストを表示する
		result.setAlreadySettings(this.employeeList.get(LaborWorkTypeAttr.FLEX).stream().map(x-> x.employeeId).collect(Collectors.toList()));
		
		this.comFlexRepo.find(AppContexts.user().companyId()).ifPresent(x -> {
			result.setComFlexMonthActCalSet(ComFlexMonthActCalSetDto.fromDomain(x));
		});

		return result;
	}
}
