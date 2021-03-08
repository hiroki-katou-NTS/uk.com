package nts.uk.ctx.at.shared.infra.entity.taskmanagement.taskmaster.metamodel;


import nts.uk.ctx.at.shared.infra.entity.taskmanagement.taskmaster.KsrmtTaskChildPk;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(KsrmtTaskChildPk.class)
public class KsrmtTaskChildPk_ {
    public static volatile SingularAttribute<KsrmtTaskChildPk,String>CID;
    public static volatile SingularAttribute<KsrmtTaskChildPk,Integer>FRAMENO;
    public static volatile SingularAttribute<KsrmtTaskChildPk,String>CD;
    public static volatile SingularAttribute<KsrmtTaskChildPk,String>CHILDCD;

}
