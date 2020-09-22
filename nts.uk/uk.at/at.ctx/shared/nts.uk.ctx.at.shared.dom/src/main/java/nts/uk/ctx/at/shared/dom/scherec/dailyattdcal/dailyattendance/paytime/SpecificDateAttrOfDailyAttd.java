package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.paytime;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.objecttype.DomainObject;

/**
 * 日別勤怠の特定日区分
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).日の勤怠計算.日別勤怠.加給時間.日別勤怠の特定日区分
 * @author tutk
 *
 */
@Getter
@NoArgsConstructor
public class SpecificDateAttrOfDailyAttd implements DomainObject {
	//特定日区分
	private List<SpecificDateAttrSheet> specificDateAttrSheets;

	public SpecificDateAttrOfDailyAttd(List<SpecificDateAttrSheet> specificDateAttrSheets) {
		super();
		this.specificDateAttrSheets = specificDateAttrSheets;
	}
	
	public List<SpecificDateItemNo> getUseNo(){
		return this.specificDateAttrSheets.stream().filter(tc -> tc.getSpecificDateAttr().isUse()).map(ts -> ts.getSpecificDateItemNo()).collect(Collectors.toList());
	}
}
