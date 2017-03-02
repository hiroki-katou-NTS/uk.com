package nts.uk.ctx.pr.report.infra.entity.wageledger;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import nts.uk.ctx.pr.report.infra.entity.wageledger.QlsptLedgerAggreDetail;
import nts.uk.ctx.pr.report.infra.entity.wageledger.QlsptLedgerAggreHeadPK;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-03-01T15:13:28")
@StaticMetamodel(QlsptLedgerAggreHead.class)
public class QlsptLedgerAggreHead_ { 

    public static volatile SingularAttribute<QlsptLedgerAggreHead, Date> insDate;
    public static volatile SingularAttribute<QlsptLedgerAggreHead, String> updCcd;
    public static volatile SingularAttribute<QlsptLedgerAggreHead, String> insCcd;
    public static volatile SingularAttribute<QlsptLedgerAggreHead, String> updScd;
    public static volatile SingularAttribute<QlsptLedgerAggreHead, Integer> dispNameZeroAtr;
    public static volatile ListAttribute<QlsptLedgerAggreHead, QlsptLedgerAggreDetail> qlsptLedgerAggreDetailList;
    public static volatile SingularAttribute<QlsptLedgerAggreHead, Date> updDate;
    public static volatile SingularAttribute<QlsptLedgerAggreHead, Integer> exclusVer;
    public static volatile SingularAttribute<QlsptLedgerAggreHead, Integer> dispZeroAtr;
    public static volatile SingularAttribute<QlsptLedgerAggreHead, QlsptLedgerAggreHeadPK> qlsptLedgerAggreHeadPK;
    public static volatile SingularAttribute<QlsptLedgerAggreHead, String> insScd;
    public static volatile SingularAttribute<QlsptLedgerAggreHead, String> aggregateName;
    public static volatile SingularAttribute<QlsptLedgerAggreHead, String> insPg;

}