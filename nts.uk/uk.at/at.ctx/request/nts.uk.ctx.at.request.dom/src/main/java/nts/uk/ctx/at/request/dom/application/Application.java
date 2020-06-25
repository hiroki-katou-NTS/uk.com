package nts.uk.ctx.at.request.dom.application;

import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.stamp.StampRequestMode;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppStandardReasonCode;

/**
 * refactor 4
 * 申請
 * @author Doan Duy Hung
 *
 */
@Getter
public class Application implements DomainAggregate {
	
	/**
	 * ID
	 */
	private String applicationID;
	
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
	private ApplicationType applicationType;
	
	/**
	 * 申請日
	 */
	private GeneralDate appDate;
	
	/**
	 * 入力者
	 */
	private String enteredPerson;
	
	/**
	 * 入力日
	 */
	private GeneralDate inputDate;
	
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
	private Optional<GeneralDate> opApplicationStartDate;
	 
	/**
	 * 申請終了日
	 */
	private Optional<GeneralDate> opApplicationEndDate;
	
	/**
	 * 申請理由
	 */
	private Optional<AppReason> opAppReason;
	
	/**
	 * 定型理由
	 */
	private Optional<AppStandardReasonCode> opAppStandardReasonCD;
	
}
