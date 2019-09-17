package nts.uk.file.pr.infra.core.socinsurnoticreset;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.CompanyInfor;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.GuaByTheInsurExportRepository;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.InsLossDataExport;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.PensionOfficeDataExport;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class JpaGuaByTheInsurExportRepository extends JpaRepository implements GuaByTheInsurExportRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QrsmtComStatutoryWrite f";
    private static final String SELECT_BY_CID = SELECT_ALL_QUERY_STRING + " WHERE  f.comStatutoryWritePk.cid =:cid order by f.comStatutoryWritePk.code ASC";

    @Override
    public List<PensionOfficeDataExport> getDataExportCSV(List<String> empIds, String cid, GeneralDate startDate, GeneralDate endDate) {
        List<Object[]> resultQuery ;
        StringBuilder exportSQL = new StringBuilder();
        exportSQL.append(" SELECT HEALTH_INSURANCE_OFFICE_NUMBER_1,");
        exportSQL.append("      WELFARE_PENSION_OFFICE_NUMBER_1,");
        exportSQL.append("      HEALTH_INSURANCE_OFFICE_NUMBER_2,");
        exportSQL.append("      WELFARE_PENSION_OFFICE_NUMBER_2,");
        exportSQL.append("      HEALTH_INSURANCE_OFFICE_NUMBER,");
        exportSQL.append("      WELFARE_PENSION_OFFICE_NUMBER,");
        exportSQL.append("      BUSINESS_NAME,");
        exportSQL.append("      BUSINESS_NAME_KANA,");
        exportSQL.append("      PERSON_NAME,");
        exportSQL.append("      PERSON_NAME_KANA,");
        exportSQL.append("      BIRTHDAY");
        exportSQL.append(" 	    GENDER,");
        exportSQL.append("      UNDERGOUND_DIVISION,");
        exportSQL.append("      LIVING_ABROAD,");
        exportSQL.append("      SHORT_STAY,");
        exportSQL.append("      REASON_OTHER,");
        exportSQL.append("      REASON_AND_OTHER_CONTENTS,");
        exportSQL.append("      qi.START_DATE,");
        exportSQL.append("      wi.START_DATE,");
        exportSQL.append("      DEPEN_APPOINT,");
        exportSQL.append("      REMUN_MONTHLY_AMOUNT,");
        exportSQL.append("      REMUN_MONTHLY_AMOUNT_KIND,");
        exportSQL.append("      TOTAL_MONTHLY_REMUN,");
        exportSQL.append("      PERCENT_OR_MORE,");
        exportSQL.append("      IS_MORE_EMP,");
        exportSQL.append("      SHORT_TIME_WORKES,");
        exportSQL.append("      CONTIN_REEM_AFTER_RETIREMENT,");
        exportSQL.append("      REMARKS_AND_OTHER_CONTENTS,");
        exportSQL.append("      HEALTH_INSURANCE_PREFECTURE_NO,");
        exportSQL.append("      WELFARE_PENSION_PREFECTURE_NO,");
        exportSQL.append("      HEAL_INSUR_SAME_CTG,");
        exportSQL.append("      QUALIFI_DISTIN");
        exportSQL.append("  FROM");
        exportSQL.append("      (SELECT *");
        exportSQL.append("         FROM QQSMT_EMP_HEAL_INSUR_QI ");
        exportSQL.append("         WHERE END_DATE <= ?endDate AND END_DATE >= ?startDate");
        exportSQL.append("         AND EMPLOYEE_ID IN ('%s') )qi");
        exportSQL.append("  LEFT JOIN ");
        exportSQL.append("       (SELECT * ");
        exportSQL.append("       FROM QQSMT_EMP_CORP_OFF_HIS ");
        exportSQL.append("       WHERE END_DATE <= ?endDate AND END_DATE >= ?startDate) his");
        exportSQL.append("       ON qi.EMPLOYEE_ID = his.EMPLOYEE_ID");
        exportSQL.append("  LEFT JOIN ");
        exportSQL.append("       (SELECT *");
        exportSQL.append("       FROM QQSMT_EMP_WELF_INS_QC_IF ");
        exportSQL.append("       WHERE END_DATE <= ?endDate AND END_DATE >= ?startDate");
        exportSQL.append("       AND EMPLOYEE_ID IN ('%s') ) wi");
        exportSQL.append("       ON qi.EMPLOYEE_ID = wi.EMPLOYEE_ID");
        exportSQL.append("  LEFT JOIN ");
        exportSQL.append("       (SELECT * ");
        exportSQL.append("       FROM QQSMT_EMP_PEN_INS ");
        exportSQL.append("       WHERE END_DATE <= ?endDate AND END_DATE >= ?startDate) pi");
        exportSQL.append("       ON qi.EMPLOYEE_ID = pi.EMPLOYEE_ID");
        exportSQL.append("  LEFT JOIN ");
        exportSQL.append("       (SELECT * ");
        exportSQL.append("       FROM QQSMT_TEM_PEN_PART_INFO ");
        exportSQL.append("       WHERE START_DATE <= ?endDate AND START_DATE >= ?startDate) ti");
        exportSQL.append("       ON qi.EMPLOYEE_ID = ti.EMPLOYEE_ID");
        exportSQL.append("  LEFT JOIN ");
        exportSQL.append("       (SELECT * ");
        exportSQL.append("       FROM QQSMT_SOC_ISACQUISI_INFO ");
        exportSQL.append("       WHERE CID = ?cid) ii");
        exportSQL.append("       ON qi.EMPLOYEE_ID = ii.EMPLOYEE_ID");
        exportSQL.append("  LEFT JOIN QQSMT_MULTI_EMP_WORK_IF mi ON mi.EMPLOYEE_ID = qi.EMPLOYEE_ID");
        exportSQL.append("  INNER JOIN (SELECT * ");
        exportSQL.append("        FROM BSYMT_EMP_DTA_MNG_INFO ");
        exportSQL.append("        WHERE CID = ?cid) i");
        exportSQL.append("        ON i.SID = qi.EMPLOYEE_ID");
        exportSQL.append("  INNER JOIN BPSMT_PERSON p ON p.PID = i.PID");
        exportSQL.append("  INNER JOIN (SELECT * ");
        exportSQL.append("        FROM QPBMT_SOCIAL_INS_OFFICE");
        exportSQL.append("        WHERE CID = ?cid) oi");
        exportSQL.append("        ON oi.CODE = his.SOCIAL_INSURANCE_OFFICE_CD");
        String sql = String.format(exportSQL.toString(), empIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining("','")));
        try {
            resultQuery = this.getEntityManager().createNativeQuery(sql)
                    .setParameter("startDate", convertDate(startDate))
                    .setParameter("endDate", convertDate(endDate))
                    .setParameter("cid", cid)
                    .getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
        return resultQuery.stream().map(i -> PensionOfficeDataExport.builder()
                .healOfficeNumber1(i[0] == null ? "" : i[0].toString())
                .welOfficeNumber1(i[1] == null ? "" : i[1].toString())
                .healOfficeNumber2(i[2] == null ? "" : i[2].toString())
                .welOfficeNumber2(i[3] == null ? "" : i[3].toString())
                .healOfficeNumber(i[4] == null ? "" : i[4].toString())
                .welOfficeNumber(i[5] == null ? "" :i[5].toString())
                .personName(i[6] == null ? "" : i[6].toString())
                .personNameKana(i[7] == null ? "" : i[7].toString())
                .oldName(i[8] == null ? "" : i[8].toString())
                .oldNameKana(i[9] == null ? "" : i[9].toString())
                .birthDay(i[10] == null ? "" : i[10].toString())
                .gender(i[11] == null ?  "" : i[11].toString())
                .underDivision(i[12] == null ? "" : i[12].toString())
                .livingAbroad(i[13] == null ? "" : i[13].toString())
                .shortStay(i[14] == null ? "" : i[14].toString())
                .resonOther(i[15] == null ? "" : i[15].toString())
                .resonAndOtherContent(i[16] == null ? "" : i[16].toString())
                .startDate1(i[17] == null ? "" : i[17].toString())
                .startDate2(i[18] == null ? "" : i[18].toString())
                .depenAppoint(i[19] == null ? "" : i[19].toString())
                .remunMonthlyAmount(i[20] == null ? 0 : ((BigDecimal) i[20]).intValue())
                .remunMonthlyAmountKind(i[21] == null ? 0 :((BigDecimal)i[21]).intValue())
                .totalMonthyRemun(i[22] == null ? 0 : ((BigDecimal)i[22]).intValue())
                .percentOrMore(i[23] == null ? 0 : ((BigDecimal)i[23]).intValue())
                .isMoreEmp(i[24] == null ? 0 : ((BigDecimal)i[24]).intValue())
                .shortTimeWorkes(i[25] == null ? 0 : ((BigDecimal)i[25]).intValue())
                .continReemAfterRetirement(i[26] == null ? 0 : ((BigDecimal)i[26]).intValue())
                .remarksAndOtherContent(i[27] == null ? 0 : ((BigDecimal)i[27]).intValue())
                .healPrefectureNo(i[28] == null ? 0 : ((BigDecimal) i[28]).intValue())
                .welPrefectureNo(i[28] == null ? 0 : ((BigDecimal) i[28]).intValue())
                .healInsCtg(i[29] == null ? 0 : ((BigDecimal) i[29]).intValue())
                .distin(i[29] == null ? "" : i[29].toString())
                .build()
        ).collect(Collectors.toList());
    }

    @Override
    public List<Object[]> getDataExport(List<String> empIds, String cid,String userId, GeneralDate startDate, GeneralDate endDate) {

        List<Object[]> resultQuery ;
        StringBuilder exportSQL = new StringBuilder();
        exportSQL.append(" SELECT");
        exportSQL.append("  QSINS.OFFICE_INFORMATION,");
        exportSQL.append("  BC.NAME,");
        exportSQL.append("  QSIO.NAME,");
        exportSQL.append("  BA.ADDRESS_1,");
        exportSQL.append("  QSIO.ADDRESS_1,");
        exportSQL.append("  BA.ADDRESS_2,");
        exportSQL.append("  QSIO.ADDRESS_2,");
        exportSQL.append("  BA.PHONE_NUMBER,");
        exportSQL.append("  QSIO.PHONE_NUMBER,");
        exportSQL.append("  BA.POSTAL_CODE,");
        exportSQL.append("  QSIO.POSTAL_CODE,");
        exportSQL.append("  BC.REPRESENTATIVE_NAME,");
        exportSQL.append("  QSIO.REPRESENTATIVE_NAME,");
        exportSQL.append("  QSINS.SUBMITTED_NAME,");
        exportSQL.append("  QSINS.BUSSINESS_ARR_SYMBOL,");
        exportSQL.append("  QSIO.HEALTH_INSURANCE_OFFICE_NUMBER_1,");
        exportSQL.append("  QSIO.HEALTH_INSURANCE_OFFICE_NUMBER_2,");
        exportSQL.append("  QSIO.WELFARE_PENSION_OFFICE_NUMBER_1,");
        exportSQL.append("  QSIO.WELFARE_PENSION_OFFICE_NUMBER_2,");
        exportSQL.append("  BP.PERSON_NAME,");
        exportSQL.append("  BP.TODOKEDE_FNAME,");
        exportSQL.append("  BP.PERSON_NAME,");
        exportSQL.append("  BP.TODOKEDE_FNAME,");
        exportSQL.append("  BP.PERSON_NAME_KANA,");
        exportSQL.append("  BP.TODOKEDE_FNAME_KANA,");
        exportSQL.append("  BP.PERSON_NAME_KANA,");
        exportSQL.append("  BP.TODOKEDE_FNAME_KANA,");
        exportSQL.append("  BP.BIRTHDAY,");
        exportSQL.append("  BP.BIRTHDAY,");
        exportSQL.append("  BP.GENDER,");
        exportSQL.append("  QTPPI.HISTORY_ID,");
        exportSQL.append("  QEPI.UNDERGOUND_DIVISION,");
        exportSQL.append("  QSII.QUALIFI_DISTIN,");
        exportSQL.append("  QEBPN.BASIC_PEN_NUMBER,");
        exportSQL.append("  QEHIQ.START_DATE,");
        exportSQL.append("  QEWIQI.START_DATE,");
        exportSQL.append("  QEHIQ.START_DATE,");
        exportSQL.append("  QEWIQI.START_DATE,");
        exportSQL.append("  QSII.DEPEN_APPOINT,");
        exportSQL.append("  QSII.REMUN_MONTHLY_AMOUNT,");
        exportSQL.append("  QSII.REMUN_MONTHLY_AMOUNT_KIND,");
        exportSQL.append("  QSII.TOTAL_MONTHLY_REMUN,");
        exportSQL.append("  QSII.PERCENT_OR_MORE,");
        exportSQL.append("  QMEWI.IS_MORE_EMP,");
        exportSQL.append("  QSII.SHORT_TIME_WORKES,");
        exportSQL.append("  QSII.CONTIN_REEM_AFTER_RETIREMENT,");
        exportSQL.append("  QSII.REMARKS_OTHER,");
        exportSQL.append("  QSII.REMARKS_AND_OTHER_CONTENTS,");
        exportSQL.append("  QSII.LIVING_ABROAD,");
        exportSQL.append("  QSII.SHORT_STAY,");
        exportSQL.append("  QSII.REASON_OTHER,");
        exportSQL.append("  QSII.REASON_AND_OTHER_CONTENTS, ");
        exportSQL.append("  QSIO.HEALTH_INSURANCE_OFFICE_NUMBER,");
        exportSQL.append("  QSIO.WELFARE_PENSION_OFFICE_NUMBER ");
        exportSQL.append(" FROM ");
        exportSQL.append("   (SELECT * ");
        exportSQL.append("    FROM QQSMT_EMP_CORP_OFF_HIS QECOH ");
        exportSQL.append("    WHERE EMPLOYEE_ID IN ('%s') ");
        exportSQL.append("      AND QECOH.END_DATE <= ?endDate ");
        exportSQL.append("      AND QECOH.END_DATE >= ?startDate  ) AS ROOT ");
        exportSQL.append(" LEFT JOIN QQSMT_SOC_INSU_NOTI_SET QSINS ON QSINS.CID = ?cid  ");
        exportSQL.append(" AND QSINS.USER_ID = ?userId ");
        exportSQL.append(" LEFT JOIN QPBMT_SOCIAL_INS_OFFICE QSIO ");
        exportSQL.append(" ON QSIO.CID = ?cid AND QSIO.CODE = ROOT.SOCIAL_INSURANCE_OFFICE_CD ");
        exportSQL.append(" LEFT JOIN BCMMT_COMPANY BC ON BC.CID = QSIO.CID ");
        exportSQL.append(" LEFT JOIN BCMMT_ADDRESS BA ON BA.CID = QSIO.CID ");
        exportSQL.append(" LEFT JOIN BSYMT_EMP_DTA_MNG_INFO BEDMI ON BEDMI.SID = ROOT.EMPLOYEE_ID ");
        exportSQL.append(" LEFT JOIN BPSMT_PERSON BP ON BEDMI.PID = BP.PID ");
        exportSQL.append(" LEFT JOIN ");
        exportSQL.append("   (SELECT * ");
        exportSQL.append("    FROM QQSMT_TEM_PEN_PART_INFO QTPPITEM ");
        exportSQL.append("    WHERE ");
        exportSQL.append("       QTPPITEM.END_DATE <= ?endDate ");
        exportSQL.append("      AND QTPPITEM.END_DATE >= ?startDate ) AS QTPPI ");
        exportSQL.append(" ON QTPPI.EMPLOYEE_ID = ROOT.EMPLOYEE_ID ");
        exportSQL.append(" LEFT JOIN ");
        exportSQL.append("   (SELECT * ");
        exportSQL.append("    FROM QQSMT_EMP_PEN_INS QEPITEM ");
        exportSQL.append("    WHERE QEPITEM.END_DATE <= ?endDate ");
        exportSQL.append("      AND QEPITEM.END_DATE >= ?startDate ) AS QEPI ");
        exportSQL.append(" ON QEPI.EMPLOYEE_ID = ROOT.EMPLOYEE_ID ");
        exportSQL.append(" LEFT JOIN QQSMT_SOC_ISACQUISI_INFO QSII ");
        exportSQL.append(" ON QSII.EMPLOYEE_ID = ROOT.EMPLOYEE_ID AND QSII.CID =  QSIO.CID ");
        exportSQL.append(" LEFT JOIN QQSMT_EMP_BA_PEN_NUM QEBPN ");
        exportSQL.append(" ON QEBPN.EMPLOYEE_ID = ROOT.EMPLOYEE_ID ");
        exportSQL.append(" LEFT JOIN ");
        exportSQL.append("   (SELECT * ");
        exportSQL.append("    FROM QQSMT_EMP_HEAL_INSUR_QI QEHIQTEM ");
        exportSQL.append("    WHERE QEHIQTEM.END_DATE <= ?endDate ");
        exportSQL.append("      AND QEHIQTEM.END_DATE >= ?startDate ) AS QEHIQ ");
        exportSQL.append(" ON QEHIQ.EMPLOYEE_ID = ROOT.EMPLOYEE_ID ");
        exportSQL.append(" LEFT JOIN QQSMT_MULTI_EMP_WORK_IF QMEWI ");
        exportSQL.append(" ON QMEWI.EMPLOYEE_ID = ROOT.EMPLOYEE_ID ");
        exportSQL.append(" LEFT JOIN ");
        exportSQL.append("   (SELECT * ");
        exportSQL.append("    FROM QQSMT_EMP_WELF_INS_QC_IF QEWIQITEMP ");
        exportSQL.append("    WHERE QEWIQITEMP.END_DATE <= ?endDate ");
        exportSQL.append("      AND QEWIQITEMP.END_DATE >= ?startDate ) AS QEWIQI ");
        exportSQL.append(" ON QEWIQI.EMPLOYEE_ID = ROOT.EMPLOYEE_ID ");
        String sql = String.format(exportSQL.toString(), empIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining("','")));
        try {
            resultQuery = this.getEntityManager().createNativeQuery(sql)
                    .setParameter("startDate", convertDate(startDate))
                    .setParameter("endDate", convertDate(endDate))
                    .setParameter("cid", cid)
                    .setParameter("userId", userId)
                    .getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
        return resultQuery;
    }
    private java.sql.Date convertDate(GeneralDate baseDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = null;
        java.sql.Date sqlDate = null;
        try {
            date = format.parse(baseDate.toString("yyyy-MM-dd"));
            sqlDate = new java.sql.Date(date.getTime());
        } catch (ParseException e) {
            return null;
        }
        return sqlDate;
    }

    public List<PensionOfficeDataExport> getDataHealthInsAss(List<String> empIds,String cid, GeneralDate startDate, GeneralDate endDate) {
        List<Object[]> resultQuery;
        StringBuilder exportSQL = new StringBuilder();
        exportSQL.append("  SELECT HEALTH_INSURANCE_OFFICE_NUMBER_1,");
        exportSQL.append("      WELFARE_PENSION_OFFICE_NUMBER_1,");
        exportSQL.append("      HEALTH_INSURANCE_OFFICE_NUMBER_2,");
        exportSQL.append("      WELFARE_PENSION_OFFICE_NUMBER_2,");
        exportSQL.append("      HEALTH_INSURANCE_OFFICE_NUMBER,");
        exportSQL.append("      WELFARE_PENSION_OFFICE_NUMBER,");
        exportSQL.append("      BUSINESS_NAME,");
        exportSQL.append("      BUSINESS_NAME_KANA,");
        exportSQL.append("      PERSON_NAME,");
        exportSQL.append("      PERSON_NAME_KANA,");
        exportSQL.append("      BIRTHDAY,");
        exportSQL.append("      GENDER,");
        exportSQL.append("      UNDERGOUND_DIVISION,");
        exportSQL.append("      LIVING_ABROAD,");
        exportSQL.append("      SHORT_STAY,");
        exportSQL.append("      REASON_OTHER,");
        exportSQL.append("      REASON_AND_OTHER_CONTENTS,");
        exportSQL.append("      qi.START_DATE,");
        exportSQL.append("      wi.START_DATE,");
        exportSQL.append("      DEPEN_APPOINT,");
        exportSQL.append("      REMUN_MONTHLY_AMOUNT,");
        exportSQL.append("      REMUN_MONTHLY_AMOUNT_KIND,");
        exportSQL.append("      TOTAL_MONTHLY_REMUN,");
        exportSQL.append("      PERCENT_OR_MORE,");
        exportSQL.append("      IS_MORE_EMP,");
        exportSQL.append("      SHORT_TIME_WORKES,");
        exportSQL.append("      CONTIN_REEM_AFTER_RETIREMENT,");
        exportSQL.append("      REMARKS_AND_OTHER_CONTENTS,");
        exportSQL.append("      HEALTH_INSURANCE_PREFECTURE_NO,");
        exportSQL.append("      WELFARE_PENSION_PREFECTURE_NO,");
        exportSQL.append("      HEAL_INSUR_SAME_CTG,     ");
        exportSQL.append("      HEAL_INSUR_INHEREN_PR");
        exportSQL.append("   FROM    ");
        exportSQL.append("       (SELECT *");
        exportSQL.append("         FROM QQSMT_EMP_HEAL_INSUR_QI ");
        exportSQL.append("         WHERE START_DATE <= ?endDate AND START_DATE >= ?startDate");
        exportSQL.append("         AND EMPLOYEE_ID IN ('%s') )qi ");
        exportSQL.append("   LEFT JOIN     ");
        exportSQL.append("       (SELECT *");
        exportSQL.append("       FROM QQSMT_HEAL_INSUR_PORT_INT ");
        exportSQL.append("       WHERE START_DATE <= ?endDate AND START_DATE >= ?startDate) pri");
        exportSQL.append("       ON pri.EMPLOYEE_ID = qi.EMPLOYEE_ID");
        exportSQL.append("    LEFT JOIN ");
        exportSQL.append("       (SELECT * ");
        exportSQL.append("       FROM QQSMT_EMP_CORP_OFF_HIS ");
        exportSQL.append("       WHERE END_DATE <= ?endDate AND END_DATE >= ?startDate) his");
        exportSQL.append("       ON qi.EMPLOYEE_ID = his.EMPLOYEE_ID");
        exportSQL.append("    LEFT JOIN ");
        exportSQL.append("       (SELECT * ");
        exportSQL.append("       FROM QQSMT_SOC_ISACQUISI_INFO ");
        exportSQL.append("       WHERE CID = ?cid) ii");
        exportSQL.append("       ON qi.EMPLOYEE_ID = ii.EMPLOYEE_ID");
        exportSQL.append("    LEFT JOIN QQSMT_EMP_HEAL_INS_UNION iu ON qi.EMPLOYEE_ID = iu.EMPLOYEE_ID");
        exportSQL.append("    LEFT JOIN (SELECT *");
        exportSQL.append("       FROM QQSMT_EMP_WELF_INS_QC_IF ");
        exportSQL.append("       WHERE START_DATE <= ?endDate AND START_DATE >= ?startDate) wi");
        exportSQL.append("       ON wi.EMPLOYEE_ID = qi.EMPLOYEE_ID");
        exportSQL.append("    LEFT JOIN (SELECT *");
        exportSQL.append("       FROM QQSMT_EMP_PEN_INS ");
        exportSQL.append("       WHERE START_DATE <= ?endDate AND START_DATE >= ?startDate) pi");
        exportSQL.append("       ON pi.EMPLOYEE_ID = qi.EMPLOYEE_ID");
        exportSQL.append("    LEFT JOIN (SELECT *");
        exportSQL.append("       FROM QQSMT_TEM_PEN_PART_INFO ");
        exportSQL.append("       WHERE START_DATE <= ?endDate AND START_DATE >= ?startDate) ti");
        exportSQL.append("       ON ti.EMPLOYEE_ID = qi.EMPLOYEE_ID  ");
        exportSQL.append("   LEFT JOIN QQSMT_MULTI_EMP_WORK_IF wf ON wf.EMPLOYEE_ID = qi.EMPLOYEE_ID");
        exportSQL.append("   INNER JOIN (SELECT * ");
        exportSQL.append("        FROM QPBMT_SOCIAL_INS_OFFICE");
        exportSQL.append("        WHERE CID = ?cid) oi");
        exportSQL.append("        ON oi.CODE = his.SOCIAL_INSURANCE_OFFICE_CD");
        exportSQL.append("   INNER JOIN (SELECT * ");
        exportSQL.append("        FROM BSYMT_EMP_DTA_MNG_INFO ");
        exportSQL.append("        WHERE CID = ?cid) i");
        exportSQL.append("        ON i.SID = qi.EMPLOYEE_ID");
        exportSQL.append("  INNER JOIN BPSMT_PERSON p ON p.PID = i.PID");
        String sql = String.format(exportSQL.toString(), empIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining("','")));
        try {
            resultQuery = this.getEntityManager().createNativeQuery(sql)
                    .setParameter("startDate", convertDate(startDate))
                    .setParameter("endDate", convertDate(endDate))
                    .setParameter("cid", cid)
                    .getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
        return resultQuery.stream().map(i -> PensionOfficeDataExport.builder()
                .healOfficeNumber1(i[0] == null ? "" : i[0].toString())
                .welOfficeNumber1(i[1] == null ? "" : i[1].toString())
                .healOfficeNumber2(i[2] == null ? "" : i[2].toString())
                .welOfficeNumber2(i[3] == null ? "" : i[3].toString())
                .healOfficeNumber(i[4] == null ? "" : i[4].toString())
                .welOfficeNumber(i[5] == null ? "" : i[5].toString())
                .personName(i[6] == null ? "" : i[6].toString())
                .personNameKana(i[7] == null ? "" : i[7].toString())
                .oldName(i[8] == null ? "" : i[8].toString())
                .oldNameKana(i[9] == null ? "" : i[9].toString())
                .birthDay(i[10] == null ? "" : i[10].toString())
                .gender(i[11] == null ? "" : i[11].toString())
                .underDivision(i[12] == null ? "" : i[12].toString())
                .livingAbroad(i[13] == null ? "" : i[13].toString())
                .shortStay(i[14] == null ? "" : i[14].toString())
                .resonOther(i[15] == null ? "" : i[15].toString())
                .resonAndOtherContent(i[16] == null ? "" : i[16].toString())
                .startDate1(i[17] == null ? "" : i[17].toString())
                .startDate2(i[18] == null ? "" : i[18].toString())
                .depenAppoint(i[19] == null ? "" : i[19].toString())
                .remunMonthlyAmount(i[20] == null ? 0 : ((BigDecimal) i[20]).intValue())
                .remunMonthlyAmountKind(i[21] == null ? 0 : ((BigDecimal) i[21]).intValue())
                .totalMonthyRemun(i[22] == null ? 0 : ((BigDecimal) i[22]).intValue())
                .percentOrMore(i[23] == null ? 0 : ((BigDecimal) i[23]).intValue())
                .isMoreEmp(i[24] == null ? 0 : ((BigDecimal) i[24]).intValue())
                .shortTimeWorkes(i[25] == null ? 0 : ((BigDecimal) i[25]).intValue())
                .continReemAfterRetirement(i[26] == null ? 0 : ((BigDecimal) i[26]).intValue())
                .remarksAndOtherContent(i[27] == null ? 0 : ((BigDecimal) i[27]).intValue())
                .healPrefectureNo(i[28] == null ? 0 : ((BigDecimal) i[28]).intValue())
                .welPrefectureNo(i[28] == null ? 0 : ((BigDecimal) i[28]).intValue())
                .healInsCtg(i[29] == null ? 0 : ((BigDecimal) i[29]).intValue())
                .distin(i[29] == null ? "" : i[29].toString())
                .build()
        ).collect(Collectors.toList());
    }
}
