package nts.uk.cnv.core.dom.conversiontable.pattern;

import java.util.Arrays;
import java.util.Optional;

import lombok.Getter;
import nts.uk.cnv.core.dom.constants.Constants;
import nts.uk.cnv.core.dom.conversionsql.ColumnExpression;
import nts.uk.cnv.core.dom.conversionsql.ColumnName;
import nts.uk.cnv.core.dom.conversionsql.ConversionSQL;
import nts.uk.cnv.core.dom.conversionsql.Join;
import nts.uk.cnv.core.dom.conversionsql.JoinAtr;
import nts.uk.cnv.core.dom.conversionsql.OnSentence;
import nts.uk.cnv.core.dom.conversionsql.TableFullName;
import nts.uk.cnv.core.dom.conversiontable.ConversionInfo;

@Getter
public class PasswordPattern extends ConversionPattern {
	private ConversionInfo info;

	private Join sourceJoin;

	private String sourceColumnName;

	public PasswordPattern(ConversionInfo info, Join sourceJoin, String sourceColumnName) {
		this.info = info;
		this.sourceJoin = sourceJoin;
		this.sourceColumnName = sourceColumnName;
	}

	@Override
	public ConversionSQL apply(ColumnName column, ConversionSQL conversionSql, boolean removeDuplicate) {
		conversionSql.addJoin(sourceJoin);

		Join mappingTableJoin = new Join(
				new TableFullName(info.getWorkDatabaseName(), info.getWorkSchema(), Constants.EncryptionTableName, Constants.EncryptionTableAlias),
				JoinAtr.InnerJoin,
				Arrays.asList(new OnSentence(
						new ColumnName(
							this.sourceJoin.tableName.getAlias(),
							this.sourceColumnName
						),
						new ColumnName(
							Constants.EncryptionTableAlias,
							Constants.kojin_id
						),
						Optional.empty()
					))
			);

		conversionSql.addJoin(mappingTableJoin);

		ColumnExpression expression = new ColumnExpression(
				Constants.EncryptionTableAlias,
				Constants.EncryptionColumnName);
		conversionSql.add(column, expression);
		if(removeDuplicate) {
			conversionSql.addGroupingColumn(expression);
		}

		return conversionSql;
	}

}
