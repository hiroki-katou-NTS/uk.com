package nts.uk.cnv.dom.conversionsql;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

import lombok.val;
import nts.uk.cnv.core.dom.conversionsql.ColumnExpression;
import nts.uk.cnv.core.dom.conversionsql.ColumnName;
import nts.uk.cnv.core.dom.conversionsql.ConversionInsertSQL;
import nts.uk.cnv.core.dom.conversionsql.ConversionSQL;
import nts.uk.cnv.core.dom.conversionsql.FormatType;
import nts.uk.cnv.core.dom.conversionsql.FromSentence;
import nts.uk.cnv.core.dom.conversionsql.InsertSentence;
import nts.uk.cnv.core.dom.conversionsql.Join;
import nts.uk.cnv.core.dom.conversionsql.JoinAtr;
import nts.uk.cnv.core.dom.conversionsql.OnSentence;
import nts.uk.cnv.core.dom.conversionsql.RelationalOperator;
import nts.uk.cnv.core.dom.conversionsql.SelectSentence;
import nts.uk.cnv.core.dom.conversionsql.TableFullName;
import nts.uk.cnv.core.dom.conversionsql.WhereSentence;

public class ConversionSQLHelper {

	private static final List<ColumnExpression> expressions = Arrays.asList(
			new ColumnExpression("SID"),
			new ColumnExpression("PID"),
			new ColumnExpression("CID"),
			new ColumnExpression("SCD"),
			new ColumnExpression("DEL_STATUS_ATR"),
			new ColumnExpression("DEL_DATE"),
			new ColumnExpression("REMV_REASON"),
			new ColumnExpression("EXT_CD"));

	public static ConversionSQL create_emptyDummy() {

		return new ConversionInsertSQL(
				Insert.createDummy(),
				Select.createDummy(),
				From.createDummy(),
				Collections.emptyList(),
				expressions.stream()
					.filter(ce -> !ce.sql().equals("NEWID()"))
					.map(ce -> ce.sql())
					.collect(Collectors.toList())
			);
	}

	public static ConversionSQL create() {
		return new ConversionInsertSQL(
				Insert.createDummy(),
				Select.createDummy(),
				From.createDummy(),
				Collections.emptyList(),
				expressions.stream()
					.filter(ce -> !ce.sql().equals("NEWID()"))
					.map(ce -> ce.sql())
					.collect(Collectors.toList())
			);
	}

	public static class Insert {
		public static InsertSentence createDummy() {
			val insert = new InsertSentence(new TableFullName("TEST", "dbo", "BSYMT_EMP_DTA_MNG_INFO", "T"));
			expressions.stream().forEach(e -> insert.addExpression(e));
			return insert;
		}
	}

	public static class Select {
		public static SelectSentence CASE_DUMMY = new SelectSentence(
				new ColumnExpression("SOURCE_ALIAS", "SOURCE_COL2"),
				caseDummy(),
				""
			);

		public static SelectSentence CONVERT_DUMMY = new SelectSentence(
				new ColumnExpression("SOURCE_ALIAS", "SOURCE_COL3"),
				convertDummy(),
				""
			);

		public static SelectSentence FUNCTION_DUMMY = new SelectSentence(
				new ColumnExpression("SOURCE_ALIAS", "SOURCE_COL4"),
				functionDummy(),
				""
			);

		private static List<SelectSentence> createDummy() {
			List<SelectSentence> result = new ArrayList<SelectSentence>();
			SelectSentence newid = new SelectSentence(
					new ColumnExpression("NEWID()"),
					new TreeMap<FormatType, String>(),
					"");
			SelectSentence nullValue = new SelectSentence(
					new ColumnExpression("NULL"),
					new TreeMap<FormatType, String>(),
					"");
			SelectSentence zeroValue = new SelectSentence(
					new ColumnExpression("0"),
					new TreeMap<FormatType, String>(),
					"");

			result.add(newid);
			result.add(newid);
			result.add(new SelectSentence(
					new ColumnExpression("CIDVIEW", "CID"),
					new TreeMap<FormatType, String>(),
					""));
			result.add(new SelectSentence(
					new ColumnExpression("SOURCE", "社員CD"),
					new TreeMap<FormatType, String>(),
					""));
			result.add(zeroValue);
			result.add(nullValue);
			result.add(nullValue);
			result.add(nullValue);

			return result;
		}

		private static TreeMap<FormatType, String> convertDummy() {
			val dummy = new TreeMap<FormatType, String>();
			dummy.put(
					FormatType.CAST,
					"CAST(%s AS VARCHAR)");
			dummy.put(
					FormatType.CONVERT,
					"CONVERT(DATETIME2, %s, 112)");
			return dummy;
		}

		private static TreeMap<FormatType, String> caseDummy() {
			val dummy = new TreeMap<FormatType, String>();
			dummy.put(
					FormatType.CASE,
					"CASE %s \r\n" +
					"	WHEN -1 THEN NULL \r\n" +
					"	ELSE %s \r\n" +
					"END"
					);
			return dummy;
		}

		private static TreeMap<FormatType, String> functionDummy() {
			val dummy = new TreeMap<FormatType, String>();
			dummy.put(
					FormatType.FUNCTION,
					"RIGHT(CONCAT('0000', %s), 4)");
			return dummy;
		}
	}

	public static class From {

		public static Join createDummyView() {
			List<OnSentence> list = new ArrayList<>();
			list.add(new OnSentence(new ColumnName("CIDVIEW", "会社CD"), new ColumnName("TARGET_ALIAS", "会社CD"), Optional.empty()));
			return new Join(
				new TableFullName("TEST", "dbo", "CONVERT_VIEW_CID", "CIDVIEW"),
				JoinAtr.InnerJoin,
				list
			);
		}

		public static Join createDummyConversionMap() {
			List<OnSentence> list = new ArrayList<>();
			list.add(new OnSentence(new ColumnName("MAP", "IN"), new ColumnName("SOURCE_ALIAS", "申請反映状態"), Optional.empty()));
			return new Join(
				new TableFullName("TARGET_DB_NAME", "dbo", "CONVERSION_MAP", "MAP"),
				JoinAtr.OuterJoin,
				list
			);
		}

		public static FromSentence createDummy() {
			List<Join> list = new ArrayList<>();
			list.add(createDummyView());
			return new FromSentence(
				Optional.of(new TableFullName("KINJIROU", "dbo", "jm_kihon", "SOURCE")),
				list
			);
		}
	}

	public static class Where {

		public static WhereSentence DUMMY = new WhereSentence(
				new ColumnName("TARGET_ALIAS", "申請区分"),
				RelationalOperator.Equal,
				Optional.of(new ColumnExpression("'0'"))
			);
	}
}
