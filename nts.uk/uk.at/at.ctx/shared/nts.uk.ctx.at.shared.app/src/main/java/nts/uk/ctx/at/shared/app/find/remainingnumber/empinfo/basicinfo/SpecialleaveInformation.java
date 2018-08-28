package nts.uk.ctx.at.shared.app.find.remainingnumber.empinfo.basicinfo;

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
	private Integer grantDays;
	
	//	特休付与テーブルコード
	private String grantTable;
	
	// 入社年月日
	private GeneralDate entryDate;
	
	// 退職年月日
	private GeneralDate retireDate;
	
	// 年休付与基準日
	private Integer year;

	public SpecialleaveInformation(String sid, int spLeaveCD, GeneralDate grantDate, int appSet, String grantTable,
			Integer grantDays, GeneralDate entryDate, GeneralDate retireDate, Integer year) {
		super();
		this.sid = sid;
		this.spLeaveCD = spLeaveCD;
		this.grantDate = grantDate;
		this.appSet = appSet;
		this.grantDays = grantDays;
		this.grantTable = grantTable;
		this.entryDate = entryDate;
		this.retireDate = retireDate;
		this.year = year;
	}

}
