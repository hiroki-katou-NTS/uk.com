package nts.uk.ctx.pereg.app.find.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Setter
@Getter
@NoArgsConstructor
public class AnnLeaEmpBasicInfo {
	
	// 社員ID
	private String sid;
	
	// 年休付与基準日
	private GeneralDate grantDate;
	
	// 年休付与テーブル
	private String grantTable;
	
	// 労働条件の期間
	private DatePeriod periodCond;
	
	// 契約時間
	private Integer contractTime;
	
	// 入社年月日
	private GeneralDate entryDate;
	
	public AnnLeaEmpBasicInfo(String sid, GeneralDate grantDate, String grantTable, DatePeriod periodCond,
			Integer contractTime, GeneralDate entryDate) {
		super();
		this.sid = sid;
		this.grantDate = grantDate;
		this.grantTable = grantTable;
		this.periodCond = periodCond;
		this.contractTime = contractTime;
		this.entryDate = entryDate;
	}
	
	
}
