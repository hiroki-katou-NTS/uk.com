package nts.uk.ctx.at.shared.infra.entity.scherec.taskmanagement.taskmaster.metamodel;


import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.infra.entity.scherec.taskmanagement.taskmaster.KsrmtTaskMaster;
import nts.uk.ctx.at.shared.infra.entity.scherec.taskmanagement.taskmaster.KsrmtTaskMasterPk;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(KsrmtTaskMaster.class)
public class KsrmtTaskMaster_ {
    public static volatile SingularAttribute<KsrmtTaskMaster, GeneralDate> EXPSTARTDATE;
    public static volatile SingularAttribute<KsrmtTaskMaster, GeneralDate> EXPENDDATE;
    public static volatile SingularAttribute<KsrmtTaskMaster, KsrmtTaskMasterPk> pk;
}
