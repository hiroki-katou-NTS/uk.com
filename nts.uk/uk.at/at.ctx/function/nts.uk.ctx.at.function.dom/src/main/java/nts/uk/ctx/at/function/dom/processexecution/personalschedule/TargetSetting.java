package nts.uk.ctx.at.function.dom.processexecution.personalschedule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * 作成対象詳細設定
 */
@Getter
@AllArgsConstructor
public class TargetSetting extends DomainObject {

	/**
	 * 勤務種別変更者を再作成
	 */
	private boolean recreateWorkType;

//	/* 手修正を保護する */
//	private boolean manualCorrection;

	/**
	 * 新入社員を作成する
	 */
	private boolean createEmployee;

	/**
	 * 異動者を再作成する
	 */
	private boolean recreateTransfer;

	/**
	 * Instantiates a new Target setting.
	 *
	 * @param recreateWorkType the recreate work type
	 * @param createEmployee   the create employee
	 * @param recreateTransfer the recreate transfer
	 */
	public TargetSetting(int recreateWorkType, int createEmployee, int recreateTransfer) {
		this.recreateWorkType = recreateWorkType == 1;
		this.createEmployee = createEmployee == 1;
		this.recreateTransfer = recreateTransfer == 1;
	}

}
