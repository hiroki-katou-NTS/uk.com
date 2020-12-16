package nts.uk.ctx.at.request.app.find.application.optitem.optitemdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.anyitem.AnyItemNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.optionalitemvalue.AnyItemAmount;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.optionalitemvalue.AnyItemTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.optionalitemvalue.AnyItemTimes;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.optionalitemvalue.AnyItemValue;

import java.math.BigDecimal;
import java.util.Optional;

@AllArgsConstructor
@Getter
@Setter
public class AnyItemValueDto {
    private Integer itemNo;

    private BigDecimal times;

    private Integer amount;

    private Integer time;

    public AnyItemValue toDomain() {
        return new AnyItemValue(new AnyItemNo(this.itemNo),
                Optional.ofNullable(this.times != null ? new AnyItemTimes(this.times) : null),
                Optional.ofNullable(this.amount != null ? new AnyItemAmount(this.amount) : null),
                Optional.ofNullable(this.time != null ? new AnyItemTime(this.time) : null)
        );
    }
}
