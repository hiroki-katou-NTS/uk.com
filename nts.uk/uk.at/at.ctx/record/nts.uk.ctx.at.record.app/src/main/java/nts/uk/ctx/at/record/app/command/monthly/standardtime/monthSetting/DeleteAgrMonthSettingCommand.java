package nts.uk.ctx.at.record.app.command.monthly.standardtime.monthSetting;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class DeleteAgrMonthSettingCommand {

    private String employeeId;

    private BigDecimal yearMonth;
}
