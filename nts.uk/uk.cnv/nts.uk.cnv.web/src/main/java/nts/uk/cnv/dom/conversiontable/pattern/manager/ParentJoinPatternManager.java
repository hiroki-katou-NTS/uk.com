package nts.uk.cnv.dom.conversiontable.pattern.manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.cnv.dom.conversionsql.ColumnExpression;
import nts.uk.cnv.dom.conversionsql.ConversionSQL;
import nts.uk.cnv.dom.conversionsql.FromSentence;
import nts.uk.cnv.dom.conversionsql.InsertSentence;
import nts.uk.cnv.dom.conversionsql.Join;
import nts.uk.cnv.dom.conversionsql.JoinAtr;
import nts.uk.cnv.dom.conversionsql.SelectSentence;
import nts.uk.cnv.dom.conversionsql.TableName;
import nts.uk.cnv.dom.conversiontable.ConversionTable;
import nts.uk.cnv.dom.conversiontable.OneColumnConversion;
import nts.uk.cnv.dom.conversiontable.pattern.ParentJoinPattern;
import nts.uk.cnv.dom.service.ConversionInfo;
import nts.uk.cnv.dom.tabledefinetype.DataType;

@Stateless
public class ParentJoinPatternManager {

	private static String sourcePkName = "SOURCE_PK";
	public static String parentValueColumnName = "PARENT_VALUE";
	public static String parentMappingTable = "SCVMT_MAPPING_PARENT";
	public static List<ColumnExpression> mappingTableColumns = Arrays.asList(
			new ColumnExpression(Optional.empty(), "CATEGORY_NAME"),
			new ColumnExpression(Optional.empty(), "PARENT_TBL_NAME"),
			new ColumnExpression(Optional.empty(), "TARGET_COLUMN_NAME"),
			new ColumnExpression(Optional.empty(), "SOURCE_PK1"),
			new ColumnExpression(Optional.empty(), "SOURCE_PK2"),
			new ColumnExpression(Optional.empty(), "SOURCE_PK3"),
			new ColumnExpression(Optional.empty(), "SOURCE_PK4"),
			new ColumnExpression(Optional.empty(), "SOURCE_PK5"),
			new ColumnExpression(Optional.empty(), "SOURCE_PK6"),
			new ColumnExpression(Optional.empty(), "SOURCE_PK7"),
			new ColumnExpression(Optional.empty(), "SOURCE_PK8"),
			new ColumnExpression(Optional.empty(), "SOURCE_PK9"),
			new ColumnExpression(Optional.empty(), "SOURCE_PK10"),
			new ColumnExpression(Optional.empty(), "PARENT_VALUE")
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

	public AdditionalConversionCode createAdditionalConversionCode(ConversionInfo info, String category, ConversionTable ct) {

		String preProcessing = "";
		String postProcessing = "";
		Map<String, Map<String, List<String>>> referencedColumnList = new HashMap<>();

		List<ParentJoinPattern> children = repo.get(info, category, ct.getTargetTableName().getName());
		if (children.isEmpty()) {
			return new AdditionalConversionCode(
					preProcessing,
					postProcessing,
					referencedColumnList
				);
		}

		TableName mappingTableName = new TableName(
				info.getTargetDatabaseName(),
				info.getTargetSchema(),
				parentMappingTable,
				""
			);

		Set<String> referencedColumns = children.stream()
			.map(c -> c.getParentColumn())
			.collect(Collectors.toSet());

		for (String parentColumn : referencedColumns) {
			ParentJoinPattern child = children.stream()
				.filter(c -> c.getParentColumn().equals(parentColumn))
				.findFirst()
				.get();

			List<String> columns = child.getParentJoin().onSentences.stream()
				.map(c -> c.getRight().getName())
				.collect(Collectors.toList());

			Map<String, List<String>> value = new HashMap<>();
			value.put(parentColumn, columns);
			referencedColumnList.put(ct.getTargetTableName().getName(), value);

			ConversionSQL cnvSql = new ConversionSQL(
					new InsertSentence(mappingTableName, new ArrayList<>()),
					new ArrayList<>(),
					new FromSentence(Optional.empty(), new ArrayList<>()),
					new ArrayList<> (ct.getWhereList())
				);
			ConversionSQL parentConversionSql = ct.createConversionSql();	// apply実行される
			cnvSql.getFrom().addJoin(
				new Join(
					parentConversionSql.getFrom().getBaseTable().get(),
					JoinAtr.Main,
					new ArrayList<>()
				));

			cnvSql.getInsert().addExpression(mappingTableColumns.get(0));
			cnvSql.getInsert().addExpression(mappingTableColumns.get(1));
			cnvSql.getInsert().addExpression(mappingTableColumns.get(2));
			cnvSql.getSelect().add(new SelectSentence(new ColumnExpression(Optional.empty(), "'" + category + "'"), new TreeMap<>()));
			cnvSql.getSelect().add(new SelectSentence(new ColumnExpression(Optional.empty(), "'" + ct.getTargetTableName().getName() + "'"), new TreeMap<>()));
			cnvSql.getSelect().add(new SelectSentence(new ColumnExpression(Optional.empty(), "'" + parentColumn + "'"), new TreeMap<>()));

			int index = 1;
			for (String column : columns) {
				cnvSql.getInsert().addExpression(mappingTableColumns.get(2 + index));
				index += 1;

				SelectSentence select = new SelectSentence(
						new ColumnExpression(Optional.empty(), column),
						new TreeMap<>()
					);
				cnvSql.getSelect().add(select);
			}
			for (; index <= 10; index++) {
				cnvSql.getInsert().addExpression(mappingTableColumns.get(2 + index));

				SelectSentence select = new SelectSentence(
						new ColumnExpression(Optional.empty(), "''"),
						new TreeMap<>()
					);
				cnvSql.getSelect().add(select);
			}
			cnvSql.getInsert().addExpression(mappingTableColumns.get(13));
			OneColumnConversion colmnConversion = ct.getConversionMap().stream()
				.filter(colConv -> colConv.getTargetColumn().equals(parentColumn))
				.findFirst().get();
			cnvSql = colmnConversion.getPattern().apply(cnvSql);

			if (!preProcessing.isEmpty()) {
				preProcessing += "\r\n\r\n";
			}

			preProcessing += cnvSql.build(info);
		}

		return new AdditionalConversionCode(
				preProcessing,
				postProcessing,
				referencedColumnList
			);
	}

	public static String createTable(ConversionInfo info) {

		TableName mappingTableName = new TableName(
				info.getTargetDatabaseName(),
				info.getTargetSchema(),
				parentMappingTable,
				""
			);
		String type = info.getDatebaseType().spec().dataType(DataType.NVARCHAR, 50);
		String type2 = info.getDatebaseType().spec().dataType(DataType.NVARCHAR, 30);

		return "CREATE TABLE " + mappingTableName.fullName() + "(\r\n"
			+ mappingTableColumns.get(0).getExpression() + " " + type + " NOT NULL,\r\n"
			+ mappingTableColumns.get(1).getExpression() + " " + type + " NOT NULL,\r\n"
			+ mappingTableColumns.get(2).getExpression() + " " + type + " NOT NULL,\r\n"
			+ mappingTableColumns.get(3).getExpression() + " " + type2 + " NOT NULL,\r\n"
			+ mappingTableColumns.get(4).getExpression() + " " + type2 + " NOT NULL,\r\n"
			+ mappingTableColumns.get(5).getExpression() + " " + type2 + " NOT NULL,\r\n"
			+ mappingTableColumns.get(6).getExpression() + " " + type2 + " NOT NULL,\r\n"
			+ mappingTableColumns.get(7).getExpression() + " " + type2 + " NOT NULL,\r\n"
			+ mappingTableColumns.get(8).getExpression() + " " + type2 + " NOT NULL,\r\n"
			+ mappingTableColumns.get(9).getExpression() + " " + type2 + " NOT NULL,\r\n"
			+ mappingTableColumns.get(10).getExpression() + " " + type2 + " NOT NULL,\r\n"
			+ mappingTableColumns.get(11).getExpression() + " " + type2 + " NOT NULL,\r\n"
			+ mappingTableColumns.get(12).getExpression() + " " + type2 + " NOT NULL,\r\n"
			+ mappingTableColumns.get(13).getExpression() + " " + type + " NOT NULL,\r\n"
			+ "CONSTRAINT PK_" + mappingTableName.getName() + " PRIMARY KEY CLUSTERED ("
			+ String.join(",", pk) + ")\r\n"
			+ ");";
	}

	public static String dropTable(ConversionInfo info) {
		TableName mappingTableName = new TableName(
				info.getTargetDatabaseName(),
				info.getTargetSchema(),
				parentMappingTable,
				""
			);
		return "DROP TABLE " + mappingTableName.fullName() + ";";
	}

}
