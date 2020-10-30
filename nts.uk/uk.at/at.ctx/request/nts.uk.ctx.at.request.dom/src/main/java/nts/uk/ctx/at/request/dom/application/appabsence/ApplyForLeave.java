package nts.uk.ctx.at.request.dom.application.appabsence;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.application.Application;

@Getter
@AllArgsConstructor
public class ApplyForLeave extends Application {

    
    public ApplyForLeave(Application application) {
        super(application);
    }
}
