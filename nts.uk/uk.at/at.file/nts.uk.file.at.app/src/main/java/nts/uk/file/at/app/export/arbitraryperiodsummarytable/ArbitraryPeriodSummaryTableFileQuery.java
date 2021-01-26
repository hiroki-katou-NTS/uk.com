package nts.uk.file.at.app.export.arbitraryperiodsummarytable;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ArbitraryPeriodSummaryTableFileQuery {
    //「１」ExcelPdf区分
    private int mode; //v

    //「２」選択期間（選択した集計枠コード & 期間(年月日)(From-To)）

    private String aggrFrameCode;//V
    private String startDate;//v

    private String endDate;//v

    //「３」社員リスト
    private List<String> lstEmpIds;//v

    //「４」定型自由区分
    private int settingClassification;//v

    //「５」項目選択の設定ID
    private String settingId;//v

    //「６」ゼロ表示区分
    private boolean isZeroDisplay;//v

    //「７」改ページ区分
    private int pageBreakClassification;//v

    //「８」改ページ職場階層
    private int pageBreakWorkplaceHierarchy;
    //「９」明細区分
    private int itemClassification;
    //「１０」職場計区分
    private int workplaceClassification;
    //「１１」総合計区分
    private int totalClassification;

    //「１２」職場累計区分
    private int cumulativeWorkplaceClassification;

    //「１３」職場累計印刷対象リスト
    private int workplacePrintTargetList;
}
