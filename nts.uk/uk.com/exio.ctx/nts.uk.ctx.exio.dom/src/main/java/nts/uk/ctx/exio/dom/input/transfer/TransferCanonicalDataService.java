//package nts.uk.ctx.exio.dom.input.transfer;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//import lombok.val;
//import nemunoki.oruta.shr.tabledefinetype.DatabaseSpec;
//import nts.uk.cnv.core.dom.conversionsql.Join;
//import nts.uk.cnv.core.dom.conversionsql.JoinAtr;
//import nts.uk.cnv.core.dom.conversionsql.OnSentence;
//import nts.uk.cnv.core.dom.conversionsql.TableFullName;
//import nts.uk.cnv.core.dom.conversiontable.ConversionInfo;
//import nts.uk.cnv.core.dom.conversiontable.ConversionTable;
//import nts.uk.cnv.core.dom.conversiontable.OneColumnConversion;
//import nts.uk.cnv.core.dom.conversiontable.pattern.NotChangePattern;
//import nts.uk.ctx.exio.dom.input.csvimport.CsvItem;
//import nts.uk.ctx.exio.dom.input.csvimport.CsvRecord;
//
///**
// * 正準化データ移送サービス
// * @author ai_muto
// */
//public class TransferCanonicalDataService {
//	public String transfer(DatabaseSpec spec, ConversionInfo info, List<CsvRecord> records, Map<Integer, Object> mapLineContent) {
//		List<String> sqls = new ArrayList<>();
//		for(CsvRecord record : records) {
//			val tableFullName = new TableFullName("postgre", "public", "[domainTableName]", "tableName");
//			List<OneColumnConversion> conversionMap =  record.getItems().stream()
//				.map(csvItem -> createColumnConversion(tableFullName, info, csvItem))
//				.collect(Collectors.toList());
//			val conversionTable = new ConversionTable(
//					spec,
//					tableFullName,
//					Optional.empty(),
//					Optional.empty(),
//					Optional.empty(),
//					new ArrayList<>(),
//					conversionMap
//				);
//	
//			val conversionSql = conversionTable.createConversionSql();
//			sqls.add(conversionSql.build(spec));
//		}
//		return String.join("\r\n", sqls);
//	}
//	
//	private OneColumnConversion createColumnConversion(TableFullName tableFullName, ConversionInfo info, CsvItem csvItem) {
//		val acceptItem = csvItem.getAcceptItem();
//		return new OneColumnConversion(
//				acceptItem.getTableName(),
//				"",
//				new NotChangePattern(
//						info,
//						new Join(new TableFullName(), JoinAtr.Main, new ArrayList<OnSentence>()),
//						acceptItem.getColumnName())
//			);
//	}
//}
