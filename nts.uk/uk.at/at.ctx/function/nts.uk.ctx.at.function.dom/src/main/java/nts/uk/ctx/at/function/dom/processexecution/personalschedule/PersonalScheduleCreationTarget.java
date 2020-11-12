package nts.uk.ctx.at.function.dom.processexecution.personalschedule;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;

/**
 * 個人スケジュール作成対象社員
 */
@Getter
@AllArgsConstructor
@Builder
public class PersonalScheduleCreationTarget extends DomainObject {

	/**
	 * 作成対象
	 */
	private TargetClassification creationTarget;

	/**
	 * 作成対象詳細設定
	 */
	private TargetSetting targetSetting;

	/**
	 * Instantiates a new Personal schedule creation target.
	 *
	 * @param creationTarget the creation target
	 * @param targetSetting  the target setting
	 */
	public PersonalScheduleCreationTarget(int creationTarget, TargetSetting targetSetting) {
		this.creationTarget = EnumAdaptor.valueOf(creationTarget, TargetClassification.class);
		this.targetSetting = targetSetting;
	}

}
