package nts.uk.ctx.at.request.infra.entity.application.timeleaveapplication;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@Getter
@EqualsAndHashCode
public class KrqdtAppTimeHdPK {

    @Column(name = "CID")
    public String companyID;

    @Column(name = "APP_ID")
    public String appID;

    @Column(name = "TIME_HD_TYPE")
    public int timeHdType;

    @Column(name = "FRAME_NO")
    public int frameNo;

}
