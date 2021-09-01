package nts.uk.cnv.core.dom.conversiontable.pattern.manager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nemunoki.oruta.shr.tabledefinetype.DataType;
import nts.uk.cnv.core.dom.conversionsql.ColumnExpression;
import nts.uk.cnv.core.dom.conversionsql.ColumnName;
import nts.uk.cnv.core.dom.conversionsql.ConversionInsertSQL;
import nts.uk.cnv.core.dom.conversionsql.ConversionSQL;
import nts.uk.cnv.core.dom.conversionsql.Join;
import nts.uk.cnv.core.dom.conversionsql.TableFullName;
import nts.uk.cnv.core.dom.conversiontable.ConversionInfo;
import nts.uk.cnv.core.dom.conversiontable.ConversionTable;
import nts.uk.cnv.core.dom.conversiontable.OneColumnConversion;
import nts.uk.cnv.core.dom.conversiontable.pattern.ParentJoinPattern;

@Stateless
public class ParentJoinPatternManager {

	private static String sourcePkName = "SOURCE_PK";
	public static String parentValueColumnName = "PARENT_VALUE";
	public static String parentMappingTable = "SCVMT_MAPPING_PARENT";
	public static List<ColumnName> mappingTableColumns = Arrays.asList(
			new ColumnName("CATEGORY_NAME"),
			new ColumnName("PARENT_TBL_NAME"),
			new ColumnName("TARGET_COLUMN_NAME"),
			new ColumnName("SOURCE_PK1"),
			new ColumnName("SOURCE_PK2"),
			new ColumnName("SOURCE_PK3"),
			new ColumnName("SOURCE_PK4"),
			new ColumnName("SOURCE_PK5"),
			new ColumnName("SOURCE_PK6"),
			new ColumnName("SOURCE_PK7"),
			new ColumnName("SOURCE_PK8"),
			new ColumnName("SOURCE_PK9"),
			new ColumnName("SOURCE_PK10"),
			new ColumnName("PARENT_VALUE")
		);
	public static String[] pk = {
		"CATEGORY_NAME", "PARENT_TBL_NAME", "TARGET_COLUMN_NAME",
		"SOURCE_PK1","SOURCE_PK2","SOURCE_PK3","SOURCE_PK4","SOURCE_PK5",
		"SOURCE_PK6","SOURCE_PK7","SOURCE_PK8","SOURCE_PK9","SOURCE_PK10"
	};

	@Inject
	ParentJoinPatternRepository repo;

	public static String getSourcePkName(int index) {
		return sourcePkName + (index + 1);
	}

	public static TableFullName mappingTableName(ConversionInfo info, String alias) {
		return new TableFullName(
				info.getWorkDatabaseName(),
				info.getWorkSchema(),
				parentMappingTable,
				alias
			);
	}

	public AdditionalConversionCode createAdditionalConversionCode(ConversionInfo info, String category, ConversionTable ct, Join join) {

		String preProcessing = "";
		String postProcessing = "";
		Map<String, Map<String, List<String>>> referencedColumnList = new HashMap<>();

		// 対象テーブルを親テーブルとして参照している子テーブルのリストを取得
		List<ParentJoinPattern> children = repo.get(info, category, ct.getTargetTableName().getName());
		if (children.isEmpty()) {
			return new AdditionalConversionCode(
					preProcessing,
					postProcessing,
					referencedColumnList
				);
		}

		TableFullName mappingTableName = mappingTableName(info, "");

		Set<String> referencedColumns = children.stream()
			.map(c -> c.getParentColumn())
			.collect(Collectors.toSet());

		for (String parentColumn : referencedColumns) {
			ParentJoinPattern child = children.stream()
				.filter(c -> c.getParentColumn().equals(parentColumn))
				.findFirst()
				.get();

			List<String> columns = child.getMappingJoin().onSentences.stream()
				.map(c -> c.getRight().getName())
				.collect(Collectors.toList());

			Map<String, List<String>> value = new HashMap<>();
			value.put(parentColumn, columns);
			referencedColumnList.put(ct.getTargetTableName().getName(), value);

			ConversionSQL cnvSql = new ConversionInsertSQL(
					mappingTableName,
					ct.getWhereList());

			cnvSql.addJoin(join);

			cnvSql.add(mappingTableColumns.get(0), new ColumnExpression("'" + category + "'"));
			cnvSql.add(mappingTableColumns.get(1), new ColumnExpression("'" + ct.getTargetTableName().getName() + "'"));
			cnvSql.add(mappingTableColumns.get(2), new ColumnExpression("'" + parentColumn + "'"));

			int index = 1;
			for (String column : columns) {
				cnvSql.add(mappingTableColumns.get(2 + index), new ColumnExpression(column));
				index += 1;
			}
			for (; index <= 10; index++) {
				cnvSql.add(mappingTableColumns.get(2 + index), new ColumnExpression("''"));
			}
			OneColumnConversion colmnConversion = ct.getConversionMap().stream()
				.filter(colConv -> colConv.getTargetColumn().equals(parentColumn))
				.findFirst().get();
			cnvSql = colmnConversion.getPattern().apply(mappingTableColumns.get(13), cnvSql, ct.isRemoveDuplicate());

			if (!preProcessing.isEmpty()) {
				preProcessing += "\r\n";
			}

			preProcessing += cnvSql.build(info.getDatebaseType().spec());
		}

		return new AdditionalConversionCode(
				preProcessing,
				postProcessing,
				referencedColumnList
			);
	}

	public static String createTable(ConversionInfo info) {

		TableFullName mappingTableName = new TableFullName(
				info.getWorkDatabaseName(),
				info.getWorkSchema(),
				parentMappingTable,
				""
			);
		String type = info.getDatebaseType().spec().dataType(DataType.NVARCHAR, 50);
		String type2 = info.getDatebaseType().spec().dataType(DataType.NVARCHAR, 30);

		return "CREATE TABLE " + mappingTableName.fullName() + "(\r\n"
			+ mappingTableColumns.get(0).getName() + " " + type + " NOT NULL,\r\n"
			+ mappingTableColumns.get(1).getName() + " " + type + " NOT NULL,\r\n"
			+ mappingTableColumns.get(2).getName() + " " + type + " NOT NULL,\r\n"
			+ mappingTableColumns.get(3).getName() + " " + type2 + " NOT NULL,\r\n"
			+ mappingTableColumns.get(4).getName() + " " + type2 + " NOT NULL,\r\n"
			+ mappingTableColumns.get(5).getName() + " " + type2 + " NOT NULL,\r\n"
			+ mappingTableColumns.get(6).getName() + " " + type2 + " NOT NULL,\r\n"
			+ mappingTableColumns.get(7).getName() + " " + type2 + " NOT NULL,\r\n"
			+ mappingTableColumns.get(8).getName() + " " + type2 + " NOT NULL,\r\n"
			+ mappingTableColumns.get(9).getName() + " " + type2 + " NOT NULL,\r\n"
			+ mappingTableColumns.get(10).getName() + " " + type2 + " NOT NULL,\r\n"
			+ mappingTableColumns.get(11).getName() + " " + type2 + " NOT NULL,\r\n"
			+ mappingTableColumns.get(12).getName() + " " + type2 + " NOT NULL,\r\n"
			+ mappingTableColumns.get(13).getName() + " " + type + " NOT NULL,\r\n"
			+ "CONSTRAINT PK_" + mappingTableName.getName() + " PRIMARY KEY CLUSTERED ("
			+ String.join(",", pk) + ")\r\n"
			+ ");";
	}

	public static String dropTable(ConversionInfo info) {
		TableFullName mappingTableName = new TableFullName(
				info.getWorkDatabaseName(),
				info.getWorkSchema(),
				parentMappingTable,
				""
			);
		return "DROP TABLE " + mappingTableName.fullName() + ";";
	}

}
