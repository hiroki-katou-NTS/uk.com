package nts.uk.ctx.at.request.app.command.setting.company.emailset;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.setting.company.emailset.*;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppEmailSetCommand {
    private int urlReason;
    private List<EmailContentCommand> emailList;

    public AppEmailSet toDomain(String companyId) {
        return new AppEmailSet(
                companyId,
                EnumAdaptor.valueOf(urlReason, NotUseAtr.class),
                emailList.stream().map(e -> new EmailContent(
                        EnumAdaptor.valueOf(e.getDivision(), Division.class),
                        StringUtils.isEmpty(e.getEmailSubject()) ? Optional.empty() : Optional.of(new EmailSubject(e.getEmailSubject())),
                        StringUtils.isEmpty(e.getEmailText()) ? Optional.empty() : Optional.of(new EmailText(e.getEmailText()))
                )).collect(Collectors.toList())
        );
    }
}
