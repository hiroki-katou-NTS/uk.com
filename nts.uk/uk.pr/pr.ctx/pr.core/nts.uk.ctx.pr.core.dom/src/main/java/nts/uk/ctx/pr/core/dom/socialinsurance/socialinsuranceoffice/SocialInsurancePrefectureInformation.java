package nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 社会保険用都道府県情報
 */
@Getter
public class SocialInsurancePrefectureInformation extends AggregateRoot {

    /**
     * No
     */
    private int no;

    /**
     * 履歴ID
     */
    private String historyID;

    /**
     * 年月開始
     */
    public int startYearMonth;

    /**
     * 年月終了
     */
    public int endYearMonth;

    /**
     * 都道府県コード
     */
    private PrefectureCode prefectureCode;

    /**
     * 都道府県名称
     */
    private PrefectureName prefectureName;



    /**
     * 社会保険用都道府県情報
     *
     * @param no             No
     * @param prefectureCode 都道府県コード
     * @param prefectureName 都道府県名称
     * @param historyID      履歴ID
     */
    public SocialInsurancePrefectureInformation(int no, String historyID,int startYearMonth,int endYearMonth, String prefectureCode, String prefectureName) {
        this.no             = no;
        this.historyID      = historyID;
        this.startYearMonth = startYearMonth;
        this.endYearMonth   = endYearMonth;
        this.prefectureCode = new PrefectureCode(prefectureCode);
        this.prefectureName = new PrefectureName(prefectureName);

    }
}
