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

    @Override
    public List<PensionOfficeDataExport> getDataExportCSV(List<String> empIds, String cid, GeneralDate startDate, GeneralDate endDate) {
        List<Object[]> resultQuery ;
        StringBuilder exportSQL = new StringBuilder();
        exportSQL.append(" SELECT ");
        exportSQL.append("      HEALTH_INSURANCE_OFFICE_NUMBER_1,");
        exportSQL.append("      WELFARE_PENSION_OFFICE_NUMBER_1,");
        exportSQL.append("      HEALTH_INSURANCE_OFFICE_NUMBER_2,");
        exportSQL.append("      WELFARE_PENSION_OFFICE_NUMBER_2,");
        exportSQL.append("      HEALTH_INSURANCE_OFFICE_NUMBER,");
        exportSQL.append("      WELFARE_PENSION_OFFICE_NUMBER,");
        exportSQL.append("      COAL_MINER_ATR,");
        exportSQL.append("      NO_MYNUM_ATR,");
        exportSQL.append("      NO_MYNUM_ATR,");
        exportSQL.append("      NO_MYNUM_ATR,");
        exportSQL.append("      NO_MYNUM_REASON,");
        exportSQL.append("      qi.START_DATE,");
        exportSQL.append("      pi.START_DATE,");
        exportSQL.append("      RPT_SUBMIT_ATR,");
        exportSQL.append("      HOSYU_CURR,");
        exportSQL.append("      HOSYU_IN_KIND,");
        exportSQL.append("      HOSYU_MONTHLY,");
        exportSQL.append("      OVER_70_ATR,");
        exportSQL.append("      MULTI_OFFICE_ATR,");
        exportSQL.append("      SHORTTIME_WORKERS_ATR,");
        exportSQL.append("      CONTINUE_REEMPLOYED_ATR,");
        exportSQL.append("      BIKO_SONOTA_REASON,");
        exportSQL.append("      HEALTH_INSURANCE_PREFECTURE_NO,");
        exportSQL.append("      WELFARE_PENSION_PREFECTURE_NO,");
        exportSQL.append("      SAME_KENHO_ATR,");
        exportSQL.append("      SYAHO_GET_ATR,");
        exportSQL.append("      POSTAL_CODE,");
        exportSQL.append("      ADDRESS_1,");
        exportSQL.append("      ADDRESS_2,");
        exportSQL.append("      ADDRESS_KANA_1,");
        exportSQL.append("      ADDRESS_KANA_2,");
        exportSQL.append("      KNKUM_ITEM,");
        exportSQL.append("      KNKUM_NUM,");
        exportSQL.append("      oi.NAME, ");
        exportSQL.append("      oi.PHONE_NUMBER, ");
        exportSQL.append("      oi.REPRESENTATIVE_NAME, ");
        exportSQL.append("      qi.SID");
        exportSQL.append("  FROM");
        exportSQL.append("      (SELECT *");
        exportSQL.append("         FROM QQSDT_KENHO_INFO ");
        exportSQL.append("         WHERE END_DATE <= ?endDate AND END_DATE >= ?startDate AND CID = ?cid ");
        exportSQL.append("         AND SID IN ('%s') )qi");
        exportSQL.append("  LEFT JOIN ");
        exportSQL.append("       (SELECT DISTINCT CID, SID, SYAHO_OFFICE_CD  ");
        exportSQL.append("       FROM QQSDT_SYAHO_OFFICE_INFO ");
        exportSQL.append("       WHERE END_DATE <= ?endDate AND END_DATE >= ?startDate AND CID = ?cid ) his");
        exportSQL.append("       ON qi.SID = his.SID");
        exportSQL.append("  LEFT JOIN (SELECT * ");
        exportSQL.append("          FROM QQSDT_KNKUM_INFO ");
        exportSQL.append("          WHERE END_DATE <= ?endDate AND END_DATE >= ?startDate  ) ppi" );
        exportSQL.append("          ON qi.SID = ppi.SID");
        exportSQL.append(" INNER JOIN  ");
        exportSQL.append("   (SELECT * ");
        exportSQL.append("    FROM QQSDT_KOUHO_INFO pi ");
        exportSQL.append("    WHERE ");
        exportSQL.append("       pi.START_DATE <= ?endDate ");
        exportSQL.append("      AND pi.START_DATE >= ?startDate AND CID = ?cid ) AS pi ");
        exportSQL.append(" ON  pi.SID = qi.SID");
        exportSQL.append("  LEFT JOIN ");
        exportSQL.append("       (SELECT * ");
        exportSQL.append("       FROM QQSDT_KIKIN_INFO ");
        exportSQL.append("       WHERE START_DATE <= ?endDate AND START_DATE >= ?startDate AND CID = ?cid) ti");
        exportSQL.append("       ON qi.SID = ti.SID");
        exportSQL.append("  LEFT JOIN ");
        exportSQL.append("       (SELECT * ");
        exportSQL.append("       FROM QQSDT_SYAHO_GET_INFO ");
        exportSQL.append("       WHERE CID = ?cid) ii");
        exportSQL.append("       ON qi.SID = ii.SID");
        exportSQL.append("  LEFT JOIN QQSDT_SYAHO_MULTI_OFFICE mi ON mi.SID = qi.SID");
        exportSQL.append("  INNER JOIN (SELECT * ");
        exportSQL.append("        FROM QPBMT_SOCIAL_INS_OFFICE");
        exportSQL.append("        WHERE CID = ?cid) oi");
        exportSQL.append("        ON oi.CODE = his.SYAHO_OFFICE_CD");
        exportSQL.append("   LEFT JOIN QQSDT_KNKUM_EGOV_INFO iu ON qi.SID = iu.SID AND iu.CID = ?cid ");
        String emp = empIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining("','"));
        String sql = String.format(exportSQL.toString(), emp, emp);
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
                .underDivision(Integer.valueOf(i[6].toString()))
                .livingAbroad(i[7] == null ? 0 : ((BigDecimal) i[7]).intValue())
                .shortStay(i[8] == null ? 0 : ((BigDecimal) i[8]).intValue())
                .resonOther(i[9] == null ? 0 : ((BigDecimal) i[9]).intValue())
                .resonAndOtherContent(i[10] == null ? "" : i[10].toString())
                .startDate1(i[11] == null ? "" : i[11].toString())
                .startDate2(i[12] == null ? "" : i[12].toString())
                .depenAppoint(i[13] == null ? "" : i[13].toString())
                .remunMonthlyAmount(i[14] == null ? 0 : ((BigDecimal) i[14]).intValue())
                .remunMonthlyAmountKind(i[15] == null ? 0 :((BigDecimal)i[15]).intValue())
                .totalMonthyRemun(i[16] == null ? 0 : ((BigDecimal)i[16]).intValue())
                .percentOrMore(i[17] == null ? 0 : ((BigDecimal)i[17]).intValue())
                .isMoreEmp(i[18] == null ? 0 : ((BigDecimal)i[18]).intValue())
                .shortTimeWorkes(i[19] == null ? 0 : ((BigDecimal)i[19]).intValue())
                .continReemAfterRetirement(i[20] == null ? 0 : ((BigDecimal)i[20]).intValue())
                .remarksAndOtherContent(i[21] == null ? "" : i[21].toString())
                .healPrefectureNo(i[22] == null ? 0 : ((BigDecimal) i[22]).intValue())
                .welPrefectureNo(i[23] == null ? 0 : ((BigDecimal) i[23]).intValue())
                .healInsCtg(i[24] == null ? 0 : ((BigDecimal) i[24]).intValue())
                .distin(i[25] == null ? "" : i[25].toString())
                .portCd(i[26] == null ? "" : i[26].toString())
                .add(i[27] != null ? i[27].toString() : "" + " " + (i[28] != null ? i[28].toString() : ""))
                .addKana(i[29] != null ? i[29].toString() : "" + " " + (i[30] != null ? i[30].toString() : ""))
                .healInsInherenPr(i[31] == null ? "" : i[31].toString())
                .healUnionNumber(i[32] == null ? "" : i[32].toString())
                .companyName(i[33] == null ? "" : i[33].toString())
                .phoneNumber(i[34] == null ? "" : i[34].toString())
                .repName(i[35] == null ? "" : i[35].toString())
                .sid(i[36].toString())
                .build()
        ).collect(Collectors.toList());
    }

    @Override
    public List<Object[]> getDataExport(List<String> empIds, String cid,String userId, GeneralDate startDate, GeneralDate endDate) {

        List<Object[]> resultQuery ;
        StringBuilder exportSQL = new StringBuilder();
        exportSQL.append(" SELECT");
        exportSQL.append("  QSINS.OUTPUT_UNIT_ATR,");
        exportSQL.append("  QSIO.NAME,");
        exportSQL.append("  QSIO.ADDRESS_1,");
        exportSQL.append("  QSIO.ADDRESS_2,");
        exportSQL.append("  QSIO.PHONE_NUMBER,");
        exportSQL.append("  QSIO.POSTAL_CODE,");
        exportSQL.append("  QSIO.REPRESENTATIVE_NAME,");
        exportSQL.append("  QSINS.SUBMIT_NAME_ATR,");
        exportSQL.append("  QSINS.OUTPUT_OFFICE_SRNUM,");
        exportSQL.append("  QSIO.HEALTH_INSURANCE_OFFICE_NUMBER_1,");
        exportSQL.append("  QSIO.HEALTH_INSURANCE_OFFICE_NUMBER_2,");
        exportSQL.append("  QSIO.WELFARE_PENSION_OFFICE_NUMBER_1,");
        exportSQL.append("  QSIO.WELFARE_PENSION_OFFICE_NUMBER_2,");
        exportSQL.append("  QEPI.HIST_ID,");
        exportSQL.append("  QEPI.COAL_MINER_ATR,");
        exportSQL.append("  QSII.SYAHO_GET_ATR,");
        exportSQL.append("  QEBPN.KISONEN_NUM,");
        exportSQL.append("  QEHIQ.START_DATE,");
        exportSQL.append("  QEPI.START_DATE,");
        exportSQL.append("  QEHIQ.START_DATE,");
        exportSQL.append("  QEPI.START_DATE,");
        exportSQL.append("  QSII.RPT_SUBMIT_ATR,");
        exportSQL.append("  QSII.HOSYU_CURR,");
        exportSQL.append("  QSII.HOSYU_IN_KIND,");
        exportSQL.append("  QSII.HOSYU_MONTHLY,");
        exportSQL.append("  QSII.OVER_70_ATR,");
        exportSQL.append("  QMEWI.MULTI_OFFICE_ATR,");
        exportSQL.append("  QSII.SHORTTIME_WORKERS_ATR,");
        exportSQL.append("  QSII.CONTINUE_REEMPLOYED_ATR,");
        exportSQL.append("  QSII.BIKO_SONOTA_ATR,");
        exportSQL.append("  QSII.BIKO_SONOTA_REASON,");
        exportSQL.append("  QSII.NO_MYNUM_ATR,");
        exportSQL.append("  QSII.NO_MYNUM_ATR,");
        exportSQL.append("  QSII.NO_MYNUM_ATR,");
        exportSQL.append("  QSII.NO_MYNUM_REASON, ");
        exportSQL.append("  QSIO.HEALTH_INSURANCE_OFFICE_NUMBER,");
        exportSQL.append("  QSIO.WELFARE_PENSION_OFFICE_NUMBER, ");
        exportSQL.append("  SYAHO_OFFICE_CD,");
        exportSQL.append("  ROOT.SID");
        exportSQL.append(" FROM ");
        exportSQL.append("   (SELECT DISTINCT CID, SID, SYAHO_OFFICE_CD  ");
        exportSQL.append("    FROM QQSDT_SYAHO_OFFICE_INFO QECOH ");
        exportSQL.append("    WHERE SID IN ('%s') ");
        exportSQL.append("      AND QECOH.START_DATE <= ?endDate ");
        exportSQL.append("      AND QECOH.START_DATE >= ?startDate ) AS ROOT ");
        exportSQL.append(" INNER JOIN QRSMT_SYAHO_RPT_SETTING QSINS ON QSINS.CID = ?cid  ");
        exportSQL.append(" AND QSINS.USER_ID = ?userId ");
        exportSQL.append(" LEFT JOIN QPBMT_SOCIAL_INS_OFFICE QSIO ");
        exportSQL.append(" ON QSIO.CID = ?cid AND QSIO.CODE = ROOT.SYAHO_OFFICE_CD ");
        exportSQL.append(" LEFT JOIN ");
        exportSQL.append("   (SELECT * ");
        exportSQL.append("    FROM QQSDT_KIKIN_INFO QTPPITEM ");
        exportSQL.append("    WHERE ");
        exportSQL.append("       QTPPITEM.START_DATE <= ?endDate ");
        exportSQL.append("      AND QTPPITEM.START_DATE >= ?startDate AND CID = ?cid ) AS QTPPI ");
        exportSQL.append(" ON QTPPI.SID = ROOT.SID ");
        exportSQL.append(" INNER JOIN  ");
        exportSQL.append("   (SELECT * ");
        exportSQL.append("    FROM QQSDT_KOUHO_INFO QEPITEM ");
        exportSQL.append("    WHERE ");
        exportSQL.append("       QEPITEM.START_DATE <= ?endDate ");
        exportSQL.append("      AND QEPITEM.START_DATE >= ?startDate AND CID = ?cid ) AS QEPI ");
        exportSQL.append(" ON  QEPI.SID = ROOT.SID");
        exportSQL.append(" LEFT JOIN QQSDT_SYAHO_GET_INFO QSII ");
        exportSQL.append(" ON QSII.SID = ROOT.SID AND QSII.CID =  QSIO.CID ");
        exportSQL.append(" LEFT JOIN QQSDT_SYAHO_KNEN_NUM QEBPN ");
        exportSQL.append(" ON QEBPN.SID = ROOT.SID ");
        exportSQL.append(" INNER JOIN ");
        exportSQL.append("   (SELECT * ");
        exportSQL.append("    FROM QQSDT_KENHO_INFO QEHIQTEM ");
        exportSQL.append("    WHERE QEHIQTEM.START_DATE <= ?endDate ");
        exportSQL.append("      AND QEHIQTEM.START_DATE >= ?startDate AND QEHIQTEM.CID = ?cid ) AS QEHIQ ");
        exportSQL.append(" ON QEHIQ.SID = ROOT.SID ");
        exportSQL.append(" LEFT JOIN QQSDT_SYAHO_MULTI_OFFICE QMEWI ");
        exportSQL.append(" ON QMEWI.SID = QSII.SID ");
        exportSQL.append("  ORDER BY SYAHO_OFFICE_CD");
        String emp = empIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining("','"));
        String sql = String.format(exportSQL.toString(), emp, emp);
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
        exportSQL.append("      COAL_MINER_ATR,");
        exportSQL.append("      NO_MYNUM_ATR,");
        exportSQL.append("      NO_MYNUM_ATR,");
        exportSQL.append("      NO_MYNUM_ATR,");
        exportSQL.append("      NO_MYNUM_REASON,");
        exportSQL.append("      qi.START_DATE,");
        exportSQL.append("      ni.START_DATE,");
        exportSQL.append("      RPT_SUBMIT_ATR,");
        exportSQL.append("      HOSYU_CURR,");
        exportSQL.append("      HOSYU_IN_KIND,");
        exportSQL.append("      HOSYU_MONTHLY,");
        exportSQL.append("      OVER_70_ATR,");
        exportSQL.append("      MULTI_OFFICE_ATR,");
        exportSQL.append("      SHORTTIME_WORKERS_ATR,");
        exportSQL.append("      CONTINUE_REEMPLOYED_ATR,");
        exportSQL.append("      BIKO_SONOTA_REASON,");
        exportSQL.append("      HEALTH_INSURANCE_PREFECTURE_NO,");
        exportSQL.append("      WELFARE_PENSION_PREFECTURE_NO,");
        exportSQL.append("      SAME_KENHO_ATR,     ");
        exportSQL.append("      SYAHO_GET_ATR,");
        exportSQL.append("      KNKUM_ITEM,");
        exportSQL.append("      KNKUM_NUM,");
        exportSQL.append("      HEALTH_INSURANCE_UNION_OFFICE_NUMBER, ");
        exportSQL.append("      ni.HIST_ID, ");
        exportSQL.append("      oi.PHONE_NUMBER, ");
        exportSQL.append("      QSINS.OUTPUT_OFFICE_SRNUM, ");
        exportSQL.append("      oi.NAME, ");
        exportSQL.append("      oi.REPRESENTATIVE_NAME, ");
        exportSQL.append("      oi.POSTAL_CODE, ");
        exportSQL.append("      oi.ADDRESS_1,");
        exportSQL.append("      oi.ADDRESS_2,");
        exportSQL.append("      qi.SID");
        exportSQL.append("   FROM    ");
        exportSQL.append("       (SELECT *");
        exportSQL.append("         FROM QQSDT_KENHO_INFO ");
        exportSQL.append("         WHERE START_DATE <= ?endDate AND START_DATE >= ?startDate AND CID = ?cid" );
        exportSQL.append("         AND SID IN ('%s') )qi ");
        exportSQL.append("   LEFT JOIN     ");
        exportSQL.append("       (SELECT *");
        exportSQL.append("       FROM QQSDT_KNKUM_INFO ");
        exportSQL.append("       WHERE START_DATE <= ?endDate AND START_DATE >= ?startDate) pri");
        exportSQL.append("       ON pri.SID = qi.SID");
        exportSQL.append("    LEFT JOIN ");
        exportSQL.append("       (SELECT DISTINCT CID, SID, SYAHO_OFFICE_CD  ");
        exportSQL.append("       FROM QQSDT_SYAHO_OFFICE_INFO ");
        exportSQL.append("       WHERE END_DATE <= ?endDate AND END_DATE >= ?startDate ) his");
        exportSQL.append("       ON qi.SID = his.SID");
        exportSQL.append("    LEFT JOIN ");
        exportSQL.append("       (SELECT * ");
        exportSQL.append("       FROM QQSDT_SYAHO_GET_INFO ");
        exportSQL.append("       WHERE CID = ?cid) ii");
        exportSQL.append("       ON qi.SID = ii.SID");
        exportSQL.append("    LEFT JOIN QQSDT_KNKUM_EGOV_INFO iu ON qi.SID = iu.SID AND iu.CID = ?cid ");
        exportSQL.append("    INNER JOIN  ");
        exportSQL.append("   (SELECT * ");
        exportSQL.append("    FROM QQSDT_KOUHO_INFO QEPITEM ");
        exportSQL.append("    WHERE ");
        exportSQL.append("       QEPITEM.START_DATE <= ?endDate ");
        exportSQL.append("      AND QEPITEM.START_DATE >= ?startDate AND CID = ?cid ) AS ni ");
        exportSQL.append(" ON  ni.SID = qi.SID");
        exportSQL.append("    LEFT JOIN (SELECT *");
        exportSQL.append("       FROM QQSDT_KIKIN_INFO ");
        exportSQL.append("       WHERE START_DATE <= ?endDate AND START_DATE >= ?startDate AND CID = ?cid ) ti");
        exportSQL.append("       ON ti.SID = qi.SID  ");
        exportSQL.append("   LEFT JOIN QQSDT_SYAHO_MULTI_OFFICE wf ON wf.SID = qi.SID");
        exportSQL.append("   INNER JOIN (SELECT * ");
        exportSQL.append("        FROM QPBMT_SOCIAL_INS_OFFICE");
        exportSQL.append("        WHERE CID = ?cid) oi");
        exportSQL.append("        ON oi.CODE = his.SYAHO_OFFICE_CD");
        exportSQL.append("  INNER JOIN QRSMT_SYAHO_RPT_SETTING QSINS ON QSINS.CID = ?cid ");
        exportSQL.append("  AND QSINS.USER_ID = ?userId ");
        String emp = empIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining("','"));
        String sql = String.format(exportSQL.toString(), emp, emp);
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
                .underDivision(Integer.valueOf(i[6].toString()))
                .livingAbroad(i[7] == null ? 0 : ((BigDecimal) i[7]).intValue())
                .shortStay(i[8] == null ?  0 : ((BigDecimal) i[8]).intValue())
                .resonOther(i[9] == null ?  0 : ((BigDecimal) i[9]).intValue())
                .resonAndOtherContent(i[10] == null ? "" : i[10].toString())
                .startDate1(i[11] == null ? "" : i[11].toString())
                .startDate2(i[12] == null ? "" : i[12].toString())
                .depenAppoint(i[13] == null ? "" : i[13].toString())
                .remunMonthlyAmount(i[14] == null ? 0 : ((BigDecimal) i[14]).intValue())
                .remunMonthlyAmountKind(i[15] == null ? 0 : ((BigDecimal) i[15]).intValue())
                .totalMonthyRemun(i[16] == null ? 0 : ((BigDecimal) i[16]).intValue())
                .percentOrMore(i[17] == null ? 0 : ((BigDecimal) i[17]).intValue())
                .isMoreEmp(i[18] == null ? 0 : ((BigDecimal) i[18]).intValue())
                .shortTimeWorkes(i[19] == null ? 0 : ((BigDecimal) i[19]).intValue())
                .continReemAfterRetirement(i[20] == null ? 0 : ((BigDecimal) i[20]).intValue())
                .remarksAndOtherContent(i[21] == null ? "" : i[21].toString())
                .healPrefectureNo(i[22] == null ? 0 : ((BigDecimal) i[22]).intValue())
                .welPrefectureNo(i[23] == null ? 0 : ((BigDecimal) i[23]).intValue())
                .healInsCtg(i[24] == null ? 0 : ((BigDecimal) i[24]).intValue())
                .distin(i[25] == null ? "" : i[25].toString())
                .healInsInherenPr(i[26] == null ? "" : i[26].toString())
                .healUnionNumber(i[27] == null ? "" : i[27].toString())
                .unionOfficeNumber(i[28] == null ? "" : i[28].toString())
                .hisId(i[29] == null ? "" : i[29].toString())
                .phoneNumber(i[30] == null ? "" : i[30].toString())
                .bussinesArrSybol(Integer.valueOf(i[31].toString()))
                .companyName(i[32] == null ? "" : i[32].toString())
                .repName(i[33] == null ? "" : i[33].toString())
                .portCd(i[34] == null ? "" : i[34].toString())
                .add(i[35] != null ? i[35].toString() : "" + " " + (i[36] != null ? i[36].toString() : ""))
                .sid(i[37].toString())
                .build()
        ).collect(Collectors.toList());
    }

    public List<EmpPenFundSubData> getDataEmpPensionFund(List<String> empIds, String cid, GeneralDate startDate, GeneralDate endDate) {
        List<Object[]> resultQuery;
        StringBuilder exportSQL = new StringBuilder();
        exportSQL.append(" SELECT ");
        exportSQL.append("      SPECIFIC_ITEM_1,");
        exportSQL.append("      SPECIFIC_ITEM_2,");
        exportSQL.append("      SPECIFIC_ITEM_3,");
        exportSQL.append("      SPECIFIC_ITEM_4,");
        exportSQL.append("      SPECIFIC_ITEM_5,");
        exportSQL.append("      SPECIFIC_ITEM_6,");
        exportSQL.append("      SPECIFIC_ITEM_7,");
        exportSQL.append("      SPECIFIC_ITEM_8,");
        exportSQL.append("      SPECIFIC_ITEM_9,");
        exportSQL.append("      SPECIFIC_ITEM_10,");
        exportSQL.append("      WELFARE_PENSION_FUND_NUMBER,");
        exportSQL.append("      WELFARE_PENSION_OFFICE_NUMBER_1,");
        exportSQL.append("      WELFARE_PENSION_OFFICE_NUMBER_2,");
        exportSQL.append("      KOUHO_NU,");
        exportSQL.append("      KNKUM_NUM,");
        exportSQL.append("      KIKIN_NUM,");
        exportSQL.append("      WELFARE_PENSION_OFFICE_NUMBER,");
        exportSQL.append("      WELFARE_PENSION_PREFECTURE_NO,");
        exportSQL.append("      qi.START_DATE,");
        exportSQL.append("      ADDRESS_KN_AFTER_RETIRE,");
        exportSQL.append("      ADDRESS_AFTER_RETIRE,");
        exportSQL.append("      LOSS_REASON_ATR,");
        exportSQL.append("      S_ADDITION_ATR,");
        exportSQL.append("      END_REASON_ATR,");
        exportSQL.append("      S_ADD_MONTHLY_AMOUNT_1,");
        exportSQL.append("      S_SRD_MONTHLY_AMOUNT_1,");
        exportSQL.append("      S_ADD_MONTHLY_AMOUNT_2,");
        exportSQL.append("      S_SRD_MONTHLY_AMOUNT_2,");
        exportSQL.append("      LOSS_CASE_ATR,");
        exportSQL.append("      MULTI_OFFICE_ATR,");
        exportSQL.append("      OTHER_REASONS,");
        exportSQL.append("      CONTINUE_REEMPLOYED_ATR,");
        exportSQL.append("      KISONEN_NUM,");
        exportSQL.append("      COAL_MINER_ATR,");
        exportSQL.append("      SYAHO_GET_ATR,");
        exportSQL.append("      OVER_70_ATR,");
        exportSQL.append("      BIKO_SONOTA_ATR,");
        exportSQL.append("      HOSYU_IN_KIND,");
        exportSQL.append("      HOSYU_CURR,");
        exportSQL.append("      HOSYU_MONTHLY,");
        exportSQL.append("      NO_MYNUM_ATR,");
        exportSQL.append("      NO_MYNUM_ATR,");
        exportSQL.append("      NO_MYNUM_REASON,");
        exportSQL.append("      SHORTTIME_WORKERS_ATR,");
        exportSQL.append("      RPT_SUBMIT_ATR,");
        exportSQL.append("      ENTRY_TYPE_ATR,");
        exportSQL.append("      APPLY_TYPE_ATR,");
        exportSQL.append("      qi.HIST_ID, ");
        exportSQL.append("      oi.ADDRESS_1,");
        exportSQL.append("      oi.ADDRESS_2,");
        exportSQL.append("      oi.ADDRESS_KANA_1,");
        exportSQL.append("      oi.ADDRESS_KANA_2, ");
        exportSQL.append("      oi.NAME, ");
        exportSQL.append("      oi.PHONE_NUMBER, ");
        exportSQL.append("      oi.REPRESENTATIVE_NAME, ");
        exportSQL.append("      oi.POSTAL_CODE, ");
        exportSQL.append("      BIKO_SONOTA_REASON,");
        exportSQL.append("      his.SID");
        exportSQL.append("  FROM ");
        exportSQL.append("         (SELECT *");
        exportSQL.append("         FROM QQSDT_KOUHO_INFO ");
        exportSQL.append("         WHERE START_DATE <= ?endDate AND START_DATE >= ?startDate");
        exportSQL.append("         AND SID IN ('%s') )qi");
        exportSQL.append("  INNER JOIN ");
        exportSQL.append("       (SELECT DISTINCT CID, SID, SYAHO_OFFICE_CD  ");
        exportSQL.append("       FROM QQSDT_SYAHO_OFFICE_INFO ");
        exportSQL.append("       WHERE START_DATE <= ?endDate AND START_DATE >= ?startDate ) his");
        exportSQL.append("       ON qi.SID = his.SID");
        exportSQL.append("  INNER JOIN QQSDT_KOUHO_LOSS_INFO loss on loss.SID = qi.SID");
        exportSQL.append("  INNER JOIN (SELECT *  ");
        exportSQL.append("        FROM QQSDT_KIKIN_INFO");
        exportSQL.append("        WHERE START_DATE <= ?endDate AND START_DATE >= ?startDate AND CID = ?cid) pi ");
        exportSQL.append("        ON qi.SID = pi.SID");
        exportSQL.append("  INNER JOIN (SELECT * ");
        exportSQL.append("        FROM QPBMT_SOCIAL_INS_OFFICE");
        exportSQL.append("        WHERE CID = ?cid) oi");
        exportSQL.append("        ON oi.CODE = his.SYAHO_OFFICE_CD");
        exportSQL.append("  INNER JOIN QQSDT_KIKIN_EGOV_INFO fi ON fi.SID = qi.SID AND fi.CID = ?cid ");
        exportSQL.append("  INNER JOIN (SELECT *");
        exportSQL.append("       FROM QQSDT_KNKUM_INFO ");
        exportSQL.append("       WHERE START_DATE <= ?endDate AND START_DATE >= ?startDate AND CID = ?cid) pri");
        exportSQL.append("       ON pri.SID = qi.SID");
        exportSQL.append("  LEFT JOIN QQSDT_SYAHO_MULTI_OFFICE mi ON mi.SID = qi.SID AND mi.CID = qi.CID");
        exportSQL.append("  LEFT JOIN (SELECT * ");
        exportSQL.append("       FROM QQSDT_SYAHO_GET_INFO");
        exportSQL.append("        WHERE CID = ?cid) ii ON ii.SID = qi.SID");
        exportSQL.append("  LEFT JOIN QQSDT_SYAHO_KNEN_NUM bp ON bp.SID = qi.SID AND bp.CID = qi.CID");

        String emp = empIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining("','"));
        String sql = String.format(exportSQL.toString(), emp, emp);
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
                .retirementAddBefore(i[19] == null ? "" : i[19].toString())
                .retirementAdd(i[20] == null ? "" : i[20].toString())
                .reasonForLoss(i[21] == null ? "" : i[21].toString())
                .addAppCtgSal(i[22] == null ? "" : i[22].toString())
                .reason(i[23] == null ? "" : i[23].toString())
                .addSal(i[24] == null ? "" : i[24].toString())
                .standSal(i[25] == null ? "" : i[25].toString())
                .secAddSalary(i[26] == null ? "" : i[26].toString())
                .secStandSal(i[27] == null ? "" : i[27].toString())
                .cause(i[28] == null ? 0 : ((BigDecimal)i[28]).intValue())
                .isMoreEmp(i[29] == null ? "" : i[29].toString())
                .otherReason(i[30] == null ? "" : i[30].toString())
                .continReemAfterRetirement(i[31] == null ? "" : i[31].toString())
                .basicPenNumber(i[32] == null ? "" : i[32].toString())
                .underDivision(Integer.valueOf(i[33].toString()))
                .qualifiDistin(i[34] == null ? "" : i[34].toString())
                .percentOrMore(i[35] == null ? 0 : ((BigDecimal)i[35]).intValue())
                .remarksOther(i[36] == null ? 0 : ((BigDecimal)i[36]).intValue())
                .remunMonthlyAmountKind(i[37] == null ? 0 : ((BigDecimal)i[37]).intValue())
                .remunMonthlyAmount(i[38] == null ? 0 : ((BigDecimal)i[38]).intValue())
                .totalMonthlyRemun(i[39] == null ? 0 : ((BigDecimal)i[39]).intValue())
                .livingAbroad(i[40] == null ? 0 : ((BigDecimal)i[40]).intValue())
                .reasonOther(i[41] == null ? 0 : ((BigDecimal)i[41]).intValue())
                .reasonAndOtherContents(i[42] == null ? "" : i[42].toString())
                .shortStay(i[43] == null ? 0 : ((BigDecimal)i[43]).intValue())
                .depenAppoint(i[44] == null ? 0 : ((BigDecimal)i[44]).intValue())
                .subType(i[45] == null ?  0 : ((BigDecimal)i[45]).intValue())
                .appFormCls(i[46] == null ? 0 : ((BigDecimal)i[46]).intValue())
                .hisId(i[47] == null ? "" : i[47].toString())
                .add(i[48] != null ? i[48].toString() : "" + " " + (i[49] != null ? i[49].toString() : ""))
                .addKana(i[50] != null ? i[50].toString() : "" + " " + (i[51] != null ? i[51].toString() : ""))
                .companyName(i[52] == null ? "" : i[52].toString())
                .phoneNumber(i[53] == null ? "" : i[53].toString())
                .repName(i[54] == null ? "" : i[54].toString())
                .portCd(i[55] == null ? "" : i[55].toString())
                .remarksAndOtherContents(i[56] == null ? "" : i[56].toString())
                .sid(i[57].toString())
                .build()
        ).collect(Collectors.toList());
    }

}
