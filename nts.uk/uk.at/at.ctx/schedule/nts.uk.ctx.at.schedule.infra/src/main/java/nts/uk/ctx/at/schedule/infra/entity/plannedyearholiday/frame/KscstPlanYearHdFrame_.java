/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.plannedyearholiday.frame;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import nts.uk.ctx.at.schedule.infra.entity.plannedyearholiday.frame.KscstPlanYearHdFrame;

/**
 * The Class KscstPlanYearHdFrame_.
 */
@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-11-17T14:08:22")
@StaticMetamodel(KscstPlanYearHdFrame.class)

public class KscstPlanYearHdFrame_ { 

    /** The use atr. */
    public static volatile SingularAttribute<KscstPlanYearHdFrame, Short> useAtr;
    
    /** The upd ccd. */
    public static volatile SingularAttribute<KscstPlanYearHdFrame, String> updCcd;
    
    /** The upd pg. */
    public static volatile SingularAttribute<KscstPlanYearHdFrame, String> updPg;
    
    /** The plan year hd name. */
    public static volatile SingularAttribute<KscstPlanYearHdFrame, String> planYearHdName;
    
    /** The kscst plan year hd frame PK. */
    public static volatile SingularAttribute<KscstPlanYearHdFrame, KscstPlanYearHdFramePK> kscstPlanYearHdFramePK;
    
    /** The upd scd. */
    public static volatile SingularAttribute<KscstPlanYearHdFrame, String> updScd;
    
    /** The exclus ver. */
    public static volatile SingularAttribute<KscstPlanYearHdFrame, Integer> exclusVer;

}