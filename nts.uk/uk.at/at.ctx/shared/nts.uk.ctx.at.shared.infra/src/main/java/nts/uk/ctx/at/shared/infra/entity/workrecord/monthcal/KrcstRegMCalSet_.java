package nts.uk.ctx.at.shared.infra.entity.workrecord.monthcal;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(KrcstRegMCalSet.class)
public abstract class KrcstRegMCalSet_ { 

    public static volatile SingularAttribute<KrcstRegMCalSet, Integer> includeHolidayAggr;
    public static volatile SingularAttribute<KrcstRegMCalSet, Integer> includeHolidayOt;
    public static volatile SingularAttribute<KrcstRegMCalSet, Integer> includeLegalOt;
    public static volatile SingularAttribute<KrcstRegMCalSet, Integer> includeLegalAggr;
    public static volatile SingularAttribute<KrcstRegMCalSet, Integer> includeExtraOt;
    public static volatile SingularAttribute<KrcstRegMCalSet, Integer> includeExtraAggr;

}