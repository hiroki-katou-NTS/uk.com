package nts.uk.ctx.at.request.dom.application.common.service.application.output;

<<<<<<< HEAD
=======
import java.util.List;

>>>>>>> pj/at/dev/Team_D/KDL034
import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.PesionInforImport;
<<<<<<< HEAD
=======
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootContentImport_New;
>>>>>>> pj/at/dev/Team_D/KDL034

@AllArgsConstructor
@Getter
public class ApplicationForSendOutput {
	private Application_New application;
	private String mailTemplate;
<<<<<<< HEAD
	private ApprovalRootOutput approvalRoot;
	private String applicantMail;
=======
	private ApprovalRootContentImport_New approvalRoot;
	private List<PesionInforImport> listApprover;
	private PesionInforImport applicant;
>>>>>>> pj/at/dev/Team_D/KDL034
}
