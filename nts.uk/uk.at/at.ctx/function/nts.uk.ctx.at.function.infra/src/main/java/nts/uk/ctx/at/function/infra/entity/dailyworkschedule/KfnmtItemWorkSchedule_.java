/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.infra.entity.dailyworkschedule;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import nts.uk.ctx.at.function.infra.entity.dailyworkschedule.KfnmtAttendanceDisplay;
import nts.uk.ctx.at.function.infra.entity.dailyworkschedule.KfnmtItemWorkSchedulePK;
import nts.uk.ctx.at.function.infra.entity.dailyworkschedule.KfnmtPrintRemarkCont;

/**
 * The Class KfnmtItemWorkSchedule_.
 * @author HoangDD
 */
@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-05-14T10:51:51")
@StaticMetamodel(KfnmtItemWorkSchedule.class)
public class KfnmtItemWorkSchedule_ { 

    /** The item name. */
    public static volatile SingularAttribute<KfnmtItemWorkSchedule, String> itemName;
    
    /** The lst kfnmt attendance display. */
    public static volatile ListAttribute<KfnmtItemWorkSchedule, KfnmtAttendanceDisplay> lstKfnmtAttendanceDisplay;
    
    /** The id. */
    public static volatile SingularAttribute<KfnmtItemWorkSchedule, KfnmtItemWorkSchedulePK> id;
    
    /** The work type name display. */
    public static volatile SingularAttribute<KfnmtItemWorkSchedule, BigDecimal> workTypeNameDisplay;
    
    /** The lst kfnmt print remark cont. */
    public static volatile ListAttribute<KfnmtItemWorkSchedule, KfnmtPrintRemarkCont> lstKfnmtPrintRemarkCont;

}