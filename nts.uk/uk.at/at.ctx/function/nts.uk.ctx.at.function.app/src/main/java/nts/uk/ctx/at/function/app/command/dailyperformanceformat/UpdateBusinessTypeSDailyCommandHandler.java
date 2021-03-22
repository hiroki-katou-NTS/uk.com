package nts.uk.ctx.at.function.app.command.dailyperformanceformat;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.BusinessTypeSFormatDaily;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.BusinessTypeSFormatDailyRepository;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.BusinessTypeSFormatMonthlyRepository;
import nts.uk.ctx.at.shared.dom.workrule.businesstype.BusinessTypeCode;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * 
 * @author anhdt
 *
 */
@Stateless
public class UpdateBusinessTypeSDailyCommandHandler extends CommandHandler<UpdateBusinessTypeSDailyCommand> {

	@Inject
	private BusinessTypeSFormatDailyRepository businessTypeFormatMDailyRepository;
	
	@Inject
	private BusinessTypeSFormatMonthlyRepository businessTypeFormatMMonthlyRepository;

	@Override
	protected void handle(CommandHandlerContext<UpdateBusinessTypeSDailyCommand> context) {
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();

		UpdateBusinessTypeSDailyCommand command = context.getCommand();

		// List attendanceItemId in DB
		List<Integer> attendanceItemIdInDBs = this.businessTypeFormatMDailyRepository
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
		List<Integer> attendanceItemIdAdd = attendanceItemIdInUI.stream()
				.filter(item -> !attendanceItemIdInDBs.contains(item))
				.collect(Collectors.toList());

		// remove all of data has removed in list attendanceId from UI
		if (!attendanceItemIdRemove.isEmpty()) {
			this.businessTypeFormatMDailyRepository.deleteExistDataByCode(command.getBusinesstypeCode(), companyId,
					attendanceItemIdRemove);
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
		List<BusinessTypeSFormatDaily> businessTypeFormatDailyAdds = command.getBusinessTypeFormatDetailDtos()
				.stream()
				.filter(item -> attendanceItemIdAdd.contains(item.getAttendanceItemId()))
				.map(f -> {
					return new BusinessTypeSFormatDaily(companyId, 
															new BusinessTypeCode(command.getBusinesstypeCode()),
															f.getAttendanceItemId(),  
															f.getOrder());
				})
				.collect(Collectors.toList());
		
		/*ドメインモデル「月別実績の修正フォーマット」を更新する
		(update domain 「月別実績の修正フォーマット」)*/
		if (!businessTypeFormatDailyAdds.isEmpty()) {
			this.businessTypeFormatMDailyRepository.add(businessTypeFormatDailyAdds);
		}
		businessTypeFormatDailyUpdates.forEach(f -> {
			this.businessTypeFormatMDailyRepository.update(f);
		});

	}
}
