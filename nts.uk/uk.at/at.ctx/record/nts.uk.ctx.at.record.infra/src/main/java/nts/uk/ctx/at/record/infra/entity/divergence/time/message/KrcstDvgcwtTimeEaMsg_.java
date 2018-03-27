package nts.uk.ctx.at.record.infra.entity.divergence.time.message;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import nts.uk.ctx.at.record.infra.entity.divergence.time.message.KrcstDvgcwtTimeEaMsgPK;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-03-12T19:03:47")
@StaticMetamodel(KrcstDvgcwtTimeEaMsg.class)
public class KrcstDvgcwtTimeEaMsg_ { 

    public static volatile SingularAttribute<KrcstDvgcwtTimeEaMsg, String> insDate;
    public static volatile SingularAttribute<KrcstDvgcwtTimeEaMsg, String> updCcd;
    public static volatile SingularAttribute<KrcstDvgcwtTimeEaMsg, String> updPg;
    public static volatile SingularAttribute<KrcstDvgcwtTimeEaMsg, String> insCcd;
    public static volatile SingularAttribute<KrcstDvgcwtTimeEaMsg, String> insScd;
    public static volatile SingularAttribute<KrcstDvgcwtTimeEaMsg, String> errorMessage;
    public static volatile SingularAttribute<KrcstDvgcwtTimeEaMsg, String> alarmMessage;
    public static volatile SingularAttribute<KrcstDvgcwtTimeEaMsg, String> updScd;
    public static volatile SingularAttribute<KrcstDvgcwtTimeEaMsg, KrcstDvgcwtTimeEaMsgPK> id;
    public static volatile SingularAttribute<KrcstDvgcwtTimeEaMsg, BigDecimal> exclusVer;
    public static volatile SingularAttribute<KrcstDvgcwtTimeEaMsg, String> updDate;
    public static volatile SingularAttribute<KrcstDvgcwtTimeEaMsg, String> insPg;

}