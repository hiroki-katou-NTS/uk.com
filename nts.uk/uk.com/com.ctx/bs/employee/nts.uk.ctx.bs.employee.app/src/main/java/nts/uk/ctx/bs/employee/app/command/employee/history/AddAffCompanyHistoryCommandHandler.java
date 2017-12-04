package nts.uk.ctx.bs.employee.app.command.employee.history;

import java.util.ArrayList;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHist;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHistByEmployee;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHistService;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHistItem;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHistRepository;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyInfo;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyInfoRepository;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.shr.pereg.app.command.PeregAddCommandHandler;
import nts.uk.shr.pereg.app.command.PeregAddCommandResult;

@Stateless
public class AddAffCompanyHistoryCommandHandler extends CommandHandlerWithResult<AddAffCompanyHistoryCommand, PeregAddCommandResult>
	implements PeregAddCommandHandler<AddAffCompanyHistoryCommand>{
	
	@Inject
	private AffCompanyHistRepository affCompanyHistRepository;
	
	@Inject
	private AffCompanyInfoRepository affCompanyInfoRepository;
	
	@Inject
	private AffCompanyHistService affCompanyHistService;
	
	@Override
	public String targetCategoryCd() {
		return "CS00003";
	}

	@Override
	public Class<?> commandClass() {
		return AddAffCompanyHistoryCommand.class;
	}

	@Override
	protected PeregAddCommandResult handle(CommandHandlerContext<AddAffCompanyHistoryCommand> context) {
		val command = context.getCommand();
		String newHistId = IdentifierUtil.randomUniqueId();
		AffCompanyHist listHist = affCompanyHistRepository.getAffCompanyHistoryOfEmployee(command.getSId());
		
		AffCompanyHistByEmployee itemToBeAdded = new AffCompanyHistByEmployee(command.getSId(), new ArrayList<>());
		if (listHist != null){
			itemToBeAdded = listHist.getAffCompanyHistByEmployee(command.getSId());
		}
		AffCompanyHistItem hist = new AffCompanyHistItem(newHistId, false, new DatePeriod(command.getStartDate(), command.getEndDate()));
		itemToBeAdded.add(hist);
		
		affCompanyHistService.add(itemToBeAdded,command.getPId());
		
		AffCompanyInfo histItem = AffCompanyInfo.createFromJavaType(newHistId, command.getRecruitmentClassification(), command.getAdoptionDate(), command.getRetirementAllowanceCalcStartDate());
		affCompanyInfoRepository.add(histItem);
		
		return new PeregAddCommandResult(newHistId);
	}

}
