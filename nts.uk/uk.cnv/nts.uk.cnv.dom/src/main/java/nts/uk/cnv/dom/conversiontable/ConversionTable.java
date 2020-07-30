package nts.uk.cnv.dom.conversiontable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import nts.uk.cnv.dom.conversionsql.ConversionSQL;
import nts.uk.cnv.dom.conversionsql.FromSentence;
import nts.uk.cnv.dom.conversionsql.InsertSentence;
import nts.uk.cnv.dom.conversionsql.TableName;
import nts.uk.cnv.dom.conversionsql.WhereSentence;

/**
 * コンバート表
 * @author ai_muto
 */
@AllArgsConstructor
public class ConversionTable {

//	private String category;
//	private int sequenceNo;
	
	private TableName targetTableName;
	
//	/** 変換元のテーブルの内、結合の基準になるテーブル **/
//	private TableName sourceTableName;
	
	private List<WhereSentence> whereList;
	private List<OneColumnConversion> conversionMap;
	
	public ConversionSQL createConversionSql() {
//		List<ColumnExpression> columns = conversionMap.stream()
//				.map(col-> new ColumnExpression(Optional.empty(), col.getTargetColumn()))
//				.collect(Collectors.toList());
		
		ConversionSQL result = new ConversionSQL(
					new InsertSentence(targetTableName, new ArrayList<>()),
					new ArrayList<>(),
					new FromSentence(Optional.empty(), new ArrayList<>()),
					whereList
				);

		for(OneColumnConversion oneColumnConversion : conversionMap) {
			result = oneColumnConversion.apply(result);
		}
		
		return result;
	}

}
