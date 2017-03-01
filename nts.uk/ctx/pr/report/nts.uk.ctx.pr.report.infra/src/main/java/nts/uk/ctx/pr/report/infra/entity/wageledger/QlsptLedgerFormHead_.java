package nts.uk.ctx.pr.report.infra.entity.wageledger;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import nts.uk.ctx.pr.report.infra.entity.wageledger.QlsptLedgerFormDetail;
import nts.uk.ctx.pr.report.infra.entity.wageledger.QlsptLedgerFormHeadPK;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-03-01T15:13:28")
@StaticMetamodel(QlsptLedgerFormHead.class)
public class QlsptLedgerFormHead_ { 

    public static volatile SingularAttribute<QlsptLedgerFormHead, Integer> print1pageByPersonSet;
    public static volatile SingularAttribute<QlsptLedgerFormHead, Date> insDate;
    public static volatile SingularAttribute<QlsptLedgerFormHead, String> updCcd;
    public static volatile SingularAttribute<QlsptLedgerFormHead, String> insCcd;
    public static volatile SingularAttribute<QlsptLedgerFormHead, String> insScd;
    public static volatile SingularAttribute<QlsptLedgerFormHead, String> formName;
    public static volatile SingularAttribute<QlsptLedgerFormHead, QlsptLedgerFormHeadPK> qlsptLedgerFormHeadPK;
    public static volatile SingularAttribute<QlsptLedgerFormHead, String> updScd;
    public static volatile SingularAttribute<QlsptLedgerFormHead, Date> updDate;
    public static volatile SingularAttribute<QlsptLedgerFormHead, Integer> exclusVer;
    public static volatile SingularAttribute<QlsptLedgerFormHead, String> insPg;
    public static volatile ListAttribute<QlsptLedgerFormHead, QlsptLedgerFormDetail> qlsptLedgerFormDetailList;

}