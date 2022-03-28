package nts.uk.screen.at.app.dailyperformance.support;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.screen.at.app.dailyperformance.correction.dto.ScreenMode;
import nts.uk.screen.at.app.dailyperformance.correction.dto.cache.DPCorrectionStateParam;
import nts.uk.screen.at.app.kdw013.query.GetEmployeesCameToSupport;

@Stateless
public class GetDailySupportWorkers {
	
	@Inject
	private GetEmployeesCameToSupport employeeSupport; 

	public DPCorrectionStateParam getDailySupportWorkers (DPCorrectionStateParam param) {
		// Input「日別実績の修正の状態．表示形式」をチェックする
		int mode = param.getDisplayMode().intValue();
		if (mode == ScreenMode.APPROVAL.value || mode == ScreenMode.NORMAL.value) {
			// 応援勤務に来た社員を取得する
			DatePeriod period = new DatePeriod(param.getPeriod().getStartDate(), param.getPeriod().getEndDate());
			List<String> lstEmpSupport = employeeSupport.getEmployeesCameToSupport(period, param.getLstWrkplaceId());
			
			// 「日別実績の修正の状態」を更新する
			// $対象者リスト = INPUT「日別実績の修正の状態．対象社員」
			List<String> lstTargetEmps = param.getEmployeeIds();
			
			// $応援来た社員 = 取得した社員リストから「$対象者リスト」に含まれない社員
			List<String> lstEmpCanSupport = lstEmpSupport.stream().filter(x -> !lstTargetEmps.contains(x)).collect(Collectors.toList());

			List<String> lstEmpsNew = new ArrayList<>();
			lstEmpsNew.addAll(lstTargetEmps);
			lstEmpsNew.addAll(lstEmpCanSupport);
			
			param.setLstEmpsSupport(lstEmpCanSupport);
			param.setEmployeeIds(lstEmpsNew);
		}
		
		return param;
		
	}
}
