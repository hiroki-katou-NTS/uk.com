package nts.uk.file.pr.infra.core.wageprovision.wagetable;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.file.app.core.wageprovision.wagetable.ItemDataNameExport;
import nts.uk.ctx.pr.file.app.core.wageprovision.wagetable.WageTableExportRepository;
import nts.uk.ctx.pr.file.app.core.wageprovision.wagetable.WageTablelData;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Stateless
public class JpaWageTableExRepository extends JpaRepository implements WageTableExportRepository {

    @Override
    public List<WageTablelData> getWageTableExport(String cid, int startYearMonth) {
        List<Object[]> resultQuery = null;
        List<WageTablelData> resulfData = new ArrayList<>();
        StringBuilder exportSQL = new StringBuilder();
        exportSQL.append("    SELECT ");
        exportSQL.append("            w.WAGE_TABLE_CD,");
        exportSQL.append("            WAGE_TABLE_NAME,");
        exportSQL.append("            START_YM,");
        exportSQL.append("            END_YM,");
        exportSQL.append("            FIXED_ELEMENT_1,");
        exportSQL.append("            OPT_ADD_ELEMENT_1,");
        exportSQL.append("            FIXED_ELEMENT_2,");
        exportSQL.append("            OPT_ADD_ELEMENT_2,");
        exportSQL.append("            FIXED_ELEMENT_3,");
        exportSQL.append("            OPT_ADD_ELEMENT_3,");
        exportSQL.append("            STEP_INCREMENT_1,");
        exportSQL.append("            FRAME_UPPER_1,");
        exportSQL.append("            FRAME_LOWER_1,");
        exportSQL.append("            s.QUALIFY_GROUP_CD,");
        exportSQL.append("            s.QUALIFY_GROUP_NAME,");
        exportSQL.append("            STEP_INCREMENT_2,");
        exportSQL.append("            FRAME_UPPER_2,");
        exportSQL.append("            FRAME_LOWER_2,");
        exportSQL.append("            STEP_INCREMENT_3,");
        exportSQL.append("            FRAME_UPPER_3,");
        exportSQL.append("            FRAME_LOWER_3,");
        exportSQL.append("            PAY_AMOUNT,");
        exportSQL.append("            PAYMENT_METHOD,");
        exportSQL.append("            ELEMENT_SET,");
        exportSQL.append("            MASTER_NUM_ATR_1,");
        exportSQL.append("            MASTER_NUM_ATR_2,");
        exportSQL.append("            MASTER_NUM_ATR_3,");
        exportSQL.append("            MASTER_CD_1,");
        exportSQL.append("            MASTER_CD_2,");
        exportSQL.append("            MASTER_CD_3,");
        exportSQL.append("            QUALIFICATION_NAME,");
        exportSQL.append("            FRAME_NUMBER_1,");
        exportSQL.append("            FRAME_NUMBER_2,");
        exportSQL.append("            FRAME_NUMBER_3");
        exportSQL.append("         FROM QPBMT_WAGE_TABLE w");
        exportSQL.append("         INNER JOIN (SELECT * ");
        exportSQL.append("               FROM QPBMT_WAGE_TABLE_HIST");
        exportSQL.append("               WHERE START_YM <= ?startYearMonth AND END_YM >= ?startYearMonth AND CID =?cid) h");
        exportSQL.append("         ON w.WAGE_TABLE_CD = h.WAGE_TABLE_CD AND w.CID = h.CID");
        exportSQL.append("         LEFT JOIN QPBMT_ELEM_RANGE_SET e ON e.CID = h.CID AND e.WAGE_TABLE_CD = w.WAGE_TABLE_CD AND e.HIST_ID = h.HIST_ID  ");
        exportSQL.append("         LEFT JOIN QPBMT_WAGE_TBL_COMBO_PAY p ON h.CID = p.CID AND p.HIST_ID = h.HIST_ID AND p.WAGE_TABLE_CD = w.WAGE_TABLE_CD");
        exportSQL.append("     LEFT JOIN QPBMT_WAGE_TBL_GRP_EQ_CD eq ON eq.CID = h.CID AND h.HIST_ID = eq.HIST_ID AND w.WAGE_TABLE_CD = eq.WAGE_TABLE_CD");
        exportSQL.append("     AND MASTER_CD_1 = eq.ELIGIBLE_QUALIFY_CD");
        exportSQL.append("     LEFT JOIN QPBMT_QUALIFICATION_INFO sf ON sf.CID = h.CID AND eq.ELIGIBLE_QUALIFY_CD = sf.QUALIFICATION_CD");
        exportSQL.append("     LEFT JOIN QPBMT_QUALIFI_GROUP_SET s ON s.CID = h.CID  AND s.QUALIFY_GROUP_CD = eq.QUALIFY_GROUP_CD ");
        exportSQL.append("         LEFT JOIN QPBMT_WAGE_TBL_GRP_SET ws ON ws.CID = h.CID AND ws.HIST_ID = h.HIST_ID AND ws.WAGE_TABLE_CD = e.WAGE_TABLE_CD ");
        exportSQL.append("     AND ws.QUALIFY_GROUP_CD = s.QUALIFY_GROUP_CD");
        exportSQL.append("         ORDER BY w.WAGE_TABLE_CD, ");
        exportSQL.append("     CASE WHEN ELEMENT_SET = 0 AND MASTER_CD_1 IS NULL THEN  FRAME_LOWER_1 END ASC,");
        exportSQL.append("     CASE WHEN ELEMENT_SET = 0 AND MASTER_CD_1 IS NOT NULL THEN  MASTER_CD_1 END ASC,");
        exportSQL.append("     CASE WHEN ELEMENT_SET = 1 AND MASTER_CD_1 IS NULL THEN  FRAME_LOWER_1 END ASC,");
        exportSQL.append("     CASE WHEN ELEMENT_SET = 1 AND MASTER_CD_1 IS NOT NULL THEN  MASTER_CD_1 END ASC,");
        exportSQL.append("     CASE WHEN ELEMENT_SET = 1 AND MASTER_CD_2 IS NULL THEN  FRAME_LOWER_2 END ASC,");
        exportSQL.append("     CASE WHEN ELEMENT_SET = 1 AND MASTER_CD_2 IS NOT NULL THEN  MASTER_CD_2 END ASC,");
        exportSQL.append("     CASE WHEN ELEMENT_SET = 2 AND MASTER_CD_3 IS NULL THEN  FRAME_LOWER_3 END ASC,");
        exportSQL.append("     CASE WHEN ELEMENT_SET = 2 AND MASTER_CD_3 IS NOT NULL THEN  MASTER_CD_3 END ASC,");
        exportSQL.append("     CASE WHEN ELEMENT_SET = 2 AND MASTER_CD_1 IS NULL THEN  FRAME_LOWER_1 END ASC,");
        exportSQL.append("     CASE WHEN ELEMENT_SET = 2 AND MASTER_CD_1 IS NOT NULL THEN  MASTER_CD_1 END ASC,");
        exportSQL.append("     CASE WHEN ELEMENT_SET = 2 AND MASTER_CD_2 IS NULL THEN  FRAME_LOWER_2 END ASC,");
        exportSQL.append("     CASE WHEN ELEMENT_SET = 2 AND MASTER_CD_2 IS NOT NULL THEN  MASTER_CD_2 END ASC,");
        exportSQL.append("     CASE WHEN ELEMENT_SET = 3 AND s.QUALIFY_GROUP_CD IS NULL THEN 1 END,");
        exportSQL.append("     CASE WHEN ELEMENT_SET = 3 AND s.QUALIFY_GROUP_CD IS NOT NULL THEN 0 END,");
        exportSQL.append("     CASE WHEN ELEMENT_SET = 3 THEN s.QUALIFY_GROUP_CD END ASC,");
        exportSQL.append("     CASE WHEN ELEMENT_SET = 3 THEN MASTER_CD_1 END ASC,");
        exportSQL.append("     CASE WHEN ELEMENT_SET = 4 AND MASTER_CD_3 IS NOT NULL THEN MASTER_CD_3 END ASC,");
        exportSQL.append("     CASE WHEN ELEMENT_SET = 4 AND MASTER_CD_3 IS NULL THEN FRAME_LOWER_3 END ASC,");
        exportSQL.append("     CASE WHEN ELEMENT_SET = 4 AND MASTER_CD_1 IS NOT NULL THEN MASTER_CD_1 END ASC,");
        exportSQL.append("     CASE WHEN ELEMENT_SET = 4 AND MASTER_CD_1 IS NULL THEN FRAME_LOWER_1 END ASC,");
        exportSQL.append("     CASE WHEN ELEMENT_SET = 4 AND MASTER_CD_2 IS NOT NULL THEN MASTER_CD_2 END ASC,");
        exportSQL.append("     CASE WHEN ELEMENT_SET = 4 AND MASTER_CD_2 IS NULL THEN FRAME_LOWER_2 END ASC");
        try {
            resultQuery = this.getEntityManager()
                    .createNativeQuery(exportSQL.toString())
                    .setParameter("cid", cid)
                    .setParameter("startYearMonth", startYearMonth)
                    .getResultList();
            for (int i = 0; i < resultQuery.size(); i++) {
                Object[] e = resultQuery.get(i);
                resulfData.add(new WageTablelData(
                        e[0] != null ? e[0].toString() : "",
                        e[1] != null ? e[1].toString() : "",
                        e[2] != null ? e[2].toString() : "",
                        e[3] != null ? e[3].toString() : "",
                        e[4] != null ? e[4].toString() : "",
                        e[5] != null ? e[5].toString() : "",
                        e[6] != null ? e[6].toString() : "",
                        e[7] != null ? e[7].toString() : "",
                        e[8] != null ? e[8].toString() : "",
                        e[9] != null ? e[9].toString() : "",
                        e[10] != null ? e[10].toString() : "",
                        e[11] != null ? e[11].toString() : "",
                        e[12] != null ? e[12].toString() : "",
                        e[13] != null ? e[13].toString() : "",
                        e[14] != null ? e[14].toString() : "",
                        e[15] != null ? e[15].toString() : "",
                        e[16] != null ? e[16].toString() : "",
                        e[17] != null ? e[17].toString() : "",
                        e[18] != null ? e[18].toString() : "",
                        e[19] != null ? e[19].toString() : "",
                        e[20] != null ? e[20].toString() : "",
                        e[21] != null ? (BigDecimal) e[21] : null,
                        e[22] != null ? e[22].toString() : "0",
                        e[23] != null ? ((BigDecimal)e[23]).intValue() : 0,
                        e[24] != null ? ((BigDecimal)e[24]).intValue() : 0,
                        e[25] != null ? ((BigDecimal)e[25]).intValue() : 0,
                        e[26] != null ? ((BigDecimal)e[26]).intValue() : 0,
                        e[27] != null ? e[27].toString() : "",
                        e[28] != null ? e[28].toString() : "",
                        e[29] != null ? e[29].toString() : "",
                        e[30] != null ? e[30].toString() : "",
                        e[31] != null ? e[31].toString() : "",
                        e[32] != null ? e[32].toString() : "",
                        e[33] != null ? e[33].toString() : ""
                ));
            }
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
        return resulfData;

    }

    @Override
    public List<ItemDataNameExport> getItemNameMaster(String cid){
        List<Object[]> result = null;
        List<ItemDataNameExport> resulfData = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("   SELECT");
        sql.append("        CODE ,");
        sql.append("        NAME,");
        sql.append("        'M001' AS TYPE");
        sql.append("      FROM BSYMT_EMPLOYMENT");
        sql.append("      WHERE CID = ?cid");
        sql.append("   UNION ALL");
        sql.append("      SELECT  CD AS CODE ,");
        sql.append("          NAME,");
        sql.append("          'M002' AS TYPE");
        sql.append("      FROM BSYMT_DEPARTMENT_INFO f");
        sql.append("      INNER JOIN BSYMT_DEPARTMENT_HIST h ON f.HIST_ID = h.HIST_ID ");
        sql.append("      AND f.CID = h.CID AND f.DEP_ID = h.DEP_ID AND h.STR_D <= ?baseDate AND h.END_D >= ?baseDate");
        sql.append("      WHERE f.CID = ?cid");
        sql.append("   UNION ALL");
        sql.append("      SELECT  CLSCD AS CODE ,");
        sql.append("          CLSNAME AS NAME,");
        sql.append("          'M003' AS TYPE");
        sql.append("      FROM BSYMT_CLASSIFICATION ");
        sql.append("      WHERE CID = ?cid");
        sql.append("   UNION ALL");
        sql.append("     SELECT  JOB_CD AS CODE ,");
        sql.append("         JOB_NAME AS NAME,");
        sql.append("         'M004' AS TYPE");
        sql.append("     FROM BSYMT_JOB_INFO f");
        sql.append("     INNER JOIN BSYMT_JOB_HIST h ON h.CID = f.CID AND h.JOB_ID = f.JOB_ID AND h.HIST_ID = f.HIST_ID ");
        sql.append("     AND h.START_DATE <= ?baseDate AND h.END_DATE >= ?baseDate");
        sql.append("     WHERE f.CID = ?cid");
        sql.append("  UNION ALL ");
        sql.append("     SELECT  SALARY_CLS_CD AS CODE ,");
        sql.append("         SALARY_CLS_NAME AS NAME,");
        sql.append("         'M005' AS TYPE");
        sql.append("     FROM QPBMT_SALARY_CLS_INFO ");
        sql.append("     WHERE CID = ?cid");
        sql.append("  UNION ALL ");
        sql.append("     SELECT QUALIFICATION_CD , ");
        sql.append("         QUALIFICATION_NAME,");
        sql.append("         'M006' AS TYPE");
        sql.append("     FROM QPBMT_QUALIFICATION_INFO");
        sql.append("     WHERE CID = ?cid");
        try {
            result = this.getEntityManager()
                    .createNativeQuery(sql.toString())
                    .setParameter("cid", cid)
                    .setParameter("baseDate", getBaseDate())
                    .getResultList();
            resulfData = result.stream().map(item -> new ItemDataNameExport(item[0].toString(),
                                    item[1].toString(), item[2].toString())).collect(Collectors.toList());
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
        return resulfData;
    }

    private java.sql.Date getBaseDate() {
        GeneralDate baseDate = GeneralDate.today();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.sql.Date sqlDate = null;
        java.util.Date date = null;
        try {
            date = format.parse(baseDate.toString("yyyy-MM-dd"));
            sqlDate = new java.sql.Date(date.getTime());
        } catch (ParseException e) {
            return null;
        }
        return sqlDate;
    }

    @Override
    public List<ItemDataNameExport> getItemName(String cid) {
        List<Object[]> resultQuery = null;
        List<ItemDataNameExport> resulfData = new ArrayList<>();
        StringBuilder exportSQL = new StringBuilder();
        exportSQL.append(" SELECT  ");
        exportSQL.append("         ITEM.ITEM_NAME_CD, ");
        exportSQL.append("         ITEMNAME.NAME ");
        exportSQL.append(" FROM QPBMT_STATEMENT_ITEM ITEM  ");
        exportSQL.append(" INNER JOIN QPBMT_STATEMENT_ITEM_NAME ITEMNAME  ");
        exportSQL.append(" ON ITEM.CID = ITEMNAME.CID  ");
        exportSQL.append("  AND ITEM.CATEGORY_ATR = ITEMNAME.CATEGORY_ATR  ");
        exportSQL.append("  AND ITEM.ITEM_NAME_CD = ITEMNAME.ITEM_NAME_CD  ");
        exportSQL.append(" WHERE ITEM.CID = ?cid ");
        exportSQL.append("  AND ITEM.CATEGORY_ATR = 2  ");
        exportSQL.append("  AND ITEM.DEPRECATED_ATR = 0 ");
        try {
            resultQuery = this.getEntityManager()
                    .createNativeQuery(exportSQL.toString())
                    .setParameter("cid", cid)
                    .getResultList();
            for (int i = 0; i < resultQuery.size(); i++) {
                Object[] e = resultQuery.get(i);
                resulfData.add(new ItemDataNameExport(
                        e[0] != null ? e[0].toString() : "",
                        e[1] != null ? e[1].toString() : ""

                ));
            }
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
        return resulfData;

    }


}
