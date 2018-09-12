package nts.uk.ctx.core.dom.socialinsurance.socialinsuranceoffice;

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
     * 都道府県コード
     */
    private PrefectureCode prefectureCode;

    /**
     * 都道府県名称
     */
    private PrefectureName prefectureName;

    /**
     * 履歴ID
     */
    private String historyID;

    /**
     * 社会保険用都道府県情報
     *
     * @param no             No
     * @param prefectureCode 都道府県コード
     * @param prefectureName 都道府県名称
     * @param historyID      履歴ID
     */
    public SocialInsurancePrefectureInformation(int no, String prefectureCode, String prefectureName, String historyID) {
        this.no             = no;
        this.prefectureCode = new PrefectureCode(prefectureCode);
        this.prefectureName = new PrefectureName(prefectureName);
        this.historyID      = historyID;
    }
}
