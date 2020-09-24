package nts.uk.ctx.at.record.infra.repository.manageclassificationagreementtime;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.manageclassificationagreementtime.Classification36AgreementTimeRepository;
import nts.uk.ctx.at.record.infra.entity.manageclassificationagreementtime.Ksrmt36AgrMgtCls;
import nts.uk.ctx.at.record.infra.entity.manageclassificationagreementtime.Ksrmt36AgrMgtClsPk;
import nts.uk.ctx.at.shared.dom.standardtime.AgreementTimeOfClassification;

import java.util.List;
import java.util.Optional;

/**
 * 	Repository	 分類３６協定時間
 */
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
    public void insert(AgreementTimeOfClassification domain) {
        this.commandProxy().insert(Ksrmt36AgrMgtCls.toEntity(domain));
        this.getEntityManager().flush();
    }

    @Override
    public void update(AgreementTimeOfClassification domain) {
        this.commandProxy().update(Ksrmt36AgrMgtCls.toEntity(domain));
    }

    @Override
    public void delete(AgreementTimeOfClassification domain) {
        val entity = this.queryProxy().find(new Ksrmt36AgrMgtClsPk(domain.getCompanyId(),domain.getClassificationCode()
                ,domain.getLaborSystemAtr().value),Ksrmt36AgrMgtCls.class);
        if(entity.isPresent()){
            this.commandProxy().remove(Ksrmt36AgrMgtCls.class,new Ksrmt36AgrMgtClsPk(domain.getCompanyId(),domain.getClassificationCode()
                    ,domain.getLaborSystemAtr().value));
        }
    }

    @Override
    public List<AgreementTimeOfClassification> getByCid(String cid) {
        return this.queryProxy().query(FIND_BY_CID,Ksrmt36AgrMgtCls.class).setParameter("cid",cid).getList(Ksrmt36AgrMgtCls::toDomain);
    }

    @Override
    public Optional<AgreementTimeOfClassification> getByCidAndClassificationCode(String cid, String classificationCode) {
        return this.queryProxy().query(FIND_BY_CID,Ksrmt36AgrMgtCls.class)
                .setParameter("cid",cid)
                .setParameter("classificationCode",classificationCode)
                .getSingle(Ksrmt36AgrMgtCls::toDomain);

    }


}
