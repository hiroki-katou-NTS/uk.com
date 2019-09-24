package nts.uk.file.pr.infra.core.insurenamechangenoti;

import lombok.SneakyThrows;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.file.app.core.insurenamechangenoti.InsuredNameChangedRepository;

import javax.ejb.Stateless;

@Stateless
public class JpaInsuredNameChangedRepository extends JpaRepository implements InsuredNameChangedRepository {

    private static final String GET_PERSON_INFO = " SELECT GENDER, BIRTHDAY, PERSON_NAME, PERSON_NAME_KANA, OLDNAME_FNAME " +
            " FROM (SELECT * " +
            " FROM BSYMT_EMP_DTA_MNG_INFO " +
            " WHERE SID = ?SID ) i " +
            " INNER JOIN BPSMT_PERSON p ON p.PID = i.PID";

    @Override
    @SneakyThrows
    public Object[] getPersonInfo(String empId) {
        /*try(PreparedStatement stmt = this.connection().prepareStatement(GET_PERSON_INFO)){
            stmt.setString(1,empId);

            return new NtsResultSet(stmt.executeQuery()).getSingle();

        }*/

        return (Object[])this.getEntityManager().createNativeQuery(GET_PERSON_INFO).setParameter("SID",empId).getSingleResult();
    }
}
