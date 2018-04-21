package nts.uk.screen.at.app.dailyperformance.correction.flex;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.CheckShortageFlex;
import nts.uk.ctx.at.shared.app.service.workingcondition.WorkingConditionService;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.ctx.at.shared.pub.worktime.predset.BreakDownTimeDayExport;
import nts.uk.ctx.at.shared.pub.worktime.predset.PredetemineTimeSettingPub;
import nts.uk.ctx.at.shared.pub.worktime.predset.PredeterminedTimeExport;
import nts.uk.screen.at.app.dailyperformance.correction.dto.FlexShortage;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class FlexInfoDisplay {
	
	@Inject
	private WorkingConditionService workingConditionService;
	
	@Inject
	private CheckShortageFlex checkShortageFlex;
	
	@Inject
	private PredetemineTimeSettingPub predetemineTimeSettingPub;
	
	//<<Public>> フレックス情報を表示する
	public FlexShortage flexInfo(String employeeId, GeneralDate baseDate, String roleId){
		 Optional<WorkingConditionItem>  workingConditionItemOpt = workingConditionService.findWorkConditionByEmployee(employeeId, baseDate);
		 if(!workingConditionItemOpt.isPresent()) return null;
		 if(!workingConditionItemOpt.get().getLaborSystem().equals(WorkingSystem.FLEX_TIME_WORK)) return null;
		 //TODO 対応するドメインモデル「月別実績の勤怠時間」を取得する
		 val value18 = "10";
		 val value21 = "10";
		 val value189 = "10";
		 val value190 = "10";
		 val value191 = "10";
		 if(!workingConditionItemOpt.get().getWorkCategory().getWeekdayTime().getWorkTimeCode().isPresent()) return null;
		 //TODO 所定時間（1日の時間内訳）を取得する
		 Optional<PredeterminedTimeExport> predertermineOpt = predetemineTimeSettingPub.acquirePredeterminedTime(AppContexts.user().companyId(),  workingConditionItemOpt.get().getWorkCategory().getWeekdayTime().getWorkTimeCode().get().v());
		 if(!predertermineOpt.isPresent()) return null;
		 //TODO フレックス不足の相殺が実施できるかチェックする
		 boolean checkFlex = checkShortageFlex.checkShortageFlex(employeeId, baseDate);
		 BreakDownTimeDayExport time = predertermineOpt.get().getAddTime();
		 return new FlexShortage(value18, value21, value189, value190, value191, new BreakTimeDay(time.getOneDay(), time.getMorning(), time.getAfternoon()), checkFlex);
	}

}
