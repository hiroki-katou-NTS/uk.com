package nts.uk.cnv.dom.conversiontable.pattern;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;

import nemunoki.oruta.shr.tabledefinetype.databasetype.DatabaseType;
import nts.uk.cnv.core.dom.conversionsql.ColumnName;
import nts.uk.cnv.core.dom.conversionsql.ConversionSQL;
import nts.uk.cnv.core.dom.conversionsql.Join;
import nts.uk.cnv.core.dom.conversionsql.JoinAtr;
import nts.uk.cnv.core.dom.conversionsql.OnSentence;
import nts.uk.cnv.core.dom.conversionsql.TableFullName;
import nts.uk.cnv.core.dom.conversiontable.ConversionCodeType;
import nts.uk.cnv.core.dom.conversiontable.ConversionInfo;
import nts.uk.cnv.core.dom.conversiontable.pattern.CodeToIdPattern;
import nts.uk.cnv.dom.conversionsql.ConversionSQLHelper;

/**
 * @author ai_muto
 *
 */
public class CodeToIdPatternTest {

	@Test
	public void test_sql() {
		ConversionSQL cs = ConversionSQLHelper.create();
		ConversionInfo info = new ConversionInfo(
				DatabaseType.sqlserver,"ERP","dbo","UK","dbo", "UK_CNV", "dbo","000000000000",
				ConversionCodeType.INSERT);

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

		ConversionSQL result = target.apply(new ColumnName("JOB_ID"), cs, true);
		String sql = result.build(info.getDatebaseType().spec());

        assertTrue(!sql.isEmpty());
	}
}
