package nts.uk.ctx.at.function.dom.processexecution;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.ot.frame.NotUseAtr;

/**
 * 外部出力
 */
@Getter
@Setter
@AllArgsConstructor
public class ExternalOutput extends DomainObject {
	
	/** 
	 * 外部出力区分 
	 **/
	private NotUseAtr externalOutputClassification;
	
	/** 
	 * 条件一覧 
	 **/
	private Optional<List<ExternalOutputConditionCode>> listConditions;
}
