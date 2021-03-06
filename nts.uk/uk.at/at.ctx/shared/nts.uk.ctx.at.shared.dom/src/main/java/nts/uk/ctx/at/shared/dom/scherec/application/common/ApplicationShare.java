package nts.uk.ctx.at.shared.dom.scherec.application.common;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDateTime;

/**
 * @author thanh_nx
 *
 *         申請(反映用)
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class ApplicationShare {

	private int version;

	/**
	 * ID
	 */
	private String appID;

	/**
	 * 事前事後区分
	 */
	private PrePostAtrShare prePostAtr;

	/**
	 * 申請者
	 */
	private String employeeID;

	/**
	 * 申請種類
	 */
	private ApplicationTypeShare appType;

	/**
	 * 申請日
	 */
	private ApplicationDateShare appDate;

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
	//private ReflectionStatusShare reflectionStatus;

	/**
	 * 打刻申請モード
	 */
	@Setter
	private Optional<StampRequestModeShare> opStampRequestMode;

	/**
	 * 申請開始日
	 */
	@Setter
	private Optional<ApplicationDateShare> opAppStartDate;

	/**
	 * 申請終了日
	 */
	@Setter
	private Optional<ApplicationDateShare> opAppEndDate;

	public ApplicationShare(int version, String appID, PrePostAtrShare prePostAtr, String employeeID,
			ApplicationTypeShare appType, ApplicationDateShare appDate, String enteredPerson, GeneralDateTime inputDate) {
		this.version = version;
		this.appID = appID;
		this.prePostAtr = prePostAtr;
		this.employeeID = employeeID;
		this.appType = appType;
		this.appDate = appDate;
		this.enteredPersonID = enteredPerson;
		this.inputDate = inputDate;
		this.opStampRequestMode = Optional.empty();
		this.opAppStartDate = Optional.empty();
		this.opAppEndDate = Optional.empty();

	}

	public ApplicationShare(ApplicationShare application) {
		this(application.getVersion(), application.getAppID(), application.getPrePostAtr(), application.getEmployeeID(),
				application.getAppType(), application.getAppDate(), application.getEnteredPersonID(),
				application.getInputDate());
		this.opStampRequestMode = application.getOpStampRequestMode();
		this.opAppStartDate = application.getOpAppStartDate();
		this.opAppEndDate = application.getOpAppEndDate();
	}
}
