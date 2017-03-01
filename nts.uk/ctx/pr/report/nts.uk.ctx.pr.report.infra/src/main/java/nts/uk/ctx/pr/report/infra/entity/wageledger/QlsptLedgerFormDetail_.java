package nts.uk.ctx.pr.report.infra.entity.wageledger;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import nts.uk.ctx.pr.report.infra.entity.wageledger.QlsptLedgerFormDetailPK;
import nts.uk.ctx.pr.report.infra.entity.wageledger.QlsptLedgerFormHead;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-03-01T15:13:28")
@StaticMetamodel(QlsptLedgerFormDetail.class)
public class QlsptLedgerFormDetail_ { 

    public static volatile SingularAttribute<QlsptLedgerFormDetail, Date> insDate;
    public static volatile SingularAttribute<QlsptLedgerFormDetail, String> updCcd;
    public static volatile SingularAttribute<QlsptLedgerFormDetail, String> insCcd;
    public static volatile SingularAttribute<QlsptLedgerFormDetail, String> insScd;
    public static volatile SingularAttribute<QlsptLedgerFormDetail, String> updScd;
    public static volatile SingularAttribute<QlsptLedgerFormDetail, QlsptLedgerFormHead> qlsptLedgerFormHead;
    public static volatile SingularAttribute<QlsptLedgerFormDetail, Integer> dispOrder;
    public static volatile SingularAttribute<QlsptLedgerFormDetail, QlsptLedgerFormDetailPK> qlsptLedgerFormDetailPK;
    public static volatile SingularAttribute<QlsptLedgerFormDetail, String> insPg;
    public static volatile SingularAttribute<QlsptLedgerFormDetail, Date> updDate;
    public static volatile SingularAttribute<QlsptLedgerFormDetail, Integer> exclusVer;

}