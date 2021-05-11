package nts.uk.screen.at.app.ksu003.getlistempworkhours;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkSchedule;

/**
 * «ScreenQuery» 日付別社員作業時間帯リストを取得する
 * UKDesign.UniversalK.就業.KSU_スケジュール.KSU003_個人スケジュール修正(日付別).A：個人スケジュール修正(日付別).メニュー別OCD.日付別社員作業時間帯リストを取得する
 * @author HieuLt
 *
 */
@Stateless
public class GetListEmpWorkHours {

	
	public String get(List<WorkSchedule> lstWorkSchedule){
		// 取得する (勤務予定リスト: 勤務予定リスト): List＜Optional<社員作業情報 dto>＞
		//1.1[勤務予定.isEmpty]:create()
		if(lstWorkSchedule.isEmpty()){
			//String empID = 
		}
		return null;
	}
}
