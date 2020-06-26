package nts.uk.ctx.at.request.dom.application;

import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.dom.application.stamp.StampRequestMode;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppStandardReasonCode;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.申請.申請
 * @author Doan Duy Hung
 *
 */
@Getter
public class Application implements DomainAggregate {
	
	/**
	 * ID
	 */
	private String appID;
	
	/**
	 * 事前事後区分
	 */
	private PrePostAtr prePostAtr;
	
	/**
	 * 申請者
	 */
	private String employeeID;
	
	/**
	 * 申請種類
	 */
	private ApplicationType appType;
	
	/**
	 * 申請日
	 */
	private ApplicationDate appDate;
	
	/**
	 * 入力者
	 */
	private String enteredPerson;
	
	/**
	 * 入力日
	 */
	private GeneralDateTime inputDate;
	
	/**
	 * 反映状態
	 */
	private ReflectionStatus reflectionStatus;
	
	/**
	 * 打刻申請モード
	 */
	private Optional<StampRequestMode> opStampRequestMode;
	
	/**
	 * 差戻し理由
	 */
	private Optional<ReasonForReversion> opReversionReason;
	
	/**
	 * 申請開始日
	 */
	private Optional<ApplicationDate> opAppStartDate;
	 
	/**
	 * 申請終了日
	 */
	private Optional<ApplicationDate> opAppEndDate;
	
	/**
	 * 申請理由
	 */
	private Optional<AppReason> opAppReason;
	
	/**
	 * 定型理由
	 */
	private Optional<AppStandardReasonCode> opAppStandardReasonCD;
	
}
