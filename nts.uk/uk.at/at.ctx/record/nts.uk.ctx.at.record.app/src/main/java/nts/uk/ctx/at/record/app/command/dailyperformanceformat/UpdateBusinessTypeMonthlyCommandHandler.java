package nts.uk.ctx.at.record.app.command.dailyperformanceformat;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.BusinessTypeFormatMonthly;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.primitivevalue.BusinessTypeCode;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.repository.BusinessTypeFormatMonthlyRepository;
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

		List<BigDecimal> attendanceItemIdInDBS = this.businessTypeFormatMonthlyRepository
				.getMonthlyDetail(companyId, command.getBusinesstypeCode()).stream().map(f -> {
					return f.getAttendanceItemId();
				}).collect(Collectors.toList());

		List<BigDecimal> attendanceItemIds = command.getWorkTypeFormatDetailDtos().stream().map(f -> {
			return f.getAttendanceItemId();
		}).collect(Collectors.toList());

		List<BigDecimal> attendanceItemIdRemove = attendanceItemIdInDBS.stream()
				.filter(item -> !attendanceItemIds.contains(item)).collect(Collectors.toList());

		List<BigDecimal> attendanceItemIdAdd = attendanceItemIds.stream()
				.filter(item -> !attendanceItemIdInDBS.contains(item)).collect(Collectors.toList());

		// remove all of data has removed in list attendanceId
		this.businessTypeFormatMonthlyRepository.deleteExistData(attendanceItemIdRemove);

		List<BusinessTypeFormatMonthly> businessTypeFormatMonthlyUpdates = command.getWorkTypeFormatDetailDtos()
				.stream().filter(item -> !attendanceItemIdAdd.contains(item.getAttendanceItemId())).map(f -> {
					return new BusinessTypeFormatMonthly(companyId, new BusinessTypeCode(command.getBusinesstypeCode()),
							f.getAttendanceItemId(), f.getOrder(), f.getColumnWidth());
				}).collect(Collectors.toList());

		List<BusinessTypeFormatMonthly> businessTypeFormatMonthlyAdds = businessTypeFormatMonthlyUpdates.stream()
				.filter(item -> attendanceItemIdAdd.contains(item.getAttendanceItemId())).collect(Collectors.toList());

		// add all of data has added in list attendanceId
		this.businessTypeFormatMonthlyRepository.add(businessTypeFormatMonthlyAdds);

		// update data has change in list attendanceId
		businessTypeFormatMonthlyUpdates.forEach(f -> {
			this.businessTypeFormatMonthlyRepository.update(f);
		});
	}

}
