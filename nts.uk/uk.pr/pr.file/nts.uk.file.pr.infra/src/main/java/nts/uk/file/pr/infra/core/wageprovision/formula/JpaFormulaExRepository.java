package nts.uk.file.pr.infra.core.wageprovision.formula;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.file.app.core.wageprovision.formula.FormulaExRepository;
import nts.uk.shr.com.i18n.TextResource;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import java.util.Collections;
import java.util.List;

@Stateless
public class JpaFormulaExRepository extends JpaRepository implements FormulaExRepository {


    @Override
    public List<Object[]> getFormulaInfor(String cid, int startDate){
        List<Object[]> resultQuery = null;
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append("        f.FORMULA_CD,");
        sql.append("        FORMULA_NAME,");
        sql.append("        START_YM,");
        sql.append("        END_YM,");
        sql.append("        SETTING_METHOD,");
        sql.append("        NESTED_ATR,");
        sql.append("        MASTER_BRANCH_USE,");
        sql.append("        MASTER_USE,");
        sql.append("        c.MASTER_USE_CD,");
        sql.append("        REFERENCE_MONTH,");
        sql.append("        c.ROUNDING_METHOD,");
        sql.append("        ROUNDING_POSITION,");
        sql.append("        d.ROUNDING_METHOD,");
        sql.append("        ROUNDING_RESULT,");
        sql.append("        ADJUSTMENT_ATR,");
        sql.append("        FORMULA_TYPE,");
        sql.append("        STANDARD_AMOUNT_ATR,");
        sql.append("        STANDARD_FIXED_VALUE,");
        sql.append("        COEFFICIENT_ATR,");
        sql.append("        COEFFICIENT_FIXED_VALUE,");
        sql.append("        EXTRA_RATE,");
        sql.append("        BASE_ITEM_FIXED_VALUE,");
        sql.append("        BASE_ITEM_ATR");
        sql.append("    FROM (SELECT * ");
        sql.append("            FROM QPBMT_FORMULA");
        sql.append("            WHERE CID = ?cid) f");
        sql.append("    INNER JOIN (SELECT ");
        sql.append("            CID,");
        sql.append("             FORMULA_CD,");
        sql.append("            START_YM,");
        sql.append("            END_YM,");
        sql.append("            HIST_ID");
        sql.append("        FROM QPBMT_FORMULA_HISTORY");
        sql.append("        WHERE START_YM <= ?startDate AND END_YM >= ?startDate) h");
        sql.append("      ON f.CID = h.CID AND f.FORMULA_CD = h.FORMULA_CD");
        sql.append("    LEFT JOIN QPBMT_BASIC_FORMULA_SET b");
        sql.append("     ON f.CID = b.CID AND h.HIST_ID = b.HIST_ID AND f.FORMULA_CD = b.FORMULA_CD");
        sql.append("    LEFT JOIN QPBMT_BASIC_CAL_FORM c");
        sql.append("     ON f.CID = c.CID AND f.FORMULA_CD = c.FORMULA_CD ");
        sql.append("     AND c.HIST_ID = h.HIST_ID");
        sql.append("    LEFT JOIN QPBMT_DETAIL_FORMULA_SET d ");
        sql.append("     ON d.HIST_ID = h.HIST_ID AND f.CID = d.CID AND f.FORMULA_CD = d.FORMULA_CD");
        sql.append("    ORDER BY f.FORMULA_CD, START_YM");
        try {
            resultQuery = this.getEntityManager().createNativeQuery(sql.toString()).setParameter("cid", cid).setParameter("startDate", startDate)
                    .getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
        return resultQuery;

    }
    @Override
    public List<Object[]> getDetailFormula(String cid) {
        List<Object[]> resultQuery = null;
        StringBuilder sql = new StringBuilder();
        sql.append("    SELECT ");
        sql.append("        FORMULA_CD,");
        sql.append("        ELEMENT_ORDER,");
        sql.append("        d.FORMULA_ELEMENT,");
        sql.append("        NAME");
        sql.append("     FROM (SELECT * ");
        sql.append("        FROM QPBMT_DETAIL_CAL_FORMULA");
        sql.append("         WHERE CID = ?cid) d ");
        sql.append("     LEFT JOIN ");
        sql.append("           (SELECT ");
        sql.append("              CASE WHEN CATEGORY_ATR = '0' THEN '0000_0' + ITEM_NAME_CD ");
        sql.append("              WHEN CATEGORY_ATR = '1' THEN '0001_0' + ITEM_NAME_CD ");
        sql.append("              ELSE '0002_0' + ITEM_NAME_CD END AS CD,");
        sql.append("              CASE WHEN CATEGORY_ATR = '0' THEN ?payment + '{' + NAME + '}'");
        sql.append("              WHEN CATEGORY_ATR = '1' THEN ?deduction +'{' + NAME + '}'");
        sql.append("              ELSE ?attendance + '{' + NAME + '}' END AS NAME");
        sql.append("           FROM QPBMT_STATEMENT_ITEM_NAME");
        sql.append("           WHERE CID = ?cid");
        sql.append("           UNION ALL");
        sql.append("           SELECT ");
        sql.append("              'U000_0' + UNIT_PRICE_CD AS CD,");
        sql.append("              ?companyUnit + '{' + UNIT_PRICE_NAME + '}'");
        sql.append("           FROM QPBMT_PAY_UNIT_PRICE");
        sql.append("           WHERE CID = ?cid");
        sql.append("           UNION ALL ");
        sql.append("           SELECT ");
        sql.append("              'U001_0' + INDIVIDUAL_UNIT_PRICE_CD AS CD,");
        sql.append("             ?individualPrice +'{' + INDIVIDUAL_UNIT_PRICE_NAME + '}'");
        sql.append("           FROM QPBMT_PER_UNIT_PRICE");
        sql.append("           WHERE CID = ?cid");
        sql.append("           UNION ");
        sql.append("           SELECT ");
        sql.append("              'wage_00' + WAGE_TABLE_CD AS CD,");
        sql.append("              ?wage + '{' + WAGE_TABLE_NAME + '}'");
        sql.append("           FROM");
        sql.append("             QPBMT_WAGE_TABLE");
        sql.append("           WHERE CID = ?cid ");
        sql.append("           UNION ALL");
        sql.append("           SELECT 'Func_00001' AS CD ,");
        sql.append("               '{' + ?func1 + '}' AS NAME  ");
        sql.append("           UNION ALL");
        sql.append("           SELECT 'Func_00002' AS CD ,");
        sql.append("               '{' + ?func2 + '}' AS NAME  ");
        sql.append("           UNION ALL");
        sql.append("           SELECT 'Func_00003' AS CD ,");
        sql.append("               '{' + ?func3 + '}' AS NAME  ");
        sql.append("           UNION ALL");
        sql.append("           SELECT 'Func_00004' AS CD ,");
        sql.append("               '{' + ?func4 + '}' AS NAME  ");
        sql.append("           UNION ALL");
        sql.append("           SELECT 'Func_00005' AS CD ,");
        sql.append("               '{' + ?func5 + '}' AS NAME  ");
        sql.append("           UNION ALL");
        sql.append("           SELECT 'Func_00006' AS CD ,");
        sql.append("               '{' + ?func6 + '}' AS NAME  ");
        sql.append("           UNION ALL");
        sql.append("           SELECT 'Func_00007' AS CD ,");
        sql.append("               '{' + ?func7 + '}' AS NAME  ");
        sql.append("           UNION ALL");
        sql.append("           SELECT 'Func_00008' AS CD ,");
        sql.append("               '{' + ?func8 + '}' AS NAME  ");
        sql.append("           UNION ALL");
        sql.append("           SELECT 'Func_00009' AS CD ,");
        sql.append("              '{' + ?func9 + '}' AS NAME  ");
        sql.append("           UNION ALL");
        sql.append("           SELECT 'Func_00010' AS CD ,");
        sql.append("               '{' + ?func10 + '}' AS NAME  ");
        sql.append("           UNION ALL");
        sql.append("           SELECT 'Func_00011' AS CD ,");
        sql.append("               '{' + ?func11 + '}' AS NAME  ");
        sql.append("           UNION ALL");
        sql.append("           SELECT 'Func_00012' AS CD ,");
        sql.append("               '{' + ?func12 + '}' AS NAME  ");
        sql.append("           UNION ALL");
        sql.append("           SELECT 'vari_00001' AS CD ,");
        sql.append("               '{' + ?vari1 + '}' AS NAME  ");
        sql.append("           UNION ALL");
        sql.append("           SELECT 'vari_00002' AS CD ,");
        sql.append("               '{' + ?vari2 + '}' AS NAME  ");
        sql.append("           UNION ALL");
        sql.append("           SELECT 'vari_00003' AS CD ,");
        sql.append("               '{' + ?vari3 + '}' AS NAME  ");
        sql.append("           UNION ALL");
        sql.append("           SELECT 'vari_00004' AS CD ,");
        sql.append("               '{' + ?vari4 + '}' AS NAME  ");
        sql.append("           UNION ALL");
        sql.append("           SELECT 'vari_00005' AS CD ,");
        sql.append("               '{' + ?vari5 + '}' AS NAME  ");
        sql.append("           UNION ALL");
        sql.append("           SELECT 'vari_00008' AS CD ,");
        sql.append("               '{' + ?vari8 + '}' AS NAME  ");
        sql.append(             ")temp");
        sql.append("     ON temp.CD = FORMULA_ELEMENT");
        sql.append("     ORDER BY ELEMENT_ORDER");
        try {
            resultQuery = this.getEntityManager().createNativeQuery(sql.toString())
                    .setParameter("cid", cid)
                    .setParameter("payment", TextResource.localize("Enum_FormulaElementType_PAYMENT_ITEM"))
                    .setParameter("deduction", TextResource.localize("Enum_FormulaElementType_DEDUCTION_ITEM"))
                    .setParameter("attendance", TextResource.localize("Enum_FormulaElementType_ATTENDANCE_ITEM"))
                    .setParameter("companyUnit", TextResource.localize("Enum_FormulaElementType_COMPANY_UNIT_PRICE_ITEM"))
                    .setParameter("individualPrice", TextResource.localize("Enum_FormulaElementType_INDIVIDUAL_UNIT_PRICE_ITEM"))
                    .setParameter("wage", TextResource.localize("Enum_FormulaElementType_WAGE_TABLE_ITEM"))
                    .setParameter("func1", TextResource.localize("Enum_FunctionList_CONDITIONAL_EXPRESSION"))
                    .setParameter("func2", TextResource.localize("Enum_FunctionList_AND"))
                    .setParameter("func3", TextResource.localize("Enum_FunctionList_OR"))
                    .setParameter("func4", TextResource.localize("Enum_FunctionList_ROUND_OFF"))
                    .setParameter("func5", TextResource.localize("Enum_FunctionList_TRUNCATION"))
                    .setParameter("func6", TextResource.localize("Enum_FunctionList_ROUND_UP"))
                    .setParameter("func7", TextResource.localize("Enum_FunctionList_MAX_VALUE"))
                    .setParameter("func8", TextResource.localize("Enum_FunctionList_MIN_VALUE"))
                    .setParameter("func9", TextResource.localize("Enum_FunctionList_NUMBER_OF_FALIMY_MEMBER"))
                    .setParameter("func10", TextResource.localize("Enum_FunctionList_ADDITIONAL_YEARMONTH"))
                    .setParameter("func11", TextResource.localize("Enum_FunctionList_YEAR_EXTRACTION"))
                    .setParameter("func12", TextResource.localize("Enum_FunctionList_MONTH_EXTRACTION"))
                    .setParameter("vari1",TextResource.localize("Enum_SystemVariableList_SYSTEM_YMD_DATE"))
                    .setParameter("vari2",TextResource.localize("Enum_SystemVariableList_SYSTEM_YM_DATE"))
                    .setParameter("vari3",TextResource.localize("Enum_SystemVariableList_SYSTEM_Y_DATE"))
                    .setParameter("vari4",TextResource.localize("Enum_SystemVariableList_PROCESSING_YEAR_MONTH"))
                    .setParameter("vari5",TextResource.localize("Enum_SystemVariableList_PROCESSING_YEAR"))
                    .setParameter("vari8",TextResource.localize("Enum_SystemVariableList_WORKDAY"))
                    .getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
        return resultQuery;
    }

    @Override
    public List<Object[]> getBaseAmountTargetItem(String cid, int startDate) {
     List<Object[]> resultQuery = null;
     StringBuilder sql = new StringBuilder();
     sql.append("	SELECT ");
     sql.append("     FORMULA_CD,");
     sql.append("     CD,");
     sql.append("     NAME,");
     sql.append("     STANDARD_AMOUNT_CLS");
     sql.append("  FROM (");
     sql.append("      SELECT *");
     sql.append("      FROM QPBMT_BASIC_CAL_STD_AMOU ");
     sql.append("      WHERE CID = ?cid) a");
     sql.append("      INNER JOIN");
     sql.append("               (SELECT *");
     sql.append("               FROM QPBMT_FORMULA_HISTORY");
     sql.append("               WHERE CID = ?cid AND START_YM <= ?startDate AND END_YM >= ?startDate) h");
     sql.append("      ON a.CID = h.CID AND a.FORMULA_CD = h.FORMULA_CD");
     sql.append("      INNER JOIN");
     sql.append("           (SELECT ");
     sql.append("              ITEM_NAME_CD AS CD ,");
     sql.append("              NAME,");
     sql.append("              CASE WHEN CATEGORY_ATR = '0' ");
     sql.append("              THEN 0 ELSE 1 END AS STANDARD_AMOUNT_CLS");
     sql.append("           FROM QPBMT_STATEMENT_ITEM_NAME");
     sql.append("           WHERE CID = ?cid AND (CATEGORY_ATR = 0 OR CATEGORY_ATR = 1)");
     sql.append("           UNION ALL");
     sql.append("           SELECT ");
     sql.append("              UNIT_PRICE_CD AS CD,");
     sql.append("              UNIT_PRICE_NAME,");
     sql.append("              2 AS STANDARD_AMOUNT_CLS");
     sql.append("           FROM QPBMT_PAY_UNIT_PRICE");
     sql.append("           WHERE CID = ?cid");
     sql.append("           UNION ALL ");
     sql.append("           SELECT ");
     sql.append("               INDIVIDUAL_UNIT_PRICE_CD AS CD,");
     sql.append("               INDIVIDUAL_UNIT_PRICE_NAME,");
     sql.append("               3 AS STANDARD_AMOUNT_CLS");
     sql.append("           FROM QPBMT_PER_UNIT_PRICE");
     sql.append("           WHERE CID = ?cid");
     sql.append("           UNION ");
     sql.append("           SELECT ");
     sql.append("               WAGE_TABLE_CD AS CD,");
     sql.append("               WAGE_TABLE_NAME,");
     sql.append("               4 AS STANDARD_AMOUNT_CLS ");
     sql.append("           FROM");
     sql.append("             QPBMT_WAGE_TABLE");
     sql.append("           WHERE CID = ?cid) temp");
     sql.append("      ON temp.CD = a.TARGET_ITEM_CD");
        try {
            resultQuery = this.getEntityManager().createNativeQuery(sql.toString())
                    .setParameter("cid", cid).setParameter("startDate", startDate)
                    .getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
        return resultQuery;
    }

}
