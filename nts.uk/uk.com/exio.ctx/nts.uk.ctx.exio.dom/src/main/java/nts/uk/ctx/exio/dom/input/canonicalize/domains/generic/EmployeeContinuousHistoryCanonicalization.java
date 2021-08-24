package nts.uk.ctx.exio.dom.input.canonicalize.domains.generic;

import static java.util.stream.Collectors.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.Value;
import lombok.val;
import nts.arc.error.BusinessException;
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
import nts.uk.ctx.exio.dom.input.canonicalize.domaindata.SystemImportingItems;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.DomainCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.AnyRecordToChange;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.AnyRecordToDelete;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.StringifiedValue;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.CanonicalizationMethodRequire;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.EmployeeCodeCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.IntermediateResult;
import nts.uk.ctx.exio.dom.input.errors.ErrorMessage;
import nts.uk.ctx.exio.dom.input.errors.ExternalImportError;
import nts.uk.ctx.exio.dom.input.meta.ImportingDataMeta;
import nts.uk.ctx.exio.dom.input.util.Either;
import nts.uk.ctx.exio.dom.input.workspace.domain.DomainWorkspace;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.history.History;

/**
 * 社員の履歴（連続）の正準化
 * 
 * 社員の履歴（連続するやつ）を正準化するための処理がまとまっている
 */
@Getter
@ToString
public abstract class EmployeeContinuousHistoryCanonicalization extends IndependentCanonicalization implements DomainCanonicalization {
	
	/** 履歴開始日の項目No */
	private final int itemNoStartDate;
	
	/** 履歴終了日の項目No */
	private final int itemNoEndDate;
	
	/** 履歴IDの項目No */
	private final int itemNoHistoryId;
	
	/** 社員コードの正準化 */
	private final EmployeeCodeCanonicalization employeeCodeCanonicalization;

	/** どんな履歴か*/
	protected abstract Class<?> getHistoryClass();
	
	public EmployeeContinuousHistoryCanonicalization(DomainWorkspace workspace) {
		super(workspace);
		itemNoStartDate = workspace.getItemByName("開始日").getItemNo();
		itemNoEndDate = workspace.getItemByName("終了日").getItemNo();
		itemNoHistoryId = workspace.getItemByName("HIST_ID").getItemNo();
		this.employeeCodeCanonicalization = new EmployeeCodeCanonicalization(workspace);
	}

	@Override
	public void canonicalize(DomainCanonicalization.RequireCanonicalize require, ExecutionContext context) {
		
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
			DomainCanonicalization.RequireCanonicalize require,
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
		//既存履歴
		val existingHistory = require.getHistory(id, getHistoryClass());
		
		/*
		 *  複数の履歴を追加する場合、全て追加し終えるまで補正結果が確定しない点に注意が必要。
		 *  例えば永続する履歴であれば、追加するたびにその履歴項目の終了日が9999/12/31に変更されるが、
		 *  次の項目を追加した時点で、次の項目の開始日の前日へと更に変更される。
		 *  そこだけ考えれば、単純に追加する履歴項目を開始日昇順でループすれば良いが、そうもいかない。
		 *  最終的には「社員コードを正準化した中間結果」に対してaddCanonicalizedをしなければならないため、
		 *  「社員コードを正準化した中間結果」と「追加する履歴項目」を束ねたもの = Containerを、開始日昇順で処理する必要がある。
		 */
		List<Container> containers = new ArrayList<>();
		
		employeeCanonicalized.stream()
				.sorted(Comparator.comparing(c -> c.getItemByNo(itemNoStartDate).get().getDate()))
				.forEach(interm -> getPeriod(interm)
						.map(p -> new Container(interm, DateHistoryItem.createNewHistory(p)))
						.ifRight(c -> containers.add(c))
						.ifLeft(e -> require.add(context, ExternalImportError.record(interm.getRowNo(), e.getText()))));

		// 追加する分と重複する未来の履歴は全て削除
		removeDuplications(require, context, employeeId, containers, existingHistory);

		//既存データと受入れようとしてるデータで補正
		//未来履歴は↑で消えているため、
		//既存データの一番未来のやつと受入れようとしてる一番過去のやつを見て、補正すればいい
		adjustExistingHistory(require, context, containers.get(0).addingHistoryItem, existingHistory);
		
		try {
			adjustAddingHistory(existingHistory, containers);
		} catch (BusinessException ex) {
			// どのデータで失敗しようと１社員分すべて受け入れるか、全て受け入れないかのどちらかとする
			containers.forEach(c -> require.add(
					context,
					ExternalImportError.record(c.interm.getRowNo(), ex.getMessage())));
		}
		
		return containers.stream()
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
	private Either<ErrorMessage, DatePeriod> getPeriod(IntermediateResult interm) {
		
		val startDate = interm.getItemByNo(itemNoStartDate).get().getDate();
		val endDate = interm.getItemByNo(itemNoEndDate).get().getDate();
		
		val period = new DatePeriod(startDate, endDate);
		if (period.isReversed()) {
			return Either.left(new ErrorMessage("開始日と終了日が逆転しています。"));
		}
		
		return Either.right(period);
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
			
			AnyRecordToDelete toDelete = new EmployeeHistoryItem(item).toDelete(context);
			require.save(context, toDelete);
		});
	}
	
	
	/**
	 * 受入データを補正
	 * @param existingHistory 受入データが入る前の既存履歴
	 * @param addingItems 受入れる履歴
	 */
	private void adjustAddingHistory(History<DateHistoryItem, DatePeriod, GeneralDate> existingHistory, List<Container> addingItems) {
		addingItems.stream().peek(c -> existingHistory.add(c.addingHistoryItem)).collect(Collectors.toList());
	}

	/**
	 * 既存データを補正
	 * @param require
	 * @param context
	 * @param addingItem 追加する履歴
	 * @param existingHistory 受入データが入る前の既存履歴
	 */
	private void adjustExistingHistory(
			CanonicalizationMethodRequire require,
			ExecutionContext context,
			DateHistoryItem addingItem,
			History<DateHistoryItem, DatePeriod, GeneralDate> existingHistory) {
		
		// addすることで重複している履歴があれば（最新履歴のはず）、それも補正される
		val latestExistingItemOpt = existingHistory.latestStartItem();
		
		latestExistingItemOpt.ifPresent(existing -> {
			existing.shortenEndToAccept(addingItem);
			AnyRecordToChange toChange = new EmployeeHistoryItem(existing).toChange(context);
			require.save(context, toChange);
		});
	}

	public static interface RequireCanonicalize{
		History<DateHistoryItem, DatePeriod, GeneralDate> getHistory(DomainDataId id, Class<?> historyClass);
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
			
			StringifiedValue stringified = 
					(SystemImportingItems.map.containsKey(dataKey.getColumnName()))
					? toChange.getPrimaryKeys().get(SystemImportingItems.map.get(dataKey.getColumnName()))
					: toChange.getPrimaryKeys().get(i);
			
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
		final String historyId;
		final DatePeriod period;
		
		EmployeeHistoryItem(DateHistoryItem item) {
			this(item.identifier(), item.span());
		}
		
		EmployeeHistoryItem(AnyRecordToChange record) {
			this(record.getKey(itemNoHistoryId).asString(),
					new DatePeriod(
							record.getChange(itemNoStartDate).asGeneralDate(),
							record.getChange(itemNoEndDate).asGeneralDate()));
		}
		
		EmployeeHistoryItem(AnyRecordToDelete record) {
			this(record.getKey(itemNoHistoryId).asString(),
					null);
		}
		
		AnyRecordToDelete toDelete(ExecutionContext context) {
			return AnyRecordToDelete.create(context)
				.addKey(itemNoHistoryId, StringifiedValue.of(historyId));
		}
		
		AnyRecordToChange toChange(ExecutionContext context) {
			return AnyRecordToChange.create(context)
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
