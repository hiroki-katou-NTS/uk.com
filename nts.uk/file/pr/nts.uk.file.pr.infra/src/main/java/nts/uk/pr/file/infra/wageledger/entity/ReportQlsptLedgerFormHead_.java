package nts.uk.pr.file.infra.wageledger.entity;

import java.util.Date;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(ReportQlsptLedgerFormHead.class)
public class ReportQlsptLedgerFormHead_ { 

    public static volatile SingularAttribute<ReportQlsptLedgerFormHead, Integer> print1pageByPersonSet;
    public static volatile SingularAttribute<ReportQlsptLedgerFormHead, Date> insDate;
    public static volatile SingularAttribute<ReportQlsptLedgerFormHead, String> updCcd;
    public static volatile SingularAttribute<ReportQlsptLedgerFormHead, String> insCcd;
    public static volatile SingularAttribute<ReportQlsptLedgerFormHead, String> insScd;
    public static volatile SingularAttribute<ReportQlsptLedgerFormHead, String> formName;
    public static volatile SingularAttribute<ReportQlsptLedgerFormHead, ReportQlsptLedgerFormHeadPK> qlsptLedgerFormHeadPK;
    public static volatile SingularAttribute<ReportQlsptLedgerFormHead, String> updScd;
    public static volatile SingularAttribute<ReportQlsptLedgerFormHead, Date> updDate;
    public static volatile SingularAttribute<ReportQlsptLedgerFormHead, Integer> exclusVer;
    public static volatile SingularAttribute<ReportQlsptLedgerFormHead, String> insPg;
    public static volatile ListAttribute<ReportQlsptLedgerFormHead, ReportQlsptLedgerFormDetail> qlsptLedgerFormDetailList;

}