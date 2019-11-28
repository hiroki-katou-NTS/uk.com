package nts.uk.ctx.hr.notice.app.command.report;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class NewLayoutReportCommand {
	private Integer id;

	private String reportCode;

	private String reportName;
	
	private String reportNameYomi;
	
	private boolean abolition;
	
	private Integer reportType;
	
	private String remark;
	
	private String memo;
	
	private String message;
	
	private boolean formReport;
	
	private List<ClassificationCommand> classifications; 

	int action;
}
