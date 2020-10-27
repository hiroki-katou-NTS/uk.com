package nts.uk.ctx.at.schedule.app.command.schedule.employeeinfo.sortsetting;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
/**
 * @author HieuLT
 */
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.app.find.schedule.employeeinfo.sortsetting.OrderListDto;
import nts.uk.ctx.at.schedule.dom.employeeinfo.employeesort.OrderedList;
import nts.uk.ctx.at.schedule.dom.employeeinfo.employeesort.SortOrder;
import nts.uk.ctx.at.schedule.dom.employeeinfo.employeesort.SortSetting;
import nts.uk.ctx.at.schedule.dom.employeeinfo.employeesort.SortSettingRepository;
import nts.uk.ctx.at.schedule.dom.employeeinfo.employeesort.SortType;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class RegisterSortSettingCommandHandler extends CommandHandler<RegisterSortSettingCommand> {

	@Inject
	private SortSettingRepository repo;

	@Override
	protected void handle(CommandHandlerContext<RegisterSortSettingCommand> context) {
		String companyId = AppContexts.user().companyId();
		RegisterSortSettingCommand command = context.getCommand();
		List<OrderListDto> data = command.getLstOrderListDto();
		// 1:get 並び替え優先順
		Optional<SortSetting> optSortSetting = repo.get(companyId);
		List<OrderedList> lstOrderList = data.stream()
				.map(x -> new OrderedList(EnumAdaptor.valueOf(x.getSortOrder(), SortOrder.class),
						EnumAdaptor.valueOf(x.getSortType(), SortType.class)))
				.collect(Collectors.toList());
		if (optSortSetting.isPresent()) {
			// 2: set 並び替え優先順
			optSortSetting.get().setOrderedList(lstOrderList);
			SortSetting domain = SortSetting.getSortSet(companyId, lstOrderList );
			repo.update(domain);
		} else {
			// 3: create 並び替え優先順
			SortSetting sort = SortSetting.getSortSet(companyId, lstOrderList);
			repo.insert(sort);
		}
	}

}
