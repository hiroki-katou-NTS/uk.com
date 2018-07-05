package nts.uk.ctx.at.shared.infra.entity.era.name;

import java.math.BigDecimal;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import nts.arc.time.GeneralDate;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-06-12T16:57:42")
@StaticMetamodel(CmnmtEra.class)
public class CmnmtEra_ { 

    public static volatile SingularAttribute<CmnmtEra, String> eraMark;
    public static volatile SingularAttribute<CmnmtEra, GeneralDate> endD;
    public static volatile SingularAttribute<CmnmtEra, String> eraName;
    public static volatile SingularAttribute<CmnmtEra, BigDecimal> fixAtr;
    public static volatile SingularAttribute<CmnmtEra, String> histId;
    public static volatile SingularAttribute<CmnmtEra, GeneralDate> strD;

}