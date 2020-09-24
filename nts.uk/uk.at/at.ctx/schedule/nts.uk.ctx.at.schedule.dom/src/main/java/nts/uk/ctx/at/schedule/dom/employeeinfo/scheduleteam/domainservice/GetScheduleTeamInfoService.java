package nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.domainservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.lang3.tuple.Pair;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.BelongScheduleTeam;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.ScheduleTeam;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.ScheduleTeamName;

/**
 * 所属スケジュールチーム情報を取得する
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務予定.社員情報.スケジュールチーム
 * 
 * @author HieuLt
 */
public class GetScheduleTeamInfoService {

	// 取得する
	public static List<EmpTeamInfor> get(Require require, List<String> lstEmpId) {
		Map<String, BelongScheduleTeam> map = new HashMap<>();
		Map<Pair<String, String>, ScheduleTeamName> mapSchedule = new HashMap<>();
		List<EmpTeamInfor> result = new ArrayList<>();
		// $所属スケジュールチームリスト = require.所属スケジュールチームを取得する(社員リスト)
		List<BelongScheduleTeam> lstBelongScheduleTeam = require.get(lstEmpId);
		// $社員たちの職場グループIDリスト = $所属スケジュールチームリスト: map $.職場グループID distinct
		List<String> listWorkplaceGroupID = lstBelongScheduleTeam.stream().map(c -> c.getWKPGRPID()).distinct()
				.collect(Collectors.toList());
		// $スケジュールチームリスト = require.スケジュールチームを取得する($社員たちの職場グループIDリスト)
		List<ScheduleTeam> lstScheduleTeam = require.getAllSchedule(listWorkplaceGroupID);

		for (BelongScheduleTeam belongScheduleTeam : lstBelongScheduleTeam) {
			map.put(belongScheduleTeam.getEmployeeID(), belongScheduleTeam);
		}
		for (ScheduleTeam item : lstScheduleTeam) {
			mapSchedule.put(Pair.of(item.getWKPGRPID(), item.getScheduleTeamCd().v()), item.getScheduleTeamName());
		}
		lstEmpId.stream().forEach(c -> {
			// $所属スケジュールチーム = $所属スケジュールチームMap.get($)
			BelongScheduleTeam belongScheduleTeam = map.get(c);
			// $所属スケジュールチーム.empty
			if (belongScheduleTeam == null) {
				// return 社員所属チーム情報($)
				EmpTeamInfor empTeamInfo = EmpTeamInfor.get(c);
				result.add(empTeamInfo);
				return;
			}
			/*
			 * $チーム名称 = $スケジュールチームTable .get($所属スケジュールチーム.職場グループID,
			 * $所属スケジュールチーム.チームコード) return 社員所属チーム情報($, $所属スケジュールチーム.チームコード,
			 * $チーム名称)
			 */
			ScheduleTeamName teamName = mapSchedule
					.get(Pair.of(belongScheduleTeam.getWKPGRPID(), belongScheduleTeam.getScheduleTeamCd().v()));
			EmpTeamInfor eTeamInfor = new EmpTeamInfor(c, Optional.ofNullable(belongScheduleTeam.getScheduleTeamCd()),
					Optional.ofNullable(teamName));
			result.add(eTeamInfor);
		});
		return result;
	}

	public static interface Require {
		/**
		 * BelongScheduleTeamRepository 所属スケジュールチームRepository.*get ( 会社ID,
		 * List<社員ID> )
		 * @param companyID
		 * @param empID
		 * @return
		 */
		List<BelongScheduleTeam> get(List<String> lstEmpId);

		/**
		 * スケジュールチームRepository 指定された職場グループ内のスケジュールチームをすべて取得する (会社ID,
		 * List<職場グループID>)
		 * @param companyID
		 * @param listWKPGRPID
		 * @return
		 */
		List<ScheduleTeam> getAllSchedule(List<String> listWKPGRPID);
	}
}
