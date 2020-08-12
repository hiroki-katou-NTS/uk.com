package nts.uk.file.at.app.export.bento;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.calendar.period.DatePeriod;

import java.util.List;

/**
 * 予約確認一覧起動情報
 * @author tuan.ha1
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CreateOrderInfoDataSource {
    /** 予約時間帯 */

    /** 会社ID */

    /** 合計書タイトル */

    /** 合計書出力 */

    /** 合計書抽出条件 */

    /** 商品毎ページ分け設定 */

    /** 明細書タイトル */

    /** 明細書出力 */

    /** 明細書抽出条件 */

    /** 注文済扱い設定 */

    /** 注文設定タブ */

    /** 社員ID */

    private DatePeriod period;
    private List<String> workplaceIds;
    private List<String> workplaceCodes;
    private int totalExtractCondition;
    private int itemExtractCondition;
    private int frameNo;
    private String totalTitle;
    private String detailTitle;
    private int reservationClosingTimeFrame;
}
