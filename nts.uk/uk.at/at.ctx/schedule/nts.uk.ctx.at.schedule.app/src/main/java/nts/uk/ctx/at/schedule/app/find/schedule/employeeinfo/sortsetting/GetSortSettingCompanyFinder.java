package nts.uk.ctx.at.schedule.app.find.schedule.employeeinfo.sortsetting;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.i18n.I18NText;
import nts.uk.ctx.at.schedule.dom.employeeinfo.employeesort.SortSetting;
import nts.uk.ctx.at.schedule.dom.employeeinfo.employeesort.SortSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author HieuLt
 *         UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務予定.社員情報.社員並び替え.App.«Query»
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
		// 指定した会社の並び替え設定を取得する
		Optional<SortSetting> data = repo.get(companyID);
		List<OrderListDto> lstOrders = new ArrayList<>();
		List<OrderListDto> lstOrderReal = new ArrayList<>();
		
		lstOrders.add(new OrderListDto(0, I18NText.getText("KSU001_4048")));
		lstOrders.add(new OrderListDto(0, I18NText.getText("KSU001_4049")));
		lstOrders.add(new OrderListDto(0, I18NText.getText("KSU001_4050")));
		lstOrders.add(new OrderListDto(0, I18NText.getText("Com_Jobtitle")));
		lstOrders.add(new OrderListDto(0, I18NText.getText("Com_Class")));
		if (data.isPresent()) {
			List<OrderListDto> lstOrder = data.get().getOrderedList().stream()
					.map(x -> {
						String name = "";
						if(x.getType().value == 0){
							name = I18NText.getText("KSU001_4048");
						}
						else if(x.getType().value == 1){
							name = I18NText.getText("KSU001_4049");
						}
						else if(x.getType().value == 2){
							name = I18NText.getText("KSU001_4050");
						}
						else if(x.getType().value == 3){
							name = I18NText.getText("Com_Jobtitle");
						}
						else if(x.getType().value == 4){
							name = I18NText.getText("Com_Class");
						}
						return new OrderListDto(x.getSortOrder().value, name);}).collect(Collectors.toList());
			if (!lstOrder.isEmpty()) {
				lstOrders.removeAll(lstOrder);
				lstOrderReal.addAll(lstOrders);

				return new SortSettingDto(companyID, lstOrder ,lstOrderReal);
			}
		}
		return new SortSettingDto(companyID, lstOrders ,lstOrderReal);

	}
}
