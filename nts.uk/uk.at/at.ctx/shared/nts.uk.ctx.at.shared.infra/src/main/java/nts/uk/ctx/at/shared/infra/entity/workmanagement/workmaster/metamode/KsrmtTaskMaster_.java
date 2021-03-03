package nts.uk.ctx.at.shared.infra.entity.workmanagement.workmaster.metamode;


import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.infra.entity.workmanagement.workmaster.KsrmtTaskMaster;
import nts.uk.ctx.at.shared.infra.entity.workmanagement.workmaster.KsrmtTaskMasterPk;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(KsrmtTaskMaster.class)
public class KsrmtTaskMaster_ {
    public static volatile SingularAttribute<KsrmtTaskMaster, GeneralDate> EXPSTARTDATE;
    public static volatile SingularAttribute<KsrmtTaskMaster, GeneralDate> EXPENDDATE;
    public static volatile SingularAttribute<KsrmtTaskMaster, KsrmtTaskMasterPk> pk;
}
