package smilelinked.cooperationoutput;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * AR_Smile連携出力設定
 */
@Getter
@AllArgsConstructor
public class SmileLinkageOutputSetting extends AggregateRoot {
	private SmileCooperationOutputClassification salaryCooperationClassification;
	private SmileCooperationOutputClassification monthlyLockClassification;
	private SmileCooperationOutputClassification monthlyApprovalCategory;
	/**
	 * String -> ExternalOutputConditionCode
	 */
	private Optional<String> salaryCooperationConditions;
}
