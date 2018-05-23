package nts.uk.ctx.at.record.pub.remainnumber.annualleave;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CompensatoryDayOffManaDataPub {
	// ID	
	private String comDayOffID;
	
	// 社員ID
	private String sID;
	
	private String cID;
	
	// 代休日
	private CompensatoryDayoffDatePub dayOffDate;
	
	// 必要日数
	private Double requireDays;
	
	// 必要時間数
	private Integer requiredTimes;
	
	// 未相殺日数
	private Double remainDays;
	
	// 未相殺時間数
	private Integer remainTimes;
}
