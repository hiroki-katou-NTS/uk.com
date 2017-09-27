package nts.uk.ctx.at.function.app.command.dailyperformanceformat;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.AuthorityFomatDaily;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.AuthorityFormatSheet;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.primitivevalue.DailyPerformanceFormatCode;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.primitivevalue.DailyPerformanceFormatName;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.AuthorityFormatDailyRepository;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.AuthorityFormatSheetRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

@Stateless
public class AddAuthorityDailyCommandHandler extends CommandHandler<AddAuthorityDailyCommand> {

	@Inject
	private AuthorityFormatDailyRepository authorityFormatDailyRepository;

	@Inject
	private AuthorityFormatSheetRepository authorityFormatSheetRepository;

	@Override
	protected void handle(CommandHandlerContext<AddAuthorityDailyCommand> context) {
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();

		AddAuthorityDailyCommand command = context.getCommand();

		List<AuthorityFomatDaily> authorityFomatDailies = command.getDailyAttendanceAuthorityDetailDtos().stream()
				.map(f -> {
					return new AuthorityFomatDaily(companyId,
							new DailyPerformanceFormatCode(command.getDailyPerformanceFormatCode()),
							f.getAttendanceItemId(), command.getSheetNo(),
							new DailyPerformanceFormatName(command.getDailyPerformanceFormatName()), f.getOrder(),
							f.getColumnWidth());
				}).collect(Collectors.toList());
		
		if(this.authorityFormatDailyRepository.checkExistCode(new DailyPerformanceFormatCode(command.getDailyPerformanceFormatCode()))){
			throw new BusinessException("#Msg_3");
		} else {
			this.authorityFormatDailyRepository.add(authorityFomatDailies);			
		}

		AuthorityFormatSheet authorityFormatSheet = new AuthorityFormatSheet(companyId,
				new DailyPerformanceFormatCode(command.getDailyPerformanceFormatCode()), command.getSheetNo(),
				command.getSheetName());

		this.authorityFormatSheetRepository.add(authorityFormatSheet);
	}

}
