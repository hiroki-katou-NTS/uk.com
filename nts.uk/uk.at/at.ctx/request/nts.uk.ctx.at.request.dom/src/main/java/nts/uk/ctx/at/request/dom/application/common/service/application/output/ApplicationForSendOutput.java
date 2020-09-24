package nts.uk.ctx.at.request.dom.application.common.service.application.output;


/*import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.PesionInforImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootContentImport_New;
import java.util.List;*/
import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.application.Application;


@AllArgsConstructor
@Getter
public class ApplicationForSendOutput {
	private Application application;
	private String mailTemplate;
	private ApprovalRootOutput approvalRoot;
	private String applicantMail;
	private String empName;
}
