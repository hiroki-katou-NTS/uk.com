package nts.uk.ctx.at.shared.infra.entity.workmanagement.workmaster.metamode;


import nts.uk.ctx.at.shared.infra.entity.workmanagement.workmaster.KsrmtTaskChildPk;
import nts.uk.ctx.at.shared.infra.entity.workmanagement.workmaster.KsrmtTaskMasterPk;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(KsrmtTaskMasterPk.class)
public class KsrmtTaskMasterPk_ {
    public static volatile SingularAttribute<KsrmtTaskMasterPk,String> CID;
    public static volatile SingularAttribute<KsrmtTaskMasterPk,Integer>FRAMENO;
    public static volatile SingularAttribute<KsrmtTaskMasterPk,String>CD;
}
