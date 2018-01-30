package nts.uk.ctx.at.function.dom.processexecution.personalschedule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * 個人スケジュール作成対象社員
 */
@Getter
@AllArgsConstructor
public class PersonalScheduleCreationTarget extends DomainObject {
	/* 作成対象 */
	private TargetClassification creationTarget;
	
	/* 作成対象詳細設定 */
	private TargetSetting targetSetting;
}
