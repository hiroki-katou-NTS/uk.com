package nts.uk.ctx.sys.assist.dom.datarestoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.sys.assist.dom.tablelist.TableList;
import nts.uk.shr.com.time.calendar.Year;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;
import nts.uk.shr.com.time.calendar.period.YearPeriod;

/**
 * 復旧対象
 */
@Getter
public class RestorationTarget extends DomainObject {

	/**
	 * データ復旧処理ID
	 */
	private String dataRecoveryProcessId;

	/**
	 * 復旧カテゴリ
	 */
	private String recoveryCategory;

	/**
	 * 保存期間区分
	 */
	private RetentionPeriodIndicator retentionPeriodIndicator;

	/**
	 * 復旧対象年
	 */
	private Optional<YearPeriod> recoveryTargetYear;

	/**
	 * 復旧対象年月
	 */
	private Optional<YearMonthPeriod> recoveryTargetYM;

	/**
	 * 復旧対象日
	 */
	private Optional<DatePeriod> recoveryTargetDate;

	public RestorationTarget(String dataRecoveryProcessId, String recoveryCategory, int retentionPeriodIndicator,
			Integer recoveryTargetStartYear, Integer recoveryTargetEndYear, Integer recoveryTargetStartYM,
			Integer recoveryTargetEndYM, GeneralDate recoveryTargetStartDate, GeneralDate recoveryTargetEndDate) {
		this.dataRecoveryProcessId = dataRecoveryProcessId;
		this.recoveryCategory = recoveryCategory;
		this.retentionPeriodIndicator = EnumAdaptor.valueOf(retentionPeriodIndicator, RetentionPeriodIndicator.class);
		// 復旧対象年
		if (Objects.isNull(recoveryTargetStartYear) && Objects.isNull(recoveryTargetEndYear)) {
			this.recoveryTargetYear = Optional.empty();
		} else {
			this.recoveryTargetYear = Optional.of(
					new YearPeriod(!Objects.isNull(recoveryTargetStartYear) ? new Year(recoveryTargetStartYear) : null,
							!Objects.isNull(recoveryTargetEndYear) ? new Year(recoveryTargetEndYear) : null));
		}
		// 復旧対象年月
		if (Objects.isNull(recoveryTargetStartYM) && Objects.isNull(recoveryTargetEndYM)) {
			this.recoveryTargetYM = Optional.empty();
		} else {
			this.recoveryTargetYM = Optional.of(new YearMonthPeriod(
					!Objects.isNull(recoveryTargetStartYM) ? new YearMonth(recoveryTargetStartYM) : null,
					!Objects.isNull(recoveryTargetEndYM) ? new YearMonth(recoveryTargetEndYM) : null));
		}
		// 復旧対象日
		if (Objects.isNull(recoveryTargetStartDate) && Objects.isNull(recoveryTargetEndDate)) {
			this.recoveryTargetDate = Optional.empty();
		} else {
			this.recoveryTargetDate = Optional
					.of(new DatePeriod(!Objects.isNull(recoveryTargetStartDate) ? recoveryTargetStartDate : null,
							!Objects.isNull(recoveryTargetEndDate) ? recoveryTargetEndDate : null));
		}
	}

	public static List<RestorationTarget> createFromTableList(List<TableList> tableList, String processingId){
		List<RestorationTarget> listTableList = new ArrayList<>();
		RestorationTarget restoreTarget = null;
		Integer saveDateFrom = null;
		Integer saveDateTo = null;
		for(TableList tableListData: tableList){
			switch (tableListData.getRetentionPeriodCls()) {
			case FULL_TIME:
				restoreTarget = new RestorationTarget(processingId, tableListData.getCategoryId(), tableListData.getRetentionPeriodCls().value, null, null, null, null, null, null);
				break;
			case ANNUAL:
				saveDateFrom = tableListData.getSaveDateFrom().isPresent() ? Integer.parseInt(tableListData.getSaveDateFrom().get().replaceAll("-", "").replaceAll("/", "")) : null;
				saveDateTo = tableListData.getSaveDateTo().isPresent() ? Integer.parseInt(tableListData.getSaveDateTo().get().replaceAll("-", "").replaceAll("/", "")) : null;
				restoreTarget = new RestorationTarget(processingId, tableListData.getCategoryId(), tableListData.getRetentionPeriodCls().value, saveDateFrom, saveDateTo, null, null, null, null);
				break;
			case MONTHLY:
				saveDateFrom = tableListData.getSaveDateFrom().isPresent() ? Integer.parseInt(tableListData.getSaveDateFrom().get().replaceAll("-", "").replaceAll("/", "")) : null;
				saveDateTo = tableListData.getSaveDateTo().isPresent() ? Integer.parseInt(tableListData.getSaveDateTo().get().replaceAll("-", "").replaceAll("/", "")) : null;
				restoreTarget = new RestorationTarget(processingId, tableListData.getCategoryId(), tableListData.getRetentionPeriodCls().value, null, null, saveDateFrom, saveDateTo, null, null);
				break;
			case DAILY:
				GeneralDate dateFrom = tableListData.getSaveDateFrom().isPresent() ? GeneralDate.fromString(tableListData.getSaveDateFrom().get(), "yyyy/MM/dd") : null;
				GeneralDate dateTo = tableListData.getSaveDateTo().isPresent() ? GeneralDate.fromString(tableListData.getSaveDateTo().get(), "yyyy/MM/dd") : null;
				restoreTarget = new RestorationTarget(processingId, tableListData.getCategoryId(), tableListData.getRetentionPeriodCls().value, null, null, null, null, dateFrom, dateTo);
				break;
			}
			listTableList.add(restoreTarget);
		}
		return listTableList.stream().filter(distinctByKey(RestorationTarget::getRecoveryCategory)).collect(Collectors.toList());
	}

	public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
	    Set<Object> seen = ConcurrentHashMap.newKeySet();
	    return t -> seen.add(keyExtractor.apply(t));
	}
}
