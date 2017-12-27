package nts.uk.ctx.at.request.app.find.application.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@Data
@AllArgsConstructor
@Builder
public class ApplicationDto_New {
	
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
	
	// 予定反映状態
	private Integer reflectPerState;
	
	// 実績強制反映
	private Integer reflectPerEnforce;
	
	private String startDate;
	
	private String endDate;
	
	
}
