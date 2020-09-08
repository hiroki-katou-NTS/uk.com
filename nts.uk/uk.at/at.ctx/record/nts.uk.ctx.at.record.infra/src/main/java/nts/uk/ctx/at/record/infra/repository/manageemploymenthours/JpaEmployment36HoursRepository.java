package nts.uk.ctx.at.record.infra.repository.manageemploymenthours;


import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.manageemploymenthours.Employment36Hours;
import nts.uk.ctx.at.record.dom.manageemploymenthours.Employment36HoursRepository;
import nts.uk.ctx.at.record.infra.entity.manageemploymenthours.Ksrmt36AgrMgtEmp;

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
    public void insert(Employment36Hours domain) {

    }

    @Override
    public void update(Employment36Hours domain) {

    }

    @Override
    public void delete(Employment36Hours domain) {

    }

    @Override
    public List<Employment36Hours> getByCid(String cid) {
        return this.queryProxy().query(FIND_BY_CID, Ksrmt36AgrMgtEmp.class)
                .setParameter("cid", cid)
                .getList(d -> convertToDomain(d));
    }

    @Override
    public Optional<Employment36Hours> getByCidAndEmployCode(String cid, String employCode) {

        return this.queryProxy().query(FIND_BY_CID_AND_CD, Ksrmt36AgrMgtEmp.class)
                .setParameter("cid", cid)
                .setParameter("cd", employCode)
                .getSingle(d -> convertToDomain(d));
    }

    private Employment36Hours convertToDomain(Ksrmt36AgrMgtEmp ksrmt36AgrMgtEmp) {
        return new Employment36Hours();
    }
}
