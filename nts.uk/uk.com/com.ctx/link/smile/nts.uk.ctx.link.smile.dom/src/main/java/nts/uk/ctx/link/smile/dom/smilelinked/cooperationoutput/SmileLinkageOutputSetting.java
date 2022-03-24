package nts.uk.ctx.link.smile.dom.smilelinked.cooperationoutput;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.function.dom.processexecution.ExternalOutputConditionCode;

/**
 * Smile連携出力設定
 */
@Getter
@AllArgsConstructor
public class SmileLinkageOutputSetting extends AggregateRoot {

	/**
	 * Name: 給与連携区分 
	 * Type: Smile連携出力区分
	 */
	private SmileCooperationOutputClassification salaryCooperationClassification;
	/**
	 * Name: 月次ロック区分 
	 * Type: Smile連携出力区分
	 */
	private SmileCooperationOutputClassification monthlyLockClassification;
	/**
	 * Name: 月次承認区分
	 * Type: Smile連携出力区分
	 */
	private SmileCooperationOutputClassification monthlyApprovalCategory;

	/**
	 * Name: 給与連携条件 
	 * Type: Optional＜外部出力条件コード＞
	 */
	private Optional<ExternalOutputConditionCode> salaryCooperationConditions;
}
