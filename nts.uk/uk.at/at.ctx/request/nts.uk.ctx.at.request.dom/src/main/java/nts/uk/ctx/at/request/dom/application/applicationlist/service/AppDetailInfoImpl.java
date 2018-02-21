package nts.uk.ctx.at.request.dom.application.applicationlist.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectly;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectlyRepository;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;
import nts.uk.ctx.at.request.dom.application.overtime.AttendanceType;
import nts.uk.ctx.at.request.dom.application.overtime.OverTimeInput;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.repository.BPTimeItemRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.timeitem.BonusPayTimeItem;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrame;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameRepository;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrame;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.TimeWithDayAttr;
/**
 * 
 * @author hoatt
 *
 */
@Stateless
public class AppDetailInfoImpl implements AppDetailInfoRepository{

	@Inject
	private GoBackDirectlyRepository repoGoBack;
	@Inject
	private OvertimeRepository repoOverTime;
	@Inject
	private BPTimeItemRepository repoBonusTime;
	@Inject
	private WorkdayoffFrameRepository repoWork;
	@Inject
	private OvertimeWorkFrameRepository repoOverTimeFr;
	@Override
	public AppOverTimeInfoFull getAppOverTimeInfo(String companyID, String appId) {
		String companyId = AppContexts.user().companyId();
		Optional<AppOverTime> appOtOp = repoOverTime.getFullAppOvertime(companyID, appId);
		AppOverTime appOt = appOtOp.get();
		List<OverTimeInput> lstOverTimeInput = appOt.getOverTimeInput();
		
//		List<Integer> lstWorkDay = new ArrayList<>();
		
		List<OverTimeFrame> lstFrame = new ArrayList<>();
		for (OverTimeInput overTime : lstOverTimeInput) {
			List<Integer> lstFrameNo = new ArrayList<>();
			if(overTime.getAttendanceType().equals(AttendanceType.BONUSPAYTIME)){
				lstFrameNo.add(overTime.getFrameNo());
				List<BonusPayTimeItem> lstFramBonus = repoBonusTime.getListBonusPayTimeItemName(companyId, lstFrameNo);
				lstFrame.add(new OverTimeFrame(3, lstFramBonus.get(0).getId(),lstFramBonus.get(0).getTimeItemName().v(), 
						lstFramBonus.get(0).getTimeItemTypeAtr().value, overTime.getApplicationTime().v()));
			}
			if(overTime.getAttendanceType().equals(AttendanceType.BREAKTIME)){
				lstFrameNo.add(overTime.getFrameNo());
				List<WorkdayoffFrame> lstFramWork = repoWork.getWorkdayoffFrameBy(companyId,lstFrameNo);
				lstFrame.add(new OverTimeFrame(2, lstFramWork.get(0).getWorkdayoffFrNo().v().intValue(), 
						lstFramWork.get(0).getWorkdayoffFrName().v(), null, overTime.getApplicationTime().v()));
			}
			if(overTime.getAttendanceType().equals(AttendanceType.NORMALOVERTIME)){
				String name = "";
				if(overTime.getFrameNo() == 11){
					name = "時間外深夜時間";
				}else if(overTime.getFrameNo() == 12){
					name = "ﾌﾚｯｸｽ超過";
				}else{
				lstFrameNo.add(overTime.getFrameNo());
				List<OvertimeWorkFrame> lstFramOt = repoOverTimeFr.getOvertimeWorkFrameByFrameNos(companyId, lstFrameNo);
				name = lstFramOt.get(0).getOvertimeWorkFrName().v();
				}
				lstFrame.add(new OverTimeFrame(1, overTime.getFrameNo(), 
						name, null, overTime.getApplicationTime().v()));
			}
		}
//		List<BonusPayTimeItem> lstFramBonus = repoBonusTime.getListBonusPayTimeItemName(companyId, lstBonus);
//		for (BonusPayTimeItem bonusPay : lstFramBonus) {
//			lstFrame.add(new OverTimeFrame(3, bonusPay.getId(),bonusPay.getTimeItemName().v(), bonusPay.getTimeItemTypeAtr().value));
//		}
//		List<WorkdayoffFrame> lstFramWork = repoWork.getWorkdayoffFrameBy(companyId,lstWorkDay);
//		for (WorkdayoffFrame workday : lstFramWork) {
//			lstFrame.add(new OverTimeFrame(2, workday.getWorkdayoffFrNo().v().intValue(), workday.getWorkdayoffFrName().v(), null));
//		}
//		List<OvertimeWorkFrame> lstFramOt = repoOverTimeFr.getOvertimeWorkFrameByFrameNos(companyId, lstOverTime);
//		for (OvertimeWorkFrame normal : lstFramOt) {
//			lstFrame.add(new OverTimeFrame(1, normal.getOvertimeWorkFrNo().v().intValue(), normal.getOvertimeWorkFrName().v(), null));
//		}
		return new AppOverTimeInfoFull(appId, 
				this.convertTime(appOt.getWorkClockFrom1()),
				this.convertTime(appOt.getWorkClockTo1()),
				this.convertTime(appOt.getWorkClockFrom2()),
				this.convertTime(appOt.getWorkClockTo2()),
				0, lstFrame, appOt.getOverTimeShiftNight(),
				appOt.getFlexExessTime());
	}

	@Override
	public AppGoBackInfoFull getAppGoBackInfo(String companyID, String appId) {
		Optional<GoBackDirectly> appGoBackOp = repoGoBack.findByApplicationID(companyID, appId);
		GoBackDirectly appGoBack = appGoBackOp.get();
		return new AppGoBackInfoFull(appId, appGoBack.getGoWorkAtr1().value,
				this.convertTime(appGoBack.getWorkTimeStart1().v()),
				appGoBack.getBackHomeAtr1().value, 
				this.convertTime(appGoBack.getWorkTimeEnd1().v()),
				appGoBack.getGoWorkAtr2().value,
				this.convertTime(appGoBack.getWorkTimeStart2().v()),
				appGoBack.getBackHomeAtr2().value,
				this.convertTime(appGoBack.getWorkTimeEnd2().v()));
	}
	//convert time
	private String convertTime(Integer time){
		if(time == null){
			return "";
		}
		TimeWithDayAttr timeConvert = new TimeWithDayAttr(time);
		return timeConvert.getDayDivision().description + timeConvert.getInDayTimeWithFormat();
	}

}
