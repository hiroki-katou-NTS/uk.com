package nts.uk.ctx.at.shared.infra.entity.taskmanagement.taskmaster.metamodel;


import nts.uk.ctx.at.shared.infra.entity.taskmanagement.taskmaster.KsrmtTaskChild;
import nts.uk.ctx.at.shared.infra.entity.taskmanagement.taskmaster.KsrmtTaskChildPk;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(KsrmtTaskChild.class)
public class KsrmtTaskChild_ {
    public static volatile SingularAttribute<KsrmtTaskChild,KsrmtTaskChildPk>pk;
}
