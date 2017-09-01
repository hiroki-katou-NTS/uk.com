package nts.uk.ctx.at.request.dom.application.common;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.GeneralDate;

/**
 * domain : 申請 (application)
 * @author tutk
 *
 */
@Getter
@AllArgsConstructor
public class Application extends DomainObject{
	/**
	 * 会社ID
	 */
	private final String CompanyID;
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
	private GeneralDate inputDate;
	/**
	 * 入力者
	 */
	@Setter
	private String enteredPersonSID;
	/**
	 * 差戻し理由
	 */
	private ApplicationReason reversionReason;
	/**
	 * 申請日
	 */
	private GeneralDate applicationDate;
	
	/**
	 * 申請理由
	 */
	private ApplicationReason applicationReason;
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
	private BigDecimal reflectPlanTime;
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
	private BigDecimal reflectPerTime;
	/**
	 * 実績反映状態
	 */
	@Setter
	private ReflectPlanPerState reflectPerState;
	/**
	 * 実績強制反映
	 */
	private ReflectPlanPerEnforce reflectPerEnforce;
	
	public static Application createFromJavaType(
			String companyID,
			String applicationID,
			int prePostAtr,
			GeneralDate inputDate, 
			String enteredPersonSID, 
			String reversionReason, 
			GeneralDate applicationDate,
			String applicationReason, 
			int applicationType, 
			String applicantSID,
			int reflectPlanScheReason, 
			BigDecimal reflectPlanTime,
			int reflectPlanState, 
			int reflectPlanEnforce,
			int reflectPerScheReason, 
			BigDecimal reflectPerTime, 
			int reflectPerState,
			int reflectPerEnforce) {
		return new  Application(
				companyID, 
				applicationID, 
				EnumAdaptor.valueOf(prePostAtr,PrePostAtr.class),
				inputDate, 
				enteredPersonSID, 
				new ApplicationReason(reversionReason), 
				applicationDate, 
				new ApplicationReason(applicationReason), 
				EnumAdaptor.valueOf(applicationType,ApplicationType.class), 
				applicantSID, 
				EnumAdaptor.valueOf(reflectPlanScheReason,ReflectPlanScheReason.class), 
				reflectPlanTime, 
				EnumAdaptor.valueOf(reflectPlanState,ReflectPlanPerState.class), 
				EnumAdaptor.valueOf(reflectPlanEnforce,ReflectPlanPerEnforce.class), 
				EnumAdaptor.valueOf(reflectPerScheReason,ReflectPerScheReason.class), 
				reflectPerTime, 
				EnumAdaptor.valueOf(reflectPerState,ReflectPlanPerState.class), 
				EnumAdaptor.valueOf(reflectPerEnforce,ReflectPlanPerEnforce.class));
	}
	
	/**
	  * change value of RelectState
	  * @param reflectPerState
	  */
	 public void changeReflectState(int reflectPerState) {
		 this.reflectPerState = reflectPlanState;
	 }
}
