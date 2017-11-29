package nts.uk.ctx.at.request.app.find.application.common;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.app.find.application.common.appapprovalphase.AppApprovalPhaseDto;
import nts.uk.ctx.at.request.app.find.application.common.approvalframe.ApprovalFrameDto;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.ReflectPerScheReason;
import nts.uk.ctx.at.request.dom.application.ReflectPlanPerEnforce;
import nts.uk.ctx.at.request.dom.application.ReflectPlanPerState;
import nts.uk.ctx.at.request.dom.application.ReflectPlanScheReason;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalAtr;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalForm;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationDto {
	private Long version;
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
	private String inputDate; 
	
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
	private String applicationDate; 
	
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
	private String reflectPlanTime;
	
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
	private String reflectPerTime;
	
	/**
	 * 予定反映状態
	 */
	private int reflectPerState;
	
	/**
	 * 実績強制反映
	 */
	private int reflectPerEnforce;
	
	private String startDate;
	
	private String endDate;
	
	private List<AppApprovalPhaseDto> listPhase;
	
	public static ApplicationDto fromDomain(Application domain) {
		return new ApplicationDto(
				domain.getVersion(),
				domain.getCompanyID(),
				domain.getApplicationID(),
				domain.getPrePostAtr().value,
				domain.getInputDate() == null ? null :domain.getInputDate().toString(), 
				domain.getEnteredPersonSID(), 
				domain.getReversionReason().v(), 
				domain.getApplicationDate() == null ? null :domain.getApplicationDate().toString(), 
				domain.getApplicationReason().v(),
				domain.getApplicationType().value, 
				domain.getApplicantSID(), 
				domain.getReflectPlanScheReason().value, 
				domain.getReflectPlanTime() == null ? null : domain.getReflectPlanTime().toString(), 
				domain.getReflectPlanState().value, 
				domain.getReflectPlanEnforce().value, 
				domain.getReflectPerScheReason().value, 
				domain.getReflectPerTime() == null ? null : domain.getReflectPerTime().toString(), 
				domain.getReflectPerState().value, 
				domain.getReflectPerEnforce().value,
				domain.getStartDate() == null ? null :domain.getStartDate().toString(),
				domain.getEndDate() == null ? null :domain.getEndDate().toString(),
				domain.getListPhase() == null ? null: domain.getListPhase().stream().map(x -> AppApprovalPhaseDto.fromDomain(x)).collect(Collectors.toList())
				);
	}
	public static Application toEntity(ApplicationDto entity) {
		Application app = new Application(entity.getCompanyID(), 
				entity.getApplicationID(), 
				EnumAdaptor.valueOf(entity.getPrePostAtr(), PrePostAtr.class), 
				entity.getInputDate() == null?null :GeneralDateTime.now(), 
				entity.getEnteredPersonSID(), 
				new AppReason(entity.getReversionReason()), 
				entity.getApplicationDate() == null?null :GeneralDate.fromString(entity.getApplicationDate(), "yyyy/MM/dd"),   
				new AppReason(entity.getApplicationReason()), 
				EnumAdaptor.valueOf(entity.getApplicationType(), ApplicationType.class), 
				entity.getApplicantSID(), 
				EnumAdaptor.valueOf(entity.getReflectPlanScheReason(), ReflectPlanScheReason.class),
				entity.getReflectPlanTime() == null?null :GeneralDate.fromString(entity.getReflectPlanTime(), "yyyy/MM/dd"), 
				EnumAdaptor.valueOf(entity.getReflectPlanState(), ReflectPlanPerState.class), 
				EnumAdaptor.valueOf(entity.getReflectPlanEnforce(), ReflectPlanPerEnforce.class), 
				EnumAdaptor.valueOf(entity.getReflectPerScheReason(), ReflectPerScheReason.class), 
				entity.getReflectPerTime() == null?null :GeneralDate.fromString(entity.getReflectPerTime(), "yyyy/MM/dd"), 
				EnumAdaptor.valueOf(entity.getReflectPerState(), ReflectPlanPerState.class), 
				EnumAdaptor.valueOf(entity.getReflectPerEnforce(), ReflectPlanPerEnforce.class), 
				entity.getStartDate() == null?null :GeneralDate.fromString(entity.getStartDate(), "yyyy/MM/dd"), 
				entity.getEndDate() == null?null :GeneralDate.fromString(entity.getEndDate(), "yyyy/MM/dd"), 
				entity.getListPhase() == null ? null: entity.getListPhase().stream().map(x -> AppApprovalPhaseDto.toEntity(x)).collect(Collectors.toList())
				);//entity.getListPhase()
		app.setVersion(entity.version);
		return app;
	}
	
	

}
