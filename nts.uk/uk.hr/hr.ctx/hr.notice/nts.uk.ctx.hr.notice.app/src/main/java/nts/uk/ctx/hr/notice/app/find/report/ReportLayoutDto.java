package nts.uk.ctx.hr.notice.app.find.report;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Data;
import nts.gul.text.StringUtil;
import nts.uk.ctx.hr.notice.dom.report.PersonalReportClassification;
import nts.uk.ctx.hr.notice.dom.report.registration.person.DocumentSampleDto;
import nts.uk.ctx.hr.notice.dom.report.registration.person.RegistrationPersonReport;
import nts.uk.ctx.hr.notice.dom.report.registration.person.ReportStartSetting;
import nts.uk.ctx.hr.shared.dom.notice.report.registration.person.ApprovalRootStateHrImport;
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
	
	private ApprovalRootStateHrImport approvalStateHrImport;
	
	
	public ReportLayoutDto(){
		
		this.classificationItems = new ArrayList<>();
		
		this.documentSampleDto = new ArrayList<>();
		
	}
	
	public ReportLayoutDto(List<LayoutReportClsDto> classificationItems) {
		
		this.classificationItems = classificationItems;
		
	}
	
	public static ReportLayoutDto createFromDomain(PersonalReportClassification domain,
			
			Optional<ReportStartSetting> reportStartSettingOpt,
			
			Optional<RegistrationPersonReport> registrationPersonReport,
			
			List<LayoutReportClsDto> classificationItems,
			
			List<DocumentSampleDto> documentSampleDtoLst,
			ApprovalRootStateHrImport approvalStateHrImport) {
		
		ReportLayoutDto dto = new ReportLayoutDto();
		
		dto.setCompanyId(domain.getCompanyId());
		
		dto.setReportName(domain.getPReportName().v());
		
		dto.setClassificationItems(classificationItems);
		
		dto.setDocumentSampleDto(documentSampleDtoLst);
		
		dto.setApprovalStateHrImport(approvalStateHrImport);
		
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
