package nts.uk.ctx.pr.report.app.find.printconfig.socialinsurnoticreset;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.NameNotificationSet;


@Value
@Data
@AllArgsConstructor
public class ReasonRomajiNameDto {
    private String basicPenNumber;
    private EmpNameReportDto empNameReport;

    @Value
    @Data
    @AllArgsConstructor
    public static class NameNotificationSetDto {
        private int other;
        private int listed;
        private int residentCard;
        private int addressOverseas;
        private String otherReason;
        private int shortResident;

        public static NameNotificationSetDto fromDomain(NameNotificationSet domain) {
            return new NameNotificationSetDto(
                    domain.getOther(),
                    domain.getListed(),
                    domain.getResidentCard().value,
                    domain.getAddressOverseas(),
                    domain.getOtherReason().map(i->i.v()).orElse(null),
                    domain.getShortResident() );
        }
    }
}

