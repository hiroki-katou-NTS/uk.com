package nts.uk.ctx.at.request.app.find.application.common;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.find.application.common.appapprovalphase.AppApprovalPhaseDto;
import nts.uk.ctx.at.request.dom.application.common.Application;

@Value
public class ApplicationDto {
	/**
	 * 会社ID
	 */
	private String companyID;
	
	/**
	 * 申請ID
	 */
	private String applicationID;
	
	/**
	 * 事前事後区分
	 */
	private int prePostAtr; 


	/**
	 * 入力日
	 */
	private GeneralDate inputDate; 
	
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
	
	private GeneralDate startDate;
	
	private GeneralDate endDate;
	
	private List<AppApprovalPhaseDto> listphase;
	
	public static ApplicationDto fromDomain(Application domain) {
		return new ApplicationDto(
				domain.getCompanyID(),
				domain.getApplicationID(),
				domain.getPrePostAtr().value,
				domain.getInputDate(), 
				domain.getEnteredPersonSID(), 
				domain.getReversionReason().v(), 
				domain.getApplicationDate(), 
				domain.getApplicationReason().v(),
				domain.getApplicationType().value, 
				domain.getApplicantSID(), 
				domain.getReflectPlanScheReason().value, 
				domain.getReflectPlanTime(), 
				domain.getReflectPlanState().value, 
				domain.getReflectPlanEnforce().value, 
				domain.getReflectPerScheReason().value, 
				domain.getReflectPerTime(), 
				domain.getReflectPerState().value, 
				domain.getReflectPerEnforce().value,
				domain.getStartDate(),
				domain.getEndDate(),
				domain.getListPhase().stream().map(x -> AppApprovalPhaseDto.fromDomain(x)).collect(Collectors.toList())
				);
	}

}
