package nts.uk.ctx.at.request.app.find.application.workchange;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChange_Old;

/**
* 勤務変更申請
*/
@AllArgsConstructor
@Value
public class AppWorkChangeDto_Old
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
    
    private Long version;
    
    /**
     * 勤務種類名
     */
    private String workTypeName;
    
    /**
     * 就業時間帯名
     */
    private String workTimeName;

	public static AppWorkChangeDto_Old fromDomain(AppWorkChange_Old domain) {
		return new AppWorkChangeDto_Old(domain.getCid(), domain.getAppId(), domain.getWorkTypeCd(), domain.getWorkTimeCd(),
				domain.getExcludeHolidayAtr(), domain.getWorkChangeAtr(), domain.getGoWorkAtr1(),
				domain.getBackHomeAtr1(), domain.getBreakTimeStart1(), domain.getBreakTimeEnd1(),
				domain.getWorkTimeStart1(), domain.getWorkTimeEnd1(), domain.getWorkTimeStart2(),
				domain.getWorkTimeEnd2(), domain.getGoWorkAtr2(), domain.getBackHomeAtr2(), domain.getVersion(),
				domain.getWorkTypeName(), domain.getWorkTimeName());
	}

}
