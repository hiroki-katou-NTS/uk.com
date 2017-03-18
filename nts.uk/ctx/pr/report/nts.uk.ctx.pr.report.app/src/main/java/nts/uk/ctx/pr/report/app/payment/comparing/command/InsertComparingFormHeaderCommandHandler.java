package nts.uk.ctx.pr.report.app.payment.comparing.command;

import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.report.dom.payment.comparing.ComparingFormHeader;
import nts.uk.ctx.pr.report.dom.payment.comparing.ComparingFormHeaderRepository;
import nts.uk.shr.com.context.AppContexts;

@RequestScoped
@Transactional
public class InsertComparingFormHeaderCommandHandler extends CommandHandler<InsertComparingFormHeaderCommand> {
	@Inject
	private ComparingFormHeaderRepository comparingFormHeaderRepository;

	@Override
	protected void handle(CommandHandlerContext<InsertComparingFormHeaderCommand> context) {
		String companyCode = AppContexts.user().companyCode();
		InsertComparingFormHeaderCommand insertCommand = context.getCommand();

		if (StringUtils.isBlank(insertCommand.getFormCode())) {
			throw new BusinessException("ER001_1");
		}
		if (StringUtils.isBlank(insertCommand.getFormName())) {
			throw new BusinessException("ER001_2");
		}

		Optional<ComparingFormHeader> comparingFormHeader = this.comparingFormHeaderRepository
				.getComparingFormHeader(companyCode, insertCommand.getFormCode());

		if (comparingFormHeader.isPresent()) {
			throw new BusinessException("ER005");
		}

		ComparingFormHeader newComparingFormHeader = ComparingFormHeader.createFromJavaType(companyCode,
				insertCommand.getFormCode(), insertCommand.getFormName());
		this.comparingFormHeaderRepository.insertComparingFormHeader(newComparingFormHeader);
	}

}
