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
public class AppWorkChange_Old extends AggregateRoot
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
    private Integer excludeHolidayAtr;
    
    /**
    * 勤務を変更する
    */
    private Integer workChangeAtr;
    
    /**
    * 勤務直行1
    */
    private Integer goWorkAtr1;
    
    /**
    * 勤務直帰1
    */
    private Integer backHomeAtr1;
    
    /**
    * 休憩時間開始1
    */
    private Integer breakTimeStart1;
    
    /**
    * 休憩時間終了1
    */
    private Integer breakTimeEnd1;
    
    /**
    * 勤務時間開始1
    */
    private Integer workTimeStart1;
    
    /**
    * 勤務時間終了1
    */
    private Integer workTimeEnd1;
    
    /**
    * 勤務時間開始2
    */
    private Integer workTimeStart2;
    
    /**
    * 勤務時間終了2
    */
    private Integer workTimeEnd2;
    
    /**
    * 勤務直行2
    */
    private Integer goWorkAtr2;
    
    /**
    * 勤務直帰2
    */
    private Integer backHomeAtr2;
    
    /**
     * 勤務種類名
     */
    private String workTypeName;
    
    /**
     * 就業時間帯名
     */
    private String workTimeName;
    
	public static AppWorkChange_Old createFromJavaType(String cid, String appId, String workTypeCd, String workTimeCd,
			Integer excludeHolidayAtr, Integer workChangeAtr, Integer goWorkAtr1, Integer backHomeAtr1, Integer breakTimeStart1,
			Integer breakTimeEnd1, Integer workTimeStart1, Integer workTimeEnd1, Integer workTimeStart2, Integer workTimeEnd2,
			Integer goWorkAtr2, Integer backHomeAtr2) {
		return new AppWorkChange_Old(cid, appId, workTypeCd, workTimeCd, excludeHolidayAtr, workChangeAtr, goWorkAtr1,
				backHomeAtr1, breakTimeStart1, breakTimeEnd1, workTimeStart1, workTimeEnd1, workTimeStart2,
				workTimeEnd2, goWorkAtr2, backHomeAtr2, null, null);
	}

}
