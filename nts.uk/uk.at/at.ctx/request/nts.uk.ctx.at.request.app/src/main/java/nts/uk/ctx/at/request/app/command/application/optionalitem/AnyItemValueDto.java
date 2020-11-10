package nts.uk.ctx.at.request.app.command.application.optionalitem;

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
                Optional.of(new AnyItemTimes(this.times)),
                Optional.of(new AnyItemAmount(this.amount)),
                Optional.of(new AnyItemTime(this.time))
        );
    }
}
