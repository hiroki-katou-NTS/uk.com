package nts.uk.ctx.at.record.dom.statutoryworkinghours.monthly;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.MonthlyUnit;

@AllArgsConstructor
@Getter
public class MonthlyFlexStatutoryLaborTimeList {

	//所定時間:月単位（List）
	private List<MonthlyUnit> specifiedList;
	//法定時間:月単位（List）
	private List<MonthlyUnit> statutoryList;

	
}
