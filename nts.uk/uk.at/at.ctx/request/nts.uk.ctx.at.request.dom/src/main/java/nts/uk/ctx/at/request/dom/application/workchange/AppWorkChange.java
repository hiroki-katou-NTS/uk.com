package nts.uk.ctx.at.request.dom.application.workchange;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;

/**
* 勤務変更申請
*/
@AllArgsConstructor
@Getter
@Setter
public class AppWorkChange extends AggregateRoot
{
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * 申請ID
    */
    private String appId;
    
    /**
    * 勤務種類コード
    */
    private String workTypeCd;
    
    /**
    * 就業時間帯コード
    */
    private String workTimeCd;
    
    /**
    * 休日を除外する
    */
    private int excludeHolidayAtr;
    
    /**
    * 勤務を変更する
    */
    private int workChangeAtr;
    
    /**
    * 勤務直行1
    */
    private int goWorkAtr1;
    
    /**
    * 勤務直帰1
    */
    private int backHomeAtr1;
    
    /**
    * 休憩時間開始1
    */
    private int breakTimeStart1;
    
    /**
    * 休憩時間終了1
    */
    private int breakTimeEnd1;
    
    /**
    * 勤務時間開始1
    */
    private int workTimeStart1;
    
    /**
    * 勤務時間終了1
    */
    private int workTimeEnd1;
    
    /**
    * 勤務時間開始2
    */
    private int workTimeStart2;
    
    /**
    * 勤務時間終了2
    */
    private int workTimeEnd2;
    
    /**
    * 勤務直行2
    */
    private int goWorkAtr2;
    
    /**
    * 勤務直帰2
    */
    private int backHomeAtr2;
    
    /**
     * 勤務種類名
     */
    private String workTypeName;
    
    /**
     * 就業時間帯名
     */
    private String workTimeName;
    
	public static AppWorkChange createFromJavaType(String cid, String appId, String workTypeCd, String workTimeCd,
			int excludeHolidayAtr, int workChangeAtr, int goWorkAtr1, int backHomeAtr1, int breakTimeStart1,
			int breakTimeEnd1, int workTimeStart1, int workTimeEnd1, int workTimeStart2, int workTimeEnd2,
			int goWorkAtr2, int backHomeAtr2) {
		return new AppWorkChange(cid, appId, workTypeCd, workTimeCd, excludeHolidayAtr, workChangeAtr, goWorkAtr1,
				backHomeAtr1, breakTimeStart1, breakTimeEnd1, workTimeStart1, workTimeEnd1, workTimeStart2,
				workTimeEnd2, goWorkAtr2, backHomeAtr2, null, null);
	}

}
