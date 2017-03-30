package nts.uk.ctx.pr.report.app.payment.comparing.command;

import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.report.dom.payment.comparing.ComparingFormDetail;
import nts.uk.ctx.pr.report.dom.payment.comparing.ComparingFormDetailRepository;
import nts.uk.ctx.pr.report.dom.payment.comparing.ComparingFormHeader;
import nts.uk.ctx.pr.report.dom.payment.comparing.ComparingFormHeaderRepository;
import nts.uk.shr.com.context.AppContexts;

@RequestScoped
@Transactional
public class InsertComparingFormCommandHandler extends CommandHandler<InsertComparingFormCommand> {
	@Inject
	private ComparingFormHeaderRepository comparingFormHeaderRepository;
	@Inject
	private ComparingFormDetailRepository comparingFormDetailRepository;

	@Override
	protected void handle(CommandHandlerContext<InsertComparingFormCommand> context) {
		String companyCode = AppContexts.user().companyCode();
		InsertComparingFormCommand insertCommand = context.getCommand();

		if (this.comparingFormHeaderRepository.getComparingFormHeader(companyCode, insertCommand.getFormCode())
				.isPresent()) {
			throw new BusinessException("1");
		}
		ComparingFormHeader newComparingFormHeader = ComparingFormHeader.createFromJavaType(companyCode,
				insertCommand.getFormCode(), insertCommand.getFormName());
		this.comparingFormHeaderRepository.insertComparingFormHeader(newComparingFormHeader);

		List<ComparingFormDetail> comparingFormDetailList = insertCommand
				.getComparingFormDetailList().stream().map(s -> ComparingFormDetail.createFromJavaType(companyCode,
						insertCommand.getFormCode(), s.getCategoryAtr(), s.getItemCode(), s.getDispOrder()))
				.collect(Collectors.toList());

		this.comparingFormDetailRepository.insertComparingFormDetail(comparingFormDetailList);

	}

}
