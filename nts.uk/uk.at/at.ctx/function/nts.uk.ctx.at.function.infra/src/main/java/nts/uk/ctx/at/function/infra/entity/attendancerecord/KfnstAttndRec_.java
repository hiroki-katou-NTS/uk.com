package nts.uk.ctx.at.function.infra.entity.attendancerecord;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.KfnstAttndRecPK;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-05-14T13:26:53")
@StaticMetamodel(KfnstAttndRec.class)
public class KfnstAttndRec_ { 

    public static volatile SingularAttribute<KfnstAttndRec, String> itemName;
    public static volatile SingularAttribute<KfnstAttndRec, BigDecimal> useAtr;
    public static volatile SingularAttribute<KfnstAttndRec, KfnstAttndRecPK> id;
    public static volatile SingularAttribute<KfnstAttndRec, BigDecimal> attribute;

}