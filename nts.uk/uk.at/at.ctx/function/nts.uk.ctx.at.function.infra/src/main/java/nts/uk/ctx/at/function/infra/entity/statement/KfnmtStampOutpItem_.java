/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.infra.entity.statement;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import nts.uk.ctx.at.function.infra.entity.statement.KfnmtStampOutpItemPK;

/**
 * The Class KfnmtStampOutpItem_.
 */
@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-07-18T10:13:01")
@StaticMetamodel(KfnmtStampOutpItem.class)
public class KfnmtStampOutpItem_ { 

    /** The output pos infor. */
    public static volatile SingularAttribute<KfnmtStampOutpItem, BigDecimal> outputPosInfor;
    
    /** The output set location. */
    public static volatile SingularAttribute<KfnmtStampOutpItem, BigDecimal> outputSetLocation;
    
    /** The output emboss method. */
    public static volatile SingularAttribute<KfnmtStampOutpItem, BigDecimal> outputEmbossMethod;
    
    /** The output ot. */
    public static volatile SingularAttribute<KfnmtStampOutpItem, BigDecimal> outputOt;
    
    /** The output night time. */
    public static volatile SingularAttribute<KfnmtStampOutpItem, BigDecimal> outputNightTime;
    
    /** The id. */
    public static volatile SingularAttribute<KfnmtStampOutpItem, KfnmtStampOutpItemPK> id;
    
    /** The stamp output set name. */
    public static volatile SingularAttribute<KfnmtStampOutpItem, String> stampOutputSetName;
    
    /** The output work hours. */
    public static volatile SingularAttribute<KfnmtStampOutpItem, BigDecimal> outputWorkHours;
    
    /** The output support card. */
    public static volatile SingularAttribute<KfnmtStampOutpItem, BigDecimal> outputSupportCard;

}