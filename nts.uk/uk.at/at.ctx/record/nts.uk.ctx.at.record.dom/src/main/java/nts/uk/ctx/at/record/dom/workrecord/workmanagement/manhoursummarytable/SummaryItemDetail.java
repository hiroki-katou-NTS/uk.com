package nts.uk.ctx.at.record.dom.workrecord.workmanagement.manhoursummarytable;

import lombok.Getter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import org.eclipse.persistence.internal.xr.ValueObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 	集計項目詳細
 */
@Getter
public class SummaryItemDetail extends ValueObject {
    /** コード */
    private String code;
    /** 表示情報 */
    private DisplayInformation displayInfo;
    /** 子階層リスト */
    private List<SummaryItemDetail> childHierarchyList;
    /** 縦計リスト */
    private List<VerticalValueDaily> verticalTotalList;
    /** 期間合計 */
    private Optional<Integer> totalPeriod;

    /**
     * 	[C-1] 新規作成
     * @param code コード
     * @param displayInfo 表示情報
     * @param childHierarchyList 子階層リスト
     */
    public SummaryItemDetail(String code, DisplayInformation displayInfo, List<SummaryItemDetail> childHierarchyList) {
        this.code = code;
        this.displayInfo = displayInfo;
        this.childHierarchyList = childHierarchyList;
        this.verticalTotalList = Collections.emptyList();
        this.totalPeriod = Optional.empty();
    }

    /**
     * 	[1] 合計を計算する
     * @param totalUnit 合計単位
     * @param dateList List<年月日>
     * @param yearMonthList List<年月>
     */
    public void calculateTotal(TotalUnit totalUnit, List<GeneralDate> dateList, List<YearMonth> yearMonthList) {
        if (totalUnit == TotalUnit.DATE)
            calculateVerticalTotalByDate(dateList);
        else
            calculateVerticalTotalByYearMonth(yearMonthList);

        calculateHorizontalTotal();
    }

    /**
     * [2] 横計を計算する
     */
    public void calculateHorizontalTotal() {
        val workingHours = verticalTotalList.stream().mapToInt(VerticalValueDaily::getWorkingHours).sum();
        totalPeriod = Optional.of(workingHours);
    }

    /**
     * [prv-1] 年月日別に縦計を計算する
     * @param dateList 	List<年月日>
     */
    private void calculateVerticalTotalByDate(List<GeneralDate> dateList) {
        List<VerticalValueDaily> lstVertical = new ArrayList<>();
        for (GeneralDate date : dateList) {
            val childVerticalList = childHierarchyList.stream().flatMap(x -> verticalTotalList.stream()).collect(Collectors.toList());
            val workingTime = childVerticalList.stream().filter(x -> x.getDate().equals(date)).mapToInt(VerticalValueDaily::getWorkingHours).sum();
            lstVertical.add(new VerticalValueDaily(workingTime, null, date)); //TODO 日々縦計値#日々縦計値($対象年月日,Optional.empty,$作業時間)
        }
        if (!lstVertical.isEmpty())
            verticalTotalList = lstVertical;
    }

    /**
     * [prv-2] 年月別に縦計を計算する
     * @param yearMonthList List<年月>
     */
    private void calculateVerticalTotalByYearMonth(List<YearMonth> yearMonthList) {
        List<VerticalValueDaily> lstVertical = new ArrayList<>();
        for (val ym : yearMonthList) {
            val childVerticalList = childHierarchyList.stream().flatMap(x -> verticalTotalList.stream()).collect(Collectors.toList());
            val workingTime = childVerticalList.stream().filter(x -> x.getYearMonth().year() == ym.year()).mapToInt(VerticalValueDaily::getWorkingHours).sum();
            lstVertical.add(new VerticalValueDaily(workingTime, ym, null));  //TODO 日々縦計値#日々縦計値(Optional.empty,$対象年月,$作業時間)
        }
        if (!lstVertical.isEmpty())
            verticalTotalList = lstVertical;
    }
}
