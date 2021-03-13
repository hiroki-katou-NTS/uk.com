package nts.uk.cnv.dom.td.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import net.sf.jsqlparser.JSQLParserException;
import nts.arc.task.tran.AtomTask;
import nts.uk.cnv.dom.td.tabledesign.Snapshot;

@RunWith(JMockit.class)
public class TableDesignImportServiceTest {

	@Injectable
	DDLImportService.Require require;

	@Test
	public void test() {
		new Expectations() {{
			require.regist((Snapshot) any);
		}};

		String ddl = "CREATE TABLE BCMMT_COMPANY(\r\n" +
				"	INS_DATE DATETIME NULL,\r\n" +
				"	INS_CCD CHAR(4) NULL,\r\n" +
				"	INS_SCD CHAR(12) NULL,\r\n" +
				"	INS_PG VARCHAR(14) NULL,\r\n" +
				"	UPD_DATE DATETIME NULL,\r\n" +
				"	UPD_CCD CHAR(4) NULL,\r\n" +
				"	UPD_SCD CHAR(12) NULL,\r\n" +
				"	UPD_PG VARCHAR(14) NULL,\r\n" +
				"	EXCLUS_VER INT(8) NOT NULL DEFAULT 0,\r\n" +
				"	CID CHAR(17) NOT NULL,\r\n" +
				"	CCD CHAR(4) NOT NULL,\r\n" +
				"	CONTRACT_CD CHAR(12) NOT NULL,\r\n" +
				"	REPRESENTATIVE_NAME NVARCHAR(20) NULL,\r\n" +
				"	REPRESENTATIVE_JOB NVARCHAR(20) NULL,\r\n" +
				"	NAME NVARCHAR(40) NULL,\r\n" +
				"	KNNAME NVARCHAR(60) NOT NULL,\r\n" +
				"	ABNAME NVARCHAR(20) NOT NULL,\r\n" +
				"	ABOLITION_ATR BOOL NOT NULL,\r\n" +
				"	MONTH_STR INT(2) NOT NULL,\r\n" +
				"	TAX_NO VARCHAR(13) NULL, \r\n" +
				" PRIMARY KEY \r\n" +
				" (\r\n" +
				"	CID \r\n" +
				" ) \r\n" +
				")";

		String createIndex = "CREATE INDEX BCMMI_COMPANY ON BCMMT_COMPANY (CONTRACT_CD, CCD)";

		String comment = "COMMENT ON COLUMN BCMMT_COMPANY.CID IS '会社ID'";

		try {
			AtomTask task = DDLImportService.regist(require, "", ddl, createIndex, comment, "uk");
			task.run();
		} catch (JSQLParserException e) {
			fail();
		}

	}
}
