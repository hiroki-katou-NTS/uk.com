package nts.uk.ctx.pr.report.infra.entity.wageledger;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import nts.uk.ctx.pr.report.infra.entity.wageledger.QlsptLedgerAggreDetailPK;
import nts.uk.ctx.pr.report.infra.entity.wageledger.QlsptLedgerAggreHead;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-03-01T15:13:28")
@StaticMetamodel(QlsptLedgerAggreDetail.class)
public class QlsptLedgerAggreDetail_ { 

    public static volatile SingularAttribute<QlsptLedgerAggreDetail, QlsptLedgerAggreDetailPK> qlsptLedgerAggreDetailPK;
    public static volatile SingularAttribute<QlsptLedgerAggreDetail, Date> insDate;
    public static volatile SingularAttribute<QlsptLedgerAggreDetail, String> updCcd;
    public static volatile SingularAttribute<QlsptLedgerAggreDetail, String> insCcd;
    public static volatile SingularAttribute<QlsptLedgerAggreDetail, String> insScd;
    public static volatile SingularAttribute<QlsptLedgerAggreDetail, String> updScd;
    public static volatile SingularAttribute<QlsptLedgerAggreDetail, QlsptLedgerAggreHead> qlsptLedgerAggreHead;
    public static volatile SingularAttribute<QlsptLedgerAggreDetail, String> insPg;
    public static volatile SingularAttribute<QlsptLedgerAggreDetail, Date> updDate;
    public static volatile SingularAttribute<QlsptLedgerAggreDetail, Integer> exclusVer;

}