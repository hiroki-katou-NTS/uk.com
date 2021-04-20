package nts.uk.screen.at.app.kdw013.a;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.app.command.workrecord.workrecord.AddAttendanceTimeZoneCommand;
import nts.uk.ctx.at.record.app.command.workrecord.workrecord.AddAttendanceTimeZoneCommandHandler;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;

/**
 * UKDesign.UniversalK.就業.KDW_日別実績.KDW013_工数入力.A:工数入力.メニュー別OCD.作業内容を登録する
 * @author tutt
 *
 */
@Stateless
public class RegisterWorkContent {
	
	@Inject
	private AddAttendanceTimeZoneCommandHandler handler;
	
	public void registerWorkContent(AddAttendanceTimeZoneCommand command) {
		
		//1: 登録する(対象者, 編集状態, 作業詳細リスト):List<日別勤怠(Work)>
		// 応援作業別勤怠時間帯を登録する
		List<IntegrationOfDaily> integrationOfDailyList = handler.handle(command);
		
		//2: [List<日別勤怠(Work)>.isPresent]:<call>(対象者,画面モードList<日別勤怠(Work)>)
		// 残業申請・休出時間申請の対象時間を取得する
		if (!integrationOfDailyList.isEmpty()) {
			
		}
		
		
		//3: [List<残業休出時間>.isPresent]:<call>
	}
}
