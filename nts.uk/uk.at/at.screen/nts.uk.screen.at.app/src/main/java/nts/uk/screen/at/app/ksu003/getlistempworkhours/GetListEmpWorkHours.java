package nts.uk.screen.at.app.ksu003.getlistempworkhours;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.schedule.task.taskschedule.TaskScheduleDetail;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.ScheManaStatuTempo;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkSchedule;
import nts.uk.ctx.at.shared.app.query.task.TaskData;
import nts.uk.screen.at.app.ksu003.getworkselectioninfor.GetOneTask;
import nts.uk.shr.com.context.AppContexts;

/**
 * «ScreenQuery» 日付別社員作業時間帯リストを取得する
 * UKDesign.UniversalK.就業.KSU_スケジュール.KSU003_個人スケジュール修正(日付別).A：個人スケジュール修正(日付別).メニュー別OCD.日付別社員作業時間帯リストを取得する
 * @author HieuLt
 *
 */
@Stateless
public class GetListEmpWorkHours {

		@Inject
		private GetOneTask getOneTask;
	
	public EmpTaskInfoDto get(Map<ScheManaStatuTempo, Optional<WorkSchedule>> mngStatusAndWScheMap){
		String companyID = AppContexts.user().companyId();
		EmpTaskInfoDto dto = null;
		// 取得する (勤務予定リスト: 勤務予定リスト): List＜Optional<社員作業情報 dto>＞
	
		//1.1[勤務予定.isEmpty]:create()
		mngStatusAndWScheMap.forEach((k,v)->{
			if(!v.isPresent()){
				EmpTaskInfoDto dto1 = new EmpTaskInfoDto(k.getDate(), k.getEmployeeID(), new ArrayList());
					// return EmpTaskInfoDto(k.getDate(), k.getEmployeeID(), new ArrayList());
			}
			else{
				if(v.isPresent() && !v.get().getTaskSchedule().getDetails().isEmpty()){
					//1.2.1:[not 勤務予定.作業予定.詳細リスト.isEmpty]: getget(会社ID, 作業枠NO, 作業コード)Optional<作業>	
				String taskCode = v.get().getTaskSchedule().getDetails().get(0).getTaskCode().v();	
				Optional<TaskData> taskDto =  getOneTask.get(companyID, 1, taskCode);
				}
				else{
				/*	1.2.2：create()
					作業予定詳細＝勤務予定．作業予定
					作業＝Optional＜作業＞
					　※勤務予定．作業予定がemptyの場合は作業予定詳細＝empty
					　※Optional＜作業＞がemptyの場合は作業＝empty*/
					List<TaskScheduleDetail> detailDto = v.get().getTaskSchedule().getDetails();
					
					
				}
			}
		});
		return dto;
	}
}
