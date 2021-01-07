package nts.uk.ctx.at.request.app.command.application.appabsence;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.app.find.application.gobackdirectly.WorkInformationDto;
import nts.uk.ctx.at.request.dom.application.appabsence.ReflectFreeTimeApp;
import nts.uk.ctx.at.shared.app.find.common.TimeZoneWithWorkNoDto;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@Data
@AllArgsConstructor
public class ReflectFreeTimeAppDto {

    // 時間帯(勤務NO付き)
    private List<TimeZoneWithWorkNoDto> workingHours;
    
    // 時間消化申請
    private TimeDigestApplicationDto timeDegestion;
    
    // 勤務情報
    private WorkInformationDto workInfo;
    
    // するしない区分
    private int workChangeUse;
    
    public ReflectFreeTimeApp toDomain() {
        return new ReflectFreeTimeApp(
                Optional.ofNullable(workingHours.stream().map(x -> x.toDomain()).collect(Collectors.toList())), 
                Optional.ofNullable(timeDegestion.toDomain()), 
                workInfo.toDomain(), 
                EnumAdaptor.valueOf(workChangeUse, NotUseAtr.class));
    }
}
