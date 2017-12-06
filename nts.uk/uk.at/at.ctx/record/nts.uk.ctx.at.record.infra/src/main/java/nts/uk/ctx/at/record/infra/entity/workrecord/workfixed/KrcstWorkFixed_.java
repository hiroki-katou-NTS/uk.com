package nts.uk.ctx.at.record.infra.entity.workrecord.workfixed;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import nts.uk.ctx.at.record.infra.entity.workrecord.workfixed.KrcstWorkFixedPK;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-11-16T11:45:41")
@StaticMetamodel(KrcstWorkFixed.class)
public class KrcstWorkFixed_ { 

    public static volatile SingularAttribute<KrcstWorkFixed, KrcstWorkFixedPK> krcstWorkFixedPK;
    public static volatile SingularAttribute<KrcstWorkFixed, Date> fixedDate;
    public static volatile SingularAttribute<KrcstWorkFixed, Integer> processYm;
    public static volatile SingularAttribute<KrcstWorkFixed, String> confirmPid;
    public static volatile SingularAttribute<KrcstWorkFixed, Integer> confirmCls;

}