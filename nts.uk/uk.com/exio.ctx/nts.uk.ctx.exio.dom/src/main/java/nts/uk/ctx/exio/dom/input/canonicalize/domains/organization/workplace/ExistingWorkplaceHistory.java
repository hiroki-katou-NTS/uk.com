package nts.uk.ctx.exio.dom.input.canonicalize.domains.organization.workplace;

import static java.util.stream.Collectors.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.bs.employee.dom.workplace.master.WorkplaceConfiguration;
import nts.uk.ctx.bs.employee.dom.workplace.master.WorkplaceInformation;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.DomainCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.organization.workplace.WorkplaceCanonicalization.Items;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.AnyRecordToChange;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.AnyRecordToDelete;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.StringifiedValue;
import nts.uk.shr.com.history.DateHistoryItem;

/**
 * 既存の職場履歴
 */
class ExistingWorkplaceHistory {
	
	private List<Item> sortedItems;
	
	public ExistingWorkplaceHistory(WorkplaceConfiguration config, List<WorkplaceInformation> allWorkplaces) {
		
		this.sortedItems = config.items().stream()
				.map(hi -> {
					val workplaces = allWorkplaces.stream()
							.filter(w -> w.getWorkplaceHistoryId().equals(hi.identifier()))
							.collect(toList());
					
					return new Item(hi.identifier(), hi.span(), workplaces);
				})
				.sorted(Comparator.comparing(i -> i.period.start()))
				.collect(toList());
	}
	
	public CodeToIdMap getLastIdMap() {
		return sortedItems.get(sortedItems.size() - 1).getIdMap();
	}

	/**
	 * 指定した基準日以降に開始する履歴をすべて削除する
	 * @param require
	 * @param context
	 * @param importingRecords
	 * @return
	 */
	public void canonicalize(
			DomainCanonicalization.RequireCanonicalize require,
			ExecutionContext context,
			List<RecordWithPeriod> importingRecords) {
		
		if (importingRecords.isEmpty()) {
			return;
		}
		
		GeneralDate baseDate = importingRecords.stream()
				.map(r -> r.period.start())
				.min(Comparator.comparing(d -> d))
				.get();
		
		List<Item> newItems = new ArrayList<>();
		
		for (val item : sortedItems) {
			
			// 基準日より過去の履歴は補正不要
			if (item.period.end().before(baseDate)) {
				newItems.add(item);
			}
			
			// 基準日を開始日より後に含む履歴は終了日の補正をする
			// （開始日に一致する場合は削除しなければならない）
			else if (item.period.contains(baseDate.addDays(-1))) {
				val newItem = item.changeEnd(baseDate.addDays(-1));
				newItems.add(newItem);
				require.save(context, toChange(context, item, newItem.period));
			}
			
			// 上記より未来の履歴は削除する
			else {
				require.save(context, toDelete(context, item));
			}
		}
		
		this.sortedItems = newItems;
		
	}
	
	private static AnyRecordToDelete toDelete(ExecutionContext context, Item item) {
		return AnyRecordToDelete.create(context)
				.addKey(Items.HIST_ID, StringifiedValue.of(item.historyId));
	}
	
	private static AnyRecordToChange toChange(ExecutionContext context, Item item, DatePeriod newPeriod) {
		return AnyRecordToChange.create(context)
				.addKey(Items.HIST_ID, StringifiedValue.of(item.historyId))
				.addChange(Items.開始日, StringifiedValue.of(newPeriod.start()))
				.addChange(Items.終了日, StringifiedValue.of(newPeriod.end()));
	}
	
	/**
	 * 既存履歴の補正処理を実行する
	 * @param require
	 * @param companyId
	 * @param recordsToChange
	 * @param recordsToDelete
	 */
	public static void adjust(
			RequireAdjust require,
			ExecutionContext context,
			List<AnyRecordToChange> recordsToChange,
			List<AnyRecordToDelete> recordsToDelete) {
		
		String companyId = context.getCompanyId();
		
		for (val record : recordsToDelete) {
			
			String historyId = record.getKey(Items.HIST_ID).asString();
			
			require.deleteWorkplaceConfigurationHistoryItem(companyId, historyId);
			require.deleteWorkplaceInformation(companyId, historyId);
		}
		
		for (val record : recordsToChange) {
			
			String historyId = record.getKey(Items.HIST_ID).asString();
			GeneralDate newStart = record.getChange(Items.開始日).asGeneralDate();
			GeneralDate newEnd = record.getChange(Items.終了日).asGeneralDate();
			val newHistoryItem = new DateHistoryItem(historyId, new DatePeriod(newStart, newEnd));
			
			require.updateWorkplaceConfigurationHistoryItem(companyId, newHistoryItem);
		}
	}
	
	public static interface RequireAdjust {
		
		void updateWorkplaceConfigurationHistoryItem(String companyId, DateHistoryItem historyItem);

		void deleteWorkplaceConfigurationHistoryItem(String companyId, String historyId);
		
		void deleteWorkplaceInformation(String companyId, String historyId);
	}

	@AllArgsConstructor
	private static class Item {
		
		/** 履歴ID */
		final String historyId;
		
		/** 期間 */
		final DatePeriod period;
		
		/** 職場情報 */
		final List<WorkplaceInformation> workplaces;
		
		Item changeEnd(GeneralDate newEnd) {
			return new Item(
					historyId,
					new DatePeriod(period.start(), newEnd),
					workplaces);
		}
		
		CodeToIdMap getIdMap() {

			val map = new CodeToIdMap();
			
			workplaces.forEach(w -> {
				map.put(w.getWorkplaceCode().v(), w.getWorkplaceId());
			});
			
			return map;
		}
	}
}
