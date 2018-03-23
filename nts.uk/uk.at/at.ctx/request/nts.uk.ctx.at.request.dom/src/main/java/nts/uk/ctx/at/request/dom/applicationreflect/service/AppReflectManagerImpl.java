package nts.uk.ctx.at.request.dom.applicationreflect.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.ApplicationRepository_New;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;
import nts.uk.ctx.at.request.dom.application.overtime.AttendanceType;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeRepository;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.AppDegreeReflectionAtr;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.AppExecutionType;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.AppReflectRecordPara;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.GobackReflectPara;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.OvertimeAppParameter;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.OvertimeReflectPara;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.ReflectRecordInfor;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.WorkReflectedStatesInfo;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.ScheAndRecordSameRequestChangeFlg;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.WorkRecordReflectService;

@Stateless
public class AppReflectManagerImpl implements AppReflectManager {
	@Inject
	private OvertimeRepository overTimeRepo;
	@Inject
	private WorkRecordReflectService workRecordReflect;
	@Inject
	private ApplicationRepository_New appRepo;

	@Override
	public void reflectEmployeeOfApp(Application_New appInfor) {
		GobackReflectPara appGobackTmp = null;
		OvertimeReflectPara overTimeTmp = null;
		AppOverTime appOvertimeData = null;
		// TODO 再実行かどうか判断する (xác nhận xem có thực hiện lại hay k)
		//申請を取得 (lấy đơn)
		if(appInfor.getAppType() == ApplicationType.OVER_TIME_APPLICATION) {
			Optional<AppOverTime> getFullAppOvertime = overTimeRepo.getAppOvertimeFrame(appInfor.getCompanyID(), appInfor.getAppID());
			if(!getFullAppOvertime.isPresent()) {
				return;
			}
			appOvertimeData = getFullAppOvertime.get();			
		}
		//TODO 反映するかどうか判断 (Xác định để phản ánh)
		//TODO 勤務予定へ反映処理	(Xử lý phản ánh đến kế hoạch công việc)
		//勤務実績へ反映処理(xử lý phản ảnh thành tích thực chuyên cần)
		ReflectRecordInfor reflectRecordInfor = new ReflectRecordInfor(AppDegreeReflectionAtr.RECORD, AppExecutionType.EXCECUTION, appInfor);
		Map<Integer, Integer> mapOvertimeFrame =  new HashMap<>();
		if(appOvertimeData.getOverTimeInput() != null) {
			appOvertimeData.getOverTimeInput().stream().forEach(x -> {
				if(x.getAttendanceType() == AttendanceType.NORMALOVERTIME && x.getFrameNo() <= 10) {
					mapOvertimeFrame.put(x.getFrameNo(), x.getApplicationTime().v());
				}
			});
		}
		
		OvertimeAppParameter overtimePara = new OvertimeAppParameter(appInfor.getReflectionInformation().getStateReflectionReal(), //can xac nhan lai
				appInfor.getReflectionInformation().getNotReasonReal().isPresent() ? appInfor.getReflectionInformation().getNotReasonReal().get() : null,
				appOvertimeData.getWorkTypeCode().v(),
				appOvertimeData.getSiftCode().v(),
				appOvertimeData.getWorkClockFrom1(),
				appOvertimeData.getWorkClockTo1(),
				appOvertimeData.getWorkClockFrom2(),
				appOvertimeData.getWorkClockTo2(),
				mapOvertimeFrame, 
				appOvertimeData.getOverTimeShiftNight(),
				appOvertimeData.getFlexExessTime()); 
		overTimeTmp = new OvertimeReflectPara(appInfor.getEmployeeID(), 
				appInfor.getAppDate(), 
				true,
				true,
				true,
				true,
				ScheAndRecordSameRequestChangeFlg.ALWAY,
				true, 
				overtimePara); 
		AppReflectRecordPara appPara = new AppReflectRecordPara(reflectRecordInfor, appGobackTmp, overTimeTmp);
		WorkReflectedStatesInfo workRecordreflect = workRecordReflect.workRecordreflect(appPara);
		appInfor.getReflectionInformation().setStateReflectionReal(workRecordreflect.getReflectedSate());
		appInfor.getReflectionInformation().setNotReasonReal(Optional.of(workRecordreflect.getNotReflectReson()));
		appRepo.updateWithVersion(appInfor);
	}

}
