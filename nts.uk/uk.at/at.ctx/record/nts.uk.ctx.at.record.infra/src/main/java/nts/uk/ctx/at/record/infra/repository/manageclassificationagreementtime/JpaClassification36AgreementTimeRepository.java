package nts.uk.ctx.at.record.infra.repository.manageclassificationagreementtime;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.manageclassificationagreementtime.Classification36AgreementTime;
import nts.uk.ctx.at.record.dom.manageclassificationagreementtime.Classification36AgreementTimeRepository;
import nts.uk.ctx.at.record.infra.entity.manageclassificationagreementtime.Ksrmt36AgrMgtCls;

import java.util.List;
import java.util.Optional;

public class JpaClassification36AgreementTimeRepository extends JpaRepository implements Classification36AgreementTimeRepository {
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
    public void insert(Classification36AgreementTime domain) {

    }

    @Override
    public void update(Classification36AgreementTime domain) {

    }

    @Override
    public void delete(Classification36AgreementTime domain) {

    }

    @Override
    public List<Classification36AgreementTime> getByCid(String cid) {
        return this.queryProxy().query(FIND_BY_CID,Ksrmt36AgrMgtCls.class).setParameter("cid",cid).getList(d->convertToDomain(d));
    }

    @Override
    public Optional<Classification36AgreementTime> getByCidAndClassificationCode(String cid, String classificationCode) {
        return this.queryProxy().query(FIND_BY_CID,Ksrmt36AgrMgtCls.class)
                .setParameter("cid",cid)
                .setParameter("classificationCode",classificationCode)
                .getSingle(d->convertToDomain(d));

    }
    private Classification36AgreementTime convertToDomain(Ksrmt36AgrMgtCls entity) {
        return new Classification36AgreementTime();
    }

}
