package nts.uk.ctx.at.request.app.command.application.optionalitem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.application.optional.OptionalItemApplication;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.optionalitemappsetting.OptionalItemApplicationTypeCode;
import nts.uk.ctx.at.shared.dom.scherec.anyitem.AnyItemNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.optionalitemvalue.AnyItemAmount;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.optionalitemvalue.AnyItemTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.optionalitemvalue.AnyItemTimes;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.optionalitemvalue.AnyItemValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OptionalItemApplicationCommand {

    private String code;
    private List<AnyItemValueDto> optionalItems;

    public OptionalItemApplication toDomain() {
        List<AnyItemValue> anyItemValues = new ArrayList();
        optionalItems.forEach(item -> {
            Optional<AnyItemTimes> times = item.getTimes() != null ? Optional.of(new AnyItemTimes(item.getTimes())) : Optional.empty();
            Optional<AnyItemAmount> amount = item.getAmount() != null ? Optional.of(new AnyItemAmount(item.getAmount())) : Optional.empty();
            Optional<AnyItemTime> time = item.getTime() != null ? Optional.of(new AnyItemTime(item.getTime())) : Optional.empty();
            anyItemValues.add(new AnyItemValue(new AnyItemNo(item.getItemNo()), times, amount, time));
        });
        return new OptionalItemApplication(new OptionalItemApplicationTypeCode(code), anyItemValues);
    }
}
