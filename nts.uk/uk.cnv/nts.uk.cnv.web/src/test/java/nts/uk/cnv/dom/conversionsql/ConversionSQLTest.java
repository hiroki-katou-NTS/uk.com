/**
 *
 */
package nts.uk.cnv.dom.conversionsql;

import static org.junit.Assert.*;

import org.junit.Test;

import nemunoki.oruta.shr.tabledefinetype.databasetype.DatabaseType;
import nts.uk.cnv.core.dom.conversionsql.ConversionSQL;
import nts.uk.cnv.core.dom.conversiontable.ConversionCodeType;
import nts.uk.cnv.core.dom.conversiontable.ConversionInfo;

/**
 * @author ai_muto
 *
 */
public class ConversionSQLTest {

	@Test
	public void test_sql() {
		ConversionSQL target = ConversionSQLHelper.create();

		ConversionInfo info = new ConversionInfo(
				DatabaseType.sqlserver,"KINJIROU","dbo","TEST","dbo", "UK_CNV", "dbo","000000000000",
				ConversionCodeType.INSERT);

		String result = target.build(info.getDatebaseType().spec());

        assertTrue(result.equals(
        		"INSERT INTO TEST.dbo.BSYMT_EMP_DTA_MNG_INFO (\r\n" + 
        		"    SID,\r\n" + 
        		"    PID,\r\n" + 
        		"    CID,\r\n" + 
        		"    SCD,\r\n" + 
        		"    DEL_STATUS_ATR,\r\n" + 
        		"    DEL_DATE,\r\n" + 
        		"    REMV_REASON,\r\n" + 
        		"    EXT_CD,\r\n" + 
        		"    INS_DATE,\r\n" + 
        		"    INS_CCD,\r\n" + 
        		"    INS_SCD,\r\n" + 
        		"    INS_PG,\r\n" + 
        		"    UPD_DATE,\r\n" + 
        		"    UPD_CCD,\r\n" + 
        		"    UPD_SCD,\r\n" + 
        		"    UPD_PG,\r\n" + 
        		"    EXCLUS_VER\r\n" + 
        		")\r\n" + 
        		"(\r\n" + 
        		" SELECT \r\n" + 
        		"    NEWID() AS SID,\r\n" + 
        		"    NEWID() AS PID,\r\n" + 
        		"    CIDVIEW.CID AS CID,\r\n" + 
        		"    SOURCE.社員CD AS SCD,\r\n" + 
        		"    0 AS DEL_STATUS_ATR,\r\n" + 
        		"    NULL AS DEL_DATE,\r\n" + 
        		"    NULL AS REMV_REASON,\r\n" + 
        		"    NULL AS EXT_CD,\r\n" + 
        		"    SYSDATETIME() AS INS_DATE,\r\n" + 
        		"    NULL AS INS_CCD,\r\n" + 
        		"    NULL AS INS_SCD,\r\n" + 
        		"    'CNV001' AS INS_PG,\r\n" + 
        		"    SYSDATETIME() AS UPD_DATE,\r\n" + 
        		"    NULL AS UPD_CCD,\r\n" + 
        		"    NULL AS UPD_SCD,\r\n" + 
        		"    'CNV001' AS UPD_PG,\r\n" + 
        		"    0 AS EXCLUS_VER\r\n" + 
        		" FROM KINJIROU.dbo.jm_kihon AS SOURCE\r\n" + 
        		" INNER JOIN TEST.dbo.CONVERT_VIEW_CID AS CIDVIEW\r\n" + 
        		" ON CIDVIEW.会社CD = TARGET_ALIAS.会社CD\r\n" + 
        		"\r\n" + 
        		"GROUP BY SID,PID,CID,SCD,DEL_STATUS_ATR,DEL_DATE,REMV_REASON,EXT_CD);"
        	));
	}
}
