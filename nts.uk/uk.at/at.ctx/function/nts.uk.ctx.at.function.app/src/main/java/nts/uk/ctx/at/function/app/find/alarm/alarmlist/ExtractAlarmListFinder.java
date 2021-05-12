package nts.uk.ctx.at.function.app.find.alarm.alarmlist;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.function.dom.adapter.standardmenu.StandardMenuAdaptor;
import nts.uk.ctx.at.function.dom.adapter.standardmenu.StandardMenuNameImport;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.ExtractAlarmListService;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.ExtractedAlarmDto;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.webmenu.AlarmListWebMenu;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.webmenu.AlarmListWebMenuRepository;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.webmenu.WebMenuInfo;
import nts.uk.ctx.at.shared.dom.alarmList.persistenceextractresult.AlarmExtractInfoResult;
import nts.uk.ctx.at.shared.dom.alarmList.persistenceextractresult.PersisAlarmListExtractResultRepository;
import nts.uk.ctx.at.shared.dom.alarmList.persistenceextractresult.PersistenceAlarmListExtractResult;
import nts.uk.shr.com.context.AppContexts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Stateless
public class ExtractAlarmListFinder {

	@Inject
	private ExtractAlarmListService extractAlarmListService;

	@Inject
	private PersisAlarmListExtractResultRepository persisAlarmListExtractResultRepo;

	@Inject
	private AlarmListWebMenuRepository alarmListWebMenuRepo;

	@Inject
	private StandardMenuAdaptor standardMenuAdaptor;

	public ExtractedAlarmDto extractAlarm(ExtractAlarmQuery query) {
		return this.extractAlarmListService.extractAlarm(query.getListEmployee(), query.getAlarmCode(), query.getListPeriodByCategory(), "");
	}

	/**
	 * アラームリストの起動
	 */
	public Map<String, Map<Integer, List<WebMenuInfoDto>>> getWebMenu(List<String> employeeIds) {
		Map<String, Map<Integer, List<WebMenuInfoDto>>> result = new HashMap<>();
	    String companyId = AppContexts.user().companyId();
	    List<PersistenceAlarmListExtractResult> listExtractResults = persisAlarmListExtractResultRepo.getAlarmExtractResult(companyId, employeeIds);
	    List<AlarmListWebMenu> listWebMenus = alarmListWebMenuRepo.getAll(companyId);
		List<StandardMenuNameImport> listStandardMenus = standardMenuAdaptor.getMenus(companyId, 4); // システム　＝　勤次郎
		List<AlarmExtractInfoResult> allResults = new ArrayList<>();
		listExtractResults.forEach(r1 -> {
			r1.getAlarmListExtractResults().forEach(r2 -> {
				Map<Integer, List<WebMenuInfoDto>> tmpValue = new HashMap<>();
				r2.getAlarmExtractInfoResults().forEach(r3 -> {
					// 取得したList＜アラームリストのWebメニュー＞を絞り込む
					List<AlarmListWebMenu> tmpWebMenus = listWebMenus.stream()
							.filter(w -> r3.getAlarmCategory() == w.getAlarmCategory() && r3.getAlarmListCheckType() == w.getCheckType())
							.collect(Collectors.toList());

					// アラームからWebメニュー情報を探す
					List<AlarmListWebMenu> filteredWebMenus = tmpWebMenus.stream()
							.filter(w -> w.getAlarmCheckConditionCode().equals(r3.getAlarmCheckConditionCode())
									&& w.getAlarmCode().equals(r3.getAlarmCheckConditionNo()))
							.collect(Collectors.toList());
					if (filteredWebMenus.isEmpty()) {
						filteredWebMenus = tmpWebMenus.stream()
								.filter(w -> ((w.getAlarmCheckConditionCode() == null || w.getAlarmCheckConditionCode().v() == null)
										&& w.getAlarmCode().equals(r3.getAlarmCheckConditionNo()))
										|| (w.getAlarmCheckConditionCode().equals(r3.getAlarmCheckConditionCode())
										&& w.getAlarmCode() == null))
								.collect(Collectors.toList());
						if (filteredWebMenus.isEmpty()) {
							filteredWebMenus = tmpWebMenus.stream()
									.filter(w -> (w.getAlarmCheckConditionCode() == null || w.getAlarmCheckConditionCode().v() == null)
											&& w.getAlarmCode() == null)
									.collect(Collectors.toList());
						}
					}
					List<WebMenuInfo> webMenuInfos = filteredWebMenus.isEmpty()
							? new ArrayList<>()
							: filteredWebMenus.stream()
							.map(i -> i.getWebMenuInfoList())
							.flatMap(List::stream)
							.collect(Collectors.toList());

					List<StandardMenuNameImport> filteredStandardMenus = listStandardMenus.stream().filter(i -> {
						return webMenuInfos.stream()
								.anyMatch(j -> j.getSystem().value == i.getSystem()
										&& j.getMenuClassification().value == i.getMenuClassification()
										&& j.getMenuClassification().value == i.getMenuClassification());
					}).collect(Collectors.toList());
					tmpValue.put(r3.getAlarmCategory().value, filteredStandardMenus.stream().map(i -> new WebMenuInfoDto(i.getProgramId(), i.getDisplayName(), i.getUrl(), i.getQueryString())).collect(Collectors.toList()));
				});
				result.put(r2.getEmployeeID(), tmpValue);
			});
		});
		return result;
	}
}
