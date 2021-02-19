package nts.uk.ctx.at.function.app.command.dailyperformanceformat;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.BusinessTypeSFormatDaily;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.BusinessTypeSFormatMonthly;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.BusinessTypeSFormatDailyRepository;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.BusinessTypeSFormatMonthlyRepository;
import nts.uk.ctx.at.shared.dom.workrule.businesstype.BusinessTypeCode;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * @author anhdt
 * 日別実績の勤務種別のフォーマットを登録する（スマホ版）
 */
@Stateless
public class AddBusinessTypeSDailyCommandHandler extends CommandHandler<AddBusinessTypeMobileCommand> {

	@Inject
	private BusinessTypeSFormatDailyRepository businessTypeMobileFormatDailyRepository;
	
	@Inject
	private BusinessTypeSFormatMonthlyRepository businessTypeMobileFormatMonthlyRepository;

	@Override
	protected void handle(CommandHandlerContext<AddBusinessTypeMobileCommand> context) {
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();
		AddBusinessTypeMobileDailyCommand commandDaily = context.getCommand().getBusTypeDailyCommand();
		AddBusinessTypeSMonthlyCommand commandMonthly = context.getCommand().getBusTypeMonthlyCommand();
		
		if (this.businessTypeMobileFormatDailyRepository.checkExistData(companyId, commandDaily.getBusinesstypeCode())) {
			updateDaily(commandDaily, companyId);
		} else {
			/*ドメインモデル「勤務種別日別実績の修正のフォーマット（スマホ）」を登録する*/
			List<BusinessTypeSFormatDaily> businessTypeFormatDailies = commandDaily.getBusinessTypeFormatDetailDtos()
					.stream()
					.map(f -> {
						return new BusinessTypeSFormatDaily(companyId,
																 new BusinessTypeCode(commandDaily.getBusinesstypeCode()), 
																 f.getAttendanceItemId(), 
																 f.getOrder());
					})
					.collect(Collectors.toList());

			this.businessTypeMobileFormatDailyRepository.add(businessTypeFormatDailies);
		}
		
		// monthly
		
		List<BusinessTypeSFormatMonthly> businessTypeFormatMonthlyAdds = commandMonthly
				.getBusinessTypeFormatDetailDtos().stream().map(f -> {
					return new BusinessTypeSFormatMonthly(companyId,
							new BusinessTypeCode(commandMonthly.getBusinesstypeCode()),
							f.getAttendanceItemId(), f.getOrder());
				}).collect(Collectors.toList());

		if (this.businessTypeMobileFormatMonthlyRepository.checkExistData(companyId,
				commandMonthly.getBusinesstypeCode())) {
				updateMonthly(commandMonthly, companyId);
		} else {
				this.businessTypeMobileFormatMonthlyRepository.add(businessTypeFormatMonthlyAdds);
		}
				
	}

	private void updateDaily(AddBusinessTypeMobileDailyCommand command, String companyId) {
		// List attendanceItemId in DB
		List<Integer> attendanceItemIdInDBs = this.businessTypeMobileFormatDailyRepository
				.getBusinessTypeFormatDailyDetail(companyId, command.getBusinesstypeCode())
				.stream()
				.map(f -> {
					return f.getAttendanceItemId();
				})
				.collect(Collectors.toList());

		// List attendanceItemId from UI
		List<Integer> attendanceItemIdInUI = command.getBusinessTypeFormatDetailDtos()
				.stream()
				.map(f -> {
					return f.getAttendanceItemId();
				})
				.collect(Collectors.toList());

		// List attendanceItemId has been removed from List UI compare List from
		// DB
		List<Integer> attendanceItemIdRemove = attendanceItemIdInDBs
				.stream()
				.filter(f -> !attendanceItemIdInUI.contains(f))
				.collect(Collectors.toList());

		// List attendanceItemId has been added from List UI compare List from
		// DB
		List<Integer> attendanceItemIdAdd = attendanceItemIdInUI
				.stream()
				.filter(item -> !attendanceItemIdInDBs.contains(item))
				.collect(Collectors.toList());

		// remove all of data has removed in list attendanceId from UI
		if (!attendanceItemIdRemove.isEmpty()) {
			this.businessTypeMobileFormatDailyRepository.deleteExistDataByCode(command.getBusinesstypeCode(), companyId, attendanceItemIdRemove);
		}

		// List Data Update from UI compare DB (exist in DB)
		List<BusinessTypeSFormatDaily> businessTypeFormatDailyUpdates = command.getBusinessTypeFormatDetailDtos()
				.stream()
				.filter(item -> !attendanceItemIdAdd.contains(item.getAttendanceItemId()))
				.map(f -> {
					return new BusinessTypeSFormatDaily(companyId, 
															 new BusinessTypeCode(command.getBusinesstypeCode()),
															 f.getAttendanceItemId(),  
															 f.getOrder());
				})
				.collect(Collectors.toList());

		// List Data Add from UI (just added in UI)
		List<BusinessTypeSFormatDaily> businessTypeFormatDailyAdds = command.getBusinessTypeFormatDetailDtos().stream()
				.filter(item -> attendanceItemIdAdd.contains(item.getAttendanceItemId())).map(f -> {
					return new BusinessTypeSFormatDaily(companyId, 
															 new BusinessTypeCode(command.getBusinesstypeCode()),
															 f.getAttendanceItemId(),  
															 f.getOrder());
				})
				.collect(Collectors.toList());

		// add all of data has added in list attendanceId
		if (!businessTypeFormatDailyAdds.isEmpty()) {
			this.businessTypeMobileFormatDailyRepository.add(businessTypeFormatDailyAdds);
		}

		// update data has changed in list attendanceId
		businessTypeFormatDailyUpdates.forEach(f -> {
			this.businessTypeMobileFormatDailyRepository.update(f);
		});		
	}
	
	private void updateMonthly(AddBusinessTypeSMonthlyCommand command, String companyId) {

		// List attendanceItemId in DB
		List<Integer> attendanceItemIdInDBS = this.businessTypeMobileFormatMonthlyRepository
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
			this.businessTypeMobileFormatMonthlyRepository.deleteExistData(companyId,command.getBusinesstypeCode(), attendanceItemIdRemove);
		}

		// List Data Update from UI compare DB (exist in DB)
		List<BusinessTypeSFormatMonthly> businessTypeFormatMonthlyUpdates = command.getBusinessTypeFormatDetailDtos()
				.stream()
				.filter(item -> !attendanceItemIdAdd.contains(item.getAttendanceItemId()))
				.map(f -> {
					return new BusinessTypeSFormatMonthly(companyId, new BusinessTypeCode(command.getBusinesstypeCode()),
							f.getAttendanceItemId(), f.getOrder());
				}).collect(Collectors.toList());

		// List Data Add from UI (just added in UI)
		List<BusinessTypeSFormatMonthly> businessTypeFormatMonthlyAdds = command.getBusinessTypeFormatDetailDtos()
				.stream()
				.filter(item -> attendanceItemIdAdd.contains(item.getAttendanceItemId()))
				.map(f -> {
					return new BusinessTypeSFormatMonthly(companyId, new BusinessTypeCode(command.getBusinesstypeCode()),
							f.getAttendanceItemId(), f.getOrder());
				}).collect(Collectors.toList());

		// add all of data has added in list attendanceId
		if (!businessTypeFormatMonthlyAdds.isEmpty()) {
			this.businessTypeMobileFormatMonthlyRepository.add(businessTypeFormatMonthlyAdds);
		}

		// update data has changed in list attendanceId
		businessTypeFormatMonthlyUpdates.forEach(f -> {
			this.businessTypeMobileFormatMonthlyRepository.update(f);
		});
	}

}
