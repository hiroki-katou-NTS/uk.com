package nts.uk.file.com.app.company.approval;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.masterapproverroot.ApproverRootMaster;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.MasterApproverRootOutput;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class MasterApproverRootExportService extends ExportService<MasterApproverRootQuery> {
	@Inject
	private MasterApproverRootOutputGenerator masterGenerator;
	
	@Inject
	private ApproverRootMaster masterRoot;

	@Override
	protected void handle(ExportServiceContext<MasterApproverRootQuery> context) {
		MasterApproverRootQuery query = context.getQuery();

		String companyID = AppContexts.user().companyId();
//		MasterApproverRootOutput masterApp = this.masterRoot.getMasterInfo(companyID, query.getBaseDate(),
//				query.isChkCompany(), query.isChkWorkplace(), query.isChkPerson());
		MasterApproverRootOutput masterApp = this.masterRoot.masterInfors(companyID, query.getBaseDate(), query.isChkCompany(), query.isChkWorkplace(), query.isChkPerson());
		if(masterApp.getCompanyRootInfor().getLstComs().isEmpty()
				&& masterApp.getWorplaceRootInfor().isEmpty()
				&& masterApp.getPersonRootInfor().isEmpty()) {
			throw new BusinessException("Msg_7");
		}
			
		val dataSource = new MasterApproverRootOutputDataSource(masterApp);

		// generate file
		this.masterGenerator.generate(context.getGeneratorContext(), dataSource);

	}

}
