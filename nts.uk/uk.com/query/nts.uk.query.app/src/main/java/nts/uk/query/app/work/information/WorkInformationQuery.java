package nts.uk.query.app.work.information;

import java.util.List;

import javax.inject.Inject;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.repository.TimeLeavingOfDailyPerformanceRepository;

/*
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.在席照会.ステータス.勤務情報の取得.勤務情報の取得
 */
//TODO
public class WorkInformationQuery {

	@Inject
	private static TimeLeavingOfDailyPerformanceRepository timeLeaveRepo;
	
	public static List<EmployeeWorkInformationDto> getWorkInformationQuery(List<String> sids, DatePeriod baseDate) {
		//1. get(社員IDリスト、基準日):  List<日別実績の勤務情報> TODO
		//2. get(社員IDリスト、基準日):  List<勤務予定> TODO
		//3. get(社員IDリスト、基準日):  List<日別実績の出退勤> TODO: convert to DTO
		List<TimeLeavingOfDailyPerformance> dto = timeLeaveRepo.finds(sids, baseDate);
		
		
		//4. get(社員IDリスト、基準日、勤務実績のエラーアラームコード＝007、008) : List<社員の日別実績エラー一覧> TODO
		//5. get(ログイン会社ID): List<勤務種類> TODO
		return null;
	}
}
