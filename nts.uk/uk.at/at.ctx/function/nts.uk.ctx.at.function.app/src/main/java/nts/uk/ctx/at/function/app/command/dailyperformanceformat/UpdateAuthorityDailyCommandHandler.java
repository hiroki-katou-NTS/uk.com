package nts.uk.ctx.at.function.app.command.dailyperformanceformat;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

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
public class UpdateAuthorityDailyCommandHandler extends CommandHandler<UpdateAuthorityDailyCommand> {

	@Inject
	private AuthorityFormatDailyRepository authorityFormatDailyRepository;

	@Inject
	private AuthorityFormatSheetRepository authorityFormatSheetRepository;

	@Inject
	private AuthorityFormatInitialDisplayRepository authorityFormatInitialDisplayRepository;
	
	@Inject
	private AuthorityDailyPerformanceFormatRepository authorityDailyPerformanceFormatRepository;

	@Override
	protected void handle(CommandHandlerContext<UpdateAuthorityDailyCommand> context) {
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();

		UpdateAuthorityDailyCommand command = context.getCommand();

		// List attendanceItemId in DB
		List<Integer> attendanceItemIdsInDB = this.authorityFormatDailyRepository
				.getAuthorityFormatDailyDetail(companyId,
						new DailyPerformanceFormatCode(command.getDailyPerformanceFormatCode()), command.getSheetNo())
				.stream().map(f -> {
					return f.getAttendanceItemId();
				}).collect(Collectors.toList());

		// List attendanceItemId from UI
		List<Integer> attendanceItemIdsInUI = command.getDailyAttendanceAuthorityDetailDtos().stream().map(f -> {
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
			this.authorityFormatDailyRepository.deleteExistData(companyId,command.getDailyPerformanceFormatCode(),command.getSheetNo(),attendanceItemIdRemove);
		}

		// List Data Update from UI compare DB (exist in DB)
		List<AuthorityFomatDaily> authorityFormatDailyUpdates = command.getDailyAttendanceAuthorityDetailDtos().stream()
				.filter(item -> !attendanceItemIdAdd.contains(item.getAttendanceItemId())).map(f -> {
					return new AuthorityFomatDaily(companyId,
							new DailyPerformanceFormatCode(command.getDailyPerformanceFormatCode()),
							f.getAttendanceItemId(), command.getSheetNo(),
							f.getOrder(),
							f.getColumnWidth());
				}).collect(Collectors.toList());
		// List Data Add from UI (just added in UI)
		List<AuthorityFomatDaily> authorityFormatDailyAdds = command.getDailyAttendanceAuthorityDetailDtos().stream()
				.filter(item -> attendanceItemIdAdd.contains(item.getAttendanceItemId())).map(f -> {
					return new AuthorityFomatDaily(companyId,
							new DailyPerformanceFormatCode(command.getDailyPerformanceFormatCode()),
							f.getAttendanceItemId(), command.getSheetNo(),
							f.getOrder(),
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
				new DailyPerformanceFormatCode(command.getDailyPerformanceFormatCode()), command.getSheetNo(),
				command.getSheetName());
		this.authorityFormatSheetRepository.update(authorityFormatSheet);
		
		AuthorityDailyPerformanceFormat authorityDailyPerformanceFormat = new AuthorityDailyPerformanceFormat(companyId,
				new DailyPerformanceFormatCode(command.getDailyPerformanceFormatCode()),
				new DailyPerformanceFormatName(command.getDailyPerformanceFormatName()));
		
		this.authorityDailyPerformanceFormatRepository.update(authorityDailyPerformanceFormat);

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
