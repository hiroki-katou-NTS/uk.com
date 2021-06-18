package nts.uk.ctx.at.request.app.command.application.optionalitem;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;

@Setter
@Getter
public class UpdateOptionalItemApplicationCommand {
    private ApplicationDto application;
    private AppDispInfoStartupDto appDispInfoStartup;
    private OptionalItemApplicationCommand optItemAppCommand;
}
