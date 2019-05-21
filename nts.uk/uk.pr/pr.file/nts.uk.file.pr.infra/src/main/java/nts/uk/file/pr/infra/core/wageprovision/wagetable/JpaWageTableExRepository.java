package nts.uk.file.pr.infra.core.wageprovision.wagetable;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.file.app.core.wageprovision.wagetable.ItemDataNameExport;
import nts.uk.ctx.pr.file.app.core.wageprovision.wagetable.WageTableExportRepository;
import nts.uk.ctx.pr.file.app.core.wageprovision.wagetable.WageTablelData;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Stateless
public class JpaWageTableExRepository extends JpaRepository implements WageTableExportRepository {

    @Override
    public List<WageTablelData> getWageTableExport(String cid, int startYearMonth) {
        List<Object[]> resultQuery = null;
        List<WageTablelData> resulfData = new ArrayList<>();
        StringBuilder exportSQL = new StringBuilder();
        exportSQL.append("SELECT ");
        exportSQL.append("    w.WAGE_TABLE_CD,");
        exportSQL.append("    WAGE_TABLE_NAME,");
        exportSQL.append("    START_YM,");
        exportSQL.append("    END_YM,");
        exportSQL.append("    FIXED_ELEMENT_3,");
        exportSQL.append("    OPT_ADD_ELEMENT_3,");
        exportSQL.append("    FIXED_ELEMENT_2,");
        exportSQL.append("    OPT_ADD_ELEMENT_2,");
        exportSQL.append("    FIXED_ELEMENT_1,");
        exportSQL.append("    OPT_ADD_ELEMENT_1,");
        exportSQL.append("    STEP_INCREMENT_1,");
        exportSQL.append("    UPPER_LIMIT_1,");
        exportSQL.append("    LOWER_LIMIT_1,");
        exportSQL.append("    s.QUALIFY_GROUP_CD,");
        exportSQL.append("    s.QUALIFY_GROUP_NAME,");
        exportSQL.append("    STEP_INCREMENT_2,");
        exportSQL.append("    UPPER_LIMIT_2,");
        exportSQL.append("    LOWER_LIMIT_2,");
        exportSQL.append("    STEP_INCREMENT_3,");
        exportSQL.append("    UPPER_LIMIT_3,");
        exportSQL.append("    LOWER_LIMIT_3,");
        exportSQL.append("    PAY_AMOUNT,");
        exportSQL.append("    PAYMENT_METHOD,");
        exportSQL.append("    ELEMENT_SET");
        exportSQL.append(" FROM QPBMT_WAGE_TABLE w");
        exportSQL.append(" INNER JOIN (SELECT * ");
        exportSQL.append("       FROM QPBMT_WAGE_TABLE_HIST");
        exportSQL.append("       WHERE START_YM <= ?startYearMonth AND END_YM >= ?startYearMonth AND CID = ?cid) h");
        exportSQL.append(" ON w.WAGE_TABLE_CD = h.WAGE_TABLE_CD AND w.CID = h.CID");
        exportSQL.append(" LEFT JOIN QPBMT_ELEM_RANGE_SET e ON e.CID = h.CID AND e.WAGE_TABLE_CD = w.WAGE_TABLE_CD AND e.HIST_ID = h.HIST_ID ");
        exportSQL.append(" LEFT JOIN QPBMT_WAGE_TBL_GRP_EQ_CD eq ON eq.CID = h.CID AND h.HIST_ID = eq.HIST_ID AND w.WAGE_TABLE_CD = eq.WAGE_TABLE_CD ");
        exportSQL.append(" LEFT JOIN QPBMT_QUALIFI_GROUP_SET s ON s.CID = h.CID  AND s.QUALIFY_GROUP_CD = eq.QUALIFY_GROUP_CD ");
        exportSQL.append(" LEFT JOIN QPBMT_WAGE_TBL_COMBO_PAY p ON h.CID = p.CID AND p.HIST_ID = h.HIST_ID AND p.WAGE_TABLE_CD = w.WAGE_TABLE_CD");
        exportSQL.append(" LEFT JOIN QPBMT_WAGE_TBL_GRP_SET ws ON ws.CID = h.CID AND ws.HIST_ID = h.HIST_ID AND ws.WAGE_TABLE_CD = e.WAGE_TABLE_CD AND ws.QUALIFY_GROUP_CD = s.QUALIFY_GROUP_CD");
        exportSQL.append(" ORDER BY w.WAGE_TABLE_CD, ws.QUALIFY_GROUP_CD");

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
                        e[21] != null ? e[21].toString() : "",
                        e[22] != null ? e[22].toString() : "3",
                        e[23] != null ? ((BigDecimal)e[23]).intValue() : 0
                ));
            }
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
        return resulfData;

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
