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
		
		List<Integer> lstWorkDay = new ArrayList<>();
		List<Integer> lstOverTime = new ArrayList<>();
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
				lstWorkDay.add(overTime.getFrameNo());
				List<WorkdayoffFrame> lstFramWork = repoWork.getWorkdayoffFrameBy(companyId,lstWorkDay);
				lstFrame.add(new OverTimeFrame(2, lstFramWork.get(0).getWorkdayoffFrNo().v().intValue(), 
						lstFramWork.get(0).getWorkdayoffFrName().v(), null, overTime.getApplicationTime().v()));
			}
			if(overTime.getAttendanceType().equals(AttendanceType.NORMALOVERTIME)){
				lstOverTime.add(overTime.getFrameNo());
				List<OvertimeWorkFrame> lstFramOt = repoOverTimeFr.getOvertimeWorkFrameByFrameNos(companyId, lstOverTime);
				lstFrame.add(new OverTimeFrame(1, lstFramOt.get(0).getOvertimeWorkFrNo().v().intValue(), 
						lstFramOt.get(0).getOvertimeWorkFrName().v(), null, overTime.getApplicationTime().v()));
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
		return new AppOverTimeInfoFull(appId, appOt.getWorkClockFrom1(), appOt.getWorkClockTo1(),
				appOt.getWorkClockFrom2(), appOt.getWorkClockTo2(),
				0, lstFrame, appOt.getOverTimeShiftNight(),
				appOt.getFlexExessTime());
	}

	@Override
	public AppGoBackInfoFull getAppGoBackInfo(String companyID, String appId) {
		Optional<GoBackDirectly> appGoBackOp = repoGoBack.findByApplicationID(companyID, appId);
		GoBackDirectly appGoBack = appGoBackOp.get();
		return new AppGoBackInfoFull(appId, appGoBack.getGoWorkAtr1().value,
				appGoBack.getWorkTimeStart1().v(), appGoBack.getBackHomeAtr1().value, 
				appGoBack.getWorkTimeEnd1().v(), appGoBack.getGoWorkAtr2().value,
				appGoBack.getWorkTimeStart2().v(), appGoBack.getBackHomeAtr2().value,
				appGoBack.getWorkTimeEnd2().v());
	}

}
