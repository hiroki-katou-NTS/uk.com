package nts.uk.ctx.at.record.app.command.dailyperformanceformat;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.BusinessFormatSheet;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.BusinessTypeFormatDaily;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.repository.BusinessFormatSheetRepository;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.repository.BusinessTypeFormatDailyRepository;
import nts.uk.ctx.at.shared.dom.workrule.businesstype.BusinessTypeCode;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * 
 * @author nampt
 *
 */
@Stateless
public class UpdateBusinessTypeDailyCommandHandler extends CommandHandler<UpdateBusinessTypeDailyCommand> {

	@Inject
	private BusinessTypeFormatDailyRepository businessTypeFormatDailyRepository;

	@Inject
	private BusinessFormatSheetRepository businessFormatSheetRepository;

	@Override
	protected void handle(CommandHandlerContext<UpdateBusinessTypeDailyCommand> context) {
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();

		UpdateBusinessTypeDailyCommand command = context.getCommand();

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

		// List attendanceItemId has been removed from List UI compare List from DB
		List<Integer> attendanceItemIdRemove = attendanceItemIdInDBs.stream()
				.filter(f -> !attendanceItemIdInUI.contains(f)).collect(Collectors.toList());

		// List attendanceItemId has been added from List UI compare List from DB
		List<Integer> attendanceItemIdAdd = attendanceItemIdInUI.stream()
				.filter(item -> !attendanceItemIdInDBs.contains(item)).collect(Collectors.toList());

		// remove all of data has removed in list attendanceId from UI
		if(!attendanceItemIdRemove.isEmpty()){
			this.businessTypeFormatDailyRepository.deleteExistDataByCode(command.getBusinesstypeCode(),companyId,command.getSheetNo().intValue(), attendanceItemIdRemove);			
		}

		// List Data Update from UI compare DB (exist in DB)
		List<BusinessTypeFormatDaily> businessTypeFormatDailyUpdates = command.getBusinessTypeFormatDetailDtos().stream()
				.filter(item -> !attendanceItemIdAdd.contains(item.getAttendanceItemId())).map(f -> {
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
		if(!businessTypeFormatDailyAdds.isEmpty()){
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
}
