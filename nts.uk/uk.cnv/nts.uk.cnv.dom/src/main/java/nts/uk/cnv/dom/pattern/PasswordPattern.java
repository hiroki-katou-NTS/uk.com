package nts.uk.cnv.dom.pattern;

import java.util.Arrays;

import nts.uk.cnv.dom.constants.Constants;
import nts.uk.cnv.dom.conversionsql.ColumnName;
import nts.uk.cnv.dom.conversionsql.ConversionSQL;
import nts.uk.cnv.dom.conversionsql.Join;
import nts.uk.cnv.dom.conversionsql.JoinAtr;
import nts.uk.cnv.dom.conversionsql.OnSentence;
import nts.uk.cnv.dom.conversionsql.SelectSentence;
import nts.uk.cnv.dom.conversionsql.TableName;
import nts.uk.cnv.dom.service.ConversionInfo;

public class PasswordPattern extends ConversionPattern {

	public PasswordPattern(ConversionInfo info) {
		super(info);
	}

	@Override
	public ConversionSQL apply(ConversionSQL conversionSql) {
		Join join = new Join(
				new TableName(info.getTargetDatabaseName(), info.getTargetSchema(), Constants.EncryptionTableName, Constants.EncryptionTableAlias),
				JoinAtr.InnerJoin,
				Arrays.asList(new OnSentence(
						new ColumnName(
							Constants.BaseTableAlias,
							Constants.kojin_id
						),
						new ColumnName(
							Constants.EncryptionTableAlias,
							Constants.kojin_id
						)
					))
			);
		
		conversionSql.getFrom().addJoin(join);

		conversionSql.getSelect().add(
				SelectSentence.createNotFormat(Constants.EncryptionTableAlias, Constants.EncryptionColumnName));

		return conversionSql;
	}

}
