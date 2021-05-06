package nts.uk.ctx.at.aggregation.app.command.schedulecounter.initcompanyinfo;

import java.util.Optional;

import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmount;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountForCompany;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountForCompanyRepository;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountList;
import nts.uk.shr.com.context.AppContexts;


// 会社の目安金額を登録する
@Stateless
@Transactional
public class CompanyInfoRegisterCommandHandler extends CommandHandler<CompanyInfoRegisterCommand> {

	@Inject
	private CriterionAmountForCompanyRepository criterionAmountForCompanyRepository;
	
	@Override
	protected void handle(CommandHandlerContext<CompanyInfoRegisterCommand> context) {
		
		String cid = AppContexts.user().companyId();
		CompanyInfoRegisterCommand command = context.getCommand();
		
		CriterionAmount criterionAmount = new CriterionAmount(
				CriterionAmountList.create(
						command.getYears().stream().map(CriterionAmountByNoCommand::toDomain).collect(Collectors.toList())),
				CriterionAmountList.create(
						command.getMonths().stream().map(CriterionAmountByNoCommand::toDomain).collect(Collectors.toList()))
				);
		CriterionAmountForCompany domain;
		// get
		Optional<CriterionAmountForCompany> criOptional = criterionAmountForCompanyRepository.get(cid);
		
		if (criOptional.isPresent()) {
			domain = criOptional.get();
			domain.update(criterionAmount);
			// 変更する(目安金額)
			criterionAmountForCompanyRepository.update(cid, domain);
		} else {
			domain = new CriterionAmountForCompany(criterionAmount);
			criterionAmountForCompanyRepository.insert(cid, domain);
		}
		
		
	}

}
