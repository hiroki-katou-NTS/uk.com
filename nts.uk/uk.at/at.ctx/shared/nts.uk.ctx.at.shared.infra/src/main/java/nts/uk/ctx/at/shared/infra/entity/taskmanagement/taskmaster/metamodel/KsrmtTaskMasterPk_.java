package nts.uk.ctx.at.shared.infra.entity.taskmanagement.taskmaster.metamodel;


import nts.uk.ctx.at.shared.infra.entity.taskmanagement.taskmaster.KsrmtTaskMasterPk;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(KsrmtTaskMasterPk.class)
public class KsrmtTaskMasterPk_ {
    public static volatile SingularAttribute<KsrmtTaskMasterPk,String> CID;
    public static volatile SingularAttribute<KsrmtTaskMasterPk,Integer>FRAMENO;
    public static volatile SingularAttribute<KsrmtTaskMasterPk,String>CD;
}
