package nts.uk.ctx.at.function.app.command.dailyperformanceformat;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.AuthorityDailyPerformanceFormat;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.AuthorityFomatDaily;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.AuthorityFormatInitialDisplay;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.AuthorityFormatSheet;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.primitivevalue.DailyPerformanceFormatCode;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.primitivevalue.DailyPerformanceFormatName;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.AuthorityDailyPerformanceFormatRepository;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.AuthorityFormatDailyRepository;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.AuthorityFormatInitialDisplayRepository;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.AuthorityFormatSheetRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

@Stateless
public class AddAuthorityDailyCommandHandler extends CommandHandler<AddAuthorityDailyCommand> {

	@Inject
	private AuthorityFormatDailyRepository authorityFormatDailyRepository;

	@Inject
	private AuthorityFormatSheetRepository authorityFormatSheetRepository;

	@Inject
	private AuthorityFormatInitialDisplayRepository authorityFormatInitialDisplayRepository;

	@Inject
	private AuthorityDailyPerformanceFormatRepository authorityDailyPerformanceFormatRepository;

	@Override
	protected void handle(CommandHandlerContext<AddAuthorityDailyCommand> context) {
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();

		AddAuthorityDailyCommand command = context.getCommand();

		List<AuthorityFomatDaily> authorityFomatDailies = command.getDailyAttendanceAuthorityDetailDtos().stream()
				.map(f -> {
					return new AuthorityFomatDaily(companyId,
							new DailyPerformanceFormatCode(command.getDailyPerformanceFormatCode()),
							f.getAttendanceItemId(), command.getSheetNo(), f.getOrder(), f.getColumnWidth());
				}).collect(Collectors.toList());

		AuthorityDailyPerformanceFormat authorityDailyPerformanceFormat = new AuthorityDailyPerformanceFormat(companyId,
				new DailyPerformanceFormatCode(command.getDailyPerformanceFormatCode()),
				new DailyPerformanceFormatName(command.getDailyPerformanceFormatName()));

		if (this.authorityDailyPerformanceFormatRepository
				.checkExistCode(companyId,new DailyPerformanceFormatCode(command.getDailyPerformanceFormatCode()))) {
			throw new BusinessException("Msg_3");
		} else {
			this.authorityFormatDailyRepository.add(authorityFomatDailies);
			this.authorityDailyPerformanceFormatRepository.add(authorityDailyPerformanceFormat);
		}

		AuthorityFormatSheet authorityFormatSheet = new AuthorityFormatSheet(companyId,
				new DailyPerformanceFormatCode(command.getDailyPerformanceFormatCode()), command.getSheetNo(),
				command.getSheetName());
		
		if(this.authorityFormatSheetRepository.checkExistData(companyId, new DailyPerformanceFormatCode(command.getDailyPerformanceFormatCode()), command.getSheetNo())){
			this.authorityFormatSheetRepository.update(authorityFormatSheet);
		} else {
			this.authorityFormatSheetRepository.add(authorityFormatSheet);	
		}

		AuthorityFormatInitialDisplay authorityFormatInitialDisplay = new AuthorityFormatInitialDisplay(companyId,
				new DailyPerformanceFormatCode(command.getDailyPerformanceFormatCode()));
		if (command.getIsDefaultInitial() == 1) {
			if (!this.authorityFormatInitialDisplayRepository.checkExistDataByCompanyId(companyId)) {
				this.authorityFormatInitialDisplayRepository.add(authorityFormatInitialDisplay);
			} else {
				this.authorityFormatInitialDisplayRepository.update(companyId,
						new DailyPerformanceFormatCode(command.getDailyPerformanceFormatCode()));
			}
		}
	}

}
