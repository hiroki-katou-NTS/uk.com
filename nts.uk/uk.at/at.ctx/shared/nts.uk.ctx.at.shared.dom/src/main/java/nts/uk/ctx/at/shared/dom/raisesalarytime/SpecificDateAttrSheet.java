package nts.uk.ctx.at.shared.dom.raisesalarytime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.raisesalarytime.enums.SpecificDateAttr;

/**
 * 
 * @author nampt
 * 特定日区分
 *
 */
@Getter
@AllArgsConstructor
public class SpecificDateAttrSheet {
	
	//特定日項目NO
	private SpecificDateItemNo specificDateItemNo;

	private SpecificDateAttr specificDateAttr;
}
