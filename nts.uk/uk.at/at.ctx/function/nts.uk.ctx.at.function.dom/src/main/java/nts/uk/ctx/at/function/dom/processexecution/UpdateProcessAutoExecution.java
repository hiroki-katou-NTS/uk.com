package nts.uk.ctx.at.function.dom.processexecution;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
//import nts.uk.ctx.at.shared.dom.ot.frame.NotUseAtr;

/**
 * Domain 更新処理自動実行<br>
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.就業機能.更新処理自動実行.更新処理自動実行
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProcessAutoExecution extends AggregateRoot {

	/**
	 * 会社ID
	 */
	private String companyId;

	/**
	 * コード
	 */
	private ExecutionCode execItemCode;

	/**
	 * 名称
	 */
	private ExecutionName execItemName;

	/**
	 * 実行範囲
	 */
	private ProcessExecutionScope execScope;

	/**
	 * 実行設定
	 */
	private ProcessExecutionSetting execSetting;

	/**
	 * 実行種別
	 */
	private ProcessExecType executionType;

	/**
	 * 再実行条件
	 */
	private ReExecutionCondition reExecCondition;

	/**
	 * クラウド作成フラグ
	 */
	private boolean cloudCreationFlag;

//	/**
//	 * Validate ver 2.
//	 */
//	public void validateVer2() {
////		List<String> listError = new ArrayList<>();
//		if (execSetting.getPerScheduleCreation().isPerSchedule()) {
//			// 対象日は、個人スケジュール作成区分（B7_1）が「する（TRUE）」の場合は必須入力とする。
//			if (execSetting.getPerScheduleCreation().getPerSchedulePeriod().getTargetDate() == null) {
//				throw new BusinessException("Msg_957");
//			}
//			// 作成期間は、個人スケジュール作成区分が「する（TRUE）」の場合は必須入力とする。
//			if (execSetting.getPerScheduleCreation().getPerSchedulePeriod().getCreationPeriod() == null) {
//				throw new BusinessException("Msg_958");
//			}
//		}
//		//B16_2がTRUEの場合
//		if (executionType == ProcessExecType.NORMAL_EXECUTION) {
//			//実行設定(B7_1,B8_1,B9_1,B10_1,B11_1)のチェックボックスのうち1つ以上TUREになっていなければならない
//			if (!execSetting.getPerScheduleCreation().isPerSchedule() && //B7_1
//					!execSetting.getDailyPerf().isDailyPerfCls() && //B8_1
//					execSetting.getReflectAppResult().getReflectResultCls() == NotUseAtr.NOT_USE && //B9_1
//					execSetting.getMonthlyAggregate().getMonthlyAggCls() == NotUseAtr.NOT_USE&& //B10_1
//					!execSetting.getAlarmExtraction().isAlarmAtr() && //B11_1
//					execSetting.getAppRouteUpdateDaily().getAppRouteUpdateAtr() == NotUseAtr.NOT_USE && //B12_1
//					execSetting.getAppRouteUpdateMonthly() == NotUseAtr.NOT_USE) { //B12_3
//
//				throw new BusinessException("Msg_1230");
//			}
//
//			if (execSetting.getAlarmExtraction().isAlarmAtr()) {
//				if (execSetting.getAlarmExtraction().getMailPrincipal().isPresent() && execSetting.getAlarmExtraction().getMailAdministrator().isPresent()) {
//					if (!execSetting.getAlarmExtraction().getMailPrincipal().get() &&  //B11_5
//							!execSetting.getAlarmExtraction().getMailAdministrator().get()) {//B11_6
//						throw new BusinessException("Msg_1429");
//					}
//				}
//
//			}
//		} else {//B16_3がTRUEの場合
//			//実行設定(B14_2,B14_3,B14_4)のチェックボックスのうち1つ以上TUREになっていなければならない。
//			if (!execSetting.getDailyPerf().isDailyPerfCls() &&//B14_3
//					!execSetting.getPerScheduleCreation().isPerSchedule() &&//B14_2
//					execSetting.getAppRouteUpdateDaily().getAppRouteUpdateAtr() == NotUseAtr.NOT_USE) {//B14_4
//				throw new BusinessException("Msg_1230");
//			}
//
//
//			//実行設定(B15_2,B15_3)のチェックボックスのうち1つ以上TUREになっていなければならない。
//			if (!execSetting.getDailyPerf().getTargetGroupClassification().isRecreateTransfer() &&//B15_2
//					!execSetting.getDailyPerf().getTargetGroupClassification().isRecreateTypeChangePerson()) {//B15_3
//				throw new BusinessException("Msg_1391");
//			}
//		}
////		return listError;
//	}

	/**
	 * Creates domain from memento.
	 *
	 * @param companyId the company id
	 * @param memento   the Memento getter
	 * @return the domain 更新処理自動実行
	 */
	public static UpdateProcessAutoExecution createFromMemento(String companyId, MementoGetter memento) {
		UpdateProcessAutoExecution domain = new UpdateProcessAutoExecution();
		domain.getMemento(memento);
		domain.companyId = companyId;
		return domain;
	}

	/**
	 * Gets memento.
	 *
	 * @param memento the Memento getter
	 */
	public void getMemento(MementoGetter memento) {
//		this.companyId = memento.getCompanyId();
		this.execItemCode = new ExecutionCode(memento.getExecItemCode());
		this.execItemName = new ExecutionName(memento.getExecItemName());
		this.execScope = memento.getExecScope();
		this.execSetting = memento.getExecSetting();
		this.executionType = EnumAdaptor.valueOf(memento.getExecutionType(), ProcessExecType.class);
		this.reExecCondition = memento.getReExecCondition();
		this.cloudCreationFlag = memento.getCloudCreFlag();
	}

	/**
	 * Sets memento.
	 *
	 * @param contractCode the contract code
	 * @param memento      the Memento setter
	 */
	public void setMemento(String contractCode, MementoSetter memento) {
		memento.setCompanyId(this.companyId);
		memento.setExecItemCode(this.execItemCode.v());
		memento.setExecItemName(this.execItemName.v());
		memento.setExecScope(this.execScope);
		memento.setContractCode(contractCode);
		memento.setExecSetting(this.execSetting);
		memento.setExecutionType(this.executionType.value);
		memento.setReExecCondition(this.reExecCondition);
		memento.setCloudCreFlag(this.cloudCreationFlag);
	}

	/**
	 * The interface Memento setter.
	 */
	public static interface MementoSetter {
		/**
		 * Sets company id.
		 *
		 * @param companyId the company id
		 */
		public void setCompanyId(String companyId);

		/**
		 * Sets execution item code.
		 *
		 * @param execItemCode the execution item code
		 */
		public void setExecItemCode(String execItemCode);

		/**
		 * Sets execution item name.
		 *
		 * @param execItemName the execution item name
		 */
		public void setExecItemName(String execItemName);

		/**
		 * Sets execution scope.
		 *
		 * @param execScope the domain process execution scope
		 */
		public void setExecScope(ProcessExecutionScope execScope);

		/**
		 * Sets contract code.
		 *
		 * @param contractCode the contract code
		 */
		public void setContractCode(String contractCode);

		/**
		 * Sets execution setting.
		 *
		 * @param execSetting the domain process execution setting
		 */
		public void setExecSetting(ProcessExecutionSetting execSetting);

		/**
		 * Sets execution type.
		 *
		 * @param executionType the execution type
		 */
		public void setExecutionType(int executionType);

		/**
		 * Sets re-execution condition.
		 *
		 * @param reExecCondition the domain re-execution condition
		 */
		public void setReExecCondition(ReExecutionCondition reExecCondition);

		/**
		 * Sets cloud creation flag.
		 *
		 * @param cloudCreFlag the cloud creation flag
		 */
		public void setCloudCreFlag(boolean cloudCreFlag);
	}

	/**
	 * The interface Memento getter.
	 */
	public static interface MementoGetter {
		/**
		 * Gets company id.
		 *
		 * @return the company id
		 */
		public String getCompanyId();

		/**
		 * Gets execution item code.
		 *
		 * @return the execution item code
		 */
		public String getExecItemCode();

		/**
		 * Gets execution item name.
		 *
		 * @return the execution item name
		 */
		public String getExecItemName();

		/**
		 * Gets execution scope.
		 *
		 * @return the domain execution scope
		 */
		public ProcessExecutionScope getExecScope();

		/**
		 * Gets execution setting.
		 *
		 * @return the domain execution setting
		 */
		public ProcessExecutionSetting getExecSetting();

		/**
		 * Gets process execution type.
		 *
		 * @return the process execution type
		 */
		public int getExecutionType();

		/**
		 * Gets re-execution condition.
		 *
		 * @return the domain re-execution condition
		 */
		public ReExecutionCondition getReExecCondition();

		/**
		 * Gets cloud creation flag.
		 *
		 * @return the cloud creation flag
		 */
		public boolean getCloudCreFlag();
	}

//	public void validate() {
//		if (execSetting.getPerSchedule().isPerSchedule()) {
//			// 対象日は、個人スケジュール作成区分が「する（TRUE）」の場合は必須入力とする。
//			if (execSetting.getPerSchedule().getPeriod().getTargetDate() == null) {
//				throw new BusinessException("Msg_957");
//			}
//			// 作成期間は、個人スケジュール作成区分が「する（TRUE）」の場合は必須入力とする。
//			if (execSetting.getPerSchedule().getPeriod().getCreationPeriod() == null) {
//				throw new BusinessException("Msg_958");
//			}
//		}
//		// 画面項目「B7_20:異動者・新入社員のみ作成」がTRUEの場合、B7_21かB7_22かB7_24のどれかがTRUEでなければならない。
//		if (execSetting.getPerSchedule().getTarget().getCreationTarget().value == TargetClassification.CONDITIONS.value) {
//			if (!execSetting.getPerSchedule().getTarget().getTargetSetting().isRecreateTransfer() &&
//					!execSetting.getPerSchedule().getTarget().getTargetSetting().isCreateEmployee()&&!execSetting.getPerSchedule().getTarget().getTargetSetting().isRecreateWorkType()) {
//				throw new BusinessException("Msg_867");
//			}
//		}
//		
//		//実行設定.個人スケジュール作成.個人スケジュール作成区分		B7_1						
//		boolean perSchedule = execSetting.getPerSchedule().isPerSchedule();
//
//		//実行設定.日別実績の作成・計算.日別実績の作成・計算区分  B8_1
//		boolean dailyPerfCls = execSetting.getDailyPerf().isDailyPerfCls();
//
//		//実行設定.承認結果反映 B9_1
//		boolean reflectResultCls = execSetting.isReflectResultCls();
//
//		//実行設定.月別集計	B10_1							
//		boolean monthlyAggCls = execSetting.isMonthlyAggCls();
//									
//		//実行設定.アラーム抽出（個人別）.アラーム抽出（個人別）区分 B11_1
//		boolean indvAlarmCls = execSetting.getIndvAlarm().isIndvAlarmCls();
//
//		//実行設定.アラーム抽出（職場別）.アラーム抽出（職場別）区分 B12_1								
//		boolean wkpAlarmCls = execSetting.getWkpAlarm().isWkpAlarmCls();
//		if(!perSchedule && !dailyPerfCls && !reflectResultCls && !monthlyAggCls && !indvAlarmCls && !wkpAlarmCls){
//			throw new BusinessException("Msg_1230");
//		}
//    }
}
