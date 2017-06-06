package nts.uk.ctx.at.shared.infra.entity.vacation.setting;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(KmfmtRetentionEmpCtr.class)
public class KmfmtRetentionEmpCtr_ { 

    public static volatile SingularAttribute<KmfmtRetentionEmpCtr, KmfmtRetentionEmpCtrPK> kmfmtRetentionEmpCtrPK;
    public static volatile SingularAttribute<KmfmtRetentionEmpCtr, Short> yearAmount;
    public static volatile SingularAttribute<KmfmtRetentionEmpCtr, Short> maxDaysRetention;
    public static volatile SingularAttribute<KmfmtRetentionEmpCtr, Short> managementCtrAtr;

}