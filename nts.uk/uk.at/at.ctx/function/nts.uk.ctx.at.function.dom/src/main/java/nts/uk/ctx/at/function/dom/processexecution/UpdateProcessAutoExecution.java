package nts.uk.ctx.at.function.dom.processexecution;

import java.util.Optional;

import lombok.*;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * Domain 更新処理自動実行<br>
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.就業機能.更新処理自動実行.更新処理自動実行
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
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

	/**
	 * Validate.
	 */
	@Override
	public void validate() throws BusinessException {
		if (execSetting.getPerScheduleCreation().getPerScheduleCls().equals(NotUseAtr.USE)) {
			// 対象日は、個人スケジュール作成区分（B7_1）が「する（TRUE）」の場合は必須入力とする。
			if (!execSetting.getPerScheduleCreation().getPerSchedulePeriod().getTargetDate().isPresent()) {
				throw new BusinessException("Msg_957");
			}
			// 作成期間は、個人スケジュール作成区分が「する（TRUE）」の場合は必須入力とする。
			if (!execSetting.getPerScheduleCreation().getPerSchedulePeriod().getCreationPeriod().isPresent()) {
				throw new BusinessException("Msg_958");	
			}
		}
		// B17_1:アラーム抽出がTRUEの場合、
		// B17_7,B17_8,B17_10,B17_11のいずれか選択されていなければならない。
		// #Msg_1429
		if (execSetting.getAlarmExtraction().getAlarmExtractionCls().equals(NotUseAtr.USE)) { //B17_1
			if (!this.isAlarmCheckboxChecked(execSetting.getAlarmExtraction().getMailAdministrator()) && //B17_7
					!this.isAlarmCheckboxChecked(execSetting.getAlarmExtraction().getMailPrincipal()) && //B17_8
					!this.isAlarmCheckboxChecked(execSetting.getAlarmExtraction().getDisplayOnTopPageAdministrator()) && //B17_10
					!this.isAlarmCheckboxChecked(execSetting.getAlarmExtraction().getDisplayOnTopPagePrincipal())) { //B17_11
				throw new BusinessException("Msg_1429");
			}
		}
		//B16_2がTRUEの場合
		if (executionType == ProcessExecType.NORMAL_EXECUTION) {
			//実行設定(B7_1,B8_1,B9_1,B10_1,B11_1)のチェックボックスのうち1つ以上TUREになっていなければならない
			if (execSetting.getPerScheduleCreation().getPerScheduleCls().equals(NotUseAtr.NOT_USE) && //B7_1
					execSetting.getDailyPerf().getDailyPerfCls().equals(NotUseAtr.NOT_USE) && //B8_1
					execSetting.getReflectAppResult().getReflectResultCls().equals(NotUseAtr.NOT_USE) && //B9_1
					execSetting.getMonthlyAggregate().getMonthlyAggCls().equals(NotUseAtr.NOT_USE) && //B10_1
					execSetting.getAlarmExtraction().getAlarmExtractionCls().equals(NotUseAtr.NOT_USE) && //B11_1
					execSetting.getAppRouteUpdateDaily().getAppRouteUpdateAtr().equals(NotUseAtr.NOT_USE) && //B12_1
					execSetting.getAppRouteUpdateMonthly().getAppRouteUpdateAtr().equals(NotUseAtr.NOT_USE)) { //B12_3
				throw new BusinessException("Msg_1230");
			}
			// B11_1がTRUE　の場合
			// B11_2が選択されなければならない
			// #Msg_1948
			if (execSetting.getAggrAnyPeriod().getAggAnyPeriodAttr().equals(NotUseAtr.USE) //B11_1
					&& !execSetting.getAggrAnyPeriod().getAggrFrameCode().isPresent()) { //B11_2
				throw new BusinessException("Msg_1948");
			}
		} else {//B16_3がTRUEの場合
			//実行設定(B14_2,B14_3,B14_4)のチェックボックスのうち1つ以上TUREになっていなければならない。
			if (execSetting.getDailyPerf().getDailyPerfCls().equals(NotUseAtr.NOT_USE) &&//B14_3
					execSetting.getPerScheduleCreation().getPerScheduleCls().equals(NotUseAtr.NOT_USE) &&//B14_2
					execSetting.getAppRouteUpdateDaily().getAppRouteUpdateAtr().equals(NotUseAtr.NOT_USE)) {//B14_4
				throw new BusinessException("Msg_1230");
			}

			//実行設定(B15_2,B15_3,B15_4)のチェックボックスのうち1つ以上TUREになっていなければならない。
			if (execSetting.getReExecCondition().getRecreateTransfer().equals(NotUseAtr.NOT_USE) && //B15_2
					execSetting.getReExecCondition().getRecreatePersonChangeWkt().equals(NotUseAtr.NOT_USE) && //B15_3
					execSetting.getReExecCondition().getRecreateLeave().equals(NotUseAtr.NOT_USE)) { //B15_4 
				throw new BusinessException("Msg_1391");
			}
		}
		// B19_1がTRUE　の場合、
		// B19_11が1つ以上選択されなければならない
		// #Msg_1949
		if (execSetting.getExternalOutput().getExtOutputCls().equals(NotUseAtr.USE) && //B19_1
				execSetting.getExternalOutput().getExtOutCondCodeList().isEmpty()) { //B19_11
			throw new BusinessException("Msg_1949");
		}
		// B21_1がTRUE　の場合、
		// B21_4が選択されなければならない
		// #Msg_1950
		if (execSetting.getSaveData().getSaveDataCls().equals(NotUseAtr.USE) && //B21_1
				!execSetting.getSaveData().getPatternCode().isPresent()) { //B21_4
			throw new BusinessException("Msg_1950");
		}
		// B22_1がTRUE　の場合、
		// B22_4が選択されなければならない
		// #Msg_1951
		if (execSetting.getDeleteData().getDataDelCls().equals(NotUseAtr.USE) && //B22_1
				!execSetting.getDeleteData().getPatternCode().isPresent()) { //B22_4
			throw new BusinessException("Msg_1951");
		}
		// B23_1がTRUE　の場合、
		// B25_2が1つ以上選択されなければならない
		// #Msg_1952
		if (execSetting.getIndexReconstruction().getIndexReorgAttr().equals(NotUseAtr.USE) && //B23_1
				execSetting.getIndexReconstruction().getCategoryList().isEmpty()) { //B25_2
			throw new BusinessException("Msg_1952");
		}
		
	}
	
	private boolean isAlarmCheckboxChecked(Optional<Boolean> value) {
		return value.isPresent() && value.get();
	}

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
	 * @param memento the Memento setter
	 */
	public void setMemento(MementoSetter memento) {
		memento.setCompanyId(this.companyId);
		memento.setExecItemCode(this.execItemCode.v());
		memento.setExecItemName(this.execItemName.v());
		memento.setExecScope(this.execScope);
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
}
