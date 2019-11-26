package nts.uk.ctx.pr.report.app.command.printconfig.socialinsurnoticreset;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.NameNotificationSet;

@Value
@AllArgsConstructor
public class NameNotificationSetCommand {
    private int other;
    private int listed;
    private int residentCard;
    private int addressOverseas;
    private String otherReason;
    private int shortResident;
    public NameNotificationSet toDomains(){
        return new NameNotificationSet(other, listed, residentCard, addressOverseas, shortResident, otherReason);
    }
}
