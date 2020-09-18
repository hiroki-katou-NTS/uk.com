package nts.uk.cnv.dom.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.uk.cnv.dom.constants.Constants;
import nts.uk.cnv.dom.conversionsql.TableName;
import nts.uk.cnv.dom.conversiontable.ConversionRecord;
import nts.uk.cnv.dom.conversiontable.ConversionSource;
import nts.uk.cnv.dom.conversiontable.ConversionTable;
import nts.uk.cnv.dom.databasetype.DataType;

/***
 * コンバートコードを生成する
 * @author ai_muto
 */
@Stateless
public class CreateConversionCodeService {

	/***
	 * コンバートコードを生成する
	 */
	public String create(Require require, ConversionInfo info) {

		List<String> categorys = require.getCategoryPriorities();

		List<String> conversionSqlList = categorys.stream()
			.map(category -> createByCategory(require, category, info))
			.collect(Collectors.toList());

		return this.preprocessing(info) + "\r\n" +
				String.join("\r\n\r\n", conversionSqlList) + "\r\n\r\n" +
				this.postprocessing(info);
	}

	private String createByCategory(Require require, String category, ConversionInfo info){

		List<String> tables = require.getCategoryTables(category);
		List<String> sqlList = tables.stream()
			.map(table -> createByTables(require, category, table, info))
			.collect(Collectors.toList());

		return String.join("\r\n\r\n", sqlList);
	}

	private String createByTables(Require require, String category, String table, ConversionInfo info) {

		List<ConversionRecord> records = require.getRecords(category, table);

		List<String> convertCodes = records.stream()
			.map(record -> {

				ConversionSource source = require.getSource(record.getSourceId());

				return require.getConversionTable(info, category, table, record.getRecordNo(), source);
			})
			.filter(ct -> ct.isPresent())
			.map(recordNo -> recordNo.get().createConversionSql())
			.map(conversionSql -> conversionSql.build())
			.collect(Collectors.toList());

		return String.join("\r\n\r\n", convertCodes);
	}

	public static interface Require {

		List<String> getCategoryPriorities();
		List<String> getCategoryTables(String category);
		List<ConversionRecord> getRecords(String category, String tableName);
		Optional<ConversionTable> getConversionTable(ConversionInfo info, String category, String tableName, int recordNo, ConversionSource source);
		ConversionSource getSource(String sourceId);

	}

	/**
	 * 【前処理】
	 * ・契約コードのパラメータ定義
	 * ・会社IDの変換用など一時テーブルの作成
	 */
	private String preprocessing(ConversionInfo info) {
		StringBuilder sb = new StringBuilder();

		// 契約コードのパラメータ定義
		sb.append(info.getDatebaseType().spec().declaration(Constants.ContractCodeParamName, DataType.CHAR, 12));
		sb.append("\r\n");
		sb.append(info.getDatebaseType().spec().initialization(Constants.ContractCodeParamName, info.getContractCode()));
		sb.append("\r\n");

		// 会社IDの変換テーブルの作成
		TableName mappingTable = new TableName(
				info.getSourceDatabaseName(), info.getSourceSchema(), Constants.CidMappingTableName, "");
		TableName source = new TableName(
				info.getSourceDatabaseName(), info.getSourceSchema(), Constants.kyotukaisya_m, "");
		sb.append("CREATE TABLE " + mappingTable.fullName() + "(\r\n");
		sb.append("    会社CD " + info.getDatebaseType().spec().dataType(DataType.INT, 2) + " NOT NULL,\r\n");
		sb.append("    CID " + info.getDatebaseType().spec().dataType(DataType.CHAR, 17) + " NOT NULL,\r\n");
		sb.append("    CCD " + info.getDatebaseType().spec().dataType(DataType.CHAR, 4) + " NOT NULL\r\n");
		sb.append(")\r\n");
		sb.append("\r\n");

		// 会社ID変換テーブルInsert
		String ccd = info.getDatebaseType().spec().right(
				info.getDatebaseType().spec().concat("'0000'", "会社CD"),
				4);
		String cid = info.getDatebaseType().spec().concat("'" + info.getContractCode() + "-'", ccd);
		sb.append("INSERT INTO ");
		sb.append(mappingTable.fullName());
		sb.append("(会社CD, CID, CCD) ");
		sb.append("( SELECT 会社CD, ");
		sb.append(cid + ", " + ccd);
		sb.append(" FROM " + source.fullName());
		sb.append(")\r\n");

		return sb.toString();
	}

	/**
	 * 【後処理】
	 * ・一時テーブルの削除コード
	 */
	private String postprocessing(ConversionInfo info) {
		StringBuilder sb = new StringBuilder();

		// 会社IDの変換テーブルの削除
		TableName mappingTable = new TableName(
				info.getSourceDatabaseName(), info.getSourceSchema(), Constants.CidMappingTableName, "");

		sb.append("DROP TABLE " + mappingTable.fullName() + "\r\n");

		return sb.toString();
	}
}
