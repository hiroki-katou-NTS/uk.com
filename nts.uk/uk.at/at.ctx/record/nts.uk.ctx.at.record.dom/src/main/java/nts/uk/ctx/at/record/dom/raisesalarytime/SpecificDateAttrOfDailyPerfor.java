package nts.uk.ctx.at.record.dom.raisesalarytime;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;

/**
 * 
 * @author nampt
 * 日別実績の特定日区分 - root
 *
 */
@Getter
@AllArgsConstructor
public class SpecificDateAttrOfDailyPerfor extends AggregateRoot {
	
	private String employeeId;
	
	private List<SpecificDateAttrSheet> specificDateAttrSheets;
	
	private GeneralDate ymd;

}
