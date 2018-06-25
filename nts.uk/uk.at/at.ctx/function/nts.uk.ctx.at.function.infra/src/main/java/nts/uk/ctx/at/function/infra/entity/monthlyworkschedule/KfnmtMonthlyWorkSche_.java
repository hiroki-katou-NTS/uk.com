package nts.uk.ctx.at.function.infra.entity.monthlyworkschedule;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import nts.uk.ctx.at.function.infra.entity.monthlyworkschedule.KfnmtMonAttenDisplay;
import nts.uk.ctx.at.function.infra.entity.monthlyworkschedule.KfnmtMonthlyWorkSchePK;

// TODO: Auto-generated Javadoc
/**
 * The Class KfnmtMonthlyWorkSche_.
 */
@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-06-25T15:47:16")
@StaticMetamodel(KfnmtMonthlyWorkSche.class)
public class KfnmtMonthlyWorkSche_ { 

    /** The item name. */
    public static volatile SingularAttribute<KfnmtMonthlyWorkSche, String> itemName;
    
    /** The lst kfnmt mon atten display. */
    public static volatile ListAttribute<KfnmtMonthlyWorkSche, KfnmtMonAttenDisplay> lstKfnmtMonAttenDisplay;
    
    /** The id. */
    public static volatile SingularAttribute<KfnmtMonthlyWorkSche, KfnmtMonthlyWorkSchePK> id;
    
    /** The is print. */
    public static volatile SingularAttribute<KfnmtMonthlyWorkSche, BigDecimal> isPrint;
    
    /** The exclus ver. */
    public static volatile SingularAttribute<KfnmtMonthlyWorkSche, BigDecimal> exclusVer;

}