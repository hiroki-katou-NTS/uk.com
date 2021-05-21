package nts.uk.ctx.at.record.dom.workrecord.workmanagement.manhoursummarytable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.auth.dom.employmentrole.DisabledSegment;
import org.eclipse.persistence.internal.xr.ValueObject;

import java.util.List;

/**
 * 	フォーマット詳細設定
 */
@AllArgsConstructor
@Getter
public class FormatAdvancedSetting extends ValueObject {
    /** 表示形式 */
    private DisplayFormat displayFormat;
    /** 合計単位 */
    private TotalUnit totalUnit;
    /** 縦計・横計を表示する */
    private DisabledSegment disabledSegment;
    /** 集計項目一覧 */
    private List<SummaryItem> totalItemList;

    //TODO
    /**
     * 	[1] 工数集計表出力内容を作成する
     * @return 	工数集計表出力内容
     */
    public void createOutputContent(List<GeneralDate> dateList, List<YearMonth> yearMonthList){

    }
}
