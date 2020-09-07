package nts.uk.ctx.at.record.infra.repository.manageclassificationagreementtime;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.manageclassificationagreementtime.ClassificationAgreementTime;
import nts.uk.ctx.at.record.dom.manageclassificationagreementtime.ClassificationAgreementTimeRepository;
import nts.uk.ctx.at.record.dom.manageemploymenthours.Employmenthours;
import nts.uk.ctx.at.record.infra.entity.manageclassificationagreementtime.Ksrmt36AgrMgtCls;
import nts.uk.ctx.at.record.infra.entity.manageemploymenthours.Ksrmt36AgrMgtEmp;

import java.util.List;
import java.util.Optional;

public class JpaClassificationAgreementTimeRepository extends JpaRepository implements ClassificationAgreementTimeRepository {
    private static String FIND_BY_CID;

    private static String FIND_BY_CID_AND_CD;

    static {
        StringBuilder builderString = new StringBuilder();
        builderString.append("SELECT");
        builderString.append("FROM Ksrmt36AgrMgtCls a");
        builderString.append("WHERE a.ksrmt36AgrMgtClsPk.companyID = :cid ");
        builderString.append("AND a.ksrmt36AgrMgtClsPk.employmentCode:cd ");
        FIND_BY_CID_AND_CD = builderString.toString();

        builderString = new StringBuilder();
        builderString.append("SELECT");
        builderString.append("FROM Ksrmt36AgrMgtCls a");
        builderString.append("WHERE a.ksrmt36AgrMgtClsPk.classificationCode = :classificationCode ");
        FIND_BY_CID = builderString.toString();
    }
    @Override
    public void insert(ClassificationAgreementTime domain) {

    }

    @Override
    public void update(ClassificationAgreementTime domain) {

    }

    @Override
    public void delete(ClassificationAgreementTime domain) {

    }

    @Override
    public List<ClassificationAgreementTime> getByCid(String cid) {
        return this.queryProxy().query(FIND_BY_CID,Ksrmt36AgrMgtCls.class).setParameter("cid",cid).getList(d->convertToDomain(d));
    }

    @Override
    public Optional<ClassificationAgreementTime> getByCidAndClassificationCode(String cid, String classificationCode) {
        return this.queryProxy().query(FIND_BY_CID,Ksrmt36AgrMgtCls.class)
                .setParameter("cid",cid)
                .setParameter("classificationCode",classificationCode)
                .getSingle(d->convertToDomain(d));

    }
    private ClassificationAgreementTime convertToDomain(Ksrmt36AgrMgtCls entity) {
        return new ClassificationAgreementTime();
    }

}
