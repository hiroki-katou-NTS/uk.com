package nts.uk.ctx.at.function.dom.outputitemsofworkstatustable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * 出力１行
 * @author chinh.hm
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OutputItemOneLine {
    // 一行の合計
    private double totalOfOneLine;
    // 出力項目名称
    private String outPutItemName;
    // 出力項目の値
    private List<DailyValue> outItemValue;

}
