package nts.uk.ctx.at.request.infra.entity.application.businesstrip;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@Getter
public class KrqdtAppTripPK {

    @Column(name = "CID")
    public String companyID;

    @Column(name = "APP_ID")
    public String appID;

    @Column(name = "APP_DATE")
    public GeneralDate targetDate;

}
