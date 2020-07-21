package nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.domainservice;

import java.util.List;
import java.util.Optional;


import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.BelongScheduleTeam;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.ScheduleTeam;

/**
 * 所属スケジュールチーム情報を取得する
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務予定.社員情報.スケジュールチーム
 * @author HieuLt
 */
public class GetScheduleTeamInfoService {
	
	// 取得する
	public static List<EmpTeamInfor> get(Require require, List<String> lstEmpId){
		
		//  $所属スケジュールチームリスト = require.所属スケジュールチームを取得する(社員リスト)	
		List<BelongScheduleTeam> lstBelongScheduleTeam  = require.get(lstEmpId);
		
		//workPlaceID = lstBelongScheduleTeam
		return 	null;
	}
	
	
	
	
	
	

	public static interface Require{
		/** 
		 * BelongScheduleTeamRepository
		 * 所属スケジュールチームRepository.*get ( 会社ID, List<社員ID> )	
		 * @param companyID
		 * @param empID
		 * @return
		 */
		List<BelongScheduleTeam> get(List<String> lstEmpId);
		
		/**
		 * スケジュールチームRepository																								
		 * 指定された職場グループ内のスケジュールチームをすべて取得する (会社ID, List<職場グループID>)
		 * @param companyID
		 * @param listWKPGRPID
		 * @return
		 */
		List<ScheduleTeam> getAllSchedule( List<String> listWKPGRPID );
	}
}
