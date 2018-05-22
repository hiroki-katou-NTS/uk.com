package nts.uk.ctx.at.record.pub.workrecord.operationsetting;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FormatPerformanceExport {
	
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * フォーマット種類 
    * 権限 : 0 - authority
    * 勤務種別 : 1 - businessType
    */
    private int settingUnitType;

	public FormatPerformanceExport(String cid, int settingUnitType) {
		super();
		this.cid = cid;
		this.settingUnitType = settingUnitType;
	}

}
