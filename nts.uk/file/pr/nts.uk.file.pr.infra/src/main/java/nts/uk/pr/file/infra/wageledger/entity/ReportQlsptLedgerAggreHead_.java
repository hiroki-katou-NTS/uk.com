package nts.uk.pr.file.infra.wageledger.entity;

import java.util.Date;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(ReportQlsptLedgerAggreHead.class)
public class ReportQlsptLedgerAggreHead_ { 

    public static volatile SingularAttribute<ReportQlsptLedgerAggreHead, Date> insDate;
    public static volatile SingularAttribute<ReportQlsptLedgerAggreHead, String> updCcd;
    public static volatile SingularAttribute<ReportQlsptLedgerAggreHead, String> insCcd;
    public static volatile SingularAttribute<ReportQlsptLedgerAggreHead, String> updScd;
    public static volatile SingularAttribute<ReportQlsptLedgerAggreHead, Integer> dispNameZeroAtr;
    public static volatile ListAttribute<ReportQlsptLedgerAggreHead, ReportQlsptLedgerAggreDetail> qlsptLedgerAggreDetailList;
    public static volatile SingularAttribute<ReportQlsptLedgerAggreHead, Date> updDate;
    public static volatile SingularAttribute<ReportQlsptLedgerAggreHead, Integer> exclusVer;
    public static volatile SingularAttribute<ReportQlsptLedgerAggreHead, Integer> dispZeroAtr;
    public static volatile SingularAttribute<ReportQlsptLedgerAggreHead, ReportQlsptLedgerAggreHeadPK> qlsptLedgerAggreHeadPK;
    public static volatile SingularAttribute<ReportQlsptLedgerAggreHead, String> insScd;
    public static volatile SingularAttribute<ReportQlsptLedgerAggreHead, String> aggregateName;
    public static volatile SingularAttribute<ReportQlsptLedgerAggreHead, String> insPg;

}