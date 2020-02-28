package nts.uk.file.pr.infra.core.socinsurnoticreset;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.adapter.company.CompanyInfor;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.InsLossDataExport;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.NotificationOfLossInsExRepository;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.PensFundSubmissData;

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
    public List<InsLossDataExport> getHealthInsLoss(List<String> empIds, String cid, GeneralDate startDate, GeneralDate endDate) {
        try {
            List<Object[]> resultQuery = null;
            StringBuilder exportSQL = new StringBuilder();
            exportSQL.append("  SELECT his.SID,");
            exportSQL.append("      SYAHO_OFFICE_CD,");
            exportSQL.append("      loss.OTHER_ATR,");
            exportSQL.append("      loss.OTHER_REASONS,");
            exportSQL.append("      loss.NUM_OF_ATTACHED,");
            exportSQL.append("      loss.NUM_OF_UNCOLLECTABLE,");
            exportSQL.append("      loss.LOSS_CASE_ATR,");
            exportSQL.append("      OVER_70_ATR,");
            exportSQL.append("      BIKO_SONOTA_ATR,");
            exportSQL.append("      CONTINUE_REEMPLOYED_ATR,");
            exportSQL.append("      HOSYU_IN_KIND,");
            exportSQL.append("      HOSYU_CURR,");
            exportSQL.append("      HOSYU_MONTHLY,");
            exportSQL.append("      NO_MYNUM_ATR,");
            exportSQL.append("      NO_MYNUM_ATR,");
            exportSQL.append("      NO_MYNUM_REASON,");
            exportSQL.append("      SHORTTIME_WORKERS_ATR,");
            exportSQL.append("      NO_MYNUM_ATR,");
            exportSQL.append("      RPT_SUBMIT_ATR,");
            exportSQL.append("      SYAHO_GET_ATR,");
            exportSQL.append("      CONTINUE_REEMPLOYED_ATR,");
            exportSQL.append("      MULTI_OFFICE_ATR,");
            exportSQL.append("      KISONEN_NUM,");
            exportSQL.append("      qi.END_DATE,");
            exportSQL.append("      KENHO_NUM,");
            exportSQL.append("      HEALTH_INSURANCE_PREFECTURE_NO,");
            exportSQL.append("      HEALTH_INSURANCE_OFFICE_NUMBER_1,");
            exportSQL.append("      HEALTH_INSURANCE_OFFICE_NUMBER_2,");
            exportSQL.append("      HEALTH_INSURANCE_OFFICE_NUMBER,");
            exportSQL.append("      HEALTH_INSURANCE_UNION_OFFICE_NUMBER,");
            exportSQL.append("      WELFARE_PENSION_OFFICE_NUMBER_1,");
            exportSQL.append("      WELFARE_PENSION_OFFICE_NUMBER_2,");
            exportSQL.append("      WELFARE_PENSION_OFFICE_NUMBER,");
            exportSQL.append("      KOUHO_NU,");
            exportSQL.append("      KNKUM_NUM,");
            exportSQL.append("      KIKIN_NUM,");
            exportSQL.append("      KNKUM_ITEM,");
            exportSQL.append("      POSTAL_CODE,");
            exportSQL.append("      ADDRESS_1,");
            exportSQL.append("      ADDRESS_2,");
            exportSQL.append("      NAME,");
            exportSQL.append("      REPRESENTATIVE_NAME,");
            exportSQL.append("      PHONE_NUMBER,");
            exportSQL.append("      wloss.OTHER_ATR,");
            exportSQL.append("      wloss.OTHER_REASONS,");
            exportSQL.append("      wloss.NUM_OF_ATTACHED,");
            exportSQL.append("      wloss.NUM_OF_UNCOLLECTABLE,");
            exportSQL.append("      wloss.LOSS_CASE_ATR,");
            exportSQL.append("      wi.END_DATE,");
            exportSQL.append("      INSURED_ATR");
            exportSQL.append("  FROM ");
            exportSQL.append("         (SELECT *");
            exportSQL.append("         FROM QQSDT_KENHO_INFO ");
            exportSQL.append("         WHERE END_DATE <= ?endDate AND END_DATE >= ?startDate AND CID = ?cid");
            exportSQL.append("         AND SID IN ('%s') )qi");
            exportSQL.append("  FULL JOIN ");
            exportSQL.append("          (SELECT *");
            exportSQL.append("          FROM QQSDT_KOUHO_INFO ");
            exportSQL.append("          WHERE END_DATE <= ?endDate AND END_DATE >= ?startDate  AND CID = ?cid");
            exportSQL.append("         AND SID IN ('%s') ) wi");
            exportSQL.append("          ON qi.SID = wi.SID ");
            exportSQL.append("  INNER JOIN ");
            exportSQL.append("       (SELECT * ");
            exportSQL.append("       FROM QQSDT_SYAHO_OFFICE_INFO ");
            exportSQL.append("       WHERE END_DATE <= ?endDate AND END_DATE >= ?startDate AND CID = ?cid) his");
            exportSQL.append("       ON qi.SID = his.SID OR wi.SID = his.SID");
            exportSQL.append("  LEFT JOIN ");
            exportSQL.append("            (SELECT *");
            exportSQL.append("       FROM QQSDT_SYAHO_WORKFORM_INFO ");
            exportSQL.append("       WHERE END_YM <= ?endYm AND END_YM >= ?startYm AND CID = ?cid ) wh ");
            exportSQL.append("       ON wh.SID = qi.SID");
            exportSQL.append("  LEFT JOIN (SELECT * ");
            exportSQL.append("          FROM QQSDT_KNKUM_INFO ");
            exportSQL.append("          WHERE END_DATE <= ?endDate AND END_DATE >= ?startDate  AND CID = ?cid) pi");
            exportSQL.append("          ON qi.SID = pi.SID");
            exportSQL.append("  LEFT JOIN (SELECT *");
            exportSQL.append("          FROM QQSDT_KIKIN_INFO");
            exportSQL.append("          WHERE END_DATE <= ?endDate AND END_DATE >= ?startDate  AND CID = ?cid) ti");
            exportSQL.append("          ON qi.SID = ti.SID");
            exportSQL.append("  LEFT JOIN QQSDT_KENHO_LOSS_INFO loss ON loss.SID = his.SID AND loss.CID = qi.CID");
            exportSQL.append("  LEFT JOIN QQSDT_KOUHO_LOSS_INFO wloss ON wloss.SID = his.SID AND wloss.CID = wi.CID ");
            exportSQL.append("  LEFT JOIN QQSDT_KNKUM_EGOV_INFO iu ON iu.SID = loss.SID AND iu.CID = his.CID");
            exportSQL.append("  LEFT JOIN QQSDT_SYAHO_GET_INFO info ON info.SID = his.SID AND info.CID = his.CID");
            exportSQL.append("  LEFT JOIN QQSDT_SYAHO_MULTI_OFFICE mt ON mt.SID = his.SID AND mt.CID = his.CID");
            exportSQL.append("  LEFT JOIN QQSDT_SYAHO_KNEN_NUM ba ON ba.SID = his.SID AND ba.CID = his.CID");
            exportSQL.append("  INNER JOIN (SELECT * ");
            exportSQL.append("       FROM QPBMT_SOCIAL_INS_OFFICE");
            exportSQL.append("       WHERE CID = ?cid) oi ");
            exportSQL.append("       ON oi.CODE = his.SYAHO_OFFICE_CD");
            exportSQL.append("  ORDER BY SYAHO_OFFICE_CD ");
            String emp = empIds.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining("','"));
            String sql = String.format(exportSQL.toString(), emp, emp);
            try {
                resultQuery = this.getEntityManager().createNativeQuery(sql)
                        .setParameter("startDate", this.convertDate(startDate.toString("yyyy-MM-dd")))
                        .setParameter("endDate", this.convertDate(endDate.toString("yyyy-MM-dd")))
                        .setParameter("startYm", this.convertDateToInt(startDate))
                        .setParameter("endYm", this.convertDateToInt(endDate))
                        .setParameter("cid", cid)
                        .getResultList();
            } catch (NoResultException e) {
                return Collections.emptyList();
            }
            return resultQuery.stream().map(i -> InsLossDataExport.builder()
                    .empId(i[0].toString())
                    .officeCd(i[1] == null ? "" : i[1].toString())
                    .other(i[2] == null ? 0 : ((BigDecimal) i[2]).intValue())
                    .otherReason(i[3] == null ? "" : i[3].toString())
                    .caInsurance(i[4] == null ? null : ((BigDecimal) i[4]).intValue())
                    .numRecoved(i[5] == null ? null : ((BigDecimal) i[5]).intValue())
                    .cause(i[6] == null ? null : ((BigDecimal) i[6]).intValue())
                    .percentOrMore(i[7] == null ? "" : i[7].toString())
                    .remarksOther(i[8] == null ? "" : i[8].toString())
                    .remarksAndOtherContent(i[9] == null ? "" : i[9].toString())
                    .remunMonthlyAmountKind(i[10] == null ? 0 : ((BigDecimal) i[10]).intValue())
                    .remunMonthlyAmount(i[11] == null ? 0 : ((BigDecimal) i[11]).intValue())
                    .totalMonthyRemun(i[12] == null ? 0 : ((BigDecimal) i[12]).intValue())
                    .livingAbroad(i[13] != null && ((BigDecimal) i[13]).intValue() == 1 ? "1" : "0")
                    .resonOther(i[14] != null && ((BigDecimal) i[14]).intValue() != 2 ? "1" : "0")
                    .resonAndOtherContent(i[15] == null ? "" : i[15].toString())
                    .shortTimeWorkes(i[16] == null ? "" : i[16].toString())
                    .shortStay(i[17] != null && ((BigDecimal) i[17]).intValue() != 3 ? "1" : "0")
                    .depenAppoint(i[18] == null ? "" : i[18].toString())
                    .qualifiDistin(i[19] == null ? "" : i[19].toString())
                    .continReemAfterRetirement(i[20] == null ? 0 : ((BigDecimal) i[20]).intValue())
                    .isMoreEmp(i[21] == null ? 0 : ((BigDecimal) i[21]).intValue())
                    .basicPenNumber(i[22] == null ? "" : i[22].toString())
                    .endDate(i[23] == null ? "" : i[23].toString())
                    .healInsNumber(i[24] == null ? "" : i[24].toString())
                    .prefectureNo(i[25] == null ? 0 : ((BigDecimal) i[25]).intValue())
                    .officeNumber1(i[26] == null ? "" : i[26].toString())
                    .officeNumber2(i[27] == null ? "" : i[27].toString())
                    .officeNumber(i[28] == null ? "" : i[28].toString())
                    .unionOfficeNumber(i[29] == null ? "" : i[29].toString())
                    .welfOfficeNumber1(i[30] == null ? "" : i[30].toString())
                    .welfOfficeNumber2(i[31] == null ? "" : i[31].toString())
                    .welfOfficeNumber(i[32] == null ? "" : i[32].toString())
                    .welfPenNumber(i[33] == null ? "" : i[33].toString())
                    .healInsUnionNumber(i[34] == null ? "" : i[34].toString())
                    .memberNumber(i[35] == null ? "" : i[35].toString())
                    .healInsInherenPr(i[36] == null ? "" : i[36].toString())
                    .portCd(i[37] == null ? "" : i[37].toString())
                    .add1(i[38] == null ? "" : i[38].toString())
                    .add2(i[39] == null ? "" : i[39].toString())
                    .companyName(i[40] == null ? "" : i[40].toString())
                    .repName(i[41] == null ? "" : i[41].toString())
                    .phoneNumber(i[42] == null ? "" : i[42].toString())
                    .other2(i[43] == null ? null : ((BigDecimal) i[43]).intValue())
                    .otherReason2(i[44] == null ? "" : i[44].toString())
                    .caInsurance2(i[45] == null ? null : ((BigDecimal) i[45]).intValue())
                    .numRecoved2(i[46] == null ? null : ((BigDecimal) i[46]).intValue())
                    .cause2(i[47] == null ? null : ((BigDecimal) i[47]).intValue())
                    .endDate2(i[48] == null ? "" : i[48].toString())
                    .insPerCls(i[49] == null ? null : ((BigDecimal) i[49]).intValue())
                    .build()
            ).collect(Collectors.toList());
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<PensFundSubmissData> getHealthInsAssociation(List<String> empIds, String cid, GeneralDate startDate, GeneralDate endDate) {
        try {
            List<Object[]> result = null;
            StringBuilder exportSQL = new StringBuilder();
            exportSQL.append("  SELECT  ");
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
            exportSQL.append("      KENHO_NUM,");
            exportSQL.append("      KOUHO_NU,");
            exportSQL.append("      KNKUM_NUM,");
            exportSQL.append("      KIKIN_NUM,");
            exportSQL.append("      WELFARE_PENSION_OFFICE_NUMBER,");
            exportSQL.append("      WELFARE_PENSION_PREFECTURE_NO,");
            exportSQL.append("      qi.END_DATE,");
            exportSQL.append("      POSTALCD_AFTER_RETIRE,");
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
            exportSQL.append("      OVER_70_ATR,");
            exportSQL.append("      NO_MYNUM_ATR,");
            exportSQL.append("      qi.SID");
            exportSQL.append("  FROM ");
            exportSQL.append("         (SELECT *");
            exportSQL.append("         FROM QQSDT_KOUHO_INFO ");
            exportSQL.append("         WHERE END_DATE <= ?endDate AND END_DATE >= ?startDate AND CID = ?cid");
            exportSQL.append("         AND SID IN ('%s') )qi");
            exportSQL.append("  INNER JOIN ");
            exportSQL.append("       (SELECT * ");
            exportSQL.append("       FROM QQSDT_SYAHO_OFFICE_INFO ");
            exportSQL.append("       WHERE END_DATE <= ?endDate AND END_DATE >= ?startDate AND CID = ?cid) his");
            exportSQL.append("       ON qi.SID = his.SID ");
            exportSQL.append("  INNER JOIN (SELECT *  ");
            exportSQL.append("        FROM QQSDT_KIKIN_INFO");
            exportSQL.append("        WHERE END_DATE <= ?endDate AND END_DATE >= ?startDate AND CID = ?cid) ppi ");
            exportSQL.append("        ON qi.SID = ppi.SID");
            exportSQL.append("  INNER JOIN (SELECT * ");
            exportSQL.append("        FROM QPBMT_SOCIAL_INS_OFFICE");
            exportSQL.append("        WHERE CID = ?cid) oi");
            exportSQL.append("        ON oi.CODE = his.SYAHO_OFFICE_CD");
            exportSQL.append("  INNER JOIN QQSDT_KIKIN_EGOV_INFO fi ON fi.SID = qi.SID AND fi.CID = qi.CID");
            exportSQL.append("  INNER JOIN (SELECT *");
            exportSQL.append("       FROM QQSDT_KENHO_INFO ");
            exportSQL.append("       WHERE END_DATE <= ?endDate AND END_DATE >= ?startDate) wi ");
            exportSQL.append("       ON wi.SID = qi.SID AND wi.CID = qi.CID");
            exportSQL.append("  INNER JOIN  QQSDT_KOUHO_LOSS_INFO ni ");
            exportSQL.append("       ON ni.SID = qi.SID AND ni.CID = qi.CID");
            exportSQL.append("  INNER JOIN (SELECT *");
            exportSQL.append("       FROM QQSDT_KNKUM_INFO ");
            exportSQL.append("       WHERE END_DATE <= ?endDate AND END_DATE >= ?startDate) pri");
            exportSQL.append("       ON pri.SID = qi.SID AND pri.CID = qi.CID");
            exportSQL.append("  LEFT JOIN QQSDT_SYAHO_MULTI_OFFICE mi ON mi.SID = qi.SID AND mi.CID = qi.CID");
            exportSQL.append("  LEFT JOIN (SELECT * ");
            exportSQL.append("              FROM QQSDT_SYAHO_GET_INFO");
            exportSQL.append("              WHERE CID = ?cid) ii ON ii.SID = qi.SID");
            exportSQL.append("  LEFT JOIN QQSDT_SYAHO_KNEN_NUM bp ON bp.SID = qi.SID AND bp.CID = qi.CID");
            exportSQL.append("  ORDER BY SYAHO_OFFICE_CD   ");
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
            return result.stream().map(i -> PensFundSubmissData.builder()
                    .funSpecific1(i[0] != null ? i[0].toString() : "")
                    .funSpecific2(i[1] != null ? i[1].toString() : "")
                    .funSpecific3(i[2] != null ? i[2].toString() : "")
                    .funSpecific4(i[3] != null ? i[3].toString() : "")
                    .funSpecific5(i[4] != null ? i[4].toString() : "")
                    .funSpecific6(i[5] != null ? i[5].toString() : "")
                    .funSpecific7(i[6] != null ? i[6].toString() : "")
                    .funSpecific8(i[7] != null ? i[7].toString() : "")
                    .funSpecific9(i[8] != null ? i[8].toString() : "")
                    .funSpecific10(i[9] != null ? i[9].toString() : "")
                    .funMember(i[10] != null ? i[10].toString() : "")
                    .officeNumber1(i[11] != null ? i[11].toString() : "")
                    .officeNumber2(i[12] != null ? i[12].toString() : "")
                    .healInsNumber(i[13] != null ? i[13].toString() : "")
                    .welNumber(i[14] != null ? i[14].toString() : "")
                    .healInsUnionNumber(i[15] != null ? i[15].toString() : "")
                    .memberNumber(i[16] != null ? i[16].toString() : "")
                    .welPenOfficeNumber(i[17] != null ? i[17].toString() : "")
                    .prefectureNo(i[18] != null ? ((BigDecimal) i[18]).intValue() : 0)
                    .endDate(i[19] != null ? i[19].toString() : "")
                    .portCd(i[20] != null ? i[20].toString() : "")
                    .retirementAddBefore(i[21] != null ? i[21].toString() : "")
                    .retirementAdd(i[22] != null ? i[22].toString() : "")
                    .reasonForLoss(i[23] != null ? i[23].toString() : "")
                    .addAppCtgSal(i[24] != null ? i[24].toString() : "")
                    .reason(i[25] != null ? i[25].toString() : "")
                    .addSal(i[26] != null ? i[26].toString() : "")
                    .standSal(i[27] != null ? i[27].toString() : "")
                    .secAddSalary(i[28] != null ? i[28].toString() : "")
                    .secStandSal(i[29] != null ? i[29].toString() : "")
                    .cause(i[30] != null ? ((BigDecimal) i[30]).intValue() : 0)
                    .isMoreEmp(i[31] != null ? i[31].toString() : "")
                    .otherReason(i[32] != null ? i[32].toString() : "")
                    .continReemAfterRetirement(i[33] != null ? i[33].toString() : "")
                    .basicPenNumber(i[34] != null ? i[34].toString() : "")
                    .percentOrMore(i[35] != null ? i[35].toString() : "")
                    .livingAbroad(i[36] != null ? i[36].toString() : "")
                    .empId(i[37].toString())
                    .build()).collect(Collectors.toList());
        }catch (Exception e) {
            return Collections.emptyList();
        }
    }

    private Integer convertDateToInt(GeneralDate date){
        return Integer.parseInt(date.toString("yyyyMM"));
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
