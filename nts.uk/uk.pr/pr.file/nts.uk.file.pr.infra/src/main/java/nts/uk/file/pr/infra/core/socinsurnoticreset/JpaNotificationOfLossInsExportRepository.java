package nts.uk.file.pr.infra.core.socinsurnoticreset;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.CompanyInfor;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.InsLossDataExport;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.NotificationOfLossInsExRepository;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class JpaNotificationOfLossInsExportRepository extends JpaRepository implements NotificationOfLossInsExRepository {



    @Override
    public List<InsLossDataExport> getWelfPenInsLoss(List<String> empIds) {
        List<Object[]> resultQuery = null;
        StringBuilder exportSQL = new StringBuilder();
        exportSQL.append("   SELECT qi.EMPLOYEE_ID,");
        exportSQL.append("      SOCIAL_INSURANCE_OFFICE_CD,");
        exportSQL.append("      OTHER,");
        exportSQL.append("      OTHER_REASON,");
        exportSQL.append("      CA_INSURANCE,");
        exportSQL.append("      NUM_RECOVED,");
        exportSQL.append("      CAUSE,");
        exportSQL.append("      PERCENT_OR_MORE,");
        exportSQL.append("      REMARKS_OTHER,");
        exportSQL.append("      REMARKS_AND_OTHER_CONTENTS,");
        exportSQL.append("      REMUN_MONTHLY_AMOUNT_KIND,");
        exportSQL.append("      REMUN_MONTHLY_AMOUNT,");
        exportSQL.append("      TOTAL_MONTHLY_REMUN,");
        exportSQL.append("      LIVING_ABROAD,");
        exportSQL.append("      REASON_OTHER,");
        exportSQL.append("      REASON_AND_OTHER_CONTENTS,");
        exportSQL.append("      SHORT_TIME_WORKES,");
        exportSQL.append("      SHORT_STAY,");
        exportSQL.append("      DEPEN_APPOINT,");
        exportSQL.append("      QUALIFI_DISTIN,");
        exportSQL.append("      CONTIN_REEM_AFTER_RETIREMENT,");
        exportSQL.append("      IS_MORE_EMP,");
        exportSQL.append("      BASIC_PEN_NUMBER");
        exportSQL.append("  FROM ");
        exportSQL.append("         (SELECT *");
        exportSQL.append("         FROM QQSMT_EMP_WELF_INS_QC_IF ");
        exportSQL.append("         WHERE START_DATE <= ?endDate AND START_DATE >= ?startDate");
        exportSQL.append("         AND EMPLOYEE_ID IN (?empIds) )qi");
        exportSQL.append("  LEFT JOIN ");
        exportSQL.append("       (SELECT * ");
        exportSQL.append("       FROM QQSMT_EMP_CORP_OFF_HIS ");
        exportSQL.append("       WHERE START_DATE <= ?endDate AND START_DATE >= ?startDate) his");
        exportSQL.append("       ON qi.EMPLOYEE_ID = his.EMPLOYEE_ID");
        exportSQL.append("  LEFT JOIN (SELECT * ");
        exportSQL.append("       FROM QQSMT_HEALTH_INS_LOSS");
        exportSQL.append("       WHERE CAUSE = 7) loss");
        exportSQL.append("  ON loss.EMP_ID = his.EMPLOYEE_ID");
        exportSQL.append("  LEFT JOIN QQSMT_SOC_ISACQUISI_INFO info ON info.EMPLOYEE_ID = his.EMPLOYEE_ID");
        exportSQL.append("  LEFT JOIN QQSMT_MULTI_EMP_WORK_IF mt ON mt.EMPLOYEE_ID = his.EMPLOYEE_ID");
        exportSQL.append("  LEFT JOIN QQSMT_EMP_BA_PEN_NUM ba ON ba.EMPLOYEE_ID = his.EMPLOYEE_ID");
        try {
            resultQuery = this.getEntityManager().createNativeQuery(exportSQL.toString()).setParameter("empIds", empIds.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(", ")))
                    .getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
        return Collections.emptyList();
    }

    @Override
    public List<InsLossDataExport> getHealthInsLoss(List<String> empIds) {
        List<Object[]> resultQuery = null;
        StringBuilder exportSQL = new StringBuilder();
        exportSQL.append("   SELECT qi.EMPLOYEE_ID,");
        exportSQL.append("      SOCIAL_INSURANCE_OFFICE_CD,");
        exportSQL.append("      OTHER,");
        exportSQL.append("      OTHER_REASON,");
        exportSQL.append("      CA_INSURANCE,");
        exportSQL.append("      NUM_RECOVED,");
        exportSQL.append("      CAUSE,");
        exportSQL.append("      PERCENT_OR_MORE,");
        exportSQL.append("      REMARKS_OTHER,");
        exportSQL.append("      REMARKS_AND_OTHER_CONTENTS,");
        exportSQL.append("      REMUN_MONTHLY_AMOUNT_KIND,");
        exportSQL.append("      REMUN_MONTHLY_AMOUNT,");
        exportSQL.append("      TOTAL_MONTHLY_REMUN,");
        exportSQL.append("      LIVING_ABROAD,");
        exportSQL.append("      REASON_OTHER,");
        exportSQL.append("      REASON_AND_OTHER_CONTENTS,");
        exportSQL.append("      SHORT_TIME_WORKES,");
        exportSQL.append("      SHORT_STAY,");
        exportSQL.append("      DEPEN_APPOINT,");
        exportSQL.append("      QUALIFI_DISTIN,");
        exportSQL.append("      CONTIN_REEM_AFTER_RETIREMENT,");
        exportSQL.append("      IS_MORE_EMP,");
        exportSQL.append("      BASIC_PEN_NUMBER");
        exportSQL.append("  FROM ");
        exportSQL.append("         (SELECT *");
        exportSQL.append("         FROM QQSMT_EMP_HEAL_INSUR_QI ");
        exportSQL.append("         WHERE START_DATE <= ?endDate AND START_DATE >= ?startDate");
        exportSQL.append("         AND EMPLOYEE_ID IN (?empIds) )qi");
        exportSQL.append("  LEFT JOIN ");
        exportSQL.append("       (SELECT * ");
        exportSQL.append("       FROM QQSMT_EMP_CORP_OFF_HIS ");
        exportSQL.append("       WHERE START_DATE <= ?endDate AND START_DATE >= ?startDate) his");
        exportSQL.append("       ON qi.EMPLOYEE_ID = his.EMPLOYEE_ID");
        exportSQL.append("  LEFT JOIN QQSMT_HEALTH_INS_LOSS loss ON loss.EMP_ID = his.EMPLOYEE_ID");
        exportSQL.append("  LEFT JOIN QQSMT_SOC_ISACQUISI_INFO info ON info.EMPLOYEE_ID = his.EMPLOYEE_ID");
        exportSQL.append("  LEFT JOIN QQSMT_MULTI_EMP_WORK_IF mt ON mt.EMPLOYEE_ID = his.EMPLOYEE_ID");
        exportSQL.append("  LEFT JOIN QQSMT_EMP_BA_PEN_NUM ba ON ba.EMPLOYEE_ID = his.EMPLOYEE_ID");
        exportSQL.append("  INNER JOIN (SELECT * ");
        exportSQL.append("      FROM BSYMT_EMP_DTA_MNG_INFO ");
        exportSQL.append("      WHERE CID = ?cid) i");
        exportSQL.append("      ON i.SID = qi.EMPLOYEE_ID");
        exportSQL.append("  INNER JOIN BPSMT_PERSON p ON p.PID = i.PID");
        exportSQL.append("  ORDER BY SOCIAL_INSURANCE_OFFICE_CD");

        try {
            resultQuery = this.getEntityManager().createNativeQuery(exportSQL.toString()).setParameter("empIds", empIds.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(", ")))
                    .getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
        return resultQuery.stream().map(i -> InsLossDataExport.builder()
                .empId(i[0].toString())
                .officeCd(i[1].toString())
                .other(((BigDecimal) i[2]).intValue())
                .otherReason(i[3] == null ? null : i[3].toString())
                .caInsurance(i[4] == null ? null : ((BigDecimal) i[4]).intValue())
                .numRecoved(i[5] == null ? null : ((BigDecimal) i[5]).intValue())
                .cause(i[6] == null ? null : ((BigDecimal) i[6]).intValue())
                .build()
                ).collect(Collectors.toList());
    }

    @Override
    public CompanyInfor getCompanyInfor(String cid) {
        Object[] result = null;
        StringBuilder exportSQL = new StringBuilder();
        exportSQL.append("  SELECT");
        exportSQL.append("    CCD,");
        exportSQL.append("    CONTRACT_CD,");
        exportSQL.append("    NAME,");
        exportSQL.append("    MONTH_STR,");
        exportSQL.append("    ABOLITION_ATR,");
        exportSQL.append("    REPRESENTATIVE_NAME,");
        exportSQL.append("    REPRESENTATIVE_JOB,");
        exportSQL.append("    KNNAME,");
        exportSQL.append("    ABNAME,");
        exportSQL.append("    TAX_NO,");
        exportSQL.append("    FAX_NUM,");
        exportSQL.append("    ADDRESS_1,");
        exportSQL.append("    ADDRESS_2,");
        exportSQL.append("    KNNAME_1,");
        exportSQL.append("    KNNAME_2,");
        exportSQL.append("    POSTAL_CODE,");
        exportSQL.append("    PHONE_NUMBER");
        exportSQL.append("  FROM  (SELECT * FROM BCMMT_COMPANY ");
        exportSQL.append("        WHERE CID = ?cid) c ");
        exportSQL.append("    INNER JOIN BCMMT_ADDRESS i ON i.CID = c.CID");
        try {
            result = (Object[]) this.getEntityManager().createNativeQuery(exportSQL.toString()).setParameter("cid", cid)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
        return CompanyInfor.builder().companyId(cid)
                .companyCode(result[0].toString())
                .contractCd(result[1].toString())
                .companyName(result[2].toString())
                .startMonth((int)result[3])
                .isAbolition((int)result[4])
                .repname(result[5] != null ? result[5].toString() : "")
                .repost(result[6] != null ? result[6].toString() : "")
                .comNameKana(result[7] != null ? result[7].toString() : "")
                .shortComName(result[8] != null ? result[8].toString() : "")
                .taxNo(result[9] != null ? result[9].toString() : "")
                .faxNum(result[10] != null ? result[10].toString() : "")
                .add_1(result[11] != null ? result[11].toString() : "")
                .add_2(result[12] != null ? result[12].toString() : "")
                .addKana_1(result[13] != null ? result[13].toString() : "")
                .addKana_2(result[14] != null ? result[14].toString() : "")
                .postCd(result[15] != null ? result[15].toString() : "")
                .phoneNum(result[16] != null ? result[16].toString() : "").build()
                ;
    }

}
