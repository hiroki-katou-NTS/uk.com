package nts.uk.file.com.app;

import java.util.ArrayList;
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
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.EmploymentRootAtr;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.SystemAtr;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.masterapproverroot.AppTypeName;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.EmpUnregisterInput;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.EmployeeUnregisterOutput;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.unregisterapproval.EmployeeUnregisterApprovalRoot;
import nts.uk.shr.com.company.CompanyAdapter;
import nts.uk.shr.com.company.CompanyInfor;
import nts.uk.shr.com.context.AppContexts;

/**
 * CMM018 - L
 * 未登録個人kリスト
 * @author hoatt
 *
 */
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
		List<AppTypeName> lstName = context.getQuery().getLstAppName();
		List<Integer> lstNotice = new ArrayList<>();
		List<String> lstEvent = new ArrayList<>();
		if(sysAtr == SystemAtr.HUMAN_RESOURCES.value) {
			lstNotice = this.lstNotice(lstName);
			lstEvent = this.lstEvent(lstName);
		}
		// create data source
		List<EmployeeUnregisterOutput> items = empUnregister.lstEmployeeUnregister(companyId, baseDate,
				sysAtr, lstNotice, lstEvent, lstName);
		if (CollectionUtil.isEmpty(items)) {
			throw new BusinessException("Msg_1765");
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
	private List<Integer> lstNotice(List<AppTypeName> lstName){
		List<Integer> lstResult = new ArrayList<>();
		for(AppTypeName app : lstName) {
			if(app.getEmpRAtr() == EmploymentRootAtr.NOTICE.value) {
				lstResult.add(Integer.valueOf(app.getValue()));
			}
		}
		return lstResult;
	}
	private List<String> lstEvent(List<AppTypeName> lstName){
		List<String> lstResult = new ArrayList<>();
		for(AppTypeName app : lstName) {
			if(app.getEmpRAtr() == EmploymentRootAtr.BUS_EVENT.value) {
				lstResult.add(app.getValue());
			}
		}
		return lstResult;
	}
}
