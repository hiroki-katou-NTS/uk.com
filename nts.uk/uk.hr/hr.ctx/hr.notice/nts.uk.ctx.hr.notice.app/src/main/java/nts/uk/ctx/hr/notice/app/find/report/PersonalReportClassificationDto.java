package nts.uk.ctx.hr.notice.app.find.report;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.hr.notice.dom.report.PersonalReportClassification;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonalReportClassificationDto {
	//会社ID
	private String companyId;
	//個別届出種類ID
	private int reportClsId;
	//個別届出コード
	private String reportCode;
	//個別届出名
	private String reportName;
	//個別届出名よみ
	private String reportNameYomi;
	//表示順
	private int displayOrder;
	//廃止区分
	private int isAbolition;
	//届出種類
	private Integer reportType;
	//備考
	private String remark;
	//メモ
	private String memo;
	//メッセージ
	private String message;
	//帳票印刷
	private int formReport;
	//代行届出可
	private int agentReportIsCan;
	
	private List<LayoutReportClsDto> listItemClsDto = new ArrayList<>();
	
	public static PersonalReportClassificationDto fromDomain(PersonalReportClassification domain, List<LayoutReportClsDto> classifications) {
		return new PersonalReportClassificationDto(domain.getCompanyId(),
				domain.getPReportClsId(), domain.getPReportCode().v(), domain.getPReportName().v(),
				domain.getPReportNameYomi() == null? null: domain.getPReportNameYomi().v(),
				domain.getDisplayOrder(), 
				domain.isAbolition() == true? 1: 0,
				domain.getReportType() == null? null: domain.getReportType().value, 
				domain.getRemark() == null? null: domain.getRemark().v(),
				domain.getMemo() == null? null: domain.getMemo().v(),
				domain.getMessage() == null? null: domain.getMessage().v(),
			    domain.isFormReport() == true? 1: 0, 
			    domain.isAgentReportIsCan() == true? 1: 0,
			    classifications);
	}
	
	public static PersonalReportClassificationDto fromDomain(PersonalReportClassification domain) {
		return new PersonalReportClassificationDto(domain.getCompanyId(),
				domain.getPReportClsId(), domain.getPReportCode().v(), domain.getPReportName().v(),
				domain.getPReportNameYomi() == null? null: domain.getPReportNameYomi().v(),
				domain.getDisplayOrder(), 
				domain.isAbolition() == true? 1: 0,
				domain.getReportType() == null? null: domain.getReportType().value, 
				domain.getRemark() == null? null: domain.getRemark().v(),
				domain.getMemo() == null? null: domain.getMemo().v(),
				domain.getMessage() == null? null: domain.getMessage().v(),
			    domain.isFormReport() == true? 1: 0, 
			    domain.isAgentReportIsCan() == true? 1: 0);
	}

	public PersonalReportClassificationDto(String companyId, int reportClsId, String reportCode, String reportName,
			String reportNameYomi, int displayOrder, int isAbolition, Integer reportType, String remark, String memo,
			String message, int formReport, int agentReportIsCan) {
		super();
		this.companyId = companyId;
		this.reportClsId = reportClsId;
		this.reportCode = reportCode;
		this.reportName = reportName;
		this.reportNameYomi = reportNameYomi;
		this.displayOrder = displayOrder;
		this.isAbolition = isAbolition;
		this.reportType = reportType;
		this.remark = remark;
		this.memo = memo;
		this.message = message;
		this.formReport = formReport;
		this.agentReportIsCan = agentReportIsCan;
	}
}
