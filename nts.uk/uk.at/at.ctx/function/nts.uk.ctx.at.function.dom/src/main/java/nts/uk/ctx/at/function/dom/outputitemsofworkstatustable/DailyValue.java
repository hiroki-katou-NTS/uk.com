package nts.uk.ctx.at.function.dom.outputitemsofworkstatustable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.CommonAttributesOfForms;
import org.eclipse.persistence.internal.xr.ValueObject;

/**
 * ValueObject: 一日の値
 * @author chinh.hm
 */
@Getter
@Setter
@AllArgsConstructor
public class DailyValue  extends ValueObject{
    // 実績値
    private double actualValue;
    // 属性
    private CommonAttributesOfForms attributes;
    // 文字値
    private String characterValue;
    // 日付
    private GeneralDate date;
}
