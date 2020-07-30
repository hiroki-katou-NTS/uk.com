//package nts.uk.cnv.dom.conversiontable;
//
//import java.util.List;
//
//import lombok.AllArgsConstructor;
//import nts.arc.layer.dom.AggregateRoot;
//import nts.uk.cnv.dom.conversionsql.TableName;
//import nts.uk.cnv.dom.conversionsql.WhereSentence;
//
///**
// * コンバート表
// * @author ai_muto
// *
// */
//@AllArgsConstructor
//public class ConversionMap extends AggregateRoot {
//	/** カテゴリ **/
//	private String category;
//	
//	/** 順序 **/
//	private int order;
//	
//	/** 変換先のテーブル **/
//	private TableName targetTable;
//
//	/** １列分の変換表リスト **/
//	private List<OneColumnConversionMap> targetColumns;
//	
//	/*変換先の抽出条件*/
//	private List<WhereSentence> sourseCondition;
//	
//}
