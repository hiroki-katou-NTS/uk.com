package nts.uk.ctx.at.request.app.command.application.applicationlist;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Data;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.app.find.application.common.dto.ApprovalPhaseStateForAppDto;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.applist.service.ApplicationTypeDisplay;
import nts.uk.ctx.at.request.dom.application.applist.service.param.ComplementLeaveAppConnect;
import nts.uk.ctx.at.request.dom.application.applist.service.param.ListOfApplication;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@Data
public class ListOfApplicationCmd {
	/**
	 * 事前事後区分
	 */
	private int prePostAtr;
	
	/**
	 * 職場名
	 */
	private String workplaceName;
	
	/**
	 * 申請
	 */
	private ApplicationDto application;
	
	/**
	 * 申請ID
	 */
	private String appID;
	
	/**
	 * 申請者CD
	 */
	private String applicantCD;
	
	/**
	 * 申請者ID
	 */
	private String applicantID;	
	
	/**
	 * 申請者名
	 */
	private String applicantName;
	
	/**
	 * 申請種類
	 */
	private int appType;
	
	/**
	 * 申請内容
	 */
	private String appContent;
	
	/**
	 * 申請日
	 */
	private String appDate;
	
	/**
	 * 入力社名
	 */
	private String inputCompanyName;
	
	/**
	 * 入力日
	 */
	private String inputDate;
	
	/**
	 * 反映状態
	 */
	private String reflectionStatus;
	
	/**
	 * 時刻計算利用区分
	 */
	private Integer opTimeCalcUseAtr;
	
	/**
	 * 承認フェーズインスタンス
	 */
	private List<ApprovalPhaseStateForAppDto> opApprovalPhaseLst;
	
	/**
	 * 承認状況照会
	 */
	private String opApprovalStatusInquiry;
	
	/**
	 * 承認枠の承認状態
	 */
	private Integer opApprovalFrameStatus;
	
	/**
	 * 振休振出申請
	 */
	private ComplementLeaveAppConnect opComplementLeaveApp;
	
	/**
	 * 申請開始日
	 */
	private String opAppStartDate;
	
	/**
	 * 申請種類表示
	 */
	private Integer opAppTypeDisplay;
	
	/**
	 * 申請終了日
	 */
	private String opAppEndDate;
	
	/**
	 * 定型理由
	 */
	private String opAppStandardReason;
	
	/**
	 * 入力者名称
	 */
	private String opEntererName;
	
	/**
	 * 背景色
	 */
	private Integer opBackgroundColor;
	
	/**
	 * 表示行数超
	 */
	private Boolean opMoreThanDispLineNO;
	
	private int version;
	
	// AnhNM add to domain
	public ListOfApplication toDomain() {
		ListOfApplication lstApp = new ListOfApplication();
		
		lstApp.setPrePostAtr(prePostAtr);
		lstApp.setWorkplaceName(workplaceName);
		lstApp.setApplication(application.toDomain());
		lstApp.setAppID(appID);
		lstApp.setApplicantCD(applicantCD);
		lstApp.setApplicantID(applicantID);
		lstApp.setApplicantName(applicantName);
		lstApp.setAppType(EnumAdaptor.valueOf(appType, ApplicationType.class));
		lstApp.setAppContent(appContent);
		lstApp.setAppDate(GeneralDate.fromString(appDate, "yyyy/MM/dd"));
		lstApp.setInputCompanyName(inputCompanyName);
		lstApp.setInputDate(GeneralDateTime.fromString(inputDate, "yyyy/MM/dd HH:mm:ss"));
		lstApp.setReflectionStatus(reflectionStatus);
		lstApp.setOpTimeCalcUseAtr(opTimeCalcUseAtr == null ? Optional.empty() : Optional.of(opTimeCalcUseAtr));
		lstApp.setOpApprovalPhaseLst(opApprovalPhaseLst == null ? Optional.empty() : Optional.of(opApprovalPhaseLst.stream().map(x -> x.toDomain()).collect(Collectors.toList())));
		lstApp.setOpApprovalStatusInquiry(opApprovalStatusInquiry == null ? Optional.empty() : Optional.of(opApprovalStatusInquiry));
		lstApp.setOpApprovalFrameStatus(opApprovalFrameStatus == null ? Optional.empty() : Optional.of(opApprovalFrameStatus));
		lstApp.setOpComplementLeaveApp(opComplementLeaveApp == null ? Optional.empty() : Optional.of(opComplementLeaveApp));
		lstApp.setOpAppStartDate(opAppStartDate == null ? Optional.empty() : Optional.of(GeneralDate.fromString(opAppStartDate, "yyyy/MM/dd")));
		lstApp.setOpAppTypeDisplay(opAppTypeDisplay == null ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(opAppTypeDisplay, ApplicationTypeDisplay.class)));
		lstApp.setOpAppEndDate(opAppEndDate == null ? Optional.empty() : Optional.of(GeneralDate.fromString(opAppEndDate, "yyyy/MM/dd")));
		lstApp.setOpAppStandardReason(opAppStandardReason == null ? Optional.empty() : Optional.of(opAppStandardReason));
		lstApp.setOpEntererName(opEntererName == null ? Optional.empty() : Optional.of(opEntererName));
		lstApp.setOpBackgroundColor(opBackgroundColor == null ? Optional.empty() : Optional.of(opBackgroundColor));
		lstApp.setOpMoreThanDispLineNO(opMoreThanDispLineNO == null ? Optional.empty() : Optional.of(opMoreThanDispLineNO));
		lstApp.setVersion(version);
		return lstApp;
	}
}
