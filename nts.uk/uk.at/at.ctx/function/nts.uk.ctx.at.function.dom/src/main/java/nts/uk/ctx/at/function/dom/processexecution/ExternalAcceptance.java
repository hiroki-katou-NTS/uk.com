package nts.uk.ctx.at.function.dom.processexecution;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.ot.frame.NotUseAtr;

/**
 * 外部受入
 */
@Getter
@Setter
@AllArgsConstructor
public class ExternalAcceptance extends DomainObject {

	/** 
	 * 外部受入区分 
	 **/
	private NotUseAtr externalAcceptanceClassification;
	
	/** 
	 * 条件一覧 
	 **/
	private Optional<List<ExternalAcceptanceConditionCode>> listConditions;
}
