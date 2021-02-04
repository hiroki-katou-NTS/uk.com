package nts.uk.screen.at.app.ksu003.changeworktype;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;

/**
 * 勤務種類を変更する
 * UKDesign.UniversalK.就業.KSU_スケジュール.KSU003_個人スケジュール修正(日付別).A：個人スケジュール修正(日付別).メニュー別OCD.勤務種類を変更する
 * @author phongtq
 *
 */

@Stateless
public class CheckWorkType {
	
	@Inject
	private BasicScheduleService basicScheduleService;
	
	public String checkWorkType(String workTypeCode){
		SetupType workTimeSetting = basicScheduleService
				.checkNeededOfWorkTimeSetting(workTypeCode);
		return workTimeSetting.name();
	}
}
