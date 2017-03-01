package nts.uk.ctx.pr.report.infra.entity.salarydetail;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import nts.uk.ctx.pr.report.infra.entity.salarydetail.QlsptPaylstAggreDetail;
import nts.uk.ctx.pr.report.infra.entity.salarydetail.QlsptPaylstAggreHeadPK;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-03-01T15:13:28")
@StaticMetamodel(QlsptPaylstAggreHead.class)
public class QlsptPaylstAggreHead_ { 

    public static volatile SingularAttribute<QlsptPaylstAggreHead, QlsptPaylstAggreHeadPK> qlsptPaylstAggreHeadPK;
    public static volatile ListAttribute<QlsptPaylstAggreHead, QlsptPaylstAggreDetail> qlsptPaylstAggreDetailList;
    public static volatile SingularAttribute<QlsptPaylstAggreHead, Date> insDate;
    public static volatile SingularAttribute<QlsptPaylstAggreHead, String> updCcd;
    public static volatile SingularAttribute<QlsptPaylstAggreHead, String> insCcd;
    public static volatile SingularAttribute<QlsptPaylstAggreHead, String> insScd;
    public static volatile SingularAttribute<QlsptPaylstAggreHead, String> aggregateName;
    public static volatile SingularAttribute<QlsptPaylstAggreHead, String> updScd;
    public static volatile SingularAttribute<QlsptPaylstAggreHead, String> insPg;
    public static volatile SingularAttribute<QlsptPaylstAggreHead, Date> updDate;
    public static volatile SingularAttribute<QlsptPaylstAggreHead, Integer> exclusVer;

}