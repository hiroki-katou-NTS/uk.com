package nts.uk.file.pr.infra.core.socinsurnoticreset;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.EmpPenFundSubData;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.GuaByTheInsurExportRepository;
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
        exportSQL.append("      BIRTHDAY,");
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
        exportSQL.append("      QUALIFI_DISTIN,");
        exportSQL.append("      POSTAL_CODE,");
        exportSQL.append("      ADDRESS_1,");
        exportSQL.append("      ADDRESS_2,");
        exportSQL.append("      ADDRESS_KANA_1,");
        exportSQL.append("      ADDRESS_KANA_2,");
        exportSQL.append("      HEAL_INSUR_INHEREN_PR,");
        exportSQL.append("      HEAL_INSUR_UNION_NMBER");
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
        exportSQL.append("        ) wi");
        exportSQL.append("       ON qi.EMPLOYEE_ID = wi.EMPLOYEE_ID");
        exportSQL.append("  LEFT JOIN (SELECT * ");
        exportSQL.append("          FROM QQSMT_HEAL_INSUR_PORT_INT ");
        exportSQL.append("          WHERE END_DATE <= ?endDate AND END_DATE >= ?startDate  ) ppi" );
        exportSQL.append("          ON qi.EMPLOYEE_ID = ppi.EMPLOYEE_ID");
        exportSQL.append("  INNER JOIN  QQSMT_EMP_PEN_INS pi ON pi.HISTORY_ID = wi.HISTORY_ID AND qi.EMPLOYEE_ID = pi.EMPLOYEE_ID");
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
        exportSQL.append("    LEFT JOIN QQSMT_EMP_HEAL_INS_UNION iu ON qi.EMPLOYEE_ID = iu.EMPLOYEE_ID");
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
                .gender(Integer.valueOf(i[11].toString()))
                .underDivision(Integer.valueOf(i[12].toString()))
                .livingAbroad(i[13] == null ? 0 : ((BigDecimal) i[13]).intValue())
                .shortStay(i[14] == null ? 0 : ((BigDecimal) i[14]).intValue())
                .resonOther(i[15] == null ? 0 : ((BigDecimal) i[15]).intValue())
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
                .remarksAndOtherContent(i[27] == null ? "" : i[27].toString())
                .healPrefectureNo(i[28] == null ? 0 : ((BigDecimal) i[28]).intValue())
                .welPrefectureNo(i[29] == null ? 0 : ((BigDecimal) i[29]).intValue())
                .healInsCtg(i[30] == null ? 0 : ((BigDecimal) i[30]).intValue())
                .distin(i[31] == null ? "" : i[31].toString())
                .portCd(i[32] == null ? "" : i[32].toString())
                .add(i[33] == null && i[34] == null ? "" : i[33].toString()+ " " + i[34].toString())
                .addKana(i[35] == null && i[36] == null ? "" : i[35].toString()+ " " + i[36].toString())
                .healInsInherenPr(i[37] == null ? "" : i[37].toString())
                .healUnionNumber(i[38] == null ? "" : i[38].toString())
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
        exportSQL.append("  QSIO.WELFARE_PENSION_OFFICE_NUMBER, ");
        exportSQL.append("  SOCIAL_INSURANCE_OFFICE_CD");
        exportSQL.append(" FROM ");
        exportSQL.append("   (SELECT * ");
        exportSQL.append("    FROM QQSMT_EMP_CORP_OFF_HIS QECOH ");
        exportSQL.append("    WHERE EMPLOYEE_ID IN ('%s') ");
        exportSQL.append("      AND QECOH.START_DATE <= ?endDate ");
        exportSQL.append("      AND QECOH.START_DATE >= ?startDate  ) AS ROOT ");
        exportSQL.append(" INNER JOIN QQSMT_SOC_INSU_NOTI_SET QSINS ON QSINS.CID = ?cid  ");
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
        exportSQL.append("       QTPPITEM.START_DATE <= ?endDate ");
        exportSQL.append("      AND QTPPITEM.START_DATE >= ?startDate ) AS QTPPI ");
        exportSQL.append(" ON QTPPI.EMPLOYEE_ID = ROOT.EMPLOYEE_ID ");
        exportSQL.append(" LEFT JOIN ");
        exportSQL.append("   (SELECT * ");
        exportSQL.append("    FROM QQSMT_EMP_WELF_INS_QC_IF QEWIQITEMP ");
        exportSQL.append("    WHERE QEWIQITEMP.START_DATE <= ?endDate ");
        exportSQL.append("      AND QEWIQITEMP.START_DATE >= ?startDate ) AS QEWIQI ");
        exportSQL.append(" ON QEWIQI.EMPLOYEE_ID = ROOT.EMPLOYEE_ID ");
        exportSQL.append(" INNER JOIN  QQSMT_EMP_PEN_INS QEPI  ON QEPI.HISTORY_ID = QEWIQI.HISTORY_ID AND QEPI.EMPLOYEE_ID = ROOT.EMPLOYEE_ID ");
        exportSQL.append(" LEFT JOIN QQSMT_SOC_ISACQUISI_INFO QSII ");
        exportSQL.append(" ON QSII.EMPLOYEE_ID = ROOT.EMPLOYEE_ID AND QSII.CID =  QSIO.CID ");
        exportSQL.append(" LEFT JOIN QQSMT_EMP_BA_PEN_NUM QEBPN ");
        exportSQL.append(" ON QEBPN.EMPLOYEE_ID = ROOT.EMPLOYEE_ID ");
        exportSQL.append(" INNER JOIN ");
        exportSQL.append("   (SELECT * ");
        exportSQL.append("    FROM QQSMT_EMP_HEAL_INSUR_QI QEHIQTEM ");
        exportSQL.append("    WHERE QEHIQTEM.START_DATE <= ?endDate ");
        exportSQL.append("      AND QEHIQTEM.START_DATE >= ?startDate ) AS QEHIQ ");
        exportSQL.append(" ON QEHIQ.EMPLOYEE_ID = ROOT.EMPLOYEE_ID ");
        exportSQL.append(" LEFT JOIN QQSMT_MULTI_EMP_WORK_IF QMEWI ");
        exportSQL.append(" ON QMEWI.EMPLOYEE_ID = ROOT.EMPLOYEE_ID ");

        exportSQL.append("  ORDER BY SOCIAL_INSURANCE_OFFICE_CD, SCD");
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

    public List<PensionOfficeDataExport> getDataHealthInsAss(List<String> empIds,String cid,String userId, GeneralDate startDate, GeneralDate endDate) {
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
        exportSQL.append("      QUALIFI_DISTIN,");
        exportSQL.append("      HEAL_INSUR_INHEREN_PR,");
        exportSQL.append("      HEAL_INSUR_UNION_NMBER,");
        exportSQL.append("      HEALTH_INSURANCE_UNION_OFFICE_NUMBER, ");
        exportSQL.append("      wi.HISTORY_ID, ");
        exportSQL.append("      oi.PHONE_NUMBER, ");
        exportSQL.append("      QSINS.BUSSINESS_ARR_SYMBOL ");
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
        exportSQL.append("    INNER JOIN  QQSMT_EMP_PEN_INS ni ON  ni.HISTORY_ID = wi.HISTORY_ID AND ni.EMPLOYEE_ID = qi.EMPLOYEE_ID");
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
        exportSQL.append("  INNER JOIN QQSMT_SOC_INSU_NOTI_SET QSINS ON QSINS.CID = ?cid ");
        exportSQL.append("  AND QSINS.USER_ID = ?userId ");
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
                .gender(Integer.valueOf(i[11].toString()))
                .underDivision(Integer.valueOf(i[12].toString()))
                .livingAbroad(i[13] == null ? 0 : ((BigDecimal) i[13]).intValue())
                .shortStay(i[14] == null ?  0 : ((BigDecimal) i[14]).intValue())
                .resonOther(i[15] == null ?  0 : ((BigDecimal) i[15]).intValue())
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
                .remarksAndOtherContent(i[27] == null ? "" : i[27].toString())
                .healPrefectureNo(i[28] == null ? 0 : ((BigDecimal) i[28]).intValue())
                .welPrefectureNo(i[29] == null ? 0 : ((BigDecimal) i[29]).intValue())
                .healInsCtg(i[30] == null ? 0 : ((BigDecimal) i[30]).intValue())
                .distin(i[31] == null ? "" : i[31].toString())
                .healInsInherenPr(i[32] == null ? "" : i[32].toString())
                .healUnionNumber(i[33] == null ? "" : i[33].toString())
                .unionOfficeNumber(i[34] == null ? "" : i[34].toString())
                .hisId(i[35] == null ? "" : i[35].toString())
                .phoneNumber(i[36] == null ? "" : i[36].toString())
                .bussinesArrSybol(Integer.valueOf(i[37].toString()))
                .build()
        ).collect(Collectors.toList());
    }

    public List<EmpPenFundSubData> getDataEmpPensionFund(List<String> empIds, String cid, GeneralDate startDate, GeneralDate endDate) {
        List<Object[]> resultQuery;
        StringBuilder exportSQL = new StringBuilder();
        exportSQL.append(" SELECT ");
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
        exportSQL.append("      ni.WEL_PEN_NUMBER,");
        exportSQL.append("      HEAL_INSUR_UNION_NMBER,");
        exportSQL.append("      MEMBER_NUMBER,");
        exportSQL.append("      WELFARE_PENSION_OFFICE_NUMBER,");
        exportSQL.append("      WELFARE_PENSION_PREFECTURE_NO,");
        exportSQL.append("      qi.START_DATE,");
        exportSQL.append("      BUSINESS_NAME,");
        exportSQL.append("      BUSINESS_NAME_KANA,");
        exportSQL.append("      PERSON_NAME,");
        exportSQL.append("      PERSON_NAME_KANA,");
        exportSQL.append("      BIRTHDAY,");
        exportSQL.append("      POST_CD,");
        exportSQL.append("      RETIREMENT_ADD_BEFORE,");
        exportSQL.append("      RETIREMENT_ADD,");
        exportSQL.append("      REASON_FOR_LOSS,");
        exportSQL.append("      ADD_APP_CTG_SAL,");
        exportSQL.append("      REASON,");
        exportSQL.append("      ADD_SAL,");
        exportSQL.append("      STAND_SAL,");
        exportSQL.append("      SEC_ADD_SALARY,");
        exportSQL.append("      SEC_STAND_SAL,");
        exportSQL.append("      CAUSE,");
        exportSQL.append("      IS_MORE_EMP,");
        exportSQL.append("      OTHER_REASON,");
        exportSQL.append("      CONTIN_REEM_AFTER_RETIREMENT,");
        exportSQL.append("      BASIC_PEN_NUMBER,");
        exportSQL.append("      GENDER,");
        exportSQL.append("      ni.UNDERGOUND_DIVISION,");
        exportSQL.append("      QUALIFI_DISTIN,");
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
        exportSQL.append("      DEPEN_APPOINT,");
        exportSQL.append("      SUB_TYPE,");
        exportSQL.append("      APP_FORM_CLS,");
        exportSQL.append("      qi.HISTORY_ID, ");
        exportSQL.append("      oi.ADDRESS_1,");
        exportSQL.append("      oi.ADDRESS_2,");
        exportSQL.append("      oi.ADDRESS_KANA_1,");
        exportSQL.append("      oi.ADDRESS_KANA_2 ");
        exportSQL.append("  FROM ");
        exportSQL.append("         (SELECT *");
        exportSQL.append("         FROM QQSMT_EMP_WELF_INS_QC_IF ");
        exportSQL.append("         WHERE START_DATE <= ?endDate AND START_DATE >= ?startDate");
        exportSQL.append("         AND EMPLOYEE_ID IN ('%s') )qi");
        exportSQL.append("  INNER JOIN ");
        exportSQL.append("       (SELECT * ");
        exportSQL.append("       FROM QQSMT_EMP_CORP_OFF_HIS ");
        exportSQL.append("       WHERE START_DATE <= ?endDate AND START_DATE >= ?startDate) his");
        exportSQL.append("       ON qi.EMPLOYEE_ID = his.EMPLOYEE_ID");
        exportSQL.append("  INNER JOIN QQSMT_WELF_PEN_INS_LOSS loss on loss.EMP_ID = qi.EMPLOYEE_ID");
        exportSQL.append("  INNER JOIN (SELECT *  ");
        exportSQL.append("        FROM QQSMT_TEM_PEN_PART_INFO");
        exportSQL.append("        WHERE START_DATE <= ?endDate AND START_DATE >= ?startDate) pi ");
        exportSQL.append("        ON qi.EMPLOYEE_ID = pi.EMPLOYEE_ID");
        exportSQL.append("  INNER JOIN (SELECT * ");
        exportSQL.append("        FROM QPBMT_SOCIAL_INS_OFFICE");
        exportSQL.append("        WHERE CID = ?cid) oi");
        exportSQL.append("        ON oi.CODE = his.SOCIAL_INSURANCE_OFFICE_CD");
        exportSQL.append("  INNER JOIN QQSMT_EMPL_PEN_FUND_INFO fi ON fi.EMPLOYEE_ID = qi.EMPLOYEE_ID");
        exportSQL.append("   INNER JOIN (SELECT * ");
        exportSQL.append("        FROM BSYMT_EMP_DTA_MNG_INFO ");
        exportSQL.append("        WHERE CID = ?cid) i");
        exportSQL.append("        ON i.SID = qi.EMPLOYEE_ID");
        exportSQL.append("  INNER JOIN BPSMT_PERSON p ON p.PID = i.PID");
        exportSQL.append("  INNER JOIN QQSMT_EMP_PEN_INS ni ON ni.HISTORY_ID = qi.HISTORY_ID AND qi.EMPLOYEE_ID = ni.EMPLOYEE_ID");
        exportSQL.append("  INNER JOIN (SELECT *");
        exportSQL.append("       FROM QQSMT_HEAL_INSUR_PORT_INT ");
        exportSQL.append("       WHERE START_DATE <= ?endDate AND START_DATE >= ?startDate) pri");
        exportSQL.append("       ON pri.EMPLOYEE_ID = qi.EMPLOYEE_ID");
        exportSQL.append("  LEFT JOIN QQSMT_MULTI_EMP_WORK_IF mi ON mi.EMPLOYEE_ID = qi.EMPLOYEE_ID");
        exportSQL.append("  LEFT JOIN (SELECT * ");
        exportSQL.append("       FROM QQSMT_SOC_ISACQUISI_INFO");
        exportSQL.append("        WHERE CID = ?cid) ii ON ii.EMPLOYEE_ID = qi.EMPLOYEE_ID");
        exportSQL.append("  LEFT JOIN QQSMT_EMP_BA_PEN_NUM bp ON bp.EMPLOYEE_ID = qi.EMPLOYEE_ID");
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
        return resultQuery.stream().map(i -> EmpPenFundSubData.builder()
                .funSpecific1(i[0] == null ? "" : i[0].toString())
                .funSpecific2(i[1] == null ? "" : i[1].toString())
                .funSpecific3(i[2] == null ? "" : i[2].toString())
                .funSpecific4(i[3] == null ? "" : i[3].toString())
                .funSpecific5(i[4] == null ? "" : i[4].toString())
                .funSpecific6(i[5] == null ? "" : i[5].toString())
                .funSpecific7(i[6] == null ? "" : i[6].toString())
                .funSpecific8(i[7] == null ? "" : i[7].toString())
                .funSpecific9(i[8] == null ? "" : i[8].toString())
                .funSpecific10(i[9] == null ? "" : i[9].toString())
                .funMember(i[10] == null ? "" : i[10].toString())
                .welOfficeNumber1(i[11] == null ? "" : i[11].toString())
                .welOfficeNumber2(i[12] == null ? "" : i[12].toString())
                .welNumber(i[13] == null ? "" : i[13].toString())
                .healInsUnionNumber(i[14] == null ? "" : i[14].toString())
                .memberNumber(i[15] == null ? "" : i[15].toString())
                .welPenOfficeNumber(i[16] == null ? "" : i[16].toString())
                .prefectureNo(i[17] == null ? 0 : ((BigDecimal)i[17]).intValue())
                .startDate(i[18] == null ? "" : i[18].toString())
                .personName(i[19] == null ? "" : i[19].toString())
                .personNameKana(i[20] == null ? "" : i[20].toString())
                .oldName(i[21] == null ? "" : i[21].toString())
                .oldNameKana(i[22] == null ? "" : i[22].toString())
                .birthDay(i[23] == null ? "" : i[23].toString())
                .portCd(i[24] == null ? "" : i[24].toString())
                .retirementAddBefore(i[25] == null ? "" : i[25].toString())
                .retirementAdd(i[26] == null ? "" : i[26].toString())
                .reasonForLoss(i[27] == null ? "" : i[27].toString())
                .addAppCtgSal(i[28] == null ? "" : i[28].toString())
                .reason(i[29] == null ? "" : i[29].toString())
                .addSal(i[30] == null ? "" : i[30].toString())
                .standSal(i[31] == null ? "" : i[31].toString())
                .secAddSalary(i[32] == null ? "" : i[32].toString())
                .secStandSal(i[33] == null ? "" : i[33].toString())
                .cause(i[34] == null ? 0 : ((BigDecimal)i[34]).intValue())
                .isMoreEmp(i[35] == null ? "" : i[35].toString())
                .otherReason(i[36] == null ? "" : i[36].toString())
                .continReemAfterRetirement(i[37] == null ? "" : i[37].toString())
                .basicPenNumber(i[38] == null ? "" : i[38].toString())
                .gender(Integer.valueOf(i[39].toString()))
                .underDivision(Integer.valueOf(i[40].toString()))
                .qualifiDistin(i[41] == null ? "" : i[41].toString())
                .percentOrMore(i[42] == null ? 0 : ((BigDecimal)i[42]).intValue())
                .remarksOther(i[43] == null ? 0 : ((BigDecimal)i[43]).intValue())
                .remarksAndOtherContents(i[44] == null ? "" : i[44].toString())
                .remunMonthlyAmountKind(i[45] == null ? 0 : ((BigDecimal)i[45]).intValue())
                .remunMonthlyAmount(i[46] == null ? 0 : ((BigDecimal)i[46]).intValue())
                .totalMonthlyRemun(i[47] == null ? 0 : ((BigDecimal)i[47]).intValue())
                .livingAbroad(i[48] == null ? 0 : ((BigDecimal)i[48]).intValue())
                .reasonOther(i[49] == null ? 0 : ((BigDecimal)i[49]).intValue())
                .reasonAndOtherContents(i[50] == null ? "" : i[50].toString())
                .shortStay(i[51] == null ? 0 : ((BigDecimal)i[51]).intValue())
                .depenAppoint(i[52] == null ? 0 : ((BigDecimal)i[52]).intValue())
                .subType(i[53] == null ?  0 : ((BigDecimal)i[53]).intValue())
                .appFormCls(i[54] == null ? 0 : ((BigDecimal)i[54]).intValue())
                .hisId(i[54] == null ? "" : i[54].toString())
                .add(i[55] == null ? "" : i[55].toString())
                .addKana(i[57] == null ? "" : i[57].toString())
                .build()
        ).collect(Collectors.toList());
    }

}
