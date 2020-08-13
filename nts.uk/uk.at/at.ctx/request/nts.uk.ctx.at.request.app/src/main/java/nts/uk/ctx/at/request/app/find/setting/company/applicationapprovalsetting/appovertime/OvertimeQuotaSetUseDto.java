package nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.appovertime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.primitive.PrimitiveValueBase;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.appovertime.OvertimeQuotaSetUse;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OvertimeQuotaSetUseDto {
    private Integer overtimeAtr;
    private Integer flexAtr;
    private List<Integer> overTimeFrames;

    public OvertimeQuotaSetUse toDomain() {
        return OvertimeQuotaSetUse.create(overtimeAtr, flexAtr, overTimeFrames);
    }

    public static OvertimeQuotaSetUseDto fromDomain(OvertimeQuotaSetUse domain) {
        return new OvertimeQuotaSetUseDto(domain.getOvertimeAppAtr().value, domain.getFlexWorkAtr().value, domain.getTargetOvertimeLimit().stream().map(PrimitiveValueBase::v).collect(Collectors.toList()));
    }
}
