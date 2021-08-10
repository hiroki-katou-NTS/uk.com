package nts.uk.ctx.exio.dom.input.canonicalize.groups.generic;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.Value;
import lombok.val;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.exio.dom.input.DataItemList;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalizeUtil;
import nts.uk.ctx.exio.dom.input.canonicalize.domaindata.DomainDataColumn;
import nts.uk.ctx.exio.dom.input.canonicalize.domaindata.DomainDataId;
import nts.uk.ctx.exio.dom.input.canonicalize.domaindata.DomainDataRepository;
import nts.uk.ctx.exio.dom.input.canonicalize.domaindata.KeyValues;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.AnyRecordToChange;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.AnyRecordToDelete;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.StringifiedValue;
import nts.uk.ctx.exio.dom.input.canonicalize.groups.EmploymentHistoryCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.groups.GroupCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.CanonicalizationMethodRequire;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.EmployeeCodeCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.IntermediateResult;
import nts.uk.ctx.exio.dom.input.meta.ImportingDataMeta;
import nts.uk.ctx.exio.dom.input.workspace.group.GroupWorkspace;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.history.History;

/**
 * 社員の履歴（連続）の正準化
 * 
 * 社員の履歴（連続するやつ）を正準化するための処理がまとまっている
 * 継承してgetHistoryさえ実装すれば、各履歴ドメインの処理がだいたい実現できるはず
 */
@Getter
@ToString
public abstract class EmployeeContinuousHistoryCanonicalization extends IndependentCanonicalization implements GroupCanonicalization {
	
	/** 履歴開始日の項目No */
	private final int itemNoStartDate;
	
	/** 履歴終了日の項目No */
	private final int itemNoEndDate;
	
	/** 履歴IDの項目No */
	private final int itemNoHistoryId;
	
	/** 社員コードの正準化 */
	private final EmployeeCodeCanonicalization employeeCodeCanonicalization;
	
	public EmployeeContinuousHistoryCanonicalization(GroupWorkspace workspace) {
		super(workspace);
		itemNoStartDate = workspace.getItemByName("開始日").getItemNo();
		itemNoEndDate = workspace.getItemByName("終了日").getItemNo();
		itemNoHistoryId = workspace.getItemByName("HIST_ID").getItemNo();
		this.employeeCodeCanonicalization = new EmployeeCodeCanonicalization(workspace);
	}


	
	@Override
	public void canonicalize(GroupCanonicalization.RequireCanonicalize require, ExecutionContext context) {
		
		CanonicalizeUtil.forEachEmployee(require, context, employeeCodeCanonicalization, interm -> {
			
			val results = canonicalizeHistory(require, context, interm);
			
			results.forEach(result -> {
				require.save(context, result.complete());
			});
		});
	}

	/**
	 * 履歴を正準化する
	 * @param require
	 * @param context
	 * @param employeeCanonicalized
	 * @return
	 */
	private List<IntermediateResult> canonicalizeHistory(
			GroupCanonicalization.RequireCanonicalize require,
			ExecutionContext context,
			List<IntermediateResult> employeeCanonicalized) {
		
		if (employeeCanonicalized.isEmpty()) {
			return Collections.emptyList();
		}
		
		// 複数レコードあったとしても同じ社員のデータなので、社員IDは先頭レコードから取り出せば良い
		String employeeId = employeeCanonicalized.get(0)
				.getItemByNo(itemNoEmployeeId())
				.get().getString();

		DomainDataId id = new DomainDataId(this.getParentTableName(), Arrays.asList(new DomainDataId.Key(DomainDataColumn.SID, employeeId))); 
		val existingHistory = require.getHistory(id);
		
		/*
		 *  複数の履歴を追加する場合、全て追加し終えるまで補正結果が確定しない点に注意が必要。
		 *  例えば永続する履歴であれば、追加するたびにその履歴項目の終了日が9999/12/31に変更されるが、
		 *  次の項目を追加した時点で、次の項目の開始日の前日へと更に変更される。
		 *  そこだけ考えれば、単純に追加する履歴項目を開始日昇順でループすれば良いが、そうもいかない。
		 *  最終的には「社員コードを正準化した中間結果」に対してaddCanonicalizedをしなければならないため、
		 *  「社員コードを正準化した中間結果」と「追加する履歴項目」を束ねたもの = Containerを、開始日昇順で処理する必要がある。
		 */
		List<Container> containers = employeeCanonicalized.stream()
				.sorted(Comparator.comparing(c -> c.getItemByNo(itemNoStartDate).get().getDate()))
				.map(interm -> new Container(interm, DateHistoryItem.createNewHistory(getPeriod(interm))))
				.collect(toList());

		// 追加する分と重複する未来の履歴は全て削除
		removeDuplications(require, context, employeeId, containers, existingHistory);
		
		return containers.stream()
				// 受入する期間を既存の履歴に繋がるように補正する
				.peek(c -> adjustAdding(require, context, employeeId, c.addingHistoryItem, existingHistory))
				.map(c -> c.complete())
				.collect(toList());
	}

	
	@Value
	private class Container {
		IntermediateResult interm;
		DateHistoryItem addingHistoryItem;
		
		public IntermediateResult complete() {
			
			// 正準化した結果を格納
			// 開始日・終了日は変わらないかもしれないし、変わるかもしれない
			val canonicalizedItems = new DataItemList()
					.add(itemNoHistoryId, addingHistoryItem.identifier())
					.add(itemNoStartDate, addingHistoryItem.start())
					.add(itemNoEndDate, addingHistoryItem.end());
			
			return interm.addCanonicalized(canonicalizedItems, itemNoStartDate, itemNoEndDate);
		}
	}
	
	/**
	 * 期間を取り出す
	 * @param revisedData
	 * @return
	 */
	private DatePeriod getPeriod(IntermediateResult interm) {
		
		val startDate = interm.getItemByNo(itemNoStartDate).get().getDate();
		val endDate = interm.getItemByNo(itemNoEndDate).get().getDate();
		
		return new DatePeriod(startDate, endDate);
	}
	
	
	/**
	 * 追加する履歴より未来の履歴を全て削除する
	 * @param require
	 * @param context
	 * @param employeeId
	 * @param addingItems
	 */
	private void removeDuplications(
			CanonicalizationMethodRequire require,
			ExecutionContext context,
			String employeeId,
			List<Container> addings,
			History<DateHistoryItem, DatePeriod, GeneralDate> existingHistory) {
		
		// 追加する履歴の開始日のうち最も過去の日付
		GeneralDate baseDate = addings.stream()
				.map(adding -> adding.addingHistoryItem.start())
				.min(Comparator.comparing(d -> d))
				.get();
		
		val itemsToRemove = existingHistory.items().stream()
				.filter(item -> item.start().afterOrEquals(baseDate))
				.collect(toList());

		itemsToRemove.forEach(item -> {
			
			existingHistory.removeForcively(item);
			
			AnyRecordToDelete toDelete = new EmployeeHistoryItem(employeeId, item).toDelete(context);
			require.save(context, toDelete);
		});
	}
	
	/**
	 * 履歴を追加しつつ既存データを補正
	 * @param require
	 * @param employeeId
	 * @param importingPeriod
	 * @param existingHistory
	 * @return 追加する履歴項目
	 */
	private void adjustAdding(
			CanonicalizationMethodRequire require,
			ExecutionContext context,
			String employeeId,
			DateHistoryItem addingItem,
			History<DateHistoryItem, DatePeriod, GeneralDate> existingHistory) {
		
		// addすることで重複している履歴があれば（最新履歴のはず）、それも補正される
		val latestExistingItemOpt = existingHistory.latestStartItem();
		existingHistory.add(addingItem);
		
		latestExistingItemOpt.ifPresent(existing -> {
			AnyRecordToChange toChange = new EmployeeHistoryItem(employeeId, existing).toChange(context);
			require.save(context, toChange);
		});
	}

	public static interface RequireCanonicalize{
		History<DateHistoryItem, DatePeriod, GeneralDate> getHistory(DomainDataId id);
	}
	
	@Override
	public AtomTask adjust(
			RequireAdjsut require,
			List<AnyRecordToChange> recordsToChange,
			List<AnyRecordToDelete> recordsToDelete) {
		
		return AtomTask.of(() -> {

			for (val record : recordsToDelete) {
				toDomainDataIds(record).forEach(id -> require.delete(id));
			}

			for (val record : recordsToChange) {
				//項目Noから何をどの値に変更するのか特定できないとここから進めない
				val period = new DatePeriod(
						record.getChange(this.itemNoStartDate).asGeneralDate(),
						record.getChange(this.itemNoEndDate).asGeneralDate());
				
				toDomainDataIds(record).forEach(id -> require.update(id, period));
			}
		});
	}
	
	protected List<DomainDataId> toDomainDataIds(AnyRecordToChange toChange) {
		
		val keyValues = new KeyValues(toKeyValueObjects(toChange));
		
		List<String> tableNames = new ArrayList<>();
		tableNames.add(getParentTableName());
		tableNames.addAll(getChildTableNames());
		
		val keys = getDomainDataKeys();
		return tableNames.stream()
				.map(tn -> createDomainDataId(tn, keys, keyValues))
				.collect(toList());
	}
	
	private List<Object> toKeyValueObjects(AnyRecordToChange toChange) {
		
		List<Object> keyValues = new ArrayList<>();
		
		val dataKeys = getDomainDataKeys();
		for (int i = 0; i < dataKeys.size(); i++) {
			val dataKey = dataKeys.get(i);
			val stringified = toChange.getPrimaryKeys().get(i);
			
			Object value;
			switch (dataKey.getDataType()) {
			case STRING:
				value = stringified.asString();
				break;
			case INT:
				value = stringified.asInteger();
				break;
			case REAL:
				value = stringified.asBigDecimal();
				break;
			case DATE:
				value = stringified.asGeneralDate();
				break;
			default:
				throw new RuntimeException("unknown: " + dataKey);
			}

			keyValues.add(value);
		}
		
		return keyValues;
	}
	
	
	@Inject
	private DomainDataRepository domainDataRepo;
	
	public static interface RequireAdjust{
		void delete(DomainDataId id);
		
		void update(DomainDataId id ,DatePeriod period) ;
	}
	
	
	@RequiredArgsConstructor
	@Getter
	protected class EmployeeHistoryItem {
		final String employeeId;
		final String historyId;
		final DatePeriod period;
		
		EmployeeHistoryItem(String employeeId, DateHistoryItem item) {
			this(employeeId, item.identifier(), item.span());
		}
		
		EmployeeHistoryItem(AnyRecordToChange record) {
			this(
					record.getKey(itemNoEmployeeId()).asString(),
					record.getKey(itemNoHistoryId).asString(),
					new DatePeriod(
							record.getChange(itemNoStartDate).asGeneralDate(),
							record.getChange(itemNoEndDate).asGeneralDate()));
		}
		
		EmployeeHistoryItem(AnyRecordToDelete record) {
			this(
					record.getKey(itemNoEmployeeId()).asString(),
					record.getKey(itemNoHistoryId).asString(),
					null);
		}
		
		AnyRecordToDelete toDelete(ExecutionContext context) {
			return AnyRecordToDelete.create(context)
				.addKey(itemNoEmployeeId(), StringifiedValue.of(employeeId))
				.addKey(itemNoHistoryId, StringifiedValue.of(historyId));
		}
		
		AnyRecordToChange toChange(ExecutionContext context) {
			return AnyRecordToChange.create(context)
					.addKey(itemNoEmployeeId(), StringifiedValue.of(employeeId))
					.addKey(itemNoHistoryId, StringifiedValue.of(historyId))
					.addChange(itemNoStartDate, StringifiedValue.of(period.start()))
					.addChange(itemNoEndDate, StringifiedValue.of(period.end()));
		}
		
		public DateHistoryItem toDateHistoryItem() {
			return new DateHistoryItem(historyId, period);
		}
	}
	
	private int itemNoEmployeeId() {
		return employeeCodeCanonicalization.getItemNoEmployeeId();
	}
	
	@Override
	public ImportingDataMeta appendMeta(ImportingDataMeta source) {
		return employeeCodeCanonicalization.appendMeta(source)
				.addItem("HIST_ID");
	}

}
