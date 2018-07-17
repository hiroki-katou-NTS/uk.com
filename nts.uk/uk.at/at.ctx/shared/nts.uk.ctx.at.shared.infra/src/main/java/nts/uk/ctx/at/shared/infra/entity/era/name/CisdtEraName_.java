package nts.uk.ctx.at.shared.infra.entity.era.name;

import java.math.BigDecimal;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import nts.arc.time.GeneralDate;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-06-12T16:57:42")
@StaticMetamodel(CisdtEraName.class)
public class CisdtEraName_ { 

    public static volatile SingularAttribute<CisdtEraName, String> symbol;
    public static volatile SingularAttribute<CisdtEraName, GeneralDate> endDate;
    public static volatile SingularAttribute<CisdtEraName, String> eraName;
    public static volatile SingularAttribute<CisdtEraName, BigDecimal> systemType;
    public static volatile SingularAttribute<CisdtEraName, String> eraNameId;
    public static volatile SingularAttribute<CisdtEraName, GeneralDate> startDate;

}