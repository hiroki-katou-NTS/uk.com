package nts.uk.pr.file.infra.wageledger.entity;

import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(ReportQlsptLedgerFormDetail.class)
public class ReportQlsptLedgerFormDetail_ { 

    public static volatile SingularAttribute<ReportQlsptLedgerFormDetail, Date> insDate;
    public static volatile SingularAttribute<ReportQlsptLedgerFormDetail, String> updCcd;
    public static volatile SingularAttribute<ReportQlsptLedgerFormDetail, String> insCcd;
    public static volatile SingularAttribute<ReportQlsptLedgerFormDetail, String> insScd;
    public static volatile SingularAttribute<ReportQlsptLedgerFormDetail, String> updScd;
    public static volatile SingularAttribute<ReportQlsptLedgerFormDetail, ReportQlsptLedgerFormHead> qlsptLedgerFormHead;
    public static volatile SingularAttribute<ReportQlsptLedgerFormDetail, Integer> dispOrder;
    public static volatile SingularAttribute<ReportQlsptLedgerFormDetail, ReportQlsptLedgerFormDetailPK> qlsptLedgerFormDetailPK;
    public static volatile SingularAttribute<ReportQlsptLedgerFormDetail, String> insPg;
    public static volatile SingularAttribute<ReportQlsptLedgerFormDetail, Date> updDate;
    public static volatile SingularAttribute<ReportQlsptLedgerFormDetail, Integer> exclusVer;

}