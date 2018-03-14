package nts.uk.ctx.at.schedule.dom.appreflectprocess.gobacksche.service;

import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.appreflectprocess.ApplyTimeAtr;
import nts.uk.ctx.at.schedule.dom.appreflectprocess.ReflectedStatesScheInfo;
import nts.uk.ctx.at.schedule.dom.appreflectprocess.gobacksche.GoBackDirectlyReflectParam;
import nts.uk.ctx.at.schedule.dom.appreflectprocess.gobacksche.OutsetBreakReflectScheAtr;
import nts.uk.ctx.at.schedule.dom.appreflectprocess.gobacksche.TimeOfDayReflectFindDto;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.service.EndTimeReflectScheService;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.service.StartTimeReflectScheService;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.service.servicedto.TimeReflectScheDto;
import nts.uk.ctx.at.shared.dom.vacation.setting.ApplyPermission;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneUse;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class TimeOfDayReflectGoBackScheImpl implements TimeOfDayReflectGoBackSche {
	@Inject
	private PredetemineTimeSettingRepository predetemineTimeRepo;
	@Inject
	private StartTimeReflectScheService startTimeService;
	@Inject
	private EndTimeReflectScheService endTimeService;
	@Override
	public ReflectedStatesScheInfo stampReflectGobackSche(GoBackDirectlyReflectParam reflectPara) {
		//(開始時刻)反映する時刻を求める
		reflectPara.setApplyTimeAtr(ApplyTimeAtr.START);
		TimeOfDayReflectFindDto startTimeReflectFind = this.timeReflectFind(reflectPara);
		TimeReflectScheDto timeData = new TimeReflectScheDto(reflectPara.getEmployeeId(),
				reflectPara.getDatePara(),
				reflectPara.getAppInfor().getWorkTimeStart1(),
				reflectPara.getAppInfor().getWorkTimeEnd1(), 
				1);
		if(startTimeReflectFind.isReflectFlg()) {
			startTimeService.updateStartTimeRflect(timeData);
		}
		//(終了時刻)反映する時刻を求める
		reflectPara.setApplyTimeAtr(ApplyTimeAtr.END);
		TimeOfDayReflectFindDto endTimeReflectFind = this.timeReflectFind(reflectPara);
		if(endTimeReflectFind.isReflectFlg()) {
			endTimeService.updateEndTimeRflect(timeData);
		}
		//TODO (開始時刻2, 終了時刻2)反映する時刻を求める
		
		return null;
	}

	@Override
	public TimeOfDayReflectFindDto timeReflectFind(GoBackDirectlyReflectParam reflectPara) {
		String companyId = AppContexts.user().companyId();
		TimeOfDayReflectFindDto timeFind = new TimeOfDayReflectFindDto(false, 0);
		//INPUT．申請する時刻をチェックする
		if(reflectPara.getAppInfor().getWorkTimeStart1() != null && reflectPara.getApplyTimeAtr() == ApplyTimeAtr.START
				|| reflectPara.getAppInfor().getWorkTimeEnd1() != null && reflectPara.getApplyTimeAtr() == ApplyTimeAtr.END
				|| reflectPara.getAppInfor().getWorkTimeStart2() != null && reflectPara.getApplyTimeAtr() == ApplyTimeAtr.START2
				|| reflectPara.getAppInfor().getWorkTimeEnd2() != null && reflectPara.getApplyTimeAtr() == ApplyTimeAtr.END2) {
			timeFind.setReflectFlg(true);
			timeFind.setTimeOfDay(reflectPara.getAppInfor().getWorkTimeStart1());
			return timeFind;
		} else {
			//INPUT．勤種・就時の反映できるフラグをチェックする
			if(reflectPara.getOutsetBreakReflectAtr() == OutsetBreakReflectScheAtr.NOTREFLECT) {
				return timeFind;
			} else {
				//ドメインモデル「就業時間帯の設定」を取得する
				Optional<PredetemineTimeSetting> optFindByCode = predetemineTimeRepo.findByWorkTimeCode(companyId, reflectPara.getAppInfor().getWorkTime());
				if(!optFindByCode.isPresent()) {
					return timeFind;
				}
				PredetemineTimeSetting workTimeData = optFindByCode.get();
				TimezoneUse timeZone = workTimeData.getPrescribedTimezoneSetting().getLstTimezone()
						.stream()
						.filter(x -> x.getWorkNo() == 1).collect(Collectors.toList()).get(0);
				//ドメインモデル「時間帯(使用区分付き)」．使用区分をチェックする
				//「所定時間設定」．使用区分をチェックする
				//TODO lam sau
				timeFind.setReflectFlg(true);
				if(reflectPara.getApplyTimeAtr() == ApplyTimeAtr.START) {
					timeFind.setTimeOfDay(timeZone.getStart().v());
				} else if (reflectPara.getApplyTimeAtr() == ApplyTimeAtr.END) {
					timeFind.setTimeOfDay(timeZone.getEnd().v());
				} 
				return timeFind;
			}
		}
	}

}
