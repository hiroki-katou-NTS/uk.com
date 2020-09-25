package nts.uk.ctx.at.record.infra.repository.manageemploymenthours;


import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.manageemploymenthours.Employment36HoursRepository;
import nts.uk.ctx.at.record.infra.entity.manageclassificationagreementtime.Ksrmt36AgrMgtCls;
import nts.uk.ctx.at.record.infra.entity.manageclassificationagreementtime.Ksrmt36AgrMgtClsPk;
import nts.uk.ctx.at.record.infra.entity.managecompanyagreedhours.Ksrmt36AgrMgtCmp;
import nts.uk.ctx.at.record.infra.entity.manageemploymenthours.Ksrmt36AgrMgtEmp;
import nts.uk.ctx.at.record.infra.entity.manageemploymenthours.Ksrmt36AgrMgtEmpPk;
import nts.uk.ctx.at.shared.dom.standardtime.AgreementTimeOfEmployment;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;

/**
 * 	Repository: 雇用３６協定時間
 */
@Stateless
public class JpaEmployment36HoursRepository extends JpaRepository implements Employment36HoursRepository {
    private static String FIND_BY_CID;

    private static String FIND_BY_CID_AND_CD;

    static {
        StringBuilder builderString = new StringBuilder();
        builderString.append("SELECT");
        builderString.append("FROM Ksrmt36AgrMgtEmp a");
        builderString.append("WHERE a.ksrmt36AgrMgtEmpPk.companyID = :cid ");
        builderString.append("AND a.ksrmt36AgrMgtEmpPk.employmentCode:cd ");
        FIND_BY_CID_AND_CD = builderString.toString();

         builderString = new StringBuilder();
        builderString.append("SELECT");
        builderString.append("FROM Ksrmt36AgrMgtEmp a");
        builderString.append("WHERE a.ksrmt36AgrMgtEmpPk.companyID = :cid ");
        FIND_BY_CID = builderString.toString();
    }

    @Override
    public void insert(AgreementTimeOfEmployment domain) {
        this.commandProxy().insert(Ksrmt36AgrMgtEmp.toEntity(domain));
        this.getEntityManager().flush();
    }

    @Override
    public void update(AgreementTimeOfEmployment domain) {
        this.commandProxy().update(Ksrmt36AgrMgtEmp.toEntity(domain));
    }

    @Override
    public void delete(AgreementTimeOfEmployment domain) {
        //TODO wait change domain from Nittsu
        val entity = this.queryProxy().find(new Ksrmt36AgrMgtEmpPk(domain.getCompanyId(),domain.getEmploymentCategoryCode()
                ,domain.getLaborSystemAtr().value),Ksrmt36AgrMgtEmp.class);
        if(entity.isPresent()){
            this.commandProxy().remove(Ksrmt36AgrMgtEmp.class,new Ksrmt36AgrMgtEmpPk(domain.getCompanyId(),domain.getEmploymentCategoryCode()
                    ,domain.getLaborSystemAtr().value));
        }
    }

    @Override
    public List<AgreementTimeOfEmployment> getByCid(String cid) {
        return this.queryProxy().query(FIND_BY_CID, Ksrmt36AgrMgtEmp.class)
                .setParameter("cid", cid)
                .getList(Ksrmt36AgrMgtEmp::toDomain);
    }

    @Override
    public Optional<AgreementTimeOfEmployment> getByCidAndEmployCode(String cid, String employCode) {

        return this.queryProxy().query(FIND_BY_CID_AND_CD, Ksrmt36AgrMgtEmp.class)
                .setParameter("cid", cid)
                .setParameter("cd", employCode)
                .getSingle(Ksrmt36AgrMgtEmp::toDomain);
    }


}
