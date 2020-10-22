package nts.uk.ctx.at.function.dom.outputitemsofannualworkledger;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 日次出力１列
 */
@Getter
@Setter
@AllArgsConstructor
public class DailyOutputColumnData {

    // 右側の値
    private List<DailyValue> lstRightValue;

    // 左側の値
    private List<DailyValue> lstLeftValue;

}
