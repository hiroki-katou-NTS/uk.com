package nts.uk.screen.at.ws.kaf.kaf021;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

import java.util.List;

@Setter
@Getter
public class SpecialProvisionOfAgreementAppParam {
    private GeneralDate startDate;
    private GeneralDate endDate;
    private List<Integer> status;
}
