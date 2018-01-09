package nts.uk.ctx.at.request.app.command.application.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateApplicationCommand {
	/**
	 * 申請ID
	 */
	private String applicationID;
	
	/**
	 * 申請理由ID
	 */
	private String appReasonID;
	
	/**
	 * 事前事後区分
	 */
	private int prePostAtr; 

	/**
	 * 入力日
	 */
	private GeneralDateTime inputDate; 
	
	/**
	 * 入力者
	 */
	private String enteredPersonSID;
	
	/**
	 * 差戻し理由
	 */
	private String reversionReason; 
	
	/**
	 * 申請日
	 */
	private GeneralDate applicationDate; 
	
	/**
	 * 申請理由
	 */
	private String applicationReason;
	/**
	 * 申請種類
	 */
	private int applicationType;
	
	/**
	 * 申請者
	 */
	private String  applicantSID;
	
	/**
	 * 予定反映不可理由
	 */
	private int reflectPlanScheReason;
	
    /**
     * 予定反映日時
     */
	private GeneralDate reflectPlanTime;
	
	/**
	 * 予定反映状態
	 */
	private int reflectPlanState;
	
	/**
	 * 予定強制反映
	 */
	private int reflectPlanEnforce;
	
	/**
	 * 実績反映不可理由
	 */
	private int  reflectPerScheReason;
	
	/**
	 * 実績反映日時
	 */
	private GeneralDate reflectPerTime;
	
	/**
	 * 予定反映状態
	 */
	private int reflectPerState;
	
	/**
	 * 実績強制反映
	 */
	private int reflectPerEnforce;
	/**
	 * 申請終了日
	 */
	private GeneralDate startDate;
	
	/**
	 * 申請開始日
	 */
	private GeneralDate endDate;
}
