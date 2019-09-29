package nts.uk.file.pr.infra.core.socinsurnoticreset;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.*;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import java.math.BigDecimal;

@Stateless
public class JpaRomajiNameNotiCreSetExReposity extends JpaRepository implements RomajiNameNotiCreSetExReposity {

    @Override
    public FamilyMember getFamilyInfo(String empId, String relationship) {
        Object[] result = null;
        StringBuilder exportSQL = new StringBuilder();
        exportSQL.append("  SELECT");
        exportSQL.append("    F.FAMILY_ID,");
        exportSQL.append("    F.NAME_ROMAJI,");
        exportSQL.append("    F.KN_NAME_ROMAJI,");
        exportSQL.append("    F.NAME,");
        exportSQL.append("    F.KN_NAME,");
        exportSQL.append("    F.BIRTHDAY,  ");
        exportSQL.append("    P.GENDER  ");
        exportSQL.append("  FROM  BPSMT_FAMILY AS F ");
        exportSQL.append("  INNER JOIN BPSMT_PERSON AS P ON F.PERSON_ID = P.PID ");
        exportSQL.append("  INNER JOIN BSYMT_EMP_DTA_MNG_INFO AS E ON F.PERSON_ID = E.PID ");
        exportSQL.append("        WHERE E.SID = ?empId AND F.RELATIONSHIP = ?relationship ");
        try {
            result = (Object[]) this.getEntityManager().createNativeQuery(exportSQL.toString())
                    .setParameter("empId",empId)
                    .setParameter("relationship", relationship)
                    .getSingleResult();
        } catch (NoResultException e){
            return null;
        }
        return FamilyMember.builder()
                .familyMemberId(result[0] != null ?  result[0].toString() :  "")
                .relationship(relationship)
                .personId(empId)
                .nameRomajiFull(result[1] !=null ? result[1].toString(): "")
                .nameRomajiFullKana(result[2] != null ? result[2].toString(): "")
                .fullName(result[3] != null ? result[3].toString(): null)
                .fullNameKana(result[4] != null ? result[4].toString() : "")
                .birthday(result[5] != null ? result[5].toString(): "")
                .gender(result[6] != null ? ((BigDecimal) result[6]).intValue() : 0)
                .build();
         }

    @Override
    public PersonInfo getPersonInfo(String personId) {
        Object[] result = null;
        StringBuilder exportSQL = new StringBuilder();
        exportSQL.append("  SELECT");
        exportSQL.append("    BIRTHDAY,");
        exportSQL.append("    PERSON_NAME,");
        exportSQL.append("    PERSON_NAME_KANA, ");
        exportSQL.append("    GENDER ");
        exportSQL.append("  FROM  BPSMT_PERSON AS P");
        exportSQL.append("  INNER JOIN BSYMT_EMP_DTA_MNG_INFO AS E ON P.PID = E.PID ");
        exportSQL.append("        WHERE E.PID = ?personId ");
        try{
            result = (Object[]) this.getEntityManager().createNativeQuery(exportSQL.toString())
                    .setParameter("personId",personId)
                    .getSingleResult();
        }catch (NoResultException e){
            return null;
        }
        return PersonInfo.builder()
                .birthday(result[0] != null ? result[0].toString() : "")
                .personNameRomaji(result[1] != null ? result[1].toString() : "")
                .personNameKana(result[2] != null ? result[2].toString() : "")
                .gender(result[3] != null ? ((BigDecimal) result[3]).intValue() : 0)
                .build();
    }
}
