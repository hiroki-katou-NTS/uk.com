package nts.uk.ctx.pr.transfer.app.command.emppaymentinfo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.transfer.dom.adapter.wageprovision.processdatecls.CurrProcessYmAdapter;
import nts.uk.ctx.pr.transfer.dom.adapter.wageprovision.processdatecls.CurrProcessYmImport;
import nts.uk.ctx.pr.transfer.dom.adapter.wageprovision.processdatecls.EmploymentTiedProcYmAdapter;
import nts.uk.ctx.pr.transfer.dom.adapter.wageprovision.processdatecls.EmploymentTiedProcYmImport;
import nts.uk.ctx.pr.transfer.dom.adapter.wageprovision.processdatecls.ProcessInformationAdapter;
import nts.uk.ctx.pr.transfer.dom.adapter.wageprovision.processdatecls.ProcessInformationImport;
import nts.uk.ctx.pr.transfer.dom.emppaymentinfo.EmployeePaymentInforRepository;
import nts.uk.ctx.pr.transfer.dom.emppaymentinfo.EmployeePaymentMethodRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author HungTT - WORKING IN PROGRESS
 *
 */

@Stateless
public class IntegrationProcessCommandHandler extends CommandHandler<List<String>> {
	
	@Inject
	private ProcessInformationAdapter procInfoAdapter;
	
	@Inject
	private CurrProcessYmAdapter currentProcYmAdapter;
	
	@Inject
	private EmploymentTiedProcYmAdapter empTiedProcYmAdapter;
	
	@Inject
	private EmployeePaymentInforRepository payInfoRepo;
	
	@Inject
	private EmployeePaymentMethodRepository payMethodRepo;

	@Override
	protected void handle(CommandHandlerContext<List<String>> context) {
		String companyId = AppContexts.user().companyId();
		//dang ki Ngan hang nguon chuyen khoan
		
		// 会社ID = ログイン会社ID
		//廃止区分 = 廃止しない
		List<ProcessInformationImport> listProcInfor = procInfoAdapter.getProcessInformationByDeprecatedCategory(companyId, 1);
		
		//
		Set<Integer> setProcCateNo = listProcInfor.stream().map(i -> i.getProcessCateNo()).collect(Collectors.toSet());
		List<CurrProcessYmImport> lstCurrProcYm = currentProcYmAdapter.getCurrentSalaryProcessYm(companyId, new ArrayList<>(setProcCateNo));
	}
	
	private void EmpIntegrationProcess(String employmenCode, String employeeId, EmploymentTiedProcYmImport empTiedProcYm) {
		if (empTiedProcYm.getEmploymentCodes().contains(employmenCode)) {
			val salPayInfo = payInfoRepo.getEmpSalPaymentInfo(employeeId).get();
			val salPayMethod = payMethodRepo.getSalPayMethodByHistoryId(salPayInfo.getPeriod().get(0).identifier()).get();
//			salPayMethod.getListPaymentMethod().get(0).getTransferInfor().get().
		} else {
			
		}
		
	}

}
