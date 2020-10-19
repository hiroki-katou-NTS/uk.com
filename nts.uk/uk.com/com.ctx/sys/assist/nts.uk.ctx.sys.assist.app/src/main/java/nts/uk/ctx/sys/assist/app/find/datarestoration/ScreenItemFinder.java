package nts.uk.ctx.sys.assist.app.find.datarestoration;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.assist.app.command.datarestoration.EmployeeSelection;
import nts.uk.ctx.sys.assist.app.command.datarestoration.FindScreenItemCommand;
import nts.uk.ctx.sys.assist.dom.category.TimeStore;
import nts.uk.ctx.sys.assist.dom.datarestoration.PerformDataRecovery;
import nts.uk.ctx.sys.assist.dom.datarestoration.PerformDataRecoveryRepository;

@Stateless
public class ScreenItemFinder {
	@Inject
	private PerformDataRecoveryRepository finder;

	public EmployeeSelection getTargetById(FindScreenItemCommand command) {
		Optional<PerformDataRecovery> optPerformData = finder
				.getPerformDatRecoverById(command.getDataRecoveryProcessId());
		if (optPerformData.isPresent()) {
			PerformDataRecovery domain = optPerformData.get();
			List<TableListDto> tableLists = command.getItemSets().stream().map(ItemSetDto::getCategoryTable)
					.collect(Collectors.toList());
			EmployeeSelection dto = new EmployeeSelection();
			dto.setTargets(
					domain.getTargets().stream().map(TargetItemDto::convertToDto).collect(Collectors.toList()));
			tableLists.stream().filter(t -> t.getRetentionPeriodCls() == TimeStore.DAILY.value).findFirst()
					.ifPresent(t -> {
						dto.setDailyFrom(t.getSaveDateFrom());
						dto.setDailyTo(t.getSaveDateTo());
					});
			tableLists.stream().filter(t -> t.getRetentionPeriodCls() == TimeStore.MONTHLY.value).findFirst()
					.ifPresent(t -> {
						dto.setMonthlyFrom(t.getSaveDateFrom());
						dto.setMonthlyTo(t.getSaveDateTo());
					});
			tableLists.stream().filter(t -> t.getRetentionPeriodCls() == TimeStore.ANNUAL.value).findFirst()
					.ifPresent(t -> {
						dto.setAnnualFrom(t.getSaveDateFrom());
						dto.setAnnualTo(t.getSaveDateTo());
					});
			return dto;
		}
		return null;
	}
}
