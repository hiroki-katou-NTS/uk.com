package nts.uk.ctx.at.function.app.command.dailyperformanceformat;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.AuthorityFomatMonthly;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.primitivevalue.DailyPerformanceFormatCode;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.AuthorityFormatMonthlyRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

@Stateless
public class UpdateAuthorityMonthlyCommandHandler extends CommandHandler<UpdateAuthorityMonthlyCommand> {

	@Inject
	private AuthorityFormatMonthlyRepository authorityFormatMonthlyRepository;

	@Override
	protected void handle(CommandHandlerContext<UpdateAuthorityMonthlyCommand> context) {

		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();

		UpdateAuthorityMonthlyCommand command = context.getCommand();

		// List attendanceItemId in DB
		List<Integer> attendanceItemIdsInDB = this.authorityFormatMonthlyRepository
				.getMonthlyDetail(companyId, new DailyPerformanceFormatCode(command.getDailyPerformanceFormatCode())).stream().map(f -> {
					return f.getAttendanceItemId();
				}).collect(Collectors.toList());

		// List attendanceItemId from UI
		List<Integer> attendanceItemIds = command.getDailyAttendanceAuthorityDetailDtos().stream().map(f -> {
			return f.getAttendanceItemId();
		}).collect(Collectors.toList());

		// List attendanceItemId has been removed from list UI compare List from
		// DB
		List<Integer> attendanceItemIdRemove = attendanceItemIdsInDB.stream()
				.filter(item -> !attendanceItemIds.contains(item)).collect(Collectors.toList());

		// List attendanceItemId has been added from List UI compare List from
		// DB
		List<Integer> attendanceItemIdAdd = attendanceItemIds.stream()
				.filter(item -> !attendanceItemIdsInDB.contains(item)).collect(Collectors.toList());

		if (!attendanceItemIdRemove.isEmpty()) {
			this.authorityFormatMonthlyRepository.deleteExistData(companyId,command.getDailyPerformanceFormatCode(), attendanceItemIdRemove);
		}

		// List Data Update from UI compare DB (exist in DB)
		List<AuthorityFomatMonthly> authorityFomatMonthlyUpdates = command.getDailyAttendanceAuthorityDetailDtos()
				.stream().filter(f -> !attendanceItemIdAdd.contains(f.getAttendanceItemId())).map(f -> {
					return new AuthorityFomatMonthly(companyId,
							new DailyPerformanceFormatCode(command.getDailyPerformanceFormatCode()),
							f.getAttendanceItemId(),
							f.getOrder(),
							f.getColumnWidth());
				}).collect(Collectors.toList());

		// List Data Add from UI (just added in UI)
		List<AuthorityFomatMonthly> authorityFomatMonthlyAdds = command.getDailyAttendanceAuthorityDetailDtos().stream()
				.filter(f -> attendanceItemIdAdd.contains(f.getAttendanceItemId())).map(f -> {
					return new AuthorityFomatMonthly(companyId,
							new DailyPerformanceFormatCode(command.getDailyPerformanceFormatCode()),
							f.getAttendanceItemId(),
							f.getOrder(),
							f.getColumnWidth());
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
