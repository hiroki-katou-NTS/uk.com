package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyresults;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.commons.lang3.tuple.Pair;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.workschedule.snapshot.DailySnapshotWorkAdapter;
import nts.uk.ctx.at.record.dom.adapter.workschedule.snapshot.DailySnapshotWorkImport;
import nts.uk.ctx.at.record.dom.adapter.workschedule.snapshot.SnapshotImport;
import nts.uk.ctx.at.record.dom.affiliationinformation.AffiliationInforOfDailyPerfor;
import nts.uk.ctx.at.record.dom.breakorgoout.BreakTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.breakorgoout.OutingTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.calculationattribute.CalAttrOfDailyPerformance;
import nts.uk.ctx.at.record.dom.daily.DailyRecordAdUpService;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.AttendanceLeavingGateOfDaily;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.PCLogOnInfoOfDaily;
import nts.uk.ctx.at.record.dom.editstate.EditStateOfDailyPerformance;
import nts.uk.ctx.at.record.dom.raisesalarytime.SpecificDateAttrOfDailyPerfor;
import nts.uk.ctx.at.record.dom.shorttimework.ShortTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.service.updateworkinfo.InsertWorkInfoOfDailyPerforService;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampDakokuRepository;
import nts.uk.ctx.at.record.dom.worktime.TemporaryTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.OutingTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.EmployeeDailyPerError;
import nts.uk.shr.com.context.AppContexts;

/**
 * ???????????? (new_2020)
 * UKDesign.?????????????????????.NittsuSystem.UniversalK.??????.contexts.????????????.????????????????????????.????????????.????????????Mgr?????????.??????????????????.????????????????????????????????????.???????????? (????ng k??).????????????
 * @author tutk
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class RegisterDailyWork {
	
	@Inject
	private InsertWorkInfoOfDailyPerforService insertWorkInfoOfDailyPerforService;
	
	@Inject
	private DailyRecordAdUpService dailyRecordAdUpService;
	
	@Inject
	private StampDakokuRepository stampDakokuRepository;
	
	@Inject
	private DailySnapshotWorkAdapter snapshotAdapter;
	
	public void register(IntegrationOfDaily integrationOfDaily,List<Stamp> listStamp) {
		String companyId = AppContexts.user().companyId();
		String employeeId = integrationOfDaily.getEmployeeId();
		GeneralDate ymd =  integrationOfDaily.getYmd();
		
		//????????????????????????????????????????????????????????????????????? (Update ?????????????????????????????????)
		if(integrationOfDaily.getWorkInformation() !=null) {
			WorkInfoOfDailyPerformance workInfoOfDailyPer = new WorkInfoOfDailyPerformance(employeeId, ymd,
					integrationOfDaily.getWorkInformation());
			this.insertWorkInfoOfDailyPerforService.updateWorkInfoOfDailyPerforService(companyId, employeeId, ymd,
					workInfoOfDailyPer);
		}
		
		//??????????????????????????????????????????
		if (integrationOfDaily.getAffiliationInfor() != null) {
			AffiliationInforOfDailyPerfor dailyPerfor = new AffiliationInforOfDailyPerfor(employeeId, ymd,
					integrationOfDaily.getAffiliationInfor());
			dailyRecordAdUpService.adUpAffilicationInfo(dailyPerfor);
		}
		
		//?????????????????????????????????????????????
		BreakTimeOfDailyPerformance breakTimes = new BreakTimeOfDailyPerformance(employeeId, ymd, integrationOfDaily.getBreakTime());
		dailyRecordAdUpService.adUpBreakTime(breakTimes);
		
		//?????????????????????????????????????????? (???? x??a n??n k insert)
		
		//?????????????????????????????????????????????
		if (integrationOfDaily.getSpecDateAttr().isPresent()) {
			dailyRecordAdUpService.adUpSpecificDate(Optional.of(
					new SpecificDateAttrOfDailyPerfor(employeeId, ymd, integrationOfDaily.getSpecDateAttr().get())));
		}
		
		//??????????????????????????????????????????
		if (integrationOfDaily.getCalAttr() != null) {
			CalAttrOfDailyPerformance attrOfDailyPerformance = new CalAttrOfDailyPerformance(employeeId, ymd,
					integrationOfDaily.getCalAttr());
			dailyRecordAdUpService.adUpCalAttr(attrOfDailyPerformance);
		}
		
		//???????????????????????????????????????
		if (integrationOfDaily.getAttendanceLeave().isPresent()
				&& integrationOfDaily.getAttendanceLeave().get().getTimeLeavingWorks() != null
				&& !integrationOfDaily.getAttendanceLeave().get().getTimeLeavingWorks().isEmpty()) {
			List<TimeLeavingWork> timeLeavingWorks = integrationOfDaily.getAttendanceLeave().get().getTimeLeavingWorks();
			timeLeavingWorks = checkExist(timeLeavingWorks);
			integrationOfDaily.getAttendanceLeave().get().setTimeLeavingWorks(timeLeavingWorks);
			dailyRecordAdUpService.adUpTimeLeaving(Optional.of(
					new TimeLeavingOfDailyPerformance(employeeId, ymd, integrationOfDaily.getAttendanceLeave().get())));
		}
		
		//?????????????????????????????????????????????
		if (integrationOfDaily.getTempTime().isPresent()) {
			
			List<TimeLeavingWork> timeLeavingWorks = integrationOfDaily.getTempTime().get().getTimeLeavingWorks();
			timeLeavingWorks = checkExist(timeLeavingWorks);
			integrationOfDaily.getTempTime().get().setTimeLeavingWorks(timeLeavingWorks);
			dailyRecordAdUpService.adUpTemporaryTime(Optional
					.of(new TemporaryTimeOfDailyPerformance(employeeId, ymd, integrationOfDaily.getTempTime().get())));
		}
		
		//?????????????????????????????????????????????
		if (integrationOfDaily.getOutingTime().isPresent()) {
			List<OutingTimeSheet> listOutingTimeSheet = checkExistOuting(integrationOfDaily.getOutingTime().get().getOutingTimeSheets());
			integrationOfDaily.getOutingTime().get().setOutingTimeSheets(listOutingTimeSheet);
			dailyRecordAdUpService.adUpOutTime(Optional
					.of(new OutingTimeOfDailyPerformance(employeeId, ymd, integrationOfDaily.getOutingTime().get())));
		}
		
		//??????????????????????????????????????????????????????
		if (integrationOfDaily.getShortTime().isPresent()) {
			dailyRecordAdUpService.adUpShortTime(Optional
					.of(new ShortTimeOfDailyPerformance(employeeId, ymd, integrationOfDaily.getShortTime().get())));
		}
		
		//???????????????????????????????????????
		if (integrationOfDaily.getAttendanceLeavingGate().isPresent()
				&& integrationOfDaily.getAttendanceLeavingGate().get().getAttendanceLeavingGates() != null
				&& !integrationOfDaily.getAttendanceLeavingGate().get().getAttendanceLeavingGates().isEmpty()) {
			dailyRecordAdUpService.adUpAttendanceLeavingGate(Optional.of(new AttendanceLeavingGateOfDaily(employeeId,
					ymd, integrationOfDaily.getAttendanceLeavingGate().get())));
		}

		// ???????????????PC?????????????????????????????????
		if (integrationOfDaily.getPcLogOnInfo().isPresent()
				&& integrationOfDaily.getPcLogOnInfo().get().getLogOnInfo() != null
				&& !integrationOfDaily.getPcLogOnInfo().get().getLogOnInfo().isEmpty()) {
			dailyRecordAdUpService.adUpPCLogOn(
					Optional.of(new PCLogOnInfoOfDaily(employeeId, ymd, integrationOfDaily.getPcLogOnInfo().get())));
		}
		if (!integrationOfDaily.getEditState().isEmpty()) {
			List<EditStateOfDailyPerformance> editStates = integrationOfDaily.getEditState().stream()
					.map(c -> new EditStateOfDailyPerformance(employeeId, ymd, c)).collect(Collectors.toList());
			// ?????????????????????????????????????????????????????????????????????
			dailyRecordAdUpService.adUpEditState(editStates);
		}
		//???????????????????????????????????????????????? (Update ????????????)
		if (!listStamp.isEmpty()) {
			listStamp.forEach(stampItem -> {
				this.stampDakokuRepository.update(stampItem);
				
			});
		}
		
		//??????????????????????????????????????????????????????????????????????????????
		if (integrationOfDaily.getEmployeeError() != null && !integrationOfDaily.getEmployeeError().isEmpty()) {
			List<EmployeeDailyPerError> errors = integrationOfDaily.getEmployeeError().stream()
					.filter(x -> x != null).collect(Collectors.toList());
			dailyRecordAdUpService.adUpEmpError(integrationOfDaily.getEmployeeError(),
					errors.stream().map(x -> Pair.of(x.getEmployeeID(), x.getDate())).collect(Collectors.toList()));
		}
		
		integrationOfDaily.getSnapshot().ifPresent(ss -> {
			val oldSnapshot = snapshotAdapter.find(integrationOfDaily.getEmployeeId(), integrationOfDaily.getYmd());
			if (!oldSnapshot.isPresent()) {
				snapshotAdapter.save(DailySnapshotWorkImport.builder()
						.sid(integrationOfDaily.getEmployeeId())
						.ymd(integrationOfDaily.getYmd())
						.snapshot(SnapshotImport.builder()
											.workTime(ss.getWorkInfo().getWorkTimeCodeNotNull().map(c -> c.v()))
											.workType(ss.getWorkInfo().getWorkTypeCode().v())
											.predetermineTime(ss.getPredetermineTime().v())
											.build())
						.build());
			}
		});
		
		// ??????????????????????????????????????????????????????????????????????????????
		if(!integrationOfDaily.getOuenTimeSheet().isEmpty()){
			dailyRecordAdUpService.adUpSupportTime(employeeId, ymd, integrationOfDaily.getOuenTimeSheet());
		}
	}
	private List<TimeLeavingWork> checkExist(List<TimeLeavingWork> listTimeLeavingWork){
		List<TimeLeavingWork> datas = new ArrayList<>();
		for(TimeLeavingWork item :listTimeLeavingWork) {
			boolean check = false;
			if(item.getAttendanceStamp().isPresent() && 
					(checkWorkStamp(item.getAttendanceStamp().get().getActualStamp())
					|| checkWorkStamp(item.getAttendanceStamp().get().getStamp()))){
				check = true;
			}
			
			if(item.getLeaveStamp().isPresent() &&
					(checkWorkStamp(item.getLeaveStamp().get().getStamp()) 
					|| checkWorkStamp(item.getLeaveStamp().get().getStamp()))) {
				check = true;
			}
			if(check) {
				if(!datas.stream().filter(c->c.getWorkNo().v() == item.getWorkNo().v()).findFirst().isPresent()) {
					datas.add(item);
				}
			}
		}
		
		return datas;
	}
	
	private List<OutingTimeSheet> checkExistOuting(List<OutingTimeSheet> listOutingTimeSheet){
		List<OutingTimeSheet> datas = new ArrayList<>();
		for(OutingTimeSheet ots : listOutingTimeSheet) {
			if(ots.getComeBack().isPresent() || ots.getGoOut().isPresent()) {
				datas.add(ots);
			}
		}
		return datas;
	}
	
	private boolean checkWorkStamp (Optional<WorkStamp> ws) {
		if(ws.isPresent() && ws.get().getTimeDay().getTimeWithDay().isPresent()) {
			return true;
		}
		return false;
	}

}
