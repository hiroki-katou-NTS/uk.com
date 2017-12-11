package nts.uk.ctx.at.request.dom.application;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhase;

/**
 * domain : 申請 (application)
 * @author tutk
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Application extends AggregateRoot{
	/**
	 * 会社ID
	 */
	private String CompanyID;
	/**
	 * 申請ID
	 */
	private String applicationID;
	/**
	 * 事前事後区分
	 */
	private PrePostAtr prePostAtr;
	/**
	 * 入力日
	 */
	private GeneralDateTime inputDate;
	/**
	 * 入力者
	 */
	@Setter
	private String enteredPersonSID;
	/**
	 * 差戻し理由
	 */
	@Setter
	private AppReason reversionReason;
	/**
	 * 申請日
	 */
	@Setter
	private GeneralDate applicationDate;
	
	/**
	 * 申請理由
	 */
	@Setter
	private AppReason applicationReason;
	/**
	 * 申請種類
	 */
	private ApplicationType applicationType;
	/**
	 * 申請者
	 */
	@Setter
	private String applicantSID;
	/**
	 * 予定反映不可理由
	 */
	private ReflectPlanScheReason reflectPlanScheReason;
    /**
     * 予定反映日時
     */
	private GeneralDate reflectPlanTime;
	/**
	 * 予定反映状態
	 */
	@Setter
	private ReflectPlanPerState reflectPlanState;
	/**
	 * 予定強制反映
	 */
	private ReflectPlanPerEnforce reflectPlanEnforce;
	/**
	 * 実績反映不可理由
	 */
	private ReflectPerScheReason  reflectPerScheReason;
	/**
	 * 実績反映日時
	 */
	private GeneralDate reflectPerTime;
	/**
	 * 実績反映状態
	 */
	@Setter
	private ReflectPlanPerState reflectPerState;
	/**
	 * 実績強制反映
	 */
	private ReflectPlanPerEnforce reflectPerEnforce;
	
	/**
	 * 申請終了日
	 */
	@Setter
	private GeneralDate startDate;
	
	/**
	 * 申請開始日
	 */
	@Setter
	private GeneralDate endDate;
	
	/**
	 * List Phase
	 */
	@Setter
	private List<AppApprovalPhase> listPhase;
	
	public static Application createFromJavaType(
			String companyID,
			int prePostAtr,
			GeneralDateTime inputDate, 
			String enteredPersonSID, 
			String reversionReason, 
			GeneralDate applicationDate,
			String applicationReason, 
			int applicationType, 
			String applicantSID,
			int reflectPlanScheReason, 
			GeneralDate reflectPlanTime,
			int reflectPlanState, 
			int reflectPlanEnforce,
			int reflectPerScheReason, 
			GeneralDate reflectPerTime, 
			int reflectPerState,
			int reflectPerEnforce,
			GeneralDate startDate,
			GeneralDate endDate,
			List<AppApprovalPhase> listPhase) {
		return new  Application(
				companyID, 
				UUID.randomUUID().toString(),
				EnumAdaptor.valueOf(prePostAtr,PrePostAtr.class),
				inputDate, 
				enteredPersonSID, 
				new AppReason(reversionReason), 
				applicationDate, 
				new AppReason(applicationReason), 
				EnumAdaptor.valueOf(applicationType,ApplicationType.class), 
				applicantSID, 
				EnumAdaptor.valueOf(reflectPlanScheReason,ReflectPlanScheReason.class), 
				reflectPlanTime, 
				EnumAdaptor.valueOf(reflectPlanState,ReflectPlanPerState.class), 
				EnumAdaptor.valueOf(reflectPlanEnforce,ReflectPlanPerEnforce.class), 
				EnumAdaptor.valueOf(reflectPerScheReason,ReflectPerScheReason.class), 
				reflectPerTime, 
				EnumAdaptor.valueOf(reflectPerState,ReflectPlanPerState.class), 
				EnumAdaptor.valueOf(reflectPerEnforce,ReflectPlanPerEnforce.class),
				startDate,
				endDate,
				listPhase);
	}
	
	/**
	  * change value of RelectState
	  * @param reflectPerState
	  */
	 public void changeReflectState(int reflectPerState) {
		 this.reflectPerState =  EnumAdaptor.valueOf(reflectPerState,ReflectPlanPerState.class);
	 }
	 /**
	  * change value of reversionReason
	  * @param reversionReason
	  */
	 public void changeReversionReason(AppReason reversionReason) {
		 this.reversionReason = reversionReason;
	 }
	 
	 /**
	  * change value of reflectPerTime
	  * @param reflectPerTime
	  */
	 public void changeReflectPerTime(GeneralDate reflectPerTime) {
		 this.reflectPerTime = reflectPerTime;
	 }
	 /**
	  * change value of applicationReason
	  * @param applicationReason
	  */
	 public void changeApplicationReason(AppReason applicationReason) {
		 this.applicationReason = applicationReason;
	 }
	 
	 /**
	  * change value of applicationReason
	  * @param applicationReason
	  */
	 public void changeListPhase(List<AppApprovalPhase> listPhase) {
		 this.listPhase = listPhase;
	 }
}
