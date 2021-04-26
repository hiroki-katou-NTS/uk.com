package nts.uk.cnv.dom.conversiontable.pattern;

import java.util.Optional;

import lombok.Getter;
import nts.uk.cnv.dom.conversionsql.ConversionSQL;
import nts.uk.cnv.dom.conversionsql.Join;
import nts.uk.cnv.dom.conversionsql.SelectSentence;
import nts.uk.cnv.dom.service.ConversionInfo;

@Getter
public class StringConcatPattern extends ConversionPattern {

	private Join sourceJoin;

	private String column1;

	private String column2;

	private Optional<String> delimiter;


	public StringConcatPattern(ConversionInfo info, Join sourceJoin, String column1, String column2, Optional<String> delimiter) {
		super(info);
		this.sourceJoin = sourceJoin;
		this.column1 = column1;
		this.column2 = column2;
		this.delimiter = delimiter;
	}

	@Override
	public ConversionSQL apply(ConversionSQL conversionSql) {
		conversionSql.getFrom().addJoin(sourceJoin);

		String source1 = sourceJoin.tableName.getAlias() + "." + column1;
		String source2 = sourceJoin.tableName.getAlias() + "." + column2;

		if(delimiter.isPresent() && !delimiter.get().isEmpty()) {
			source1 = info.getDatebaseType().spec().concat(source1, "'" + delimiter.get() + "'");
		}

		String concatString = info.getDatebaseType().spec().concat(source1, source2);


		conversionSql.getSelect().add(SelectSentence.createNotFormat("", concatString));

		return conversionSql;
	}

}
