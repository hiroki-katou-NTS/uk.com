package nts.uk.ctx.exio.dom.input.canonicalize.domains.organization.workplace;

import java.util.List;
import java.util.Set;

import lombok.val;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.util.Either;
import nts.uk.ctx.exio.dom.input.errors.RecordError;

/**
 * 履歴の重複をチェックし、エラーレコードを除外する
 */
public class CheckHistoryDuplications {

	public static Either.Sequence<RecordError, RecordWithPeriod> check(List<RecordWithPeriod> records) {

		if (records.isEmpty()) {
			return Either.sequenceEmpty();
		}
		
		val periods = PeriodUtil.getSortedPeriods(records);
		
		// 期間の重複をチェック
		Set<DatePeriod> duplicatedPeriods = PeriodUtil.collectDuplicated(periods);
		
		return Either.sequenceOf(records)
				.separate(r -> !duplicatedPeriods.contains(r.period))
				.mapLeft(r -> RecordError.record(r.getRowNo(), "履歴の期間が重複しています。"));
	}
	
	static Either.Sequence<RecordWithPeriod, RecordWithPeriod> checkDuplicates(List<RecordWithPeriod> records) {
		
		val periods = PeriodUtil.getSortedPeriods(records);
		
		// 期間の重複をチェック
		Set<DatePeriod> duplicatedPeriods = PeriodUtil.collectDuplicated(periods);
		
		return Either.sequenceOf(records)
				.separate(r -> duplicatedPeriods.contains(r.period));
	}
}
