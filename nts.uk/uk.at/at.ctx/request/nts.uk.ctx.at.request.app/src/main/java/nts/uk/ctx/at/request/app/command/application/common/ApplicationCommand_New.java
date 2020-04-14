package nts.uk.ctx.at.request.app.command.application.common;
/*import nts.uk.ctx.at.request.app.find.application.common.ApplicationDto_New;
import nts.uk.ctx.at.request.app.find.application.common.ApplicationDto_New.ApplicationDto_NewBuilder;*/
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicationCommand_New {
//	private static final String DATE_FORMAT = "yyyy/MM/dd";
//	private static final String DATE_TIME_FORMAT = "yyyy/MM/dd HH:mm";
	private Long version;
	
	// 会社ID
	private String companyID;
	
	// 申請ID
	private String applicationID;
	
	// 事前事後区分
	private Integer prePostAtr; 

	// 入力日
	private String inputDate; 
	
	// 入力者
	private String enteredPersonSID;
	
	// 差戻し理由
	private String reversionReason; 
	
	// 申請日
	private String applicationDate; 
	
	// 申請理由
	private String appReasonID;
	private String applicationReason;
	
	// 申請種類
	private Integer applicationType;
	
	// 申請者
	private String  applicantSID;
	
	// 予定反映不可理由
	private Integer reflectPlanScheReason;
    
    // 予定反映日時
	private String reflectPlanTime;
	
	// 予定反映状態
	private Integer reflectPlanState;
	
	// 予定強制反映
	private Integer reflectPlanEnforce;

	// 実績反映不可理由
	private Integer  reflectPerScheReason;
	
	// 実績反映日時
	private String reflectPerTime;
	
	// 予定反映状態=comment line71???
	private Integer reflectPerState;
	
	// 実績強制反映
	private Integer reflectPerEnforce;
	
	private String startDate;
	
	private String endDate;
}
