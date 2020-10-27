package nts.uk.ctx.at.record.infra.entity.divergence.time;

import java.math.BigDecimal;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-03-27T23:12:35")
@StaticMetamodel(KrcmtDvgcTime.class)
public class KrcmtDvgcTime_ { 

    public static volatile SingularAttribute<KrcmtDvgcTime, String> dvgcTimeName;
    public static volatile SingularAttribute<KrcmtDvgcTime, BigDecimal> reasonSelectCanceled;
    public static volatile SingularAttribute<KrcmtDvgcTime, BigDecimal> dvgcTimeUseSet;
    public static volatile SingularAttribute<KrcmtDvgcTime, BigDecimal> dvgcReasonInputed;
    public static volatile SingularAttribute<KrcmtDvgcTime, BigDecimal> dvgcReasonSelected;
    public static volatile SingularAttribute<KrcmtDvgcTime, BigDecimal> reasonInputCanceled;
    public static volatile SingularAttribute<KrcmtDvgcTime, KrcmtDvgcTimePK> id;
    public static volatile SingularAttribute<KrcmtDvgcTime, BigDecimal> dvgcType;
    public static volatile ListAttribute<KrcmtDvgcTime, KrcmtDvgcAttendance> krcmtDvgcAttendances;

}