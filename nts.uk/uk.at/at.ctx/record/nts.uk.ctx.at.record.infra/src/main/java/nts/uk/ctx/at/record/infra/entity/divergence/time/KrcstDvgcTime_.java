package nts.uk.ctx.at.record.infra.entity.divergence.time;

import java.math.BigDecimal;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-03-27T23:12:35")
@StaticMetamodel(KrcstDvgcTime.class)
public class KrcstDvgcTime_ { 

    public static volatile SingularAttribute<KrcstDvgcTime, String> dvgcTimeName;
    public static volatile SingularAttribute<KrcstDvgcTime, BigDecimal> reasonSelectCanceled;
    public static volatile SingularAttribute<KrcstDvgcTime, BigDecimal> dvgcTimeUseSet;
    public static volatile SingularAttribute<KrcstDvgcTime, BigDecimal> dvgcReasonInputed;
    public static volatile SingularAttribute<KrcstDvgcTime, BigDecimal> dvgcReasonSelected;
    public static volatile SingularAttribute<KrcstDvgcTime, BigDecimal> reasonInputCanceled;
    public static volatile SingularAttribute<KrcstDvgcTime, KrcstDvgcTimePK> id;
    public static volatile SingularAttribute<KrcstDvgcTime, BigDecimal> dvgcType;
    public static volatile ListAttribute<KrcstDvgcTime, KrcstDvgcAttendance> krcstDvgcAttendances;

}