/**
 *
 */
package nts.uk.cnv.dom.pattern;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;

import nts.uk.cnv.dom.conversionsql.ColumnName;
import nts.uk.cnv.dom.conversionsql.ConversionSQL;
import nts.uk.cnv.dom.conversionsql.ConversionSQLHelper;
import nts.uk.cnv.dom.conversionsql.Join;
import nts.uk.cnv.dom.conversionsql.JoinAtr;
import nts.uk.cnv.dom.conversionsql.OnSentence;
import nts.uk.cnv.dom.conversionsql.TableName;
import nts.uk.cnv.dom.databasetype.DatabaseType;
import nts.uk.cnv.dom.service.ConversionInfo;

/**
 * @author ai_muto
 *
 */
public class CodeToIdPatternTest {

	@Test
	public void test_sql() {
		ConversionSQL cs = ConversionSQLHelper.create();
		ConversionInfo info = new ConversionInfo(
				DatabaseType.sqlserver,"ERP","dbo","UK","dbo","000000000000");

		Join join = new Join(
				new TableName("UKDB", "dbo", "BSYMT_JOB_INFO", "jobinfo"),
				JoinAtr.InnerJoin,
				Arrays.asList(new OnSentence(new ColumnName("main", "職位CD"), new ColumnName("jobinfo", "JOB_CD"), Optional.empty()))
			);

		CodeToIdPattern target = new CodeToIdPattern(
				info,
				join,
				"JOB_ID",
				"TO_JOB_ID",
				null);

		ConversionSQL result = target.apply(cs);
		String sql = result.build(info);

        //assertTrue(result);
	}
}
