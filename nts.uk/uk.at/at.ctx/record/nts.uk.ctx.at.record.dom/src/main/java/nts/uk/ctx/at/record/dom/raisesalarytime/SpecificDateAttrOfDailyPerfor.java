package nts.uk.ctx.at.record.dom.raisesalarytime;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.raisesalarytime.primitivevalue.SpecificDateItemNo;

/**
 * 
 * @author nampt
 * 日別実績の特定日区分 - root
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SpecificDateAttrOfDailyPerfor extends AggregateRoot {
	
	private String employeeId;
	
	private List<SpecificDateAttrSheet> specificDateAttrSheets;
	
	private GeneralDate ymd;
	
	/**
	 * 使用する　になっている特定日Noを取得
	 * @return　使用する特定日No
	 */
	public List<SpecificDateItemNo> getUseNo(){
		return this.specificDateAttrSheets.stream().filter(tc -> tc.getSpecificDateAttr().isUse()).map(ts -> ts.getSpecificDateItemNo()).collect(Collectors.toList());
	}
}
