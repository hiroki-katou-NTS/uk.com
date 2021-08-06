package nts.uk.ctx.at.schedule.app.command.budget.premium.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.personcostcalc.premiumitem.service.AttendanceNamePriniumDto;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PremiumSettingDto {
    private int iD;
    private String name;
    private Integer useAtr;
    private Integer rate;
    private int unitPrice;
    private List<Integer> attendanceItems;
    private List<AttendanceNamePriniumDto> attendanceNames;
}
