package nts.uk.ctx.at.shared.infra.entity.worktime;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import nts.uk.ctx.at.shared.infra.entity.worktime.worktimeset.KshmtWorkTimeSetPK;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-12-23T17:00:11")
@StaticMetamodel(KshmtWorkTimeSet.class)
public class KshmtWorkTimeSet_ { 

    public static volatile SingularAttribute<KshmtWorkTimeSet, String> symbol;
    public static volatile SingularAttribute<KshmtWorkTimeSet, String> note;
    public static volatile SingularAttribute<KshmtWorkTimeSet, String> abname;
    public static volatile SingularAttribute<KshmtWorkTimeSet, Integer> worktimeSetMethod;
    public static volatile SingularAttribute<KshmtWorkTimeSet, String> color;
    public static volatile SingularAttribute<KshmtWorkTimeSet, KshmtWorkTimeSetPK> kshmtWorkTimeSetPK;
    public static volatile SingularAttribute<KshmtWorkTimeSet, String> name;
    public static volatile SingularAttribute<KshmtWorkTimeSet, String> memo;
    public static volatile SingularAttribute<KshmtWorkTimeSet, Integer> dailyWorkAtr;
    public static volatile SingularAttribute<KshmtWorkTimeSet, Integer> abolitionAtr;
    public static volatile SingularAttribute<KshmtWorkTimeSet, Integer> exclusVer;

}