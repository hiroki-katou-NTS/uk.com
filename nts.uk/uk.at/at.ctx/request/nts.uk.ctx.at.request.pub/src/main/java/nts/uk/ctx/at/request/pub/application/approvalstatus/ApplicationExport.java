package nts.uk.ctx.at.request.pub.application.approvalstatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

@Data
@Getter
@AllArgsConstructor
public class ApplicationExport {
	/**
	 * ID
	 */
	private String appID;
	
	/**
	 * 事前事後区分
	 */
	private int prePostAtr;
	
	/**
	 * 申請者
	 */
	private String employeeID;
	
	/**
	 * 申請種類
	 */
	private int appType;
	
	/**
	 * 申請日
	 */
	private GeneralDate appDate;
	
	/**
	 * 入力者
	 */
	private String enteredPersonID;
	
	/**
	 * 入力日
	 */
	private GeneralDateTime inputDate;
	
	/**
	 * 反映状態
	 */
	private ReflectionStatusExport reflectionStatus;
	
	/**
	 * 打刻申請モード
	 */
	private Integer opStampRequestMode;
	
	/**
	 * 差戻し理由
	 */
	private String opReversionReason;
	
	/**
	 * 申請開始日
	 */
	private GeneralDate opAppStartDate;
	 
	/**
	 * 申請終了日
	 */
	private GeneralDate opAppEndDate;
	
	/**
	 * 申請理由
	 */
	private String opAppReason;
	
	/**
	 * 定型理由
	 */
	private Integer opAppStandardReasonCD;

}
