package nts.uk.file.com.app;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.EmpUnregisterInput;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.EmployeeUnregisterOutput;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.unregisterapproval.EmployeeUnregisterApprovalRoot;
import nts.uk.shr.com.company.CompanyAdapter;
import nts.uk.shr.com.company.CompanyInfor;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class EmployeeUnregisterOutputExportService extends ExportService<EmpUnregisterInput> {

	@Inject
	private EmployeeUnregisterApprovalRoot empUnregister;

	@Inject
	private CompanyAdapter company;

	@Inject
	private EmployeeUnregisterOutputGenerator employgenerator;

	@Override
	protected void handle(ExportServiceContext<EmpUnregisterInput> context) {

		String companyId = AppContexts.user().companyId();

		// get query parameters
		GeneralDate baseDate = context.getQuery().getBaseDate();
		int sysAtr = context.getQuery().getSysAtr();

		// create data source
		List<EmployeeUnregisterOutput> items = empUnregister.lstEmployeeUnregister(companyId, baseDate, sysAtr);
		if (CollectionUtil.isEmpty(items)) {
			throw new BusinessException("Msg_7");
		}
		HeaderEmployeeUnregisterOutput header = this.setHeader(items.get(0));
		val dataSource = new EmployeeUnregisterOutputDataSoure(header, items);

		// generate file
		this.employgenerator.generate(context.getGeneratorContext(), dataSource);
	}

	private HeaderEmployeeUnregisterOutput setHeader(EmployeeUnregisterOutput employee) {
		HeaderEmployeeUnregisterOutput header = new HeaderEmployeeUnregisterOutput();
		Optional<CompanyInfor> companyInfo = company.getCurrentCompany();
		if (companyInfo.isPresent()) {
			header.setNameCompany(companyInfo.get().getCompanyName());
		}
		return header;
	}

}
