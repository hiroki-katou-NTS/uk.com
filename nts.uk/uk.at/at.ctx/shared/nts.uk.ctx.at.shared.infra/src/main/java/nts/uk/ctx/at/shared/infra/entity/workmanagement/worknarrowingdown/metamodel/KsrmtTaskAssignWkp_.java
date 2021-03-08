package nts.uk.ctx.at.shared.infra.entity.workmanagement.worknarrowingdown.metamodel;


import nts.uk.ctx.at.shared.infra.entity.workmanagement.worknarrowingdown.KsrmtTaskAssignWkp;
import nts.uk.ctx.at.shared.infra.entity.workmanagement.worknarrowingdown.KsrmtTaskAssignWkpPk;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(KsrmtTaskAssignWkp.class)
public class KsrmtTaskAssignWkp_ {
    public static volatile SingularAttribute<KsrmtTaskAssignWkp, KsrmtTaskAssignWkpPk> pk;
    public static volatile SingularAttribute<KsrmtTaskAssignWkp, String> companyId;
}
