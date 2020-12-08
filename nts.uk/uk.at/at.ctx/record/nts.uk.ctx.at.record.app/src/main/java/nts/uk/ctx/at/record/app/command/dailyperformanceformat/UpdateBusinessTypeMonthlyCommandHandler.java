package nts.uk.ctx.at.record.app.command.dailyperformanceformat;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.BusinessTypeFormatMonthly;
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
public class UpdateBusinessTypeMonthlyCommandHandler extends CommandHandler<UpdateBusinessTypeMonthlyCommand> {

	@Inject
	private BusinessTypeFormatMonthlyRepository businessTypeFormatMonthlyRepository;

	@Override
	protected void handle(CommandHandlerContext<UpdateBusinessTypeMonthlyCommand> context) {

		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();

		UpdateBusinessTypeMonthlyCommand command = context.getCommand();

		// List attendanceItemId in DB
		List<Integer> attendanceItemIdInDBS = this.businessTypeFormatMonthlyRepository
				.getMonthlyDetail(companyId, command.getBusinesstypeCode()).stream().map(f -> {
					return f.getAttendanceItemId();
				}).collect(Collectors.toList());

		// List attendanceItemId from UI
		List<Integer> attendanceItemIds = command.getBusinessTypeFormatDetailDtos().stream().map(f -> {
			return f.getAttendanceItemId();
		}).collect(Collectors.toList());

		// List attendanceItemId has been removed from list UI compare List from DB
		List<Integer> attendanceItemIdRemove = attendanceItemIdInDBS.stream()
				.filter(item -> !attendanceItemIds.contains(item)).collect(Collectors.toList());
		
		// List attendanceItemId has been added from List UI compare List from DB
		List<Integer> attendanceItemIdAdd = attendanceItemIds.stream()
				.filter(item -> !attendanceItemIdInDBS.contains(item)).collect(Collectors.toList());

		// remove all of data has removed in list attendanceId from UI
		if(!attendanceItemIdRemove.isEmpty()){
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
		if(!businessTypeFormatMonthlyAdds.isEmpty()){
			this.businessTypeFormatMonthlyRepository.add(businessTypeFormatMonthlyAdds);			
		}

		// update data has changed in list attendanceId
		businessTypeFormatMonthlyUpdates.forEach(f -> {
			this.businessTypeFormatMonthlyRepository.update(f);
		});
	}

}
