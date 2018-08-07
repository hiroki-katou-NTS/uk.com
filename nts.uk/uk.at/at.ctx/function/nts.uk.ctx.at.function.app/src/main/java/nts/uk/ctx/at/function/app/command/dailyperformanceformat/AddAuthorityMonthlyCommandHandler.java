package nts.uk.ctx.at.function.app.command.dailyperformanceformat;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.AuthorityFomatMonthly;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.primitivevalue.DailyPerformanceFormatCode;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.AuthorityFormatMonthlyRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

@Stateless
public class AddAuthorityMonthlyCommandHandler extends CommandHandler<AddAuthorityMonthlyCommand> {

	@Inject
	private AuthorityFormatMonthlyRepository authorityFormatMonthlyRepository;

	@Override
	protected void handle(CommandHandlerContext<AddAuthorityMonthlyCommand> context) {

		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();

		AddAuthorityMonthlyCommand command = context.getCommand();

		List<AuthorityFomatMonthly> authorityFomatMonthlies = command.getDailyAttendanceAuthorityDetailDtos().stream()
				.map(f -> {
					return new AuthorityFomatMonthly(companyId,
							new DailyPerformanceFormatCode(command.getDailyPerformanceFormatCode()),
							f.getAttendanceItemId(),
							f.getOrder(),
							f.getColumnWidth());
				}).collect(Collectors.toList());

		if (this.authorityFormatMonthlyRepository.checkExistCode(companyId,new DailyPerformanceFormatCode(command.getDailyPerformanceFormatCode()))) {
			throw new BusinessException("#Msg_3");
		} else {
			this.authorityFormatMonthlyRepository.add(authorityFomatMonthlies);
		}

	}

}
