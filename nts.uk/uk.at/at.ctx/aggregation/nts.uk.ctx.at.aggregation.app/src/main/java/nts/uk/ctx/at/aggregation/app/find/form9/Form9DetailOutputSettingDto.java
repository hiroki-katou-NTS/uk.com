package nts.uk.ctx.at.aggregation.app.find.form9;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Form9DetailOutputSettingDto {
    private int roundingUnit;

    /** 端数処理  **/
    private int roundingMode;

    /** 勤務時間がない場合に属性を空白とするか **/
    private boolean attributeBlankIfZero;

}
