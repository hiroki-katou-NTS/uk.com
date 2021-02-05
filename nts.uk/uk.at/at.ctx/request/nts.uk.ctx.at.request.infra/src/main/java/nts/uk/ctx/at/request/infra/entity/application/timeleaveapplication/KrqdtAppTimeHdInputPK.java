package nts.uk.ctx.at.request.infra.entity.application.timeleaveapplication;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class KrqdtAppTimeHdInputPK {

    @Column(name = "CID")
    public String companyID;

    @Column(name = "APP_ID")
    public String appID;

    @Column(name = "TIME_HD_TYPE")
    public int timeHdType;

}
