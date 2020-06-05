package nts.uk.ctx.hr.notice.app.find.report;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Data;
import nts.gul.text.StringUtil;
import nts.uk.ctx.hr.notice.app.find.report.regis.person.ApprovalPhaseStateForAppDto;
import nts.uk.ctx.hr.notice.dom.report.PersonalReportClassification;
import nts.uk.ctx.hr.notice.dom.report.registration.person.DocumentSampleDto;
import nts.uk.ctx.hr.notice.dom.report.registration.person.RegistrationPersonReport;
import nts.uk.ctx.hr.notice.dom.report.registration.person.ReportStartSetting;
import nts.uk.ctx.hr.shared.dom.notice.report.registration.person.ApprRootStateHrImport;
@Data
public class ReportLayoutDto {
	
	private String companyId;
	
	private String message;
	
	private boolean backComment;
	
	private String backCommentContent;
	
	private String approveComment;
	
	private int reportId;
	
	private String reportName;
	
	private boolean denyOraprrove;
	
	private List<LayoutReportClsDto> classificationItems;
	
	private List<DocumentSampleDto> documentSampleDto;
	
	private ApprRootStateHrImport approvalStateHrImport;
	
	private List<ApprovalPhaseStateForAppDto> listApprovalFrame;
	
	private boolean release;
	
	private boolean approve;
	
	private String appSid;//申請者社員ID
	private String appBussinessName;//申請者表示氏名
	
	
	
	public ReportLayoutDto(){
		
		this.classificationItems = new ArrayList<>();
		
		this.documentSampleDto = new ArrayList<>();
		
		this.listApprovalFrame = new ArrayList<>();
		
	}
	
	public ReportLayoutDto(List<LayoutReportClsDto> classificationItems) {
		
		this.classificationItems = classificationItems;
		
	}
	
	public static ReportLayoutDto createFromDomain(PersonalReportClassification domain,
			
			Optional<ReportStartSetting> reportStartSettingOpt,
			
			Optional<RegistrationPersonReport> registrationPersonReport,
			
			List<LayoutReportClsDto> classificationItems,
			
			List<DocumentSampleDto> documentSampleDtoLst,
			
			List<ApprovalPhaseStateForAppDto> listApprovalFrame,
			
			boolean release, boolean approve , String appSid , String appBussinessName) {
		
		ReportLayoutDto dto = new ReportLayoutDto();
		
		dto.setCompanyId(domain.getCompanyId());
		
		dto.setReportName(domain.getPReportName().v());
		
		dto.setClassificationItems(classificationItems);
		
		dto.setDocumentSampleDto(documentSampleDtoLst);
		
		dto.setListApprovalFrame(listApprovalFrame);
		
		dto.setRelease(release);
		
		dto.setApprove(approve);
		
		dto.setAppSid(appSid);
		
		dto.setAppBussinessName(appBussinessName);
		
		if(reportStartSettingOpt.isPresent()) {
			
			ReportStartSetting  reportStartSetting = reportStartSettingOpt.get();
			
			dto.setDenyOraprrove(reportStartSetting.isChangeDisp());
			
		}
		
		if(registrationPersonReport.isPresent()) {
			
			dto.setReportId(registrationPersonReport.get().getReportID());
			
			//差し戻しコメントがある
			if(!StringUtil.isNullOrEmpty(registrationPersonReport.get().getSendBackComment(),true)) {
				
				dto.setBackComment(true);
				
				dto.setBackCommentContent(registrationPersonReport.get().getSendBackComment());
				
			}else {
				
				dto.setBackComment(false);
				
			}
		}
		
		//メッセージエリア、メッセージを表示する (Hiển thị message area and message)・・
		dto.setMessage(domain.getMessage().v());
		
		return dto;
	}
	
}
