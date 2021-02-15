package nts.uk.ctx.at.record.infra.entity.divergence.time.history;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import nts.uk.ctx.at.record.infra.entity.divergence.time.history.KrcstDrtPK;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-03-27T23:12:35")
@StaticMetamodel(KrcstDrt.class)
public class KrcstDrt_ { 

    public static volatile SingularAttribute<KrcstDrt, BigDecimal> errorTime;
    public static volatile SingularAttribute<KrcstDrt, BigDecimal> dvgcTimeUseSet;
    public static volatile SingularAttribute<KrcstDrt, BigDecimal> alarmTime;
    public static volatile SingularAttribute<KrcstDrt, KrcstDrtPK> id;

}