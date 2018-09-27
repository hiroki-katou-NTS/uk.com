package nts.uk.ctx.pereg.app.find.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@Getter
@Setter
@NoArgsConstructor
public class SpecialleaveInformation {
	
	// 社員ID
	private String sid;
	
	// 特別休暇コード
	private int spLeaveCD;

	// 特休付与基準日
	private GeneralDate grantDate;
	
	//	適用設定
	private int appSet;
	
	//	付与日数 
	private Double grantDays;
	
	//	特休付与テーブルコード
	private String grantTable;
	
	// 入社年月日
	private GeneralDate entryDate;
	
	// 年休付与基準日
	private GeneralDate yearRefDate;

	public SpecialleaveInformation(String sid, int spLeaveCD, GeneralDate grantDate, int appSet, String grantTable,
			Double grantDays, GeneralDate entryDate, GeneralDate yearRefDate) {
		super();
		this.sid = sid;
		this.spLeaveCD = spLeaveCD;
		this.grantDate = grantDate;
		this.appSet = appSet;
		this.grantDays = grantDays;
		this.grantTable = grantTable;
		this.entryDate = entryDate;
		this.yearRefDate = yearRefDate;
	}

}
