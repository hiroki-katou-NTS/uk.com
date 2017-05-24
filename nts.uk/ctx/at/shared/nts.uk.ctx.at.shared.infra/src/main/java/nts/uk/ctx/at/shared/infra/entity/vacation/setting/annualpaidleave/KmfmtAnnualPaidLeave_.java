/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.vacation.setting.annualpaidleave;

import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(KmfmtAnnualPaidLeave.class)
public class KmfmtAnnualPaidLeave_ {
    
    public static volatile SingularAttribute<KmfmtAnnualPaidLeave, Date> insDate;
    public static volatile SingularAttribute<KmfmtAnnualPaidLeave, String> insCcd;
    public static volatile SingularAttribute<KmfmtAnnualPaidLeave, String> insScd;
    public static volatile SingularAttribute<KmfmtAnnualPaidLeave, String> insPg;
    public static volatile SingularAttribute<KmfmtAnnualPaidLeave, Date> updDate;
    public static volatile SingularAttribute<KmfmtAnnualPaidLeave, String> updCcd;
    public static volatile SingularAttribute<KmfmtAnnualPaidLeave, String> updScd;
    public static volatile SingularAttribute<KmfmtAnnualPaidLeave, String> updPg;
    public static volatile SingularAttribute<KmfmtAnnualPaidLeave, Integer> exclusVer;
    public static volatile SingularAttribute<KmfmtAnnualPaidLeave, String> cid;
    public static volatile SingularAttribute<KmfmtAnnualPaidLeave, Integer> manageAtr;
}
