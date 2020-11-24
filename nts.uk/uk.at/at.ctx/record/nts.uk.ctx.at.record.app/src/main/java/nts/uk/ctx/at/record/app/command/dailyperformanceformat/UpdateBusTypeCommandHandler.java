package nts.uk.ctx.at.record.app.command.dailyperformanceformat;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.BusinessFormatSheet;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.BusinessTypeFormatDaily;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.BusinessTypeFormatMonthly;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.repository.BusinessFormatSheetRepository;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.repository.BusinessTypeFormatDailyRepository;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.repository.BusinessTypeFormatMonthlyRepository;
import nts.uk.ctx.at.shared.dom.workrule.businesstype.BusinessTypeCode;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * 
 * @author nampt
 *
 */
@Stateless
public class UpdateBusTypeCommandHandler extends CommandHandler<UpdateBusTypeCommand> {

	@Inject
	private BusinessTypeFormatDailyRepository businessTypeFormatDailyRepository;

	@Inject
	private BusinessFormatSheetRepository businessFormatSheetRepository;

	@Inject
	private BusinessTypeFormatMonthlyRepository businessTypeFormatMonthlyRepository;

	@Override
	protected void handle(CommandHandlerContext<UpdateBusTypeCommand> context) {
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();

		UpdateBusTypeCommand command = context.getCommand();

		// List attendanceItemId in DB
		List<Integer> attendanceItemIdInDBs = this.businessTypeFormatDailyRepository
				.getBusinessTypeFormatDailyDetail(companyId,
						command.getBusTypeDailyCommand().getBusinesstypeCode(),
						command.getBusTypeDailyCommand().getSheetNo())
				.stream().map(f -> {
					return f.getAttendanceItemId();
				}).collect(Collectors.toList());

		// List attendanceItemId from UI
		List<Integer> attendanceItemIdInUI = command.getBusTypeDailyCommand()
				.getBusinessTypeFormatDetailDtos().stream().map(f -> {
					return f.getAttendanceItemId();
				}).collect(Collectors.toList());

		// List attendanceItemId has been removed from List UI compare List from
		// DB
		List<Integer> attendanceItemIdRemove = attendanceItemIdInDBs.stream()
				.filter(f -> !attendanceItemIdInUI.contains(f)).collect(Collectors.toList());

		// List attendanceItemId has been added from List UI compare List from
		// DB
		List<Integer> attendanceItemIdAdd = attendanceItemIdInUI.stream()
				.filter(item -> !attendanceItemIdInDBs.contains(item)).collect(Collectors.toList());

		// remove all of data has removed in list attendanceId from UI
		if (!attendanceItemIdRemove.isEmpty()) {
			this.businessTypeFormatDailyRepository.deleteExistDataByCode(command.getBusTypeDailyCommand().getBusinesstypeCode(),companyId,command.getBusTypeDailyCommand().getSheetNo().intValue(), attendanceItemIdRemove);
		}

		// List Data Update from UI compare DB (exist in DB)
		List<BusinessTypeFormatDaily> businessTypeFormatDailyUpdates = command.getBusTypeDailyCommand()
				.getBusinessTypeFormatDetailDtos().stream()
				.filter(item -> !attendanceItemIdAdd.contains(item.getAttendanceItemId())).map(f -> {
					return new BusinessTypeFormatDaily(companyId,
							new BusinessTypeCode(command.getBusTypeDailyCommand().getBusinesstypeCode()),
							f.getAttendanceItemId(), command.getBusTypeDailyCommand().getSheetNo(),
							f.getOrder(), f.getColumnWidth());
				}).collect(Collectors.toList());

		// List Data Add from UI (just added in UI)
		List<BusinessTypeFormatDaily> businessTypeFormatDailyAdds = command.getBusTypeDailyCommand()
				.getBusinessTypeFormatDetailDtos().stream()
				.filter(item -> attendanceItemIdAdd.contains(item.getAttendanceItemId())).map(f -> {
					return new BusinessTypeFormatDaily(companyId,
							new BusinessTypeCode(command.getBusTypeDailyCommand().getBusinesstypeCode()),
							f.getAttendanceItemId(), command.getBusTypeDailyCommand().getSheetNo(),
							f.getOrder(), f.getColumnWidth());
				}).collect(Collectors.toList());

		// add all of data has added in list attendanceId
		if (!businessTypeFormatDailyAdds.isEmpty()) {
			this.businessTypeFormatDailyRepository.add(businessTypeFormatDailyAdds);
		}

		// update data has changed in list attendanceId
		businessTypeFormatDailyUpdates.forEach(f -> {
			this.businessTypeFormatDailyRepository.update(f);
		});

		BusinessFormatSheet businessFormatSheetUpdate = new BusinessFormatSheet(companyId,
				new BusinessTypeCode(command.getBusTypeDailyCommand().getBusinesstypeCode()),
				command.getBusTypeDailyCommand().getSheetNo(),
				command.getBusTypeDailyCommand().getSheetName());

		this.businessFormatSheetRepository.update(businessFormatSheetUpdate);

		// update Monthly
		// List attendanceItemId in DB
		List<Integer> attendanceItemIdInDBS = this.businessTypeFormatMonthlyRepository
				.getMonthlyDetail(companyId, command.getBusTypeMonthlyCommand().getBusinesstypeCode())
				.stream().map(f -> {
					return f.getAttendanceItemId();
				}).collect(Collectors.toList());

		// List attendanceItemId from UI
		List<Integer> attendanceItemIds = command.getBusTypeMonthlyCommand()
				.getBusinessTypeFormatDetailDtos().stream().map(f -> {
					return f.getAttendanceItemId();
				}).collect(Collectors.toList());

		// List attendanceItemId has been removed from list UI compare List from
		// DB
		List<Integer> attendanceItemIdMonthlyRemove = attendanceItemIdInDBS.stream()
				.filter(item -> !attendanceItemIds.contains(item)).collect(Collectors.toList());

		// List attendanceItemId has been added from List UI compare List from
		// DB
		List<Integer> attendanceItemIdMonthlyAdd = attendanceItemIds.stream()
				.filter(item -> !attendanceItemIdInDBS.contains(item)).collect(Collectors.toList());

		// remove all of data has removed in list attendanceId from UI
		if (!attendanceItemIdMonthlyRemove.isEmpty()) {
			this.businessTypeFormatMonthlyRepository.deleteExistData(companyId,command.getBusTypeDailyCommand().getBusinesstypeCode(),attendanceItemIdMonthlyRemove);
		}

		// List Data Update from UI compare DB (exist in DB)
		List<BusinessTypeFormatMonthly> businessTypeFormatMonthlyUpdates = command.getBusTypeMonthlyCommand()
				.getBusinessTypeFormatDetailDtos().stream()
				.filter(item -> !attendanceItemIdMonthlyAdd.contains(item.getAttendanceItemId())).map(f -> {
					return new BusinessTypeFormatMonthly(companyId,
							new BusinessTypeCode(command.getBusTypeMonthlyCommand().getBusinesstypeCode()),
							f.getAttendanceItemId(), f.getOrder(), f.getColumnWidth());
				}).collect(Collectors.toList());

		// List Data Add from UI (just added in UI)
		List<BusinessTypeFormatMonthly> businessTypeFormatMonthlyAdds = command.getBusTypeMonthlyCommand()
				.getBusinessTypeFormatDetailDtos().stream()
				.filter(item -> attendanceItemIdMonthlyAdd.contains(item.getAttendanceItemId())).map(f -> {
					return new BusinessTypeFormatMonthly(companyId,
							new BusinessTypeCode(command.getBusTypeMonthlyCommand().getBusinesstypeCode()),
							f.getAttendanceItemId(), f.getOrder(), f.getColumnWidth());
				}).collect(Collectors.toList());

		// add all of data has added in list attendanceId
		if (!businessTypeFormatMonthlyAdds.isEmpty()) {
			this.businessTypeFormatMonthlyRepository.add(businessTypeFormatMonthlyAdds);
		}

		// update data has changed in list attendanceId
		businessTypeFormatMonthlyUpdates.forEach(f -> {
			this.businessTypeFormatMonthlyRepository.update(f);
		});
	}
}
