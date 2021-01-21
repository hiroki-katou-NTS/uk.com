package nts.uk.ctx.at.request.app.find.setting.company.emailset;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.primitive.PrimitiveValue;
import nts.uk.ctx.at.request.app.command.setting.company.emailset.EmailContentCommand;
import nts.uk.ctx.at.request.dom.setting.company.emailset.*;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppEmailSetDto {
    private int urlReason;
    private List<EmailContentDto> emailList;

    public AppEmailSetDto(AppEmailSet domain) {
        this.urlReason = domain.getUrlReason().value;
        this.emailList = domain.getEmailContentLst().stream().map(e -> new EmailContentDto(
                e.getDivision().value,
                e.getOpEmailSubject().map(PrimitiveValue::v).orElse(null),
                e.getOpEmailText().map(PrimitiveValue::v).orElse(null)
        )).collect(Collectors.toList());
    }
}
