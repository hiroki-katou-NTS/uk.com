package nts.uk.file.com.app.company.approval;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.masterapproverroot.ApproverRootMaster;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.MasterApproverRootOutput;
import nts.uk.file.com.app.HeaderEmployeeUnregisterOutput;
import nts.uk.shr.com.company.CompanyAdapter;
import nts.uk.shr.com.company.CompanyInfor;
import nts.uk.shr.com.context.AppContexts;
/**
 * CMM018 - M
 * マスタリスト
 * @author hoatt
 *
 */
@Stateless
public class MasterApproverRootExportService extends ExportService<MasterApproverRootQuery> {
	@Inject
	private MasterApproverRootOutputGenerator masterGenerator;

	@Inject
	private ApproverRootMaster masterRoot;
	
	@Inject
	private CompanyAdapter company;


	@Override
	protected void handle(ExportServiceContext<MasterApproverRootQuery> context) {
		MasterApproverRootQuery query = context.getQuery();
		String companyID = AppContexts.user().companyId();
		
		// get data
		MasterApproverRootOutput masterApp = masterRoot.masterInfors(companyID, query.getBaseDate(),
				query.isChkCompany(), query.isChkWorkplace(), query.isChkPerson());
		
		// check condition
		if (masterApp.getComRootInfor() == null && masterApp.getWkpRootOutput().getWorplaceRootInfor().isEmpty()
				&& masterApp.getEmpRootOutput().getPersonRootInfor().isEmpty()) {
			throw new BusinessException("Msg_7");
		}

		val dataSource = new MasterApproverRootOutputDataSource(masterApp,this.setHeader(), query.isChkCompany(), query.isChkPerson(),
				query.isChkWorkplace());

		// generate file
		masterGenerator.generate(context.getGeneratorContext(), dataSource);

	}
	
	private HeaderEmployeeUnregisterOutput setHeader() {
		HeaderEmployeeUnregisterOutput header = new HeaderEmployeeUnregisterOutput();
		Optional<CompanyInfor> companyInfo = this.company.getCurrentCompany();
		if (companyInfo.isPresent()) {
			header.setNameCompany(companyInfo.get().getCompanyName());
		}
		return header;
	}

}
