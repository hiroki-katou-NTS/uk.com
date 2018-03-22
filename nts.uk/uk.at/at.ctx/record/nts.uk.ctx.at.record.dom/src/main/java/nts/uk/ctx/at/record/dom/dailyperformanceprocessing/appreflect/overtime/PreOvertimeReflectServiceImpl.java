package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.overtime;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.affiliationinformation.AffiliationInforOfDailyPerfor;
import nts.uk.ctx.at.record.dom.affiliationinformation.repository.AffiliationInforOfDailyPerforRepository;
import nts.uk.ctx.at.record.dom.calculationattribute.CalAttrOfDailyPerformance;
import nts.uk.ctx.at.record.dom.calculationattribute.repo.CalAttrOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.PCLogOnInfoOfDaily;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.repo.PCLogOnInfoOfDailyRepo;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.ApplicationReflectOutput;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.ReasonNotReflectRecord;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.ReflectedStateRecord;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.IntegrationOfDaily;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerErrorRepository;

@Stateless
public class PreOvertimeReflectServiceImpl implements PreOvertimeReflectService {
	@Inject
	private PreOvertimeReflectProcess priorProcess;
	@Inject
	private WorkInformationRepository workRepository;
	@Inject
	private StartEndTimeOffReflect startEndtimeOffReflect;
	@Inject
	private CalAttrOfDailyPerformanceRepository calAttrOfDaily;
	@Inject
	private AffiliationInforOfDailyPerforRepository affiliationInfor;
	@Inject
	private PCLogOnInfoOfDailyRepo pcLogOnInfo;
	@Inject
	private EmployeeDailyPerErrorRepository employeeDailyPerError;
	
	@Override
	public ApplicationReflectOutput overtimeReflect(PreOvertimeParameter param) {
		try {
			ApplicationReflectOutput output = new ApplicationReflectOutput(param.getOvertimePara().getReflectedState(), param.getOvertimePara().getReasonNotReflect());
			//予定勤種・就時の反映
			priorProcess.workTimeWorkTimeUpdate(param);
			//勤種・就時の反映
			boolean changeFlg = priorProcess.changeFlg(param);
			//予定勤種・就時反映後の予定勤種・就時を取得する
			//勤種・就時反映後の予定勤種・就時を取得する
			Optional<WorkInfoOfDailyPerformance> optDailyData = workRepository.find(param.getEmployeeId(), param.getDateInfo());
			if(!optDailyData.isPresent()) {
				return output;
			}
			//予定開始終了時刻の反映
			WorkInfoOfDailyPerformance dailyData = optDailyData.get();
			priorProcess.startAndEndTimeReflectSche(param, changeFlg, dailyData);
			//開始終了時刻の反映
			startEndtimeOffReflect.startEndTimeOffReflect(param, dailyData);
			//残業時間の反映
			priorProcess.getReflectOfOvertime(param);
			//所定外深夜時間の反映
			priorProcess.overTimeShiftNight(param.getEmployeeId(), param.getDateInfo(), param.isTimeReflectFlg(), param.getOvertimePara().getOverTimeShiftNight());
			//フレックス時間の反映
			priorProcess.reflectOfFlexTime(param.getEmployeeId(), param.getDateInfo(), param.isTimeReflectFlg(), param.getOvertimePara().getFlexExessTime());
			
			//日別実績の修正からの計算
			//○日別実績を置き換える Replace daily performance		
			
			output.setReflectedState(ReflectedStateRecord.REFLECTED);
			//dang lay nham thong tin enum
			output.setReasonNotReflect(ReasonNotReflectRecord.WORK_FIXED);
			return output;
	
		} catch (Exception ex) {
			return new ApplicationReflectOutput(param.getOvertimePara().getReflectedState(), param.getOvertimePara().getReasonNotReflect());
		}
	}


	@Override
	public IntegrationOfDaily calculateForAppReflect(IntegrationOfDaily dailyData, String employeeId,
			GeneralDate dateData) {
		//日別実績の計算区分
		CalAttrOfDailyPerformance calAtrrOfDailyData = calAttrOfDaily.find(employeeId, dateData);
		//日別実績の所属情報
		Optional<AffiliationInforOfDailyPerfor> findByKey = affiliationInfor.findByKey(employeeId, dateData);
		//日別実績のPCログオン情報
		Optional<PCLogOnInfoOfDaily> pcLogOnDarta = pcLogOnInfo.find(employeeId, dateData);
		//社員の日別実績エラー一覧
		EmployeeDailyPerError findEror = employeeDailyPerError.find(employeeId, dateData);
		//日別実績の外出時間帯
		
		//
				
		
		return null;
	}
	
	

}
