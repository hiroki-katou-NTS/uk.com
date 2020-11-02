package nts.uk.ctx.at.schedule.app.find.schedule.employeeinfo.sortsetting;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.employeeinfo.employeesort.SortSetting;
import nts.uk.ctx.at.schedule.dom.employeeinfo.employeesort.SortSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author HieuLt
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務予定.社員情報.社員並び替え.App.«Query»
 *         指定した会社の並び替え設定を取得する
 *
 */
@Stateless
public class GetSortSettingCompanyFinder {

	@Inject
	private SortSettingRepository repo;

	public SortSettingDto getSortSetting() {
		String companyID = AppContexts.user().companyId();
		SortSettingDto result = new SortSettingDto();
		//指定した会社の並び替え設定を取得する
		Optional<SortSetting> data = repo.get(companyID);
		if (data.isPresent()) {
			List<OrderListDto> lstOrder = data.get().getOrderedList().stream()
					.map(x -> new OrderListDto(x.getSortOrder().value, x.getType().value)).collect(Collectors.toList());
			result = new SortSettingDto(companyID, lstOrder);
		}
		return result;

	}
}
