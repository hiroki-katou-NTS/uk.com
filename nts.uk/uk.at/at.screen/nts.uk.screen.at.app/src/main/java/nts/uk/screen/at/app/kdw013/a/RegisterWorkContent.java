package nts.uk.screen.at.app.kdw013.a;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.app.command.workrecord.workrecord.AddAttendanceTimeZoneCommand;
import nts.uk.ctx.at.record.app.command.workrecord.workrecord.AddAttendanceTimeZoneCommandHandler;
import nts.uk.ctx.at.record.app.command.workrecord.workrecord.WorkDetail;
import nts.uk.ctx.at.record.dom.daily.ouen.SupportFrameNo;
import nts.uk.ctx.at.record.dom.daily.ouen.WorkDetailsParam;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output.ExecutionAttr;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.ExecutionTypeDaily;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyresults.CreateDailyResultDomainServiceNew;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyresults.OutputCreateDailyResult;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.WorkinputRemarks;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.work.WorkGroup;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.UniversalK.就業.KDW_日別実績.KDW013_工数入力.A:工数入力.メニュー別OCD.作業内容を登録する
 * 
 * @author tutt
 *
 */
@Stateless
public class RegisterWorkContent {

	@Inject
	private AddAttendanceTimeZoneCommandHandler handler;

	@Inject
	private GetTargetTime getTargetTime;
	
	@Inject
	private CheckAlarmTargetDate checkAlarmTargetDate;

	@Inject
	private CreateDailyResultDomainServiceNew createDailyResultDomainServiceNew;

	public RegisterWorkContentDto registerWorkContent(RegisterWorkContentCommand command) {

		RegisterWorkContentDto registerWorkContentDto = new RegisterWorkContentDto();

		// 1: <call>() 日別実績の作成
		OutputCreateDailyResult outputCreateDailyOneDay = createDailyResultDomainServiceNew.createDataNewNotAsync(
				command.getEmployeeId(), new DatePeriod(command.getChangedDate(), command.getChangedDate()),
				ExecutionAttr.MANUAL, AppContexts.user().companyId(), ExecutionTypeDaily.CREATE, Optional.empty(),
				Optional.empty());

		// 変更があった日付分をループする
		List<ErrorMessageInfoDto> lstErrorMessageInfoDto = new ArrayList<>();

		if (outputCreateDailyOneDay != null) {
			lstErrorMessageInfoDto = outputCreateDailyOneDay.getListErrorMessageInfo().stream()
					.map(m -> ErrorMessageInfoDto.toDto(m)).collect(Collectors.toList());
		}

		// 2: 登録する(対象者, 編集状態, 作業詳細リスト):List<日別勤怠(Work)>
		// 応援作業別勤怠時間帯を登録する
		AddAttendanceTimeZoneCommand param = new AddAttendanceTimeZoneCommand();

		param.setEmployeeId(command.getEmployeeId());
		param.setEditStateSetting(EnumAdaptor.valueOf(command.getEditStateSetting(), EditStateSetting.class));

		List<WorkDetailCommand> lstWorkDetailCmd = command.getWorkDetails();

		List<WorkDetail> workDetails = lstWorkDetailCmd.stream()
				.map(m -> new WorkDetail(m.getDate(),
						m.getLstWorkDetailsParamCommand().stream()
								.map(n -> new WorkDetailsParam(new SupportFrameNo(n.getSupportFrameNo()), null,
										Optional.ofNullable(WorkGroup.create(n.getWorkGroup().getWorkCD1(),
												n.getWorkGroup().getWorkCD2(), n.getWorkGroup().getWorkCD3(),
												n.getWorkGroup().getWorkCD4(), n.getWorkGroup().getWorkCD5())),
										Optional.ofNullable(new WorkinputRemarks(n.getRemarks())),
										Optional.ofNullable(new WorkLocationCD(n.getWorkLocationCD()))))
								.collect(Collectors.toList())))
				.collect(Collectors.toList());

		param.setWorkDetails(workDetails);

		List<IntegrationOfDaily> integrationOfDailyList = handler.handle(param);
		
		//3: <call> List<日別勤怠(Work)>.エラー一覧
		List<EmployeeDailyPerError> employeeError = new ArrayList<>();
		
		for (IntegrationOfDaily i : integrationOfDailyList ) {
			for (EmployeeDailyPerError e : i.getEmployeeError()) {
				employeeError.add(e);
			}
		}
		
		if(!employeeError.isEmpty()) {
			checkAlarmTargetDate.checkAlarm(employeeError);
		}
		
		//4: [List<日別勤怠(Work)>.isPresent]:<call>(対象者,画面モードList<日別勤怠(Work)>)
		// 残業申請・休出時間申請の対象時間を取得する
		//5: [List<残業休出時間>.isPresent]:<call>
		List<OvertimeLeaveTime> lstOvertimeLeaveTime = new ArrayList<>();
		
		if (!integrationOfDailyList.isEmpty()) {
			lstOvertimeLeaveTime = getTargetTime.get(command.getEmployeeId(), command.getMode(), integrationOfDailyList);
		}
		
		registerWorkContentDto.setLstErrorMessageInfo(lstErrorMessageInfoDto);
		registerWorkContentDto.setLstOvertimeLeaveTime(lstOvertimeLeaveTime);
		
		return registerWorkContentDto;
	}
}
