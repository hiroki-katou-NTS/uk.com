package nts.uk.pr.file.infra.wageledger.entity;

import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(ReportQlsptLedgerAggreDetail.class)
public class ReportQlsptLedgerAggreDetail_ { 

    public static volatile SingularAttribute<ReportQlsptLedgerAggreDetail, ReportQlsptLedgerAggreDetailPK> qlsptLedgerAggreDetailPK;
    public static volatile SingularAttribute<ReportQlsptLedgerAggreDetail, Date> insDate;
    public static volatile SingularAttribute<ReportQlsptLedgerAggreDetail, String> updCcd;
    public static volatile SingularAttribute<ReportQlsptLedgerAggreDetail, String> insCcd;
    public static volatile SingularAttribute<ReportQlsptLedgerAggreDetail, String> insScd;
    public static volatile SingularAttribute<ReportQlsptLedgerAggreDetail, String> updScd;
    public static volatile SingularAttribute<ReportQlsptLedgerAggreDetail, ReportQlsptLedgerAggreHead> qlsptLedgerAggreHead;
    public static volatile SingularAttribute<ReportQlsptLedgerAggreDetail, String> insPg;
    public static volatile SingularAttribute<ReportQlsptLedgerAggreDetail, Date> updDate;
    public static volatile SingularAttribute<ReportQlsptLedgerAggreDetail, Integer> exclusVer;

}