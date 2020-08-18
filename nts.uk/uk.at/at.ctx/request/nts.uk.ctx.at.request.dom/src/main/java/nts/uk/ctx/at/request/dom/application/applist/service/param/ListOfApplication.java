package nts.uk.ctx.at.request.dom.application.applist.service.param;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.ReflectedState;
import nts.uk.ctx.at.request.dom.application.applist.service.ApplicationTypeDisplay;

/**
 * refactor 4
 * UKDesign.UniversalK.就業.KAF_申請.CMM045_申請一覧・承認一覧.A:申請一覧画面.アルゴリズム.パラメータ.申請一覧
 * @author Doan Duy Hung
 *
 */
@Setter
@Getter
public class ListOfApplication {
	
	/**
	 * 事前事後区分
	 */
	private int prePostAtr;
	
	/**
	 * 職場名
	 */
	private String workplaceName;
	
	/**
	 * 申請ID
	 */
	private String appID;
	
	/**
	 * 申請者CD
	 */
	private String applicantCD;
	
	/**
	 * 申請者名
	 */
	private String applicantName;
	
	/**
	 * 申請種類
	 */
	private ApplicationType appTye;
	
	/**
	 * 申請内容
	 */
	private String appContent;
	
	/**
	 * 申請日
	 */
	private GeneralDate appDate;
	
	/**
	 * 入力社名
	 */
	private String inputCompanyName;
	
	/**
	 * 入力日
	 */
	private GeneralDateTime inputDate;
	
	/**
	 * 反映状態
	 */
	private ReflectedState reflectionStatus;
	
	/**
	 * 時刻計算利用区分
	 */
	private Optional<Integer> opTimeCalcUseAtr;
	
	/**
	 * 承認状況照会
	 */
	private Optional<String> opApprovalStatusInquiry;
	
	/**
	 * 承認枠の承認状態
	 */
	private Optional<Integer> opApprovalFrameStatus;
	
	/**
	 * 振休振出申請
	 */
	private Optional<ComplementLeaveAppConnect> opComplementLeaveApp;
	
	/**
	 * 申請開始日
	 */
	private Optional<GeneralDate> opAppStartDate;
	
	/**
	 * 申請種類表示
	 */
	private Optional<ApplicationTypeDisplay> opAppTypeDisplay;
	
	/**
	 * 申請終了日
	 */
	private Optional<GeneralDate> opAppEndDate;
	
	/**
	 * 定型理由
	 */
	private Optional<String> opAppStandardReason;
	
	/**
	 * 入力者名称
	 */
	private Optional<String> opEntererName;
	
	/**
	 * 背景色
	 */
	private Optional<Integer> opBackgroundColor;
	
	/**
	 * 表示行数超
	 */
	private Optional<Boolean> opMoreThanDispLineNO;
	
	public ListOfApplication() {
		this.prePostAtr = 0;
		this.workplaceName = null;
		this.appID = null;
		this.applicantCD = null;
		this.applicantName = null;
		this.appTye = null;
		this.appContent = null;
		this.appDate = null;
		this.inputCompanyName  = null;
		this.inputDate  = null;
		this.reflectionStatus = null;
		this.opTimeCalcUseAtr = Optional.empty();
		this.opApprovalStatusInquiry = Optional.empty();
		this.opApprovalFrameStatus = Optional.empty();
		this.opComplementLeaveApp = Optional.empty();
		this.opAppStartDate = Optional.empty();
		this.opAppTypeDisplay = Optional.empty();
		this.opAppEndDate = Optional.empty();
		this.opAppStandardReason = Optional.empty();
		this.opEntererName = Optional.empty();
		this.opBackgroundColor = Optional.empty();
		this.opMoreThanDispLineNO = Optional.empty();
	}
}
