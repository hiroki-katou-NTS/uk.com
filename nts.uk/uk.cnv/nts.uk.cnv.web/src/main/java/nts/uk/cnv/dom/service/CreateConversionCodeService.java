package nts.uk.cnv.dom.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nemunoki.oruta.shr.tabledefinetype.DataType;
import nts.arc.time.GeneralDate;
import nts.uk.cnv.core.dom.constants.Constants;
import nts.uk.cnv.core.dom.conversionsql.ConversionSQL;
import nts.uk.cnv.core.dom.conversionsql.TableFullName;
import nts.uk.cnv.core.dom.conversiontable.ConversionInfo;
import nts.uk.cnv.core.dom.conversiontable.ConversionRecord;
import nts.uk.cnv.core.dom.conversiontable.ConversionSource;
import nts.uk.cnv.core.dom.conversiontable.ConversionTable;
import nts.uk.cnv.core.dom.conversiontable.OneColumnConversion;
import nts.uk.cnv.core.dom.conversiontable.pattern.ReferencedParentPattern;
import nts.uk.cnv.core.dom.conversiontable.pattern.manager.AdditionalConversionCode;
import nts.uk.cnv.core.dom.conversiontable.pattern.manager.ParentJoinPatternManager;

/***
 * コンバートコードを生成する
 * @author ai_muto
 */
@Stateless
public class CreateConversionCodeService {

	@Inject
	ParentJoinPatternManager manager;

	/***
	 * コンバートコードを生成する
	 */
	public String create(Require require, ConversionInfo info) {

		List<String> categorys = require.getCategoryPriorities();

		List<String> conversionSqlList = categorys.stream()
			.map(category -> createByCategory(require, category, info))
			.filter(sql -> !sql.isEmpty())
			.collect(Collectors.toList());

		return this.preprocessing(require, info) + "\r\n" +
				String.join("\r\n", conversionSqlList) + "\r\n" +
				this.postprocessing(require, info);
	}

	public String createForUpdate(Require require, ConversionInfo info) {

		List<String> categorys = require.getCategoryPriorities();

		List<String> conversionSqlList = categorys.stream()
			.map(category -> createByCategory(require, category, info))
			.collect(Collectors.toList());

		return String.join("\r\n", conversionSqlList);
	}

	private String createByCategory(Require require, String category, ConversionInfo info){

		List<String> tables = require.getCategoryTables(category);
		List<String> sqlList = tables.stream()
			.map(table -> createByTables(require, category, table, info))
			.filter(sql -> !sql.isEmpty())
			.collect(Collectors.toList());

		return String.join("\r\n", sqlList);
	}

	private String createByTables(Require require, String category, String table, ConversionInfo info) {

		List<ConversionRecord> records = require.getRecords(category, table);

		List<ConversionTable> conversionTables = records.stream()
			.map(record -> {

				ConversionSource source = require.getSource(record.getSourceId());

				return require.getConversionTable(info, category, table, record.getRecordNo(), source);
			})
			.filter(opCt -> opCt.isPresent())
			.map(opCt -> opCt.get())
			.collect(Collectors.toList());

		// 親テーブル参照関連の処理 - マッピングテーブルへの事前insertの追加など
		Map<String, Map<String, List<String>>> referencedColumnList = new HashMap<>();
		conversionTables.stream()
			.forEach(ct -> {
				AdditionalConversionCode additional = manager.createAdditionalConversionCode(info, category, ct);
				require.addPreProcessing(additional.getPreProcessing());
				require.addPostProcessing(additional.getPostProcessing());
				additional.getReferencedColumnList().keySet().stream()
					.forEach(key -> {
						Map<String, List<String>> value = new HashMap<>();
						value = additional.getReferencedColumnList().get(key);
						if(referencedColumnList.isEmpty() || !referencedColumnList.containsKey(key)) {
							referencedColumnList.put(key, value);
						}
						else {
							referencedColumnList.get(key).putAll(value);
						}
					});
			});

		// 親テーブル参照前処理 - 被参照側の該当列は、親テーブル参照のマッピングテーブルより取得する必要があるためフラグを立てておく
		conversionTables = conversionTables.stream()
			.map(ct -> {
				if (!referencedColumnList.containsKey(ct.getTargetTableName().getName())) {
					return ct;
				}
				Map<String, List<String>> columns = referencedColumnList.get(ct.getTargetTableName().getName());

				List<OneColumnConversion> newConversionMap = ct.getConversionMap().stream()
					.map(oneColumnCt -> {
						if (!columns.containsKey(oneColumnCt.getTargetColumn())) return oneColumnCt;

						ReferencedParentPattern refParentPattern = new ReferencedParentPattern(
								info,
								category,
								table,
								oneColumnCt.getTargetColumn(),
								columns.get(oneColumnCt.getTargetColumn())
							);

						oneColumnCt.setReferenced(true, refParentPattern);
						return oneColumnCt;
					})
					.collect(Collectors.toList());
				return new ConversionTable(
						info.getDatebaseType().spec(),
						ct.getTargetTableName(),
						ct.getDateColumnName(),
						ct.getStartDateColumnName(),
						ct.getEndDateColumnName(),
						ct.getWhereList(),
						newConversionMap);
			})
			.collect(Collectors.toList());

		List<String> convertCodes = conversionTables.stream()
			.map(ct -> require.createConversionSQL(ct))
			.map(conversionSql -> conversionSql.build(info.getDatebaseType().spec()))
			.collect(Collectors.toList());

		return String.join("\r\n", convertCodes);
	}

	public static interface Require {

		List<String> getCategoryPriorities();
		List<String> getCategoryTables(String category);
		List<ConversionRecord> getRecords(String category, String tableName);
		Optional<ConversionTable> getConversionTable(ConversionInfo info, String category, String tableName, int recordNo, ConversionSource source);
		ConversionSource getSource(String sourceId);
		ConversionSQL createConversionSQL(ConversionTable ct);

		void addPreProcessing(String sql);
		void addPostProcessing(String sql);
		String getPreProcessing();
		String getPostProcessing();
	}

	/**
	 * 【前処理】
	 * ・契約コードのパラメータ定義
	 * ・会社IDの変換用など一時テーブルの作成
	 */
	private String preprocessing(Require require, ConversionInfo info) {
		StringBuilder sb = new StringBuilder();
		val dbspec = info.getDatebaseType().spec();

		// 契約コードのパラメータ定義
		sb.append(dbspec.declaration(Constants.ContractCodeParamName, DataType.CHAR, 12));
		sb.append("\r\n");
		sb.append(dbspec.initialization(Constants.ContractCodeParamName, info.getContractCode()));
		sb.append("\r\n");

		// 期間指定のパラメータ定義
		sb.append(dbspec.declaration(Constants.StartDateParamName, DataType.DATE));
		sb.append("\r\n");
		sb.append(dbspec.declaration(Constants.EndDateParamName, DataType.DATE));
		sb.append("\r\n");
		sb.append(dbspec.initialization(Constants.StartDateParamName, GeneralDate.min().toString()));
		sb.append("\r\n");
		sb.append(dbspec.initialization(Constants.EndDateParamName, GeneralDate.max().toString()));
		sb.append("\r\n");

		// 会社ID変換テーブルInsert
		TableFullName mappingTable = new TableFullName(
				info.getWorkDatabaseName(), info.getWorkSchema(), Constants.CidMappingTableName, "");
		TableFullName source = new TableFullName(
				info.getSourceDatabaseName(), info.getSourceSchema(), Constants.kyotukaisya_m, "");
		String ccd = dbspec.right(
				dbspec.concat("'0000'", "会社CD"),
				4);
		String cid = dbspec.concat("'" + info.getContractCode() + "-'", ccd);
		sb.append("TRUNCATE TABLE " + mappingTable.fullName() + ";\r\n");
		sb.append("INSERT INTO ");
		sb.append(mappingTable.fullName());
		sb.append("(会社CD, CID) ");
		sb.append("( SELECT 会社CD, " + cid);
		sb.append(" FROM " + source.fullName());
		sb.append(");\r\n");

		// 親テーブル参照の一時テーブルを作成
		sb.append(ParentJoinPatternManager.createTable(info) + "\r\n");

		// 各変換処理で追加された事前処理を追記
		sb.append(require.getPreProcessing());

		return sb.toString();
	}

	/**
	 * 【後処理】
	 * ・一時テーブルの削除コード
	 */
	private String postprocessing(Require require, ConversionInfo info) {
		StringBuilder sb = new StringBuilder();

//		// 会社IDの変換テーブルの削除
//		TableFullName mappingTable = new TableFullName(
//				info.getTargetDatabaseName(), info.getTargetSchema(), Constants.CidMappingTableName, "");
//		sb.append("DROP TABLE " + mappingTable.fullName() + "\r\n");
//		sb.append("\r\n");

		// 親テーブル参照の一時テーブルを削除
		sb.append(ParentJoinPatternManager.dropTable(info) + "\r\n");

		sb.append(require.getPostProcessing());
		sb.append("\r\n");
		return sb.toString();
	}
}
