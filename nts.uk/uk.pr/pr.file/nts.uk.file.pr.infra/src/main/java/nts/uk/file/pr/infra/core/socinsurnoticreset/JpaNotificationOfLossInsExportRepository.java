package nts.uk.file.pr.infra.core.socinsurnoticreset;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.CompanyInfor;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.PensFundSubmissData;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.InsLossDataExport;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.NotificationOfLossInsExRepository;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class JpaNotificationOfLossInsExportRepository extends JpaRepository implements NotificationOfLossInsExRepository {

    @Override
    public List<InsLossDataExport> getWelfPenInsLoss(List<String> empIds,String cid, GeneralDate startDate, GeneralDate endDate) {
        List<Object[]> resultQuery = null;
        StringBuilder exportSQL = new StringBuilder();
        exportSQL.append("  SELECT qi.EMPLOYEE_ID,");
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
        exportSQL.append("      BASIC_PEN_NUMBER,");
        exportSQL.append("      PERSON_NAME,");
        exportSQL.append("      PERSON_NAME_KANA,");
        exportSQL.append("      OLDNAME_FNAME,");
        exportSQL.append("      BIRTHDAY,");
        exportSQL.append("      qi.END_DATE,");
        exportSQL.append("      WEL_PEN_NUMBER,");
        exportSQL.append("      OLDNAME_FNAME_KANA,");
        exportSQL.append("      WELFARE_PENSION_OFFICE_NUMBER_1,");
        exportSQL.append("      WELFARE_PENSION_OFFICE_NUMBER_2,");
        exportSQL.append("      WELFARE_PENSION_OFFICE_NUMBER,");
        exportSQL.append("      POSTAL_CODE,");
        exportSQL.append("      ADDRESS_1,");
        exportSQL.append("      ADDRESS_2,");
        exportSQL.append("      NAME,");
        exportSQL.append("      REPRESENTATIVE_NAME,");
        exportSQL.append("      PHONE_NUMBER");
        exportSQL.append("    FROM ");
        exportSQL.append("         (SELECT *");
        exportSQL.append("         FROM QQSMT_EMP_WELF_INS_QC_IF");
        exportSQL.append("         WHERE START_DATE <= ?endDate AND START_DATE >= ?startDate");
        exportSQL.append("         AND EMPLOYEE_ID IN ('%s') )qi");
        exportSQL.append("    INNER JOIN ");
        exportSQL.append("       (SELECT * ");
        exportSQL.append("       FROM QQSMT_EMP_CORP_OFF_HIS ");
        exportSQL.append("       WHERE START_DATE <= ?endDate AND START_DATE >= ?startDate) his");
        exportSQL.append("       ON qi.EMPLOYEE_ID = his.EMPLOYEE_ID");
        exportSQL.append("    INNER JOIN (SELECT *");
        exportSQL.append("        FROM QQSMT_WELF_PEN_INS_LOSS w");
        exportSQL.append("        WHERE EMP_ID NOT IN (SELECT w.EMP_ID");
        exportSQL.append("                         FROM ");
        exportSQL.append("                           (SELECT *");
        exportSQL.append("                           FROM QQSMT_EMP_WELF_INS_QC_IF ");
        exportSQL.append("                           WHERE START_DATE <= ?endDate AND START_DATE >= ?startDate) wi");
        exportSQL.append("                         INNER JOIN QQSMT_WELF_PEN_INS_LOSS w ON w.EMP_ID = wi.EMPLOYEE_ID");
        exportSQL.append("                         INNER JOIN QQSMT_HEALTH_INS_LOSS h ON w.EMP_ID = h.EMP_ID");
        exportSQL.append("                         INNER JOIN QQSMT_EMP_HEAL_INSUR_QI hi ON hi.EMPLOYEE_ID = h.EMP_ID");
        exportSQL.append("                         WHERE wi.START_DATE = hi.START_DATE)) loss ");
        exportSQL.append("       ON loss.EMP_ID = his.EMPLOYEE_ID");
        exportSQL.append("       INNER JOIN QQSMT_SOC_ISACQUISI_INFO info ON info.EMPLOYEE_ID = his.EMPLOYEE_ID");
        exportSQL.append("       INNER JOIN QQSMT_WEL_PEN_NUM_INFO pi ON pi.AFF_MOUR_PERIOD_HISID = his.HISTORY_ID");
        exportSQL.append("       INNER JOIN QQSMT_MULTI_EMP_WORK_IF mt ON mt.EMPLOYEE_ID = his.EMPLOYEE_ID");
        exportSQL.append("       LEFT JOIN QQSMT_EMP_BA_PEN_NUM ba ON ba.EMPLOYEE_ID = his.EMPLOYEE_ID");
        exportSQL.append("       LEFT JOIN (SELECT * ");
        exportSQL.append("            FROM QPBMT_SOCIAL_INS_OFFICE");
        exportSQL.append("            WHERE CID = ?cid) oi ");
        exportSQL.append("            ON oi.CODE = his.SOCIAL_INSURANCE_OFFICE_CD");
        exportSQL.append("       INNER JOIN (SELECT * ");
        exportSQL.append("             FROM BSYMT_EMP_DTA_MNG_INFO ");
        exportSQL.append("             WHERE CID = ?cid) i");
        exportSQL.append("             ON i.SID = qi.EMPLOYEE_ID");
        exportSQL.append("       INNER JOIN BPSMT_PERSON p ON p.PID = i.PID");
        String sql = String.format(exportSQL.toString(), empIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining("','")));
        try {
            resultQuery = this.getEntityManager().createNativeQuery(sql)
                    .setParameter("startDate", this.convertDate(startDate.toString("yyyy-MM-dd")))
                    .setParameter("endDate", this.convertDate(endDate.toString("yyyy-MM-dd")))
                    .setParameter("cid", cid)
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
                .percentOrMore(i[7] == null ? null : i[7].toString())
                .remarksOther(i[8] == null ? null : i[8].toString())
                .remarksAndOtherContent(i[9] == null ? null : i[9].toString())
                .remunMonthlyAmountKind(i[10] == null ? null : i[10].toString())
                .remunMonthlyAmount(i[11] == null ? null : i[11].toString())
                .totalMonthyRemun(i[12] == null ? null : i[12].toString())
                .livingAbroad(i[13] == null ? null : i[13].toString())
                .resonOther(i[14] == null ? null : i[14].toString())
                .resonAndOtherContent(i[15] == null ? null : i[15].toString())
                .shortTimeWorkes(i[16] == null ? null : i[16].toString())
                .shortStay(i[17] == null ? null : i[17].toString())
                .depenAppoint(i[18] == null ? null : i[18].toString())
                .qualifiDistin(i[19] == null ? null : i[19].toString())
                .continReemAfterRetirement(((BigDecimal) i[20]).intValue())
                .isMoreEmp(((BigDecimal)i[21]).intValue())
                .basicPenNumber(i[22] == null ? null :i[22].toString())
                .personName(i[23].toString())
                .personNameKana(i[24] == null ? null : i[24].toString())
                .oldName(i[25] == null ? null : i[25].toString())
                .birthDay(i[26].toString())
                .endDate(i[27] == null ? null : i[27].toString())
                .healInsNumber(i[28] == null ? null : i[28].toString())
                .officeNumber1(i[29] == null ? null : i[29].toString())
                .officeNumber2(i[30] == null ? null : i[30].toString())
                .officeNumber(i[31] == null ? null : i[31].toString())
                .portCd(i[32] == null ? null : i[32].toString())
                .add1(i[33] == null ? null : i[33].toString())
                .add2(i[34] == null ? null : i[34].toString())
                .companyName(i[35] == null ? null : i[35].toString())
                .repName(i[36] == null ? null : i[36].toString())
                .phoneNumber(i[37] == null ? null : i[37].toString())
                .build()
        ).collect(Collectors.toList());
    }

    @Override
    public List<InsLossDataExport> getHealthInsLoss(List<String> empIds, String cid, GeneralDate startDate, GeneralDate endDate) {
        List<Object[]> resultQuery = null;
        StringBuilder exportSQL = new StringBuilder();
        exportSQL.append("  SELECT qi.EMPLOYEE_ID,");
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
        exportSQL.append("      BASIC_PEN_NUMBER,");
        exportSQL.append("      PERSON_NAME,");
        exportSQL.append("      PERSON_NAME_KANA,");
        exportSQL.append("      OLDNAME_FNAME,");
        exportSQL.append("      BIRTHDAY,");
        exportSQL.append("      qi.END_DATE,");
        exportSQL.append("      HEAL_INSUR_NUMBER,");
        exportSQL.append("      HEALTH_INSURANCE_PREFECTURE_NO,");
        exportSQL.append("      HEALTH_INSURANCE_OFFICE_NUMBER_1,");
        exportSQL.append("      HEALTH_INSURANCE_OFFICE_NUMBER_2,");
        exportSQL.append("      HEALTH_INSURANCE_OFFICE_NUMBER,");
        exportSQL.append("      HEALTH_INSURANCE_UNION_OFFICE_NUMBER");
        exportSQL.append("  FROM ");
        exportSQL.append("         (SELECT *");
        exportSQL.append("         FROM QQSMT_EMP_HEAL_INSUR_QI ");
        exportSQL.append("         WHERE START_DATE <= ?endDate AND START_DATE >= ?startDate");
        exportSQL.append("         AND EMPLOYEE_ID IN ('%s') )qi");
        exportSQL.append("  LEFT JOIN ");
        exportSQL.append("       (SELECT * ");
        exportSQL.append("       FROM QQSMT_EMP_CORP_OFF_HIS ");
        exportSQL.append("       WHERE START_DATE <= ?endDate AND START_DATE >= ?startDate) his");
        exportSQL.append("       ON qi.EMPLOYEE_ID = his.EMPLOYEE_ID");
        exportSQL.append("  LEFT JOIN QQSMT_HEALTH_INS_LOSS loss ON loss.EMP_ID = his.EMPLOYEE_ID");
        exportSQL.append("  LEFT JOIN QQSMT_SOC_ISACQUISI_INFO info ON info.EMPLOYEE_ID = his.EMPLOYEE_ID");
        exportSQL.append("  LEFT JOIN QQSMT_MULTI_EMP_WORK_IF mt ON mt.EMPLOYEE_ID = his.EMPLOYEE_ID");
        exportSQL.append("  LEFT JOIN QQSMT_EMP_BA_PEN_NUM ba ON ba.EMPLOYEE_ID = his.EMPLOYEE_ID");
        exportSQL.append("  LEFT JOIN (SELECT * ");
        exportSQL.append("       FROM QPBMT_SOCIAL_INS_OFFICE");
        exportSQL.append("       WHERE CID = ?cid) oi ");
        exportSQL.append("       ON oi.CODE = his.SOCIAL_INSURANCE_OFFICE_CD");
        exportSQL.append("  INNER JOIN (SELECT * ");
        exportSQL.append("       FROM BSYMT_EMP_DTA_MNG_INFO ");
        exportSQL.append("       WHERE CID = ?cid) i");
        exportSQL.append("       ON i.SID = qi.EMPLOYEE_ID");
        exportSQL.append("  INNER JOIN BPSMT_PERSON p ON p.PID = i.PID");
        exportSQL.append("  ORDER BY SOCIAL_INSURANCE_OFFICE_CD   ");
        String sql = String.format(exportSQL.toString(), empIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining("','")));
        try {
            resultQuery = this.getEntityManager().createNativeQuery(sql)
                    .setParameter("startDate", this.convertDate(startDate.toString("yyyy-MM-dd")))
                    .setParameter("endDate", this.convertDate(endDate.toString("yyyy-MM-dd")))
                    .setParameter("cid", cid)
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
                .percentOrMore(i[7] == null ? null : i[7].toString())
                .remarksOther(i[8] == null ? null : i[8].toString())
                .remarksAndOtherContent(i[9] == null ? null : i[9].toString())
                .remunMonthlyAmountKind(i[10] == null ? null : i[10].toString())
                .remunMonthlyAmount(i[11] == null ? null : i[11].toString())
                .totalMonthyRemun(i[12] == null ? null : i[12].toString())
                .livingAbroad(i[13] == null ? null : i[13].toString())
                .resonOther(i[14] == null ? null : i[14].toString())
                .resonAndOtherContent(i[15] == null ? null : i[15].toString())
                .shortTimeWorkes(i[16] == null ? null : i[16].toString())
                .shortStay(i[17] == null ? null : i[17].toString())
                .depenAppoint(i[18] == null ? null : i[18].toString())
                .qualifiDistin(i[19] == null ? null : i[19].toString())
                .continReemAfterRetirement(((BigDecimal) i[20]).intValue())
                .isMoreEmp(((BigDecimal) i[21]).intValue())
                .basicPenNumber(i[22] == null ? null : i[22].toString())
                .personName(i[23].toString())
                .personNameKana(i[24] == null ? null : i[24].toString())
                .oldName(i[25] == null ? null : i[25].toString())
                .birthDay(i[26].toString())
                .endDate(i[27] == null ? null : i[27].toString())
                .healInsNumber(i[28] == null ? null : i[28].toString())
                .officeNumber1(i[29] == null ? null : i[29].toString())
                .officeNumber2(i[30] == null ? null : i[30].toString())
                .officeNumber(i[31] == null ? null : i[31].toString())
                .unionOfficeNumber(i[31] == null ? null : i[31].toString())
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
        exportSQL.append("  INNER JOIN BCMMT_ADDRESS i ON i.CID = c.CID");
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
                .startMonth(((BigDecimal)result[3]).intValue())
                .isAbolition(((BigDecimal)result[4]).intValue())
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
                .phoneNum(result[16] != null ? result[16].toString() : "")
                .build()
                ;
    }

    @Override
    public List<PensFundSubmissData> getHealthInsAssociation(List<String> empIds, String cid, GeneralDate startDate, GeneralDate endDate) {
        List<Object[]> result = null;
        StringBuilder exportSQL = new StringBuilder();
        exportSQL.append("  SELECT  WELFARE_PENSION_OFFICE_NUMBER,");
        exportSQL.append("      FUND_SPECIFIC1,");
        exportSQL.append("      FUND_SPECIFIC2,");
        exportSQL.append("      FUND_SPECIFIC3,");
        exportSQL.append("      FUND_SPECIFIC4,");
        exportSQL.append("      FUND_SPECIFIC5,");
        exportSQL.append("      FUND_SPECIFIC6,");
        exportSQL.append("      FUND_SPECIFIC7,");
        exportSQL.append("      FUND_SPECIFIC8,");
        exportSQL.append("      FUND_SPECIFIC9,");
        exportSQL.append("      FUND_SPECIFIC10,");
        exportSQL.append("      WELFARE_PENSION_FUND_NUMBER,");
        exportSQL.append("      WELFARE_PENSION_OFFICE_NUMBER_1,");
        exportSQL.append("      WELFARE_PENSION_OFFICE_NUMBER_2,");
        exportSQL.append("      HEAL_INSUR_NUMBER,");
        exportSQL.append("      WEL_PEN_NUMBER,");
        exportSQL.append("      HEAL_INSUR_UNION_NMBER,");
        exportSQL.append("      MEMBER_NUMBER,");
        exportSQL.append("      TODOKEDE_FNAME_KANA");
        exportSQL.append("  FROM ");
        exportSQL.append("         (SELECT *");
        exportSQL.append("         FROM QQSMT_EMP_WELF_INS_QC_IF ");
        exportSQL.append("         WHERE START_DATE <= '?endDate' AND START_DATE >= ?startDate");
        exportSQL.append("         AND EMPLOYEE_ID IN ('%s') )qi");
        exportSQL.append("  INNER JOIN ");
        exportSQL.append("       (SELECT * ");
        exportSQL.append("       FROM QQSMT_EMP_CORP_OFF_HIS ");
        exportSQL.append("       WHERE START_DATE <= '?endDate' AND START_DATE >= ?startDate) his");
        exportSQL.append("       ON qi.EMPLOYEE_ID = his.EMPLOYEE_ID");
        exportSQL.append("  INNER JOIN QQSMT_WELF_PEN_INS_LOSS loss on loss.EMP_ID = qi.EMPLOYEE_ID");
        exportSQL.append("  INNER JOIN (SELECT *  ");
        exportSQL.append("        FROM QQSMT_TEM_PEN_PART_INFO");
        exportSQL.append("        WHERE START_DATE <= '?endDate' AND START_DATE >= ?startDate) pi ");
        exportSQL.append("        ON qi.EMPLOYEE_ID = pi.EMPLOYEE_ID");
        exportSQL.append("  INNER JOIN (SELECT * ");
        exportSQL.append("        FROM QPBMT_SOCIAL_INS_OFFICE");
        exportSQL.append("        WHERE CID = '?cid') oi");
        exportSQL.append("        ON oi.CODE = his.SOCIAL_INSURANCE_OFFICE_CD");
        exportSQL.append("  INNER JOIN QQSMT_EMPL_PEN_FUND_INFO fi ON fi.EMPLOYEE_ID = qi.EMPLOYEE_ID");
        exportSQL.append("   INNER JOIN (SELECT * ");
        exportSQL.append("        FROM BSYMT_EMP_DTA_MNG_INFO ");
        exportSQL.append("        WHERE CID = '000000000000-0001') i");
        exportSQL.append("        ON i.SID = qi.EMPLOYEE_ID");
        exportSQL.append("  INNER JOIN BPSMT_PERSON p ON p.PID = i.PID");
        exportSQL.append("  INNER JOIN (SELECT *");
        exportSQL.append("       FROM QQSMT_EMP_HEAL_INSUR_QI ");
        exportSQL.append("       WHERE START_DATE <= '?endDate' AND START_DATE >= ?startDate) wi ");
        exportSQL.append("       ON wi.EMPLOYEE_ID = qi.EMPLOYEE_ID");
        exportSQL.append("  INNER JOIN QQSMT_WEL_PEN_NUM_INFO ni ON ni.AFF_MOUR_PERIOD_HISID = qi.HISTORY_ID");
        exportSQL.append("  INNER JOIN (SELECT *");
        exportSQL.append("       FROM QQSMT_HEAL_INSUR_PORT_INT ");
        exportSQL.append("       WHERE START_DATE <= '?endDate' AND START_DATE >= ?startDate) pri");
        exportSQL.append("       ON pri.EMPLOYEE_ID = qi.EMPLOYEE_ID");
        String sql = String.format(exportSQL.toString(), empIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining("','")));
        try {
            result = this.getEntityManager().createNativeQuery(sql)
                    .setParameter("startDate", this.convertDate(startDate.toString("yyyy-MM-dd")))
                    .setParameter("endDate", this.convertDate(endDate.toString("yyyy-MM-dd")))
                    .setParameter("cid", cid)
                    .getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
        return result.stream().map(i ->PensFundSubmissData.builder()
                .officeNumber(i[0].toString())
                .funSpecific1(i[1] != null ? i[1].toString() : "")
                .funSpecific2(i[2] != null ? i[2].toString() : "")
                .funSpecific3(i[3] != null ? i[3].toString() : "")
                .funSpecific4(i[4] != null ? i[4].toString() : "")
                .funSpecific5(i[5] != null ? i[5].toString() : "")
                .funSpecific1(i[6] != null ? i[6].toString() : "")
                .funSpecific1(i[7] != null ? i[7].toString() : "")
                .funSpecific1(i[8] != null ? i[8].toString() : "")
                .funSpecific1(i[9] != null ? i[9].toString() : "")
                .funSpecific1(i[10] != null ? i[10].toString() : "")
                .funMember(i[11] != null ? i[11].toString() : "")
                .officeNumber1(i[12] != null ? i[12].toString() : "")
                .officeNumber2(i[13] != null ? i[13].toString() : "")
                .healInsNumber(i[14] != null ? i[14].toString() : "")
                .welNumber(i[15] != null ? i[15].toString() : "")
                .healInsUnionNumber(i[16] != null ? i[16].toString() : "")
                .memberNumber(i[16] != null ? i[16].toString() : "")
                .build() ).collect(Collectors.toList());

    }

    private java.sql.Date convertDate(String Date) {
        java.util.Date date = null;
        java.sql.Date sqlDate = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = format.parse(Date);
            sqlDate = new java.sql.Date(date.getTime());
        } catch (ParseException e) {
            return null;
        }
        return sqlDate;
    }

}
