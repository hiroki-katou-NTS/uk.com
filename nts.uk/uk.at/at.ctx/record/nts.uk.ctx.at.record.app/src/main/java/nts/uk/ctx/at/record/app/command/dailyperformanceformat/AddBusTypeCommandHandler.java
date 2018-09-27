package nts.uk.ctx.at.record.app.command.dailyperformanceformat;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.BusinessFormatSheet;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.BusinessTypeFormatDaily;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.BusinessTypeFormatMonthly;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.primitivevalue.BusinessTypeCode;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.repository.BusinessFormatSheetRepository;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.repository.BusinessTypeFormatDailyRepository;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.repository.BusinessTypeFormatMonthlyRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

@Stateless
public class AddBusTypeCommandHandler extends CommandHandler<AddBusTypeCommand> {

	@Inject
	private BusinessTypeFormatDailyRepository businessTypeFormatDailyRepository;

	@Inject
	private BusinessFormatSheetRepository businessFormatSheetRepository;

	@Inject
	private BusinessTypeFormatMonthlyRepository businessTypeFormatMonthlyRepository;

	@Override
	protected void handle(CommandHandlerContext<AddBusTypeCommand> context) {

		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();

		AddBusTypeCommand command = context.getCommand();

		if (command.getBusTypeDailyCommand().getBusinessTypeFormatDetailDtos().isEmpty()) {
			throw new BusinessException("Msg_920");
		}
		
		List<BusinessFormatSheet> listBusFormatSheet = businessFormatSheetRepository.getSheetInformationByCode(
				companyId, command.getBusTypeDailyCommand().getBusinesstypeCode());
		
		if (this.businessTypeFormatDailyRepository.checkExistData(companyId, command.getBusTypeDailyCommand().getBusinesstypeCode(), 
				command.getBusTypeDailyCommand().getSheetNo())) {
			updateDaily(command.getBusTypeDailyCommand(), companyId);
		} else {
			command.getBusTypeDailyCommand().setSheetNo(BigDecimal.valueOf(listBusFormatSheet.size()+1));
			
			List<BusinessTypeFormatDaily> businessTypeFormatDailies = command.getBusTypeDailyCommand()
			.getBusinessTypeFormatDetailDtos().stream().map(f -> {
				return new BusinessTypeFormatDaily(companyId,
						new BusinessTypeCode(command.getBusTypeDailyCommand().getBusinesstypeCode()),
						f.getAttendanceItemId(), command.getBusTypeDailyCommand().getSheetNo(), f.getOrder(),
						f.getColumnWidth());
			}).collect(Collectors.toList());
			this.businessTypeFormatDailyRepository.add(businessTypeFormatDailies);
		}

		
		if (this.businessFormatSheetRepository.checkExistData(companyId,
				new BusinessTypeCode(command.getBusTypeDailyCommand().getBusinesstypeCode()),
				command.getBusTypeDailyCommand().getSheetNo())) {
			
			BusinessFormatSheet businessFormatSheet = new BusinessFormatSheet(companyId,
					new BusinessTypeCode(command.getBusTypeDailyCommand().getBusinesstypeCode()),
					command.getBusTypeDailyCommand().getSheetNo(), command.getBusTypeDailyCommand().getSheetName());
			this.businessFormatSheetRepository.update(businessFormatSheet);
		} else {
			command.getBusTypeDailyCommand().setSheetNo(BigDecimal.valueOf(listBusFormatSheet.size()+1));
			BusinessFormatSheet businessFormatSheet = new BusinessFormatSheet(companyId,
					new BusinessTypeCode(command.getBusTypeDailyCommand().getBusinesstypeCode()),
					command.getBusTypeDailyCommand().getSheetNo(), command.getBusTypeDailyCommand().getSheetName());
			this.businessFormatSheetRepository.add(businessFormatSheet);
		}

		// monthly
		List<BusinessTypeFormatMonthly> businessTypeFormatMonthlyAdds = command.getBusTypeMonthlyCommand()
				.getBusinessTypeFormatDetailDtos().stream().map(f -> {
					return new BusinessTypeFormatMonthly(companyId,
							new BusinessTypeCode(command.getBusTypeMonthlyCommand().getBusinesstypeCode()),
							f.getAttendanceItemId(), f.getOrder(), f.getColumnWidth());
				}).collect(Collectors.toList());

		if (this.businessTypeFormatMonthlyRepository.checkExistData(companyId,
				command.getBusTypeMonthlyCommand().getBusinesstypeCode())) {
			updateMonthly(command.getBusTypeMonthlyCommand(), companyId);
		} else {
			this.businessTypeFormatMonthlyRepository.add(businessTypeFormatMonthlyAdds);
		}
	}

	private void updateDaily(AddBusinessTypeDailyCommand command, String companyId) {
		// List attendanceItemId in DB
		List<Integer> attendanceItemIdInDBs = this.businessTypeFormatDailyRepository
				.getBusinessTypeFormatDailyDetail(companyId, command.getBusinesstypeCode(), command.getSheetNo())
				.stream().map(f -> {
					return f.getAttendanceItemId();
				}).collect(Collectors.toList());

		// List attendanceItemId from UI
		List<Integer> attendanceItemIdInUI = command.getBusinessTypeFormatDetailDtos().stream().map(f -> {
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
			this.businessTypeFormatDailyRepository.deleteExistDataByCode(command.getBusinesstypeCode(),companyId,command.getSheetNo().intValue(), attendanceItemIdRemove);
		}

		// List Data Update from UI compare DB (exist in DB)
		List<BusinessTypeFormatDaily> businessTypeFormatDailyUpdates = command.getBusinessTypeFormatDetailDtos()
				.stream().filter(item -> !attendanceItemIdAdd.contains(item.getAttendanceItemId())).map(f -> {
					return new BusinessTypeFormatDaily(companyId, new BusinessTypeCode(command.getBusinesstypeCode()),
							f.getAttendanceItemId(), command.getSheetNo(), f.getOrder(), f.getColumnWidth());
				}).collect(Collectors.toList());

		// List Data Add from UI (just added in UI)
		List<BusinessTypeFormatDaily> businessTypeFormatDailyAdds = command.getBusinessTypeFormatDetailDtos().stream()
				.filter(item -> attendanceItemIdAdd.contains(item.getAttendanceItemId())).map(f -> {
					return new BusinessTypeFormatDaily(companyId, new BusinessTypeCode(command.getBusinesstypeCode()),
							f.getAttendanceItemId(), command.getSheetNo(), f.getOrder(), f.getColumnWidth());
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
				new BusinessTypeCode(command.getBusinesstypeCode()), command.getSheetNo(), command.getSheetName());

		this.businessFormatSheetRepository.update(businessFormatSheetUpdate);
	}

	private void updateMonthly(AddBusinessTypeMonthlyCommand command, String companyId) {

		// List attendanceItemId in DB
		List<Integer> attendanceItemIdInDBS = this.businessTypeFormatMonthlyRepository
				.getMonthlyDetail(companyId, command.getBusinesstypeCode()).stream().map(f -> {
					return f.getAttendanceItemId();
				}).collect(Collectors.toList());

		// List attendanceItemId from UI
		List<Integer> attendanceItemIds = command.getBusinessTypeFormatDetailDtos().stream().map(f -> {
			return f.getAttendanceItemId();
		}).collect(Collectors.toList());

		// List attendanceItemId has been removed from list UI compare List from
		// DB
		List<Integer> attendanceItemIdRemove = attendanceItemIdInDBS.stream()
				.filter(item -> !attendanceItemIds.contains(item)).collect(Collectors.toList());

		// List attendanceItemId has been added from List UI compare List from
		// DB
		List<Integer> attendanceItemIdAdd = attendanceItemIds.stream()
				.filter(item -> !attendanceItemIdInDBS.contains(item)).collect(Collectors.toList());

		// remove all of data has removed in list attendanceId from UI
		if (!attendanceItemIdRemove.isEmpty()) {
			this.businessTypeFormatMonthlyRepository.deleteExistData(companyId,command.getBusinesstypeCode(), attendanceItemIdRemove);
		}

		// List Data Update from UI compare DB (exist in DB)
		List<BusinessTypeFormatMonthly> businessTypeFormatMonthlyUpdates = command.getBusinessTypeFormatDetailDtos()
				.stream().filter(item -> !attendanceItemIdAdd.contains(item.getAttendanceItemId())).map(f -> {
					return new BusinessTypeFormatMonthly(companyId, new BusinessTypeCode(command.getBusinesstypeCode()),
							f.getAttendanceItemId(), f.getOrder(), f.getColumnWidth());
				}).collect(Collectors.toList());

		// List Data Add from UI (just added in UI)
		List<BusinessTypeFormatMonthly> businessTypeFormatMonthlyAdds = command.getBusinessTypeFormatDetailDtos()
				.stream().filter(item -> attendanceItemIdAdd.contains(item.getAttendanceItemId())).map(f -> {
					return new BusinessTypeFormatMonthly(companyId, new BusinessTypeCode(command.getBusinesstypeCode()),
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
