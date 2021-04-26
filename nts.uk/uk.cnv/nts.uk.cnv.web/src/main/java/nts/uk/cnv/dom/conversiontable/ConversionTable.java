package nts.uk.cnv.dom.conversiontable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.cnv.dom.conversionsql.ConversionSQL;
import nts.uk.cnv.dom.conversionsql.FromSentence;
import nts.uk.cnv.dom.conversionsql.InsertSentence;
import nts.uk.cnv.dom.conversionsql.TableFullName;
import nts.uk.cnv.dom.conversionsql.WhereSentence;

/**
 * コンバート表
 * @author ai_muto
 */
@Getter
@AllArgsConstructor
public class ConversionTable {
	private TableFullName targetTableName;

	private List<WhereSentence> whereList;
	private List<OneColumnConversion> conversionMap;

	public ConversionSQL createConversionSql() {
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
