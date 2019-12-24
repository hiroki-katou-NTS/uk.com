package nts.uk.ctx.pr.transfer.app.command.rsdttaxpayee;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.transfer.dom.adapter.core.emprsdttaxinfo.EmployeeResidentTaxPayeeInfoAdapter;
import nts.uk.ctx.pr.transfer.dom.adapter.core.emprsdttaxinfo.EmployeeResidentTaxPayeeInfoImport;
import nts.uk.ctx.pr.transfer.dom.adapter.core.emprsdttaxinfo.PayeeInfoAdapter;
import nts.uk.ctx.pr.transfer.dom.adapter.employee.TransferEmployeeAdapter;
import nts.uk.ctx.pr.transfer.dom.rsdttaxpayee.ResidentTaxPayee;
import nts.uk.ctx.pr.transfer.dom.rsdttaxpayee.ResidentTaxPayeeCode;
import nts.uk.ctx.pr.transfer.dom.rsdttaxpayee.ResidentTaxPayeeRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class ResidentTaxPayeeIntegrationCommandHandler extends CommandHandler<RtpIntegrationCommand> {

	@Inject
	private ResidentTaxPayeeRepository residentTaxRepo;

	@Inject
	private TransferEmployeeAdapter employeeAdapter;

	@Inject
	private EmployeeResidentTaxPayeeInfoAdapter empRsdtTaxAdapter;

	@Inject
	private PayeeInfoAdapter payeeInfoAdapter;

	@Override
	protected void handle(CommandHandlerContext<RtpIntegrationCommand> context) {
		String companyId = AppContexts.user().companyId();
		List<String> sourceCodes = context.getCommand().getSourceCode();
		String destinationCode = context.getCommand().getDestinationCode();

		List<ResidentTaxPayee> listRsdtTax = residentTaxRepo.getListResidentTaxPayee(companyId, sourceCodes);
		for (ResidentTaxPayee taxPayee : listRsdtTax) {
			taxPayee.setReportCd(Optional.of(new ResidentTaxPayeeCode(destinationCode)));
			residentTaxRepo.update(taxPayee);
		}

		List<String> lstEmp = employeeAdapter.getListEmpIdOfLoginCompany(companyId);
		List<EmployeeResidentTaxPayeeInfoImport> listEmpRsdtTaxPayee = empRsdtTaxAdapter.getEmpRsdtTaxPayeeInfo(lstEmp, sourceCodes);
		for (EmployeeResidentTaxPayeeInfoImport rtp : listEmpRsdtTaxPayee) {
			List<YearMonthHistoryItem> historyItems = rtp.getHistoryItems().stream()
					.filter(i -> i.end().v() >= context.getCommand().getTargetYm()).collect(Collectors.toList());
			for (YearMonthHistoryItem hist : historyItems) {
				payeeInfoAdapter.updateResidentTaxPayeeCode(hist.identifier(), destinationCode);
			}
		}
	}

}
