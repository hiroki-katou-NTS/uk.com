package nts.uk.ctx.pr.report.infra.entity.salarydetail;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import nts.uk.ctx.pr.report.infra.entity.salarydetail.QlsptPaylstFormDetail;
import nts.uk.ctx.pr.report.infra.entity.salarydetail.QlsptPaylstFormHeadPK;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-03-01T15:13:28")
@StaticMetamodel(QlsptPaylstFormHead.class)
public class QlsptPaylstFormHead_ { 

    public static volatile SingularAttribute<QlsptPaylstFormHead, Date> insDate;
    public static volatile SingularAttribute<QlsptPaylstFormHead, String> updCcd;
    public static volatile SingularAttribute<QlsptPaylstFormHead, String> insCcd;
    public static volatile SingularAttribute<QlsptPaylstFormHead, String> insScd;
    public static volatile SingularAttribute<QlsptPaylstFormHead, String> formName;
    public static volatile SingularAttribute<QlsptPaylstFormHead, QlsptPaylstFormHeadPK> qlsptPaylstFormHeadPK;
    public static volatile SingularAttribute<QlsptPaylstFormHead, String> updScd;
    public static volatile ListAttribute<QlsptPaylstFormHead, QlsptPaylstFormDetail> qlsptPaylstFormDetailList;
    public static volatile SingularAttribute<QlsptPaylstFormHead, String> insPg;
    public static volatile SingularAttribute<QlsptPaylstFormHead, Date> updDate;
    public static volatile SingularAttribute<QlsptPaylstFormHead, Integer> exclusVer;

}