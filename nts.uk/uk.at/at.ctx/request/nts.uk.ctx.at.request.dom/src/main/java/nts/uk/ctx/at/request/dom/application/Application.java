package nts.uk.ctx.at.request.dom.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.request.dom.application.stamp.StampRequestMode;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppStandardReasonCode;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.申請.申請
 * @author Doan Duy Hung
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class Application implements DomainAggregate {
	
	private int version;
	
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
	private String enteredPersonID;
	
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
	@Setter
	private Optional<StampRequestMode> opStampRequestMode;
	
	/**
	 * 差戻し理由
	 */
	@Setter
	private Optional<ReasonForReversion> opReversionReason;
	
	/**
	 * 申請開始日
	 */
	@Setter
	private Optional<ApplicationDate> opAppStartDate;
	 
	/**
	 * 申請終了日
	 */
	@Setter
	private Optional<ApplicationDate> opAppEndDate;
	
	/**
	 * 申請理由
	 */
	@Setter
	private Optional<AppReason> opAppReason;
	
	/**
	 * 定型理由
	 */
	@Setter
	private Optional<AppStandardReasonCode> opAppStandardReasonCD;

	public Application(int version, String appID, PrePostAtr prePostAtr, String employeeID, ApplicationType appType,
			ApplicationDate appDate, String enteredPerson, GeneralDateTime inputDate,
			ReflectionStatus reflectionStatus) {
		super();
		this.version = version;
		this.appID = appID;
		this.prePostAtr = prePostAtr;
		this.employeeID = employeeID;
		this.appType = appType;
		this.appDate = appDate;
		this.enteredPersonID = enteredPerson;
		this.inputDate = inputDate;
		this.reflectionStatus = reflectionStatus;
		this.opStampRequestMode = Optional.empty();
		this.opReversionReason = Optional.empty();
		this.opAppStartDate = Optional.empty();
		this.opAppEndDate = Optional.empty();
		this.opAppReason = Optional.empty();
		this.opAppStandardReasonCD = Optional.empty();
		
	}
	
	public Application(Application application) {
		this(application.getVersion(),
				application.getAppID(), 
				application.getPrePostAtr(), 
				application.getEmployeeID(), 
				application.getAppType(), 
				application.getAppDate(), 
				application.getEnteredPersonID(), 
				application.getInputDate(), 
				application.getReflectionStatus());
		this.opStampRequestMode = application.getOpStampRequestMode();
		this.opReversionReason = application.getOpReversionReason();
		this.opAppStartDate = application.getOpAppStartDate();
		this.opAppEndDate = application.getOpAppEndDate();
		this.opAppReason = application.getOpAppReason();
		this.opAppStandardReasonCD = application.getOpAppStandardReasonCD();
	}
	
	public static Application createFromNew(PrePostAtr prePostAtr, String employeeID, ApplicationType appType,
			ApplicationDate appDate, String enteredPerson, Optional<StampRequestMode> opStampRequestMode,
			Optional<ReasonForReversion> opReversionReason, Optional<ApplicationDate> opAppStartDate,
			Optional<ApplicationDate> opAppEndDate, Optional<AppReason> opAppReason,
			Optional<AppStandardReasonCode> opAppStandardReasonCD) {
		List<ReflectionStatusOfDay> listReflectionStatusOfDay = new ArrayList<>();
		if(opAppStartDate.isPresent() && opAppEndDate.isPresent()) {
			GeneralDate startDate = opAppStartDate.get().getApplicationDate();
			GeneralDate endDate = opAppEndDate.get().getApplicationDate();
			for(GeneralDate loopDate = startDate; loopDate.beforeOrEquals(endDate); loopDate = loopDate.addDays(1)) {
				listReflectionStatusOfDay.add(ReflectionStatusOfDay.createNew(ReflectedState.NOTREFLECTED, ReflectedState.NOTREFLECTED, loopDate));
			}
		} else {
			listReflectionStatusOfDay.add(ReflectionStatusOfDay.createNew(ReflectedState.NOTREFLECTED, ReflectedState.NOTREFLECTED, appDate.getApplicationDate()));
		}
		
		Application application = new Application(
				0,
				IdentifierUtil.randomUniqueId(), 
				prePostAtr, 
				employeeID, 
				appType, 
				appDate, 
				enteredPerson, 
				GeneralDateTime.now(), 
				new ReflectionStatus(listReflectionStatusOfDay));
		application.setOpStampRequestMode(opStampRequestMode);
		application.setOpReversionReason(opReversionReason);
		application.setOpAppStartDate(opAppStartDate);
		application.setOpAppEndDate(opAppEndDate);
		application.setOpAppReason(opAppReason);
		application.setOpAppStandardReasonCD(opAppStandardReasonCD);
		return application;
	}
	
	public boolean isPreApp() {
		return prePostAtr == PrePostAtr.PREDICT;
	}
	
	public boolean isOverTimeApp() {
		return appType == ApplicationType.OVER_TIME_APPLICATION;
	} 
	
	public boolean isAbsenceApp() {
		return appType == ApplicationType.ABSENCE_APPLICATION;
	}
	
	public boolean isWorkChangeApp() {
		return appType == ApplicationType.WORK_CHANGE_APPLICATION;
	} 
	
	public boolean isGoReturnDirectlyApp() {
		return appType == ApplicationType.GO_RETURN_DIRECTLY_APPLICATION;
	} 
	
	public boolean isHolidayWorkApp() {
		return appType == ApplicationType.HOLIDAY_WORK_APPLICATION;
	} 
	
	/**
	 * refactor 4
	 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.申請承認.申請.アルゴリズム.反映状態を取得する.反映状態を取得する
	 * @return
	 */
	public ReflectedState getAppReflectedState() {
		// OUTPUT．反映状態＝未反映
		ReflectedState result = ReflectedState.NOTREFLECTED;
		// INPUT．反映状態．対象日の反映状態をループする
		for(ReflectionStatusOfDay reflectionStatusOfDay : reflectionStatus.getListReflectionStatusOfDay()) {
			// OUTPUT．反映状態＝ループ中の対象日の反映状態．実績反映状態
			result = reflectionStatusOfDay.getActualReflectStatus();
			// ループ中の対象日の反映状態．実績反映状態をチェックする
			if(result == ReflectedState.REFLECTED || result == ReflectedState.CANCELED) {
				continue;
			}
			return result;
		}
		return result;
		
	}
	
	public Application getApplication() {
		return this;
	}
}
