package nts.uk.ctx.at.function.dom.outputitemsofannualworkledger;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import org.eclipse.persistence.internal.xr.ValueObject;

/**
 * 日次一日の値
 */
@Getter
@Setter
@AllArgsConstructor
public class DailyValue extends ValueObject {

    // 実績値
    private Double actualValue;

    // 文字値
    private String characterValue;

    // 日付
    private GeneralDate date;

}
