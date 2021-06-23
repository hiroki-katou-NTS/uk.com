package nts.uk.ctx.at.function.app.query.holidayconfirmationtable;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


/**
 * 代休発生取得情報
 */
@AllArgsConstructor
@Getter
@Setter
public class ObservationOfExitLeave {
    private boolean er;
    // 使用数: 代休使用数合計
    private Double numOfUse;
    // 未消化数 : 代休未消化数
    private Double undeterminedNumber;
    // 残数 : 代休残数
    private Double numberOfRemaining;
    //発生取得明細 : 発生取得明細
}
