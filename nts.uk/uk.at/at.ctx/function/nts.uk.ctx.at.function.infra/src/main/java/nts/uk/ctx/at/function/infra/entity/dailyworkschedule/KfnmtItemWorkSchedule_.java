package nts.uk.ctx.at.function.infra.entity.dailyworkschedule;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import nts.uk.ctx.at.function.infra.entity.dailyworkschedule.KfnmtAttendanceDisplay;
import nts.uk.ctx.at.function.infra.entity.dailyworkschedule.KfnmtItemWorkSchedulePK;
import nts.uk.ctx.at.function.infra.entity.dailyworkschedule.KfnmtPrintRemarkCont;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-05-14T10:51:51")
@StaticMetamodel(KfnmtItemWorkSchedule.class)
public class KfnmtItemWorkSchedule_ { 

    public static volatile SingularAttribute<KfnmtItemWorkSchedule, String> itemName;
    public static volatile ListAttribute<KfnmtItemWorkSchedule, KfnmtAttendanceDisplay> lstKfnmtAttendanceDisplay;
    public static volatile SingularAttribute<KfnmtItemWorkSchedule, KfnmtItemWorkSchedulePK> id;
    public static volatile SingularAttribute<KfnmtItemWorkSchedule, BigDecimal> workTypeNameDisplay;
    public static volatile ListAttribute<KfnmtItemWorkSchedule, KfnmtPrintRemarkCont> lstKfnmtPrintRemarkCont;

}