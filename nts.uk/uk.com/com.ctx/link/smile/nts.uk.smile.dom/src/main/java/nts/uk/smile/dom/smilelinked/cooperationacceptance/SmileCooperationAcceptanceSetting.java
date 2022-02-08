package nts.uk.smile.dom.smilelinked.cooperationacceptance;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.function.dom.processexecution.ExternalAcceptanceConditionCode;

/**
 * Smile連携受入設定
 *
 */
@Getter
@AllArgsConstructor
public class SmileCooperationAcceptanceSetting extends AggregateRoot {

	/**
	 * Name: Smile連携受入 Type: Smile連携受入項目
	 */
	private final SmileCooperationAcceptanceItem cooperationAcceptance;

	/**
	 * Name: Smile連携受入区分 Type: Smile連携受入区分
	 */
	private SmileCooperationAcceptanceClassification cooperationAcceptanceClassification;

	/**
	 * Name: Smile連携受入条件 Type: Optional 外部受入条件コード
	 */
	private Optional<ExternalAcceptanceConditionCode> cooperationAcceptanceConditions;
}
