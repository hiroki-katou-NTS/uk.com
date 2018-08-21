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
public class UpdateAutDaiFormatCommandHandler extends CommandHandler<UpdateAuthorityDailyFormatCommand> {

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
	protected void handle(CommandHandlerContext<UpdateAuthorityDailyFormatCommand> context) {
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();

		UpdateAuthorityDailyFormatCommand command = context.getCommand();
		
		if (command.getAuthorityDailyCommand().getDailyAttendanceAuthorityDetailDtos().isEmpty()) {
			throw new BusinessException("Msg_920");
		}
		
		// update daily
		// List attendanceItemId in DB
		List<Integer> attendanceItemIdsInDB = this.authorityFormatDailyRepository
			.getAuthorityFormatDailyDetail(companyId,
					new DailyPerformanceFormatCode(
							command.getAuthorityDailyCommand().getDailyPerformanceFormatCode()),
					command.getAuthorityDailyCommand().getSheetNo())
			.stream().map(f -> {
				return f.getAttendanceItemId();
			}).collect(Collectors.toList());
		
		if(attendanceItemIdsInDB.isEmpty()) {
			int size = authorityFormatSheetRepository.findByCode(companyId, command.getAuthorityDailyCommand().getDailyPerformanceFormatCode()).size();
			command.getAuthorityDailyCommand().setSheetNo(BigDecimal.valueOf(size+1));
		}
		
		

		// List attendanceItemId from UI
		List<Integer> attendanceItemIdsInUI = command.getAuthorityDailyCommand().getDailyAttendanceAuthorityDetailDtos()
				.stream().map(f -> {
					return f.getAttendanceItemId();
				}).collect(Collectors.toList());

		// List attendanceItemId has been removed from List UI compare List from
		// DB
		List<Integer> attendanceItemIdRemove = attendanceItemIdsInDB.stream()
				.filter(f -> !attendanceItemIdsInUI.contains(f)).collect(Collectors.toList());

		// List attendanceItemId has been added from List UI compare List from
		// DB
		List<Integer> attendanceItemIdAdd = attendanceItemIdsInUI.stream()
				.filter(item -> !attendanceItemIdsInDB.contains(item)).collect(Collectors.toList());

		// remove all of data has removed in list attendanceId from UI
		if (!attendanceItemIdRemove.isEmpty()) {
			this.authorityFormatDailyRepository.deleteExistData( companyId,command.getAuthorityDailyCommand().getDailyPerformanceFormatCode(),command.getAuthorityDailyCommand().getSheetNo(),attendanceItemIdRemove);
		}

		// List Data Update from UI compare DB (exist in DB)
		List<AuthorityFomatDaily> authorityFormatDailyUpdates = command.getAuthorityDailyCommand()
				.getDailyAttendanceAuthorityDetailDtos().stream()
				.filter(item -> !attendanceItemIdAdd.contains(item.getAttendanceItemId())).map(f -> {
					return new AuthorityFomatDaily(companyId,
							new DailyPerformanceFormatCode(
									command.getAuthorityDailyCommand().getDailyPerformanceFormatCode()),
							f.getAttendanceItemId(), command.getAuthorityDailyCommand().getSheetNo(), f.getOrder(),
							f.getColumnWidth());
				}).collect(Collectors.toList());
		// List Data Add from UI (just added in UI)
		List<AuthorityFomatDaily> authorityFormatDailyAdds = command.getAuthorityDailyCommand()
				.getDailyAttendanceAuthorityDetailDtos().stream()
				.filter(item -> attendanceItemIdAdd.contains(item.getAttendanceItemId())).map(f -> {
					return new AuthorityFomatDaily(companyId,
							new DailyPerformanceFormatCode(
									command.getAuthorityDailyCommand().getDailyPerformanceFormatCode()),
							f.getAttendanceItemId(), command.getAuthorityDailyCommand().getSheetNo(), f.getOrder(),
							f.getColumnWidth());
				}).collect(Collectors.toList());

		// add all of data has added in list attendanceId
		if (!authorityFormatDailyAdds.isEmpty()) {
			this.authorityFormatDailyRepository.add(authorityFormatDailyAdds);
		}

		// update data has changed in list attendanceId
		authorityFormatDailyUpdates.forEach(f -> {
			this.authorityFormatDailyRepository.update(f);
		});

		AuthorityFormatSheet authorityFormatSheet = new AuthorityFormatSheet(companyId,
				new DailyPerformanceFormatCode(command.getAuthorityDailyCommand().getDailyPerformanceFormatCode()),
				command.getAuthorityDailyCommand().getSheetNo(), command.getAuthorityDailyCommand().getSheetName());
		if (this.authorityFormatSheetRepository.checkExistData(companyId,
				new DailyPerformanceFormatCode(command.getAuthorityDailyCommand().getDailyPerformanceFormatCode()),
				command.getAuthorityDailyCommand().getSheetNo())) {
			this.authorityFormatSheetRepository.update(authorityFormatSheet);
		} else {
			this.authorityFormatSheetRepository.add(authorityFormatSheet);
		}

		AuthorityDailyPerformanceFormat authorityDailyPerformanceFormat = new AuthorityDailyPerformanceFormat(companyId,
				new DailyPerformanceFormatCode(command.getAuthorityDailyCommand().getDailyPerformanceFormatCode()),
				new DailyPerformanceFormatName(command.getAuthorityDailyCommand().getDailyPerformanceFormatName()));

		this.authorityDailyPerformanceFormatRepository.update(authorityDailyPerformanceFormat);

		AuthorityFormatInitialDisplay authorityFormatInitialDisplay = new AuthorityFormatInitialDisplay(companyId,
				new DailyPerformanceFormatCode(command.getAuthorityDailyCommand().getDailyPerformanceFormatCode()));
		if (command.getAuthorityDailyCommand().getIsDefaultInitial() == 1) {
			authorityFormatInitialDisplayRepository.removeByCid(companyId);
			this.authorityFormatInitialDisplayRepository.add(authorityFormatInitialDisplay);
//			if (!this.authorityFormatInitialDisplayRepository.checkExistDataByCompanyId(companyId)) {
//				this.authorityFormatInitialDisplayRepository.add(authorityFormatInitialDisplay);
//			} else {
//				this.authorityFormatInitialDisplayRepository.update(companyId, new DailyPerformanceFormatCode(
//						command.getAuthorityDailyCommand().getDailyPerformanceFormatCode()));
//			}
		}

		// update Monthly
		// List attendanceItemId in DB
		List<Integer> attendanceItemIdsInDBMonthly = this.authorityFormatMonthlyRepository
				.getMonthlyDetail(companyId,
						new DailyPerformanceFormatCode(
								command.getAuthorityMonthlyCommand().getDailyPerformanceFormatCode()))
				.stream().map(f -> {
					return f.getAttendanceItemId();
				}).collect(Collectors.toList());

		// List attendanceItemId from UI
		List<Integer> attendanceItemIdsMonthly = command.getAuthorityMonthlyCommand()
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
			this.authorityFormatMonthlyRepository.deleteExistData(companyId,command.getAuthorityMonthlyCommand().getDailyPerformanceFormatCode(),attendanceItemIdRemoveMonthly);
		}

		// List Data Update from UI compare DB (exist in DB)
		List<AuthorityFomatMonthly> authorityFomatMonthlyUpdates = command.getAuthorityMonthlyCommand()
				.getDailyAttendanceAuthorityDetailDtos().stream()
				.filter(f -> !attendanceItemIdAddMonthly.contains(f.getAttendanceItemId())).map(f -> {
					return new AuthorityFomatMonthly(companyId,
							new DailyPerformanceFormatCode(
									command.getAuthorityMonthlyCommand().getDailyPerformanceFormatCode()),
							f.getAttendanceItemId(), f.getOrder(), f.getColumnWidth());
				}).collect(Collectors.toList());

		// List Data Add from UI (just added in UI)
		List<AuthorityFomatMonthly> authorityFomatMonthlyAdds = command.getAuthorityMonthlyCommand()
				.getDailyAttendanceAuthorityDetailDtos().stream()
				.filter(f -> attendanceItemIdAddMonthly.contains(f.getAttendanceItemId())).map(f -> {
					return new AuthorityFomatMonthly(companyId,
							new DailyPerformanceFormatCode(
									command.getAuthorityMonthlyCommand().getDailyPerformanceFormatCode()),
							f.getAttendanceItemId(), f.getOrder(), f.getColumnWidth());
				}).collect(Collectors.toList());

		// add all of data has added in list attendanceId
		if (!authorityFomatMonthlyAdds.isEmpty()) {
			this.authorityFormatMonthlyRepository.add(authorityFomatMonthlyAdds);
		}

		// update data has changed in list attendanceId
		authorityFomatMonthlyUpdates.forEach(f -> {
			this.authorityFormatMonthlyRepository.update(f);
		});

	}

}
