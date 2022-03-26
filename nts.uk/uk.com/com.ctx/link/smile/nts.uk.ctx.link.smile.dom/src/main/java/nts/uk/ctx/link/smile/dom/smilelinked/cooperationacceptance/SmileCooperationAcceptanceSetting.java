package nts.uk.ctx.link.smile.dom.smilelinked.cooperationacceptance;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.function.dom.processexecution.ExternalAcceptanceConditionCode;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportCode;

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
	@Setter
	private SmileCooperationAcceptanceClassification cooperationAcceptanceClassification;

	/**
	 * Name: Smile連携受入条件 Type: Optional 外部受入条件コード
	 */
	@Setter
	private Optional<ExternalImportCode> cooperationAcceptanceConditions;

}
