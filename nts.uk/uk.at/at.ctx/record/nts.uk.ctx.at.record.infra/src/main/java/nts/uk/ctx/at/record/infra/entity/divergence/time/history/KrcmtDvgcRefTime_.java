package nts.uk.ctx.at.record.infra.entity.divergence.time.history;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import nts.uk.ctx.at.record.infra.entity.divergence.time.history.KrcmtDvgcRefTimePK;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-03-27T23:12:35")
@StaticMetamodel(KrcmtDvgcRefTime.class)
public class KrcmtDvgcRefTime_ { 

    public static volatile SingularAttribute<KrcmtDvgcRefTime, BigDecimal> errorTime;
    public static volatile SingularAttribute<KrcmtDvgcRefTime, BigDecimal> dvgcTimeUseSet;
    public static volatile SingularAttribute<KrcmtDvgcRefTime, BigDecimal> alarmTime;
    public static volatile SingularAttribute<KrcmtDvgcRefTime, KrcmtDvgcRefTimePK> id;

}