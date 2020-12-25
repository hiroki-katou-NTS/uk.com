package nts.uk.ctx.at.schedule.app.command.budget.premium.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PremiumSettingDto {
    private int iD;
    private String name;
    private Integer rate;

    private int unitPrice;

    private List<Integer> attendanceItems;
}
