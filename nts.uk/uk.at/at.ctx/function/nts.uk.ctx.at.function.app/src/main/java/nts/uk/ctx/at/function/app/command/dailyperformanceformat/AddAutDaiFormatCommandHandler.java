package nts.uk.ctx.at.function.app.command.dailyperformanceformat;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.AuthorityDailyPerformanceFormat;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.AuthorityFomatDaily;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.AuthorityFomatMonthly;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.AuthorityFormatInitialDisplay;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.AuthorityFormatSheet;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.primitivevalue.DailyPerformanceFormatCode;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.primitivevalue.DailyPerformanceFormatName;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.AuthorityDailyPerformanceFormatRepository;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.AuthorityFormatDailyRepository;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.AuthorityFormatInitialDisplayRepository;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.AuthorityFormatMonthlyRepository;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.AuthorityFormatSheetRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

@Stateless
public class AddAutDaiFormatCommandHandler extends CommandHandler<AddAuthorityDailyFormatCommand> {

	@Inject
	private AuthorityFormatDailyRepository authorityFormatDailyRepository;
 
	@Inject
	private AuthorityFormatSheetRepository authorityFormatSheetRepository;

	@Inject
	private AuthorityFormatInitialDisplayRepository authorityFormatInitialDisplayRepository;

	@Inject
	private AuthorityDailyPerformanceFormatRepository authorityDailyPerformanceFormatRepository;
	
	@Inject
	private AuthorityFormatMonthlyRepository authorityFormatMonthlyRepository;

	@Override
	protected void handle(CommandHandlerContext<AddAuthorityDailyFormatCommand> context) {
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();

		AddAuthorityDailyFormatCommand command = context.getCommand();
		command.getAuthorityDailyCommand().setSheetNo(BigDecimal.valueOf(1));
		
		if (command.getAuthorityDailyCommand().getDailyAttendanceAuthorityDetailDtos().isEmpty()) {
			throw new BusinessException("Msg_920");
		}

		// add Format Daily
		List<AuthorityFomatDaily> authorityFomatDailies = command.getAuthorityDailyCommand().getDailyAttendanceAuthorityDetailDtos().stream()
				.map(f -> {
					return new AuthorityFomatDaily(companyId,
							new DailyPerformanceFormatCode(command.getAuthorityDailyCommand().getDailyPerformanceFormatCode()),
							f.getAttendanceItemId(), command.getAuthorityDailyCommand().getSheetNo(), f.getOrder(), f.getColumnWidth());
				}).collect(Collectors.toList());

		AuthorityDailyPerformanceFormat authorityDailyPerformanceFormat = new AuthorityDailyPerformanceFormat(companyId,
				new DailyPerformanceFormatCode(command.getAuthorityDailyCommand().getDailyPerformanceFormatCode()),
				new DailyPerformanceFormatName(command.getAuthorityDailyCommand().getDailyPerformanceFormatName()));

		if (this.authorityDailyPerformanceFormatRepository
				.checkExistCode(companyId,new DailyPerformanceFormatCode(command.getAuthorityDailyCommand().getDailyPerformanceFormatCode()))) {
			throw new BusinessException("Msg_3");
		} else {
			this.authorityFormatDailyRepository.add(authorityFomatDailies);
			this.authorityDailyPerformanceFormatRepository.add(authorityDailyPerformanceFormat);
		}

		AuthorityFormatSheet authorityFormatSheet = new AuthorityFormatSheet(companyId,
				new DailyPerformanceFormatCode(command.getAuthorityDailyCommand().getDailyPerformanceFormatCode()), command.getAuthorityDailyCommand().getSheetNo(),
				command.getAuthorityDailyCommand().getSheetName());
		
		if(this.authorityFormatSheetRepository.checkExistData(companyId, new DailyPerformanceFormatCode(command.getAuthorityDailyCommand().getDailyPerformanceFormatCode()), command.getAuthorityDailyCommand().getSheetNo())){
			this.authorityFormatSheetRepository.update(authorityFormatSheet);
		} else {
			this.authorityFormatSheetRepository.add(authorityFormatSheet);	
		}

		AuthorityFormatInitialDisplay authorityFormatInitialDisplay = new AuthorityFormatInitialDisplay(companyId,
				new DailyPerformanceFormatCode(command.getAuthorityDailyCommand().getDailyPerformanceFormatCode()));
		if (command.getAuthorityDailyCommand().getIsDefaultInitial() == 1) {
			authorityFormatInitialDisplayRepository.removeByCid(companyId);
			this.authorityFormatInitialDisplayRepository.add(authorityFormatInitialDisplay);
//			if (!this.authorityFormatInitialDisplayRepository.checkExistDataByCompanyId(companyId)) {
//				this.authorityFormatInitialDisplayRepository.add(authorityFormatInitialDisplay);
//			} else {
//				this.authorityFormatInitialDisplayRepository.update(companyId,
//						new DailyPerformanceFormatCode(command.getAuthorityDailyCommand().getDailyPerformanceFormatCode()));
//			}
		}
		
		// add Format monthly	
		List<AuthorityFomatMonthly> authorityFomatMonthlies = command.getAuthorityMonthlyCommand().getDailyAttendanceAuthorityDetailDtos().stream()
				.map(f -> {
					return new AuthorityFomatMonthly(companyId,
							new DailyPerformanceFormatCode(command.getAuthorityMonthlyCommand().getDailyPerformanceFormatCode()),
							f.getAttendanceItemId(),
							f.getOrder(),
							f.getColumnWidth());
				}).collect(Collectors.toList());

		if (this.authorityFormatMonthlyRepository.checkExistCode(companyId,new DailyPerformanceFormatCode(command.getAuthorityMonthlyCommand().getDailyPerformanceFormatCode()))) {
			throw new BusinessException("#Msg_3");
		} else {
			this.authorityFormatMonthlyRepository.add(authorityFomatMonthlies);
		}
	}

}
