package nts.uk.ctx.at.function.app.command.dailyperformanceformat;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.AuthorityDailyPerformanceSFormat;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.AuthorityFomatMonthly;
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
 * @author anhdt
 * 
 *         フォーマットを登録する
 * 
 */

@Stateless
public class UpdateAutDaiSFormatCommandHandler extends CommandHandler<UpdateAuthorityDailySFormatCommand> {

	@Inject
	private AuthorityDailySItemRepository authDailyItemRepository;
	
	@Inject
	private AuthorityFormatMonthlySRepository authorityFormatMonthlyRepository;

	@Inject
	private AuthorityFormatInitialDisplayRepository authorityFormatInitialDisplayRepository;

	@Inject
	private AuthorityDailyPerformanceSFormatRepository authDailyPerMobFormatRepository;

	@Override
	protected void handle(CommandHandlerContext<UpdateAuthorityDailySFormatCommand> context) {
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();

		UpdateAuthorityDailySFormatCommand command = context.getCommand();
		UpdateAuthoritySDailyCommand updateCommand = command.getAuthorityDailyCommand();

		if (updateCommand.getDailyAttendanceAuthorityDetailDtos().isEmpty()) {
			throw new BusinessException("Msg_920");
		}

		// update daily
		// List attendanceItemId in DB
		List<Integer> attendanceItemIdsInDB = this.authDailyItemRepository
				.getAuthorityFormatDailyDetail(companyId,
						new DailyPerformanceFormatCode(updateCommand.getDailyPerformanceFormatCode()))
				.stream()
				.map(f -> {
					return f.getAttendanceItemId();
				}).collect(Collectors.toList());

		// List attendanceItemId from UI
		List<Integer> attendanceItemIdsInUI = updateCommand
				.getDailyAttendanceAuthorityDetailDtos()
				.stream()
				.map(f -> {
					return f.getAttendanceItemId();
				}).collect(Collectors.toList());

		// List attendanceItemId has been removed from List UI compare List from
		// DB
		List<Integer> attendanceItemIdRemove = attendanceItemIdsInDB
				.stream()
				.filter(f -> !attendanceItemIdsInUI.contains(f))
				.collect(Collectors.toList());

		// List attendanceItemId has been added from List UI compare List from
		// DB
		List<Integer> attendanceItemIdAdd = attendanceItemIdsInUI
				.stream()
				.filter(item -> !attendanceItemIdsInDB.contains(item))
				.collect(Collectors.toList());

		// remove all of data has removed in list attendanceId from UI
		if (!attendanceItemIdRemove.isEmpty()) {
			this.authDailyItemRepository.deleteExistData(companyId, updateCommand.getDailyPerformanceFormatCode(),
					attendanceItemIdRemove);
		}

		// List Data Update from UI compare DB (exist in DB)
		List<AuthoritySFomatDaily> authorityFormatDailyUpdates = updateCommand.getDailyAttendanceAuthorityDetailDtos()
				.stream().filter(item -> !attendanceItemIdAdd.contains(item.getAttendanceItemId())).map(f -> {
					return new AuthoritySFomatDaily(f.getAttendanceItemId(), f.getOrder(), f.getColumnWidth());
				}).collect(Collectors.toList());

		// List Data Add from UI (just added in UI)
		List<AuthoritySFomatDaily> authorityFormatDailyAdds = updateCommand.getDailyAttendanceAuthorityDetailDtos()
				.stream().filter(item -> attendanceItemIdAdd.contains(item.getAttendanceItemId())).map(f -> {
					return new AuthoritySFomatDaily(f.getAttendanceItemId(), f.getOrder(), f.getColumnWidth());
				}).collect(Collectors.toList());

		// add all of data has added in list attendanceId
		if (!authorityFormatDailyAdds.isEmpty()) {
			this.authDailyItemRepository.add(companyId, updateCommand.getDailyPerformanceFormatCode(),
					authorityFormatDailyAdds);
		}

		// update data has changed in list attendanceId
		authorityFormatDailyUpdates.forEach(f -> {
			this.authDailyItemRepository.update(companyId, updateCommand.getDailyPerformanceFormatCode(), f);
		});

		AuthorityDailyPerformanceSFormat authorityDailyPerformanceFormat = new AuthorityDailyPerformanceSFormat(
				companyId,
				new DailyPerformanceFormatCode(command.getAuthorityDailyCommand().getDailyPerformanceFormatCode()),
				new DailyPerformanceFormatName(command.getAuthorityDailyCommand().getDailyPerformanceFormatName()));

		this.authDailyPerMobFormatRepository.update(authorityDailyPerformanceFormat);

		/* パラメータ「初期表示区分」をチェックする */
		AuthorityFormatInitialDisplay authorityFormatInitialDisplay = new AuthorityFormatInitialDisplay(companyId,
				new DailyPerformanceFormatCode(command.getAuthorityDailyCommand().getDailyPerformanceFormatCode()),
				PCSmartPhoneAtt.SMART_PHONE);
		if (command.getAuthorityDailyCommand().getIsDefaultInitial() == 1) {
			authorityFormatInitialDisplayRepository.removeByCid(companyId, PCSmartPhoneAtt.SMART_PHONE);
			this.authorityFormatInitialDisplayRepository.add(authorityFormatInitialDisplay);
		}

		// update Monthly
		// List attendanceItemId in DB
		UpdateAuthorityMonthlyCommand monthlyCommand = command.getAuthorityMonthlyCommand();
//		AuthoritySFomatMonthly
		List<Integer> attendanceItemIdsInDBMonthly = this.authorityFormatMonthlyRepository
				.getMonthlyDetail(companyId,
						new DailyPerformanceFormatCode(
								monthlyCommand.getDailyPerformanceFormatCode()))
				.stream().map(f -> {
					return f.getAttendanceItemId();
				}).collect(Collectors.toList());

		// List attendanceItemId from UI
		List<Integer> attendanceItemIdsMonthly = monthlyCommand
				.getDailyAttendanceAuthorityDetailDtos().stream().map(f -> {
					return f.getAttendanceItemId();
				}).collect(Collectors.toList());

		// List attendanceItemId has been removed from list UI compare List from
		// DB
		List<Integer> attendanceItemIdRemoveMonthly = attendanceItemIdsInDBMonthly.stream()
				.filter(item -> !attendanceItemIdsMonthly.contains(item)).collect(Collectors.toList());

		// List attendanceItemId has been added from List UI compare List from
		// DB
		List<Integer> attendanceItemIdAddMonthly = attendanceItemIdsMonthly.stream()
				.filter(item -> !attendanceItemIdsInDBMonthly.contains(item)).collect(Collectors.toList());

		if (!attendanceItemIdRemoveMonthly.isEmpty()) {
			this.authorityFormatMonthlyRepository.deleteExistData(companyId,
					monthlyCommand.getDailyPerformanceFormatCode(),
					attendanceItemIdRemoveMonthly);
		}

		// List Data Update from UI compare DB (exist in DB)
		List<AuthoritySFomatMonthly> authorityFomatMonthlyUpdates = monthlyCommand
				.getDailyAttendanceAuthorityDetailDtos().stream()
				.filter(f -> !attendanceItemIdAddMonthly.contains(f.getAttendanceItemId()))
				.map(f -> {
					return new AuthoritySFomatMonthly(f.getAttendanceItemId(), f.getOrder(), f.getColumnWidth());
				}).collect(Collectors.toList());

		// List Data Add from UI (just added in UI)
		List<AuthoritySFomatMonthly> authorityFomatMonthlyAdds = monthlyCommand
				.getDailyAttendanceAuthorityDetailDtos().stream()
				.filter(f -> attendanceItemIdAddMonthly.contains(f.getAttendanceItemId()))
				.map(f -> {
					return new AuthoritySFomatMonthly(f.getAttendanceItemId(), f.getOrder(), f.getColumnWidth());
				}).collect(Collectors.toList());

		// add all of data has added in list attendanceId
		if (!authorityFomatMonthlyAdds.isEmpty()) {
			this.authorityFormatMonthlyRepository.add(companyId, new DailyPerformanceFormatCode(updateCommand.getDailyPerformanceFormatCode()), authorityFomatMonthlyAdds);
		}

		// update data has changed in list attendanceId
		authorityFomatMonthlyUpdates.forEach(f -> {
			this.authorityFormatMonthlyRepository.update(companyId, new DailyPerformanceFormatCode(updateCommand.getDailyPerformanceFormatCode()), f);
		});

	}

}
