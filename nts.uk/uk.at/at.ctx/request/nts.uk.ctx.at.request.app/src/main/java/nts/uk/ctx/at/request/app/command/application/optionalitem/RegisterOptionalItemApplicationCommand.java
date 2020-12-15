package nts.uk.ctx.at.request.app.command.application.optionalitem;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;

@Getter
@Setter
public class RegisterOptionalItemApplicationCommand {
    private ApplicationDto application;
    private AppDispInfoStartupDto appDispInfoStartup;
    private OptionalItemApplicationCommand optItemAppCommand;
}
