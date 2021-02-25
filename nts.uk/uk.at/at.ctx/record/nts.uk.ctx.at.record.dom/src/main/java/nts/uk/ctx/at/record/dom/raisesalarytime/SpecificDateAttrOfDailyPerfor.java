package nts.uk.ctx.at.record.dom.raisesalarytime;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.paytime.SpecificDateAttr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.paytime.SpecificDateAttrOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.paytime.SpecificDateAttrSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.paytime.SpecificDateItemNo;

/**
 * 
 * @author nampt
 * 日別実績の特定日区分 - root
 *
 */
@Getter
@NoArgsConstructor
public class SpecificDateAttrOfDailyPerfor extends AggregateRoot {
	//社員ID
	private String employeeId;
	//年月日
	private GeneralDate ymd;
	
	//特定日
	private SpecificDateAttrOfDailyAttd specificDay;
	
	/**
	 * 使用する　になっている特定日Noを取得
	 * @return　使用する特定日No
	 */
	public List<SpecificDateItemNo> getUseNo(){
		return this.specificDay.getSpecificDateAttrSheets().stream().filter(tc -> tc.getSpecificDateAttr().isUse()).map(ts -> ts.getSpecificDateItemNo()).collect(Collectors.toList());
	}
	public SpecificDateAttrOfDailyPerfor(String employeeId, List<SpecificDateAttrSheet> specificDateAttrSheets,
			GeneralDate ymd) {
		super();
		this.employeeId = employeeId;
		this.ymd = ymd;
		this.specificDay = new SpecificDateAttrOfDailyAttd(specificDateAttrSheets);
	}
	public SpecificDateAttrOfDailyPerfor(String employeeId, GeneralDate ymd, SpecificDateAttrOfDailyAttd specificDay) {
		super();
		this.employeeId = employeeId;
		this.ymd = ymd;
		this.specificDay = specificDay;
	}
	
	/** 特定日項目NO毎の特定日日数を返す */
	public int getSpecDays (int no) {
		
		return this.specificDay.getSpecificDateAttrSheets().stream()
							.filter(s -> s.getSpecificDateItemNo().v() == no)
							.findFirst()
							.map(s -> s.getSpecificDateAttr() == SpecificDateAttr.USE ? 1 : 0)
							.orElse(0);
	}
}
