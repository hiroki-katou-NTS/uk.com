package nts.uk.ctx.at.shared.infra.entity.scherec.taskmanagement.taskassign.taskassignworkplace.metamodel;


import nts.uk.ctx.at.shared.infra.entity.scherec.taskmanagement.taskassign.taskassignworkplace.KsrmtTaskAssignWkp;
import nts.uk.ctx.at.shared.infra.entity.scherec.taskmanagement.taskassign.taskassignworkplace.KsrmtTaskAssignWkpPk;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(KsrmtTaskAssignWkp.class)
public class KsrmtTaskAssignWkp_ {
    public static volatile SingularAttribute<KsrmtTaskAssignWkp, KsrmtTaskAssignWkpPk> pk;
    public static volatile SingularAttribute<KsrmtTaskAssignWkp, String> companyId;
}
