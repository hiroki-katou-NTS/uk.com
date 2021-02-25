package nts.uk.ctx.at.request.app.find.setting.workplace.requestbyworkplace;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.setting.workplace.appuseset.ApplicationUseSetDto;
import nts.uk.ctx.at.request.dom.setting.workplace.requestbyworkplace.RequestByWorkplace;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestByWorkplaceDto {
    private String workplaceId;
    private List<ApplicationUseSetDto> settings;

    public static RequestByWorkplaceDto fromDomain(RequestByWorkplace domain) {
        return new RequestByWorkplaceDto(
                domain.getWorkplaceID(),
                domain.getApprovalFunctionSet().getAppUseSetLst().stream().map(ApplicationUseSetDto::fromDomain).collect(Collectors.toList())
        );
    }
}
