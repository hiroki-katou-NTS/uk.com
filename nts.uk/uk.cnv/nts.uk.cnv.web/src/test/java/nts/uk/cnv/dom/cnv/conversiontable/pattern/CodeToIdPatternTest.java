package nts.uk.cnv.dom.cnv.conversiontable.pattern;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;

import nts.uk.cnv.dom.cnv.conversionsql.ColumnName;
import nts.uk.cnv.dom.cnv.conversionsql.ConversionSQL;
import nts.uk.cnv.dom.cnv.conversionsql.ConversionSQLHelper;
import nts.uk.cnv.dom.cnv.conversionsql.Join;
import nts.uk.cnv.dom.cnv.conversionsql.JoinAtr;
import nts.uk.cnv.dom.cnv.conversionsql.OnSentence;
import nts.uk.cnv.dom.cnv.conversionsql.TableFullName;
import nts.uk.cnv.dom.cnv.service.ConversionInfo;
import nts.uk.cnv.dom.td.tabledefinetype.databasetype.DatabaseType;

/**
 * @author ai_muto
 *
 */
public class CodeToIdPatternTest {

	@Test
	public void test_sql() {
		ConversionSQL cs = ConversionSQLHelper.create();
		ConversionInfo info = new ConversionInfo(
				DatabaseType.sqlserver,"ERP","dbo","UK","dbo", "UK_CNV", "dbo","000000000000");

		Join join = new Join(
				new TableFullName("UKDB", "dbo", "BSYMT_JOB_INFO", "jobinfo"),
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

        assertTrue(!sql.isEmpty());
	}
}
