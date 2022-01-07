package smilelinked.cooperationacceptance;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

@Getter
@AllArgsConstructor
public class SmileCooperationAcceptanceSetting extends AggregateRoot {
	 private final SmileCooperationAcceptanceItem cooperationAcceptance;
	 private SmileCooperationAcceptanceClassification cooperationAcceptanceClassification;
	 /**
	  * String ->ExternalAcceptanceConditionCode 外部受入条件コード
	  */
	 private Optional<String> cooperationAcceptanceConditions;
}
