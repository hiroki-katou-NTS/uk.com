package nts.uk.ctx.at.function.dom.processexecution.createfromupdateautorunerror;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.function.dom.processexecution.ExecutionCode;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.CurrentExecutionStatus;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.EndStatus;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.OverallErrorDetail;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ProcessExecutionLogManage;

public class CreateFromUpdateAutoRunErrorHelper {

	/*
	 * Mock [R-1] 更新処理自動実行管理を取得
	 * 
	 * 1 Element
	 * 前回終了日時 EMPTY
	 */
	public static List<ProcessExecutionLogManage> mockR1LastExecEmpty() {
		List<ProcessExecutionLogManage> returnList = new ArrayList<>();
		returnList.add(ProcessExecutionLogManage.builder()
				.execItemCd(new ExecutionCode("execItemCd"))
				.companyId("companyId")
				.overallError(Optional.ofNullable(OverallErrorDetail.EXCEED_TIME))
				.overallStatus(Optional.ofNullable(EndStatus.CLOSING))
				.lastExecDateTime(Optional.ofNullable(GeneralDateTime.now()))
				.currentStatus(Optional.ofNullable(CurrentExecutionStatus.RUNNING))
				.lastExecDateTimeEx(Optional.ofNullable(GeneralDateTime.now()))
				.lastEndExecDateTime(Optional.empty())
				.errorSystem(Optional.ofNullable(false))
				.errorBusiness(Optional.ofNullable(false))
				.build());
		return returnList;
	}
	
	/*
	 * Mock [R-1] 更新処理自動実行管理を取得
	 * 
	 * 3 Elements
	 * 前回終了日時 NOT EMPTY
	 */
	public static List<ProcessExecutionLogManage> mockR1LastExecNotEmpty() {
		List<ProcessExecutionLogManage> returnList = new ArrayList<>();
		
		//index = 0
		returnList.add(ProcessExecutionLogManage.builder()
				.execItemCd(new ExecutionCode("execItemCd1"))
				.companyId("companyId1")
				.overallError(Optional.ofNullable(OverallErrorDetail.EXCEED_TIME))
				.overallStatus(Optional.ofNullable(EndStatus.CLOSING))
				.lastExecDateTime(Optional.ofNullable(GeneralDateTime.now()))
				.currentStatus(Optional.ofNullable(CurrentExecutionStatus.RUNNING))
				.lastExecDateTimeEx(Optional.ofNullable(GeneralDateTime.now()))
				.lastEndExecDateTime(Optional.ofNullable(GeneralDateTime.now()))
				.errorSystem(Optional.ofNullable(false))
				.errorBusiness(Optional.ofNullable(false))
				.build());
		
		//index = 1 -> lastEndExecDateTime is before first element
		returnList.add(ProcessExecutionLogManage.builder()
				.execItemCd(new ExecutionCode("execItemCd2"))
				.companyId("companyId2")
				.overallError(Optional.ofNullable(OverallErrorDetail.EXCEED_TIME))
				.overallStatus(Optional.ofNullable(EndStatus.CLOSING))
				.lastExecDateTime(Optional.ofNullable(GeneralDateTime.now()))
				.currentStatus(Optional.ofNullable(CurrentExecutionStatus.RUNNING))
				.lastExecDateTimeEx(Optional.ofNullable(GeneralDateTime.now()))
				.lastEndExecDateTime(Optional.ofNullable(GeneralDateTime.now().addHours(-10)))
				.errorSystem(Optional.ofNullable(false))
				.errorBusiness(Optional.ofNullable(false))
				.build());
		
		//index = 2 -> lastEndExecDateTime is empty
		returnList.add(ProcessExecutionLogManage.builder()
				.execItemCd(new ExecutionCode("execItemCd3"))
				.companyId("companyId3")
				.overallError(Optional.ofNullable(OverallErrorDetail.EXCEED_TIME))
				.overallStatus(Optional.ofNullable(EndStatus.CLOSING))
				.lastExecDateTime(Optional.ofNullable(GeneralDateTime.now()))
				.currentStatus(Optional.ofNullable(CurrentExecutionStatus.RUNNING))
				.lastExecDateTimeEx(Optional.empty())
				.lastEndExecDateTime(Optional.empty())
				.errorSystem(Optional.ofNullable(false))
				.errorBusiness(Optional.ofNullable(false))
				.build());
		
		return returnList;
	}
}
