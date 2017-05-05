package nts.uk.ctx.pr.report.app.payment.comparing.command;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.report.dom.payment.comparing.settingoutputitem.ComparingFormDetail;
import nts.uk.ctx.pr.report.dom.payment.comparing.settingoutputitem.ComparingFormDetailRepository;
import nts.uk.ctx.pr.report.dom.payment.comparing.settingoutputitem.ComparingFormHeader;
import nts.uk.ctx.pr.report.dom.payment.comparing.settingoutputitem.ComparingFormHeaderRepository;
import nts.uk.shr.com.context.AppContexts;

@RequestScoped
@Transactional
public class UpdateComparingFormCommandHandler extends CommandHandler<UpdateComparingFormCommand> {

	@Inject
	private ComparingFormHeaderRepository comparingFormHeaderRepository;
	@Inject
	private ComparingFormDetailRepository comparingFormDetailRepository;

	@Override
	protected void handle(CommandHandlerContext<UpdateComparingFormCommand> context) {
		String companyCode = AppContexts.user().companyCode();
		UpdateComparingFormCommand updateCommand = context.getCommand();

		Optional<ComparingFormHeader> comparingFormHeader = this.comparingFormHeaderRepository
				.getComparingFormHeader(companyCode, updateCommand.getFormCode());

		if (!comparingFormHeader.isPresent()) {
			throw new BusinessException("2");
		}
		ComparingFormHeader newComparingFormHeader = ComparingFormHeader.createFromJavaType(companyCode,
				updateCommand.getFormCode(), updateCommand.getFormName());

		this.comparingFormHeaderRepository.updateComparingFormHeader(newComparingFormHeader);
		this.comparingFormDetailRepository.deleteComparingFormDetail(companyCode, updateCommand.getFormCode());

		List<ComparingFormDetail> comparingFormDetailList = updateCommand
				.getComparingFormDetailList().stream().map(s -> ComparingFormDetail.createFromJavaType(companyCode,
						updateCommand.getFormCode(), s.getCategoryAtr(), s.getItemCode(), s.getDispOrder()))
				.collect(Collectors.toList());
		this.comparingFormDetailRepository.insertComparingFormDetail(comparingFormDetailList);
	}
}
