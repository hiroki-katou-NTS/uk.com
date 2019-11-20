package nts.uk.ctx.at.function.app.command.dailyperformanceformat;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.AuthorityDailyPerformanceSFormat;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.AuthorityFormatInitialDisplay;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.AuthoritySFomatDaily;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.AuthoritySFomatMonthly;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.enums.PCSmartPhoneAtt;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.primitivevalue.DailyPerformanceFormatCode;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.primitivevalue.DailyPerformanceFormatName;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.AuthorityDailySItemRepository;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.AuthorityDailyPerformanceSFormatRepository;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.AuthorityFormatInitialDisplayRepository;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.AuthorityFormatMonthlySRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * 
 * @author anhdt
 *
 */
@Stateless
public class AddAuthDaiSCommandHandler extends CommandHandler<AddAuthorityDailySCommand> {

	@Inject
	private AuthorityDailySItemRepository authDailyItemRepository;

	@Inject
	private AuthorityFormatInitialDisplayRepository authorityFormatInitialDisplayRepository;

	@Inject
	private AuthorityFormatMonthlySRepository authorityFormatMonthlyRepository;

	@Inject
	private AuthorityDailyPerformanceSFormatRepository authDailyPerMobFormatRepository;

	/*
	 * フォーマットを登録する
	 */
	@Override
	protected void handle(CommandHandlerContext<AddAuthorityDailySCommand> context) {
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();

		AddAuthorityDailyCommand dailyCommand = context.getCommand().getAuthorityDailyCommand();
		AddAuthorityMonthlyCommand monthlyCommand = context.getCommand().getAuthorityMonthlyCommand();

		List<AuthoritySFomatDaily> authorityFomatDailies = dailyCommand.getDailyAttendanceAuthorityDetailDtos().stream()
				.map(f -> {
					return new AuthoritySFomatDaily(f.getAttendanceItemId(), f.getOrder(), f.getColumnWidth());
				}).collect(Collectors.toList());

		AuthorityDailyPerformanceSFormat authDaiPerFormat = new AuthorityDailyPerformanceSFormat(
				companyId,
				new DailyPerformanceFormatCode(dailyCommand.getDailyPerformanceFormatCode()),
				new DailyPerformanceFormatName(dailyCommand.getDailyPerformanceFormatName()));
		/*
		 * ドメインモデル「会社の日別実績の修正のフォーマット（スマホ版）」の事前条件をチェックする
		 */
		if (this.authDailyPerMobFormatRepository.checkExistCode(companyId,
				new DailyPerformanceFormatCode(dailyCommand.getDailyPerformanceFormatCode()))) {
			throw new BusinessException("Msg_3");
		} else {
			String dailyCode = dailyCommand.getDailyPerformanceFormatCode();
			/*
			 * ドメインモデル「会社の日別実績の修正のフォーマット（スマホ版）」を登録する
			 */
			this.authDailyItemRepository.add(companyId, dailyCode, authorityFomatDailies);
			this.authDailyPerMobFormatRepository.add(authDaiPerFormat);
		}

		AuthorityFormatInitialDisplay authorityFormatInitialDisplay = new AuthorityFormatInitialDisplay(companyId,
				new DailyPerformanceFormatCode(dailyCommand.getDailyPerformanceFormatCode()),
				PCSmartPhoneAtt.SMART_PHONE);

		/* ドメインモデル「会社の日別実績の修正のフォーマット（スマホ版）」を登録する */
		if (dailyCommand.getIsDefaultInitial() == 1) {
			if (!this.authorityFormatInitialDisplayRepository.checkExistDataByCompanyId(companyId,
					PCSmartPhoneAtt.SMART_PHONE)) {
				this.authorityFormatInitialDisplayRepository.add(authorityFormatInitialDisplay);
			} else {
				this.authorityFormatInitialDisplayRepository.update(companyId,
						new DailyPerformanceFormatCode(dailyCommand.getDailyPerformanceFormatCode()),
						PCSmartPhoneAtt.SMART_PHONE);
			}
		}
		// add Format monthly
		List<AuthoritySFomatMonthly> authorityFomatMonthlies = monthlyCommand.getDailyAttendanceAuthorityDetailDtos()
				.stream().map(f -> {
					return new AuthoritySFomatMonthly(f.getAttendanceItemId(), f.getOrder(), f.getColumnWidth());
				}).collect(Collectors.toList());

		if (this.authorityFormatMonthlyRepository.checkExistCode(companyId,
				new DailyPerformanceFormatCode(monthlyCommand.getDailyPerformanceFormatCode()))) {
			throw new BusinessException("#Msg_3");
		} else {
			this.authorityFormatMonthlyRepository.add(companyId,
					new DailyPerformanceFormatCode(monthlyCommand.getDailyPerformanceFormatCode()),
					authorityFomatMonthlies);
		}
	}

}
