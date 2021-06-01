package nts.uk.ctx.at.record.dom.workrecord.workmanagement.manhoursummarytable;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import org.eclipse.persistence.internal.xr.ValueObject;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

    public void calculate(TotalUnit totalUnit, List<GeneralDate> dateList, List<YearMonth> yearList){
//        List<VerticalValueDaily> lstVertical = dateList.
    }

    /**
     * [prv-1] 年月日別に縦計を計算する
     * @param dateList 	List<年月日>
     */
    private void calculateVerticalTotalByDate(List<GeneralDate> dateList){
//        List<VerticalValueDaily> lstVertical = dateList.
    }
}
