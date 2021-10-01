package nts.uk.ctx.exio.dom.input.canonicalize.domains.organization.workplace;

import static java.util.stream.Collectors.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.val;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.DomainCanonicalization;
import nts.uk.ctx.exio.dom.input.errors.ExternalImportError;
import nts.uk.ctx.exio.dom.input.setting.assembly.RevisedDataRecord;

/**
 * レコードを履歴で束ねたやつ
 */
@AllArgsConstructor
public class HistoryRevisedData {
	
	/** 期間 */
	DatePeriod period;
	
	/** 当該期間の履歴にぶらさがるレコード */
	final List<CanonicalizableRecord> records;
	
	/**
	 * 編集済みデータのリストから構築する
	 * @param require
	 * @param context
	 * @param source
	 * @return
	 */
	static List<HistoryRevisedData> build(
			DomainCanonicalization.RequireCanonicalize require,
			ExecutionContext context,
			List<RevisedDataRecord> source) {
		
		if (source.isEmpty()) {
			return Collections.emptyList();
		}
		
		val groupedMap = source.stream()
				.collect(Collectors.groupingBy(s -> Util.getPeriod(s)));
		
		val histories = groupedMap.entrySet().stream()
				.map(e -> HistoryRevisedData.of(e.getKey(), e.getValue()))
				.sorted(Comparator.comparing(h -> h.period.start()))
				.collect(toList());
		
		return canonicalizePeriods(require, context, histories);
	}
	
	private static HistoryRevisedData of(DatePeriod period, List<RevisedDataRecord> source) {
		
		val records = source.stream()
				.map(CanonicalizableRecord::of)
				.collect(toList());
		
		return new HistoryRevisedData(period, records);
	}

	/**
	 * 受け入れる履歴の期間同士が連続しているかチェックする（最後の履歴は99991231に自動補正する）
	 * @param histories
	 * @return
	 */
	private static List<HistoryRevisedData> canonicalizePeriods(
			DomainCanonicalization.RequireCanonicalize require,
			ExecutionContext context,
			List<HistoryRevisedData> histories) {
		
		if (histories.isEmpty()) {
			return Collections.emptyList();
		}
		
		List<HistoryRevisedData> results = new ArrayList<>();
		results.add(histories.get(0));
		
		// 前の履歴との連続性をチェック
		for (int i = 1; i < histories.size(); i++) {
			
			val current = histories.get(i);
			val prev = results.get(results.size() - 1);
			
			// 前の履歴と繋がっていない
			if (!prev.period.end().addDays(1).equals(current.period.start())) {
				continuousError(require, context, current, prev);
				continue;
			}
			
			results.add(current);
		}
		
		// 最後の履歴は99991231に自動補正
		results.get(results.size() - 1).changeEndToMax();
		
		return results;
	}

	private static void continuousError(
			DomainCanonicalization.RequireCanonicalize require,
			ExecutionContext context,
			HistoryRevisedData current,
			HistoryRevisedData prev) {
		
		for (val record : current.records) {
			
			val error = ExternalImportError.record(
					record.revised.getRowNo(),
					"受入データの中にある直前の履歴（終了日:" + prev.period.end().toString("yyyy/MM/dd") + "）と連続していません。");
		
			require.add(context, error);
		}
	}

	void canonicalize(
			DomainCanonicalization.RequireCanonicalize require,
			ExecutionContext context,
			WorkplaceIdMap idMap) {
		
		String historyId = IdentifierUtil.randomUniqueId();
		records.forEach(r -> {
			r.canonicalize(require, context, historyId, period, idMap);
		});
	}
	
	private void changeEndToMax() {
		period = period.newSpanWithMaxEnd();
	}
}
