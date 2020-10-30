/**
 *
 */
package nts.uk.cnv.dom.conversionsql;

import static org.junit.Assert.*;

import org.junit.Test;

import nts.uk.cnv.dom.databasetype.DatabaseType;
import nts.uk.cnv.dom.service.ConversionInfo;

/**
 * @author ai_muto
 *
 */
public class ConversionSQLTest {

	@Test
	public void test_sql() {
		ConversionSQL target = ConversionSQLHelper.create();

		ConversionInfo info = new ConversionInfo(
				DatabaseType.sqlserver,"KINJIROU","dbo","TEST","dbo","000000000000");

		String result = target.build(info);

        assertTrue(result.equals(
        		"INSERT INTO TEST.dbo.BSYMT_EMP_DTA_MNG_INFO (\r\n" +
        		"    INS_DATE,\r\n" +
        		"    INS_CCD,\r\n" +
        		"    INS_SCD,\r\n" +
        		"    INS_PG,\r\n" +
        		"    UPD_DATE,\r\n" +
        		"    UPD_CCD,\r\n" +
        		"    UPD_SCD,\r\n" +
        		"    UPD_PG,\r\n" +
        		"    EXCLUS_VER,\r\n" +
        		"    SID,\r\n" +
        		"    PID,\r\n" +
        		"    CID,\r\n" +
        		"    SCD,\r\n" +
        		"    DEL_STATUS_ATR,\r\n" +
        		"    DEL_DATE,\r\n" +
        		"    REMV_REASON,\r\n" +
        		"    EXT_CD\r\n" +
        		")\r\n" +
        		"(\r\n" +
        		" SELECT \r\n" +
        		"    SYSDATETIME(),\r\n" +
        		"    CIDVIEW.CCD,\r\n" +
        		"    'CONVERT',\r\n" +
        		"    'CONVERT',\r\n" +
        		"    SYSDATETIME(),\r\n" +
        		"    CIDVIEW.CCD,\r\n" +
        		"    'CONVERT',\r\n" +
        		"    'CONVERT',\r\n" +
        		"    0,\r\n" +
        		"    NEWID(),\r\n" +
        		"    NEWID(),\r\n" +
        		"    CIDVIEW.CID,\r\n" +
        		"    SOURCE.社員CD,\r\n" +
        		"    0,\r\n" +
        		"    NULL,\r\n" +
        		"    NULL,\r\n" +
        		"    NULL\r\n" +
        		" FROM KINJIROU.dbo.jm_kihon AS SOURCE\r\n" +
        		" INNER JOIN TEST.dbo.CONVERT_VIEW_CID AS CIDVIEW\r\n" +
        		" ON CIDVIEW.会社CD = TARGET_ALIAS.会社CD\r\n" +
        		");"
        	));
	}
}
