package nts.uk.ctx.at.function.dom.processexecution;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.ot.frame.NotUseAtr;

/**
 * The Class SaveData.
 * データの保存
 */
@Getter
@Setter
@AllArgsConstructor
public class SaveData extends DomainObject {

	/**
	 * データの保存区分 
	 **/
	private NotUseAtr saveDataClassification;
	
	/**
	 * パターンコード
	 **/
	private Optional<AuxiliaryPatternCode> patternCode;
}
