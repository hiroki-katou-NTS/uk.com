package nts.uk.ctx.pr.core.app.command.wageprovision.processdatecls;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls.AdvancedSettingDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls.BasicSettingDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls.ValPayDateSetDto;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.ProcessInformation;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.ProcessInformationRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.ValPayDateSet;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.ValPayDateSetRepository;
import nts.uk.shr.com.context.AppContexts;


@Stateless
@Transactional
public class UpdateProcessingCommandHandler extends CommandHandler<ProcessingSegmentCommand>{
	
	@Inject
	ValPayDateSetRepository valPayDateSetRepository;

	@Inject
	ProcessInformationRepository processInformationRepository;
	


	@Override
	protected void handle(CommandHandlerContext<ProcessingSegmentCommand> context) {
		// TODO Auto-generated method stub
		String cid = AppContexts.user().companyId();
		ProcessingSegmentCommand addCommand = context.getCommand();
		

		

		this.processInformationRepository
				.update(new ProcessInformation(cid, addCommand.getProcessInformation().getProcessCateNo(),
						addCommand.getProcessInformation().getDeprecatCate(),
						addCommand.getProcessInformation().getProcessDivisionName()));

		this.valPayDateSetRepository.update(new ValPayDateSet(cid, addCommand.getValPayDateSet().getProcessCateNo(),
				addCommand.getValPayDateSet().getBasicSetting().getAccountingClosureDate().getProcessMonth(),
				addCommand.getValPayDateSet().getBasicSetting().getAccountingClosureDate().getDisposalDay(),
				addCommand.getValPayDateSet().getBasicSetting().getEmployeeExtractionReferenceDate().getRefeMonth(),
				addCommand.getValPayDateSet().getBasicSetting().getEmployeeExtractionReferenceDate().getRefeDate(),
				addCommand.getValPayDateSet().getBasicSetting().getMonthlyPaymentDate().getDatePayMent(),
				addCommand.getValPayDateSet().getBasicSetting().getWorkDay(),
				addCommand.getValPayDateSet().getAdvancedSetting().getDetailPrintingMon().getPrintingMonth(),
				addCommand.getValPayDateSet().getAdvancedSetting().getSalaryInsuColMon().getMonthCollected(),
				addCommand.getValPayDateSet().getAdvancedSetting().getEmpInsurStanDate().getRefeDate(),
				addCommand.getValPayDateSet().getAdvancedSetting().getEmpInsurStanDate().getBaseMonth(),
				addCommand.getValPayDateSet().getAdvancedSetting().getIncomTaxBaseYear().getRefeDate(),
				addCommand.getValPayDateSet().getAdvancedSetting().getIncomTaxBaseYear().getBaseYear(),
				addCommand.getValPayDateSet().getAdvancedSetting().getIncomTaxBaseYear().getBaseMonth(),
				addCommand.getValPayDateSet().getAdvancedSetting().getSociInsuStanDate().getBaseMonth(),
				addCommand.getValPayDateSet().getAdvancedSetting().getSociInsuStanDate().getBaseYear(),
				addCommand.getValPayDateSet().getAdvancedSetting().getSociInsuStanDate().getRefeDate(),
				addCommand.getValPayDateSet().getAdvancedSetting().getCloseDate().getTimeCloseDate(),
				addCommand.getValPayDateSet().getAdvancedSetting().getCloseDate().getTimeCloseDate()==1 ? addCommand.getValPayDateSet().getAdvancedSetting().getCloseDate().getBaseMonth():null,
				addCommand.getValPayDateSet().getAdvancedSetting().getCloseDate().getTimeCloseDate()==1 ? addCommand.getValPayDateSet().getAdvancedSetting().getCloseDate().getBaseYear():null,
				addCommand.getValPayDateSet().getAdvancedSetting().getCloseDate().getTimeCloseDate()==1 ? addCommand.getValPayDateSet().getAdvancedSetting().getCloseDate().getRefeDate():null
			)
		);

		
		
	}

}
