package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.overtime;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.dailyprocess.calc.AdTimeAndAnyItemAdUpService;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.CalculateDailyRecordService;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.IntegrationOfDaily;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess.WorkUpdateService;


@Stateless
public class AfterOvertimeReflectServiceImpl implements AfterOvertimeReflectService {
	@Inject
	private AfterOvertimeReflectProcess afterOverTimeReflect;
	@Inject
	private PreOvertimeReflectProcess preAfterOvertimeReflectProcess;
	@Inject
	private WorkInformationRepository workRepository;
	@Inject
	private WorkUpdateService scheWorkUpdate;
	@Inject
	private PreOvertimeReflectService preOvertimeService;
	@Inject
	private CalculateDailyRecordService calculate;
	@Inject
	private AdTimeAndAnyItemAdUpService timeAndAnyItemUpService;
	@Override
	public boolean reflectAfterOvertime(OvertimeParameter overtimePara) {
		try {
			//予定勤種・就時の反映
			afterOverTimeReflect.checkScheReflect(overtimePara);
			//勤種・就時の反映
			boolean isWorkReflect = preAfterOvertimeReflectProcess.changeFlg(overtimePara);
			//予定勤種・就時反映後の予定勤種・就時を取得する
			//勤種・就時反映後の予定勤種・就時を取得する
			Optional<WorkInfoOfDailyPerformance> optDailyData = workRepository.find(overtimePara.getEmployeeId(),
					overtimePara.getDateInfo());		
			if(!optDailyData.isPresent()) {
				return false;
			}
			WorkInfoOfDailyPerformance dailyData = optDailyData.get();
			//予定開始終了時刻の反映
			WorkTimeTypeOutput workTimeTypeScheData = new WorkTimeTypeOutput( dailyData.getScheduleInfo().getWorkTimeCode().v(), dailyData.getScheduleInfo().getWorkTypeCode().v());		
			afterOverTimeReflect.checkScheWorkStarEndReflect(overtimePara, isWorkReflect, workTimeTypeScheData);
			//開始終了時刻の反映
			WorkTimeTypeOutput workTimeTypeRecordData = new WorkTimeTypeOutput(dailyData.getRecordInfo().getWorkTimeCode().v(), dailyData.getRecordInfo().getWorkTypeCode().v());
			afterOverTimeReflect.recordStartEndReflect(overtimePara, workTimeTypeRecordData);
			//残業時間の反映
			afterOverTimeReflect.reflectOvertimeFrame(overtimePara);
			//所定外深夜時間の反映
			afterOverTimeReflect.reflectTimeShiftNight(overtimePara.getEmployeeId(), overtimePara.getDateInfo(), overtimePara.getOvertimePara().getOverTimeShiftNight());
			//フレックス時間の反映
			//INPUT．フレックス時間をチェックする
			if(overtimePara.getOvertimePara().getFlexExessTime() > 0) {
				scheWorkUpdate.updateFlexTime(overtimePara.getEmployeeId(), overtimePara.getDateInfo(), overtimePara.getOvertimePara().getFlexExessTime(), false);
			}
			
			//日別実績の修正からの計算
			//○日別実績を置き換える Replace daily performance		
			IntegrationOfDaily calculateData = calculate.calculate(preOvertimeService.calculateForAppReflect(overtimePara.getEmployeeId(), overtimePara.getDateInfo()),null,Optional.empty(),Optional.empty());			
			timeAndAnyItemUpService.addAndUpdate(overtimePara.getEmployeeId(), overtimePara.getDateInfo(), 
					calculateData.getAttendanceTimeOfDailyPerformance(), Optional.empty());
			return true;
			
		} catch (Exception e) {
			return false;
		}
		
	}
}
