package nts.uk.ctx.at.request.app.find.application.applicationlist;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.app.find.application.common.dto.ApprovalPhaseStateForAppDto;
import nts.uk.ctx.at.request.dom.application.applist.service.param.ComplementLeaveAppConnect;
import nts.uk.ctx.at.request.dom.application.applist.service.param.ListOfApplication;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class ListOfApplicationDto {
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
	
	public static ListOfApplicationDto fromDomain(ListOfApplication listOfApplication) {
		return new ListOfApplicationDto(
				listOfApplication.getPrePostAtr(), 
				listOfApplication.getWorkplaceName(),
				ApplicationDto.fromDomain(listOfApplication.getApplication()),
				listOfApplication.getAppID(), 
				listOfApplication.getApplicantCD(), 
				listOfApplication.getApplicantID(),
				listOfApplication.getApplicantName(), 
				listOfApplication.getAppType().value, 
				listOfApplication.getAppContent(), 
				listOfApplication.getAppDate().toString(), 
				listOfApplication.getInputCompanyName(), 
				listOfApplication.getInputDate().toString(), 
				listOfApplication.getReflectionStatus(), 
				listOfApplication.getOpTimeCalcUseAtr().orElse(null), 
				listOfApplication.getOpApprovalPhaseLst().map(x -> x.stream().map(y -> ApprovalPhaseStateForAppDto.fromApprovalPhaseStateImport(y)).collect(Collectors.toList())).orElse(null), 
				listOfApplication.getOpApprovalStatusInquiry().orElse(null), 
				listOfApplication.getOpApprovalFrameStatus().orElse(null), 
				listOfApplication.getOpComplementLeaveApp().orElse(null), 
				listOfApplication.getOpAppStartDate().map(x -> x.toString()).orElse(null), 
				listOfApplication.getOpAppTypeDisplay().map(x -> x.value).orElse(null), 
				listOfApplication.getOpAppEndDate().map(x -> x.toString()).orElse(null), 
				listOfApplication.getOpAppStandardReason().orElse(null), 
				listOfApplication.getOpEntererName().orElse(null), 
				listOfApplication.getOpBackgroundColor().orElse(null), 
				listOfApplication.getOpMoreThanDispLineNO().orElse(null),
				listOfApplication.getVersion());
	}
}
