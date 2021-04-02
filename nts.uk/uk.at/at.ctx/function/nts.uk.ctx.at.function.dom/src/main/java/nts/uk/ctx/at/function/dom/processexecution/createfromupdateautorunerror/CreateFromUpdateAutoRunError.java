package nts.uk.ctx.at.function.dom.processexecution.createfromupdateautorunerror;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.val;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ProcessExecutionLogManage;

/*
 * 更新処理自動実行エラーからトップページアラームを作成する
 * 
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.就業機能.更新処理自動実行.DomainService.更新処理自動実行エラーからトップページアラームを作成する.更新処理自動実行エラーからトップページアラームを作成する
 */
public class CreateFromUpdateAutoRunError {
	public static Optional<AtomTask> create(Require rq, String cid) {
		 List<ProcessExecutionLogManage> listAutorun = rq.getAutorunItemsWithErrors(cid);
		 List<String> sids = rq.getListEmpID(cid, GeneralDate.today());
		 
		 if(listAutorun.isEmpty()) { //解消済み
//			 val delInfo = null; //TODO dongnt
//			 val alarmParams = null; //TODO dongnt
//			 return Optional.ofNullable(AtomTask.of(() -> rq.createAlarmData(alarmParams, delInfo)));
			 return Optional.empty();
		 }
		//アラームがある
		 List<ProcessExecutionLogManage> removedOptionals = listAutorun.stream().filter(item -> item.getLastEndExecDateTime().isPresent()).collect(Collectors.toList());
		 Optional<ProcessExecutionLogManage> lastItem;
		 if(removedOptionals.isEmpty()) {
			 lastItem = listAutorun.stream().findFirst();
		 } else {
			 lastItem = removedOptionals.stream().sorted((i1, i2) -> i1.getLastEndExecDateTime().get().compareTo(i2.getLastEndExecDateTime().get())).findFirst();
		 }
		 //TODO dongnt
		return Optional.empty();
	}

	public interface Require {
		/**
		 * [R-1] エラーがある自動実行項目を取得する
		 * 
		 * エラーがある自動実行項目を取得する(会社ID)
		 * 
		 * @param 会社ID companyId
		 * @return List<ProcessExecutionLogManage>
		 */
		public List<ProcessExecutionLogManage> getAutorunItemsWithErrors(String companyId);

		/**
		 * [R-2] 就業担当者Listを取得する
		 * 
		 * 就業担当者(社員IDList)を取得するAdapter(会社ID, 基準日)
		 * 
		 * @param 会社ID companyID
		 * @param 基準日  referenceDate
		 * @return
		 */
		public List<String> getListEmpID(String companyID, GeneralDate referenceDate);
		
		//[R-3] トップページアラームデータを作成する
		public void createAlarmData(val alarmParams, val deleteInfo);
	}
}
