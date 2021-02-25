package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.paytime;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
	//するしない区分
	private SpecificDateAttr specificDateAttr;
}
