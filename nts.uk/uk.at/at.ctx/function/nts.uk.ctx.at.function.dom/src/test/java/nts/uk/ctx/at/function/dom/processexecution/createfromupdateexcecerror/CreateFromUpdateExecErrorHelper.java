package nts.uk.ctx.at.function.dom.processexecution.createfromupdateexcecerror;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.function.dom.adapter.toppagealarmpub.DeleteInfoAlarmImport;
import nts.uk.ctx.at.function.dom.adapter.toppagealarmpub.TopPageAlarmImport;
import nts.uk.ctx.at.function.dom.processexecution.ExecutionCode;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.CurrentExecutionStatus;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.EndStatus;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.OverallErrorDetail;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ProcessExecutionLogManage;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.ExecutionTaskSetting;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.OneDayRepeatInterval;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.RepeatDetailSetting;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.TaskEndDate;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.TaskEndTime;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.enums.EndDateClassification;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.enums.EndTimeClassification;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.enums.OneDayRepeatClassification;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.enums.RepeatContentItem;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.primitivevalue.StartTime;

public class CreateFromUpdateExecErrorHelper {
	
	public static final String CID = "cid";
	public static final List<String> SIDS = Stream.of("sid").collect(Collectors.toList());
	public static final GeneralDate REFER_DATE = GeneralDate.today();

	/*
	 * Mock [R-1] 更新処理自動実行管理を取得
	 * 
	 * 現在の実行状態  NOT PRESENT
	 */
	public static List<ProcessExecutionLogManage> mockR1StatusEmpty() {
		List<ProcessExecutionLogManage> returnList = new ArrayList<>();
		returnList.add(ProcessExecutionLogManage.builder()
				.execItemCd(new ExecutionCode("execItemCode"))
				.companyId("cid")
				.overallError(Optional.ofNullable(OverallErrorDetail.EXCEED_TIME))
				.overallStatus(Optional.ofNullable(EndStatus.CLOSING))
				.lastExecDateTime(Optional.ofNullable(GeneralDateTime.now()))
				.currentStatus(Optional.empty())
				.lastExecDateTimeEx(Optional.ofNullable(GeneralDateTime.now()))
				.lastEndExecDateTime(Optional.ofNullable(GeneralDateTime.now()))
				.errorSystem(Optional.ofNullable(false))
				.errorBusiness(Optional.ofNullable(false))
				.build());
		return returnList;
	}
	
	/*
	 * Mock [R-1] 更新処理自動実行管理を取得
	 * 
	 * 更新処理自動実行項目の状態 = 実行中
	 * 前回実行日時 NOT EMPTY
	 */
	public static List<ProcessExecutionLogManage> mockR1StatusRunningNotEmpty() {
		List<ProcessExecutionLogManage> returnList = new ArrayList<>();
		returnList.add(ProcessExecutionLogManage.builder()
				.execItemCd(new ExecutionCode("execItemCode"))
				.companyId("cid")
				.overallError(Optional.ofNullable(OverallErrorDetail.EXCEED_TIME))
				.overallStatus(Optional.ofNullable(EndStatus.CLOSING))
				.lastExecDateTime(Optional.ofNullable(GeneralDateTime.now()))
				.currentStatus(Optional.ofNullable(CurrentExecutionStatus.RUNNING))
				.lastExecDateTimeEx(Optional.ofNullable(GeneralDateTime.now()))
				.lastEndExecDateTime(Optional.ofNullable(GeneralDateTime.now()))
				.errorSystem(Optional.ofNullable(false))
				.errorBusiness(Optional.ofNullable(false))
				.build());
		return returnList;
	}
	
	/*
	 * Mock [R-1] 更新処理自動実行管理を取得
	 * 
	 * 更新処理自動実行項目の状態 = 実行中
	 * 前回実行日時 NOT EMPTY
	 */
	public static List<ProcessExecutionLogManage> mockR1StatusRunningEmpty() {
		List<ProcessExecutionLogManage> returnList = new ArrayList<>();
		returnList.add(ProcessExecutionLogManage.builder()
				.execItemCd(new ExecutionCode("execItemCode"))
				.companyId("cid")
				.overallError(Optional.ofNullable(OverallErrorDetail.EXCEED_TIME))
				.overallStatus(Optional.ofNullable(EndStatus.CLOSING))
				.lastExecDateTime(Optional.empty())
				.currentStatus(Optional.ofNullable(CurrentExecutionStatus.RUNNING))
				.lastExecDateTimeEx(Optional.ofNullable(GeneralDateTime.now()))
				.lastEndExecDateTime(Optional.ofNullable(GeneralDateTime.now()))
				.errorSystem(Optional.ofNullable(false))
				.errorBusiness(Optional.ofNullable(false))
				.build());
		return returnList;
	}
	
	/*
	 * Mock [R-1] 更新処理自動実行管理を取得
	 * 
	 * 更新処理自動実行項目の状態 = 待機中
	 */
	public static List<ProcessExecutionLogManage> mockR1StatusWaiting() {
		List<ProcessExecutionLogManage> returnList = new ArrayList<>();
		returnList.add(ProcessExecutionLogManage.builder()
				.execItemCd(new ExecutionCode("execItemCode"))
				.companyId("cid")
				.overallError(Optional.ofNullable(OverallErrorDetail.EXCEED_TIME))
				.overallStatus(Optional.ofNullable(EndStatus.CLOSING))
				.lastExecDateTime(Optional.ofNullable(GeneralDateTime.now()))
				.currentStatus(Optional.ofNullable(CurrentExecutionStatus.WAITING))
				.lastExecDateTimeEx(Optional.ofNullable(GeneralDateTime.now()))
				.lastEndExecDateTime(Optional.ofNullable(GeneralDateTime.now()))
				.errorSystem(Optional.ofNullable(false))
				.errorBusiness(Optional.ofNullable(false))
				.build());
		return returnList;
	}
	
	/*
	 * Mock [R-2] 実行タスク設定を取得
	 */
	public static List<ExecutionTaskSetting> mockR2() {
		List<ExecutionTaskSetting> returnList = new ArrayList<>();
		returnList.add(ExecutionTaskSetting.builder()
				.companyId("cid")
				.execItemCd(new ExecutionCode("execItemCode"))
				.enabledSetting(false)
				.oneDayRepInr(new OneDayRepeatInterval(Optional.empty(), OneDayRepeatClassification.NONE))
				.nextExecDateTime(Optional.empty())
				.endDate(new TaskEndDate(EndDateClassification.NONE, Optional.empty()))
				.endTime(new TaskEndTime(EndTimeClassification.NONE, Optional.empty()))
				.content(RepeatContentItem.ONCE_TIME)
				.detailSetting(new RepeatDetailSetting(Optional.empty(), Optional.empty()))
				.startDate(GeneralDate.today())
				.startTime(new StartTime(100))
				.scheduleId("scheduleId")
				.endScheduleId(Optional.empty())
				.repeatScheduleId(Optional.empty())
				.build());
		return returnList;
	}
	
	/*
	 * Mock [R-4] 次回実行日時作成処理 param
	 */
	public static ExecutionTaskSetting mockR4Param() {
		return ExecutionTaskSetting.builder()
				.companyId("cid")
				.execItemCd(new ExecutionCode("execItemCode"))
				.enabledSetting(false)
				.oneDayRepInr(new OneDayRepeatInterval(Optional.empty(), OneDayRepeatClassification.NONE))
				.nextExecDateTime(Optional.empty())
				.endDate(new TaskEndDate(EndDateClassification.NONE, Optional.empty()))
				.endTime(new TaskEndTime(EndTimeClassification.NONE, Optional.empty()))
				.content(RepeatContentItem.ONCE_TIME)
				.detailSetting(new RepeatDetailSetting(Optional.empty(), Optional.empty()))
				.startDate(GeneralDate.today())
				.startTime(new StartTime(100))
				.scheduleId("scheduleId")
				.endScheduleId(Optional.empty())
				.repeatScheduleId(Optional.empty())
				.build();
	}
	
	public static DeleteInfoAlarmImport mockDelInfoImport() {
		return DeleteInfoAlarmImport.builder()
				.alarmClassification(2) // 更新処理自動実行動作異常
				.sids(CreateFromUpdateExecErrorHelper.SIDS)
				.displayAtr(1) // 上長
				.patternCode(Optional.empty())
				.build();
	}
	
	public static List<TopPageAlarmImport> mockListTopPageAlarmInport() {
		return CreateFromUpdateExecErrorHelper.SIDS.stream()
			.map(sid -> TopPageAlarmImport.builder()
					.alarmClassification(2) // 更新処理自動実行動作異常
					.occurrenceDateTime(GeneralDateTime.now())
					.displaySId(sid)
					.displayAtr(1) // 上長
					.patternCode(Optional.empty())
					.patternName(Optional.empty())
					.linkUrl(Optional.empty())
					.displayMessage(Optional.empty())
					.build())
			.collect(Collectors.toList());
	}
}
