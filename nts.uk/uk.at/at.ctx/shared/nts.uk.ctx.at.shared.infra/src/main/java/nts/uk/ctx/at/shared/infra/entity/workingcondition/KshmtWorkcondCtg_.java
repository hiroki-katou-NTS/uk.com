package nts.uk.ctx.at.shared.infra.entity.workingcondition;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtWorkcondCtgPK;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtWorkcondCtgTs;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-12-11T13:52:52")
@StaticMetamodel(KshmtWorkcondCtg.class)
public class KshmtWorkcondCtg_ { 

    public static volatile SingularAttribute<KshmtWorkcondCtg, KshmtWorkcondCtgPK> kshmtWorkcondCtgPK;
    public static volatile SingularAttribute<KshmtWorkcondCtg, String> workTypeCode;
    public static volatile SingularAttribute<KshmtWorkcondCtg, String> workTimeCode;
    public static volatile SingularAttribute<KshmtWorkcondCtg, Integer> exclusVer;
    public static volatile ListAttribute<KshmtWorkcondCtg, KshmtWorkcondCtgTs> kshmtWorkcondCtgTss;

}