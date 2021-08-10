package nts.uk.cnv.core.dom.conversiontable.pattern;

import java.util.ArrayList;
import java.util.List;
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

/**
 * コード変換
 * @author ai_muto
 *
 * 変換元テーブル、変換元列名には変換元のコードの列を指定。
 * 変換テーブルをFrom句に追加する
 */
@Getter
public class CodeToCodePattern extends ConversionPattern  {
	private ConversionInfo info;

	/** 変換元 **/
	private Join sourceJoin;

	private String sourceColumnName;

	/** 変換種別 変換テーブルの抽出キー **/
	private String mappingType;

	public CodeToCodePattern(ConversionInfo info, Join sourceJoin, String sourceColumnName, String mappingType) {
		this.info = info;
		this.sourceJoin = sourceJoin;
		this.sourceColumnName = sourceColumnName;
		this.mappingType = mappingType;
	}

	@Override
	public ConversionSQL apply(ColumnName columnName, ConversionSQL conversionSql, boolean removeDuplicate) {
		conversionSql.addJoin(sourceJoin);

		ColumnExpression expression = new ColumnExpression(
				this.mappingJoin().tableName.getAlias(),
				Constants.MAPPING_OUT_COLUMN_NAME);
		conversionSql.addJoin(this.mappingJoin());
		conversionSql.add(columnName, expression);
		if(removeDuplicate) {
			conversionSql.addGroupingColumn(expression);
		}

		return conversionSql;
	}

	private Join mappingJoin() {
		List<OnSentence> onSentences = new ArrayList<>();
		onSentences.add(new OnSentence(
				new ColumnName(mappingAlias(), Constants.MAPPING_IN_COLUMN_NAME),
				new ColumnName(this.sourceJoin.tableName.getAlias(), this.sourceColumnName),
				Optional.empty()
			));
		onSentences.add(new OnSentence(
				new ColumnName(mappingAlias(), Constants.MAPPING_TYPE_COLUMN_NAME),
				new ColumnName("", "'" + this.mappingType + "'"),
				Optional.empty()
			));

		return new Join(
				new TableFullName(
					info.getWorkDatabaseName(),
					info.getWorkSchema(),
					Constants.MAPPING_TABLE_NAME,
					this.mappingAlias()
				),
				JoinAtr.OuterJoin,
				onSentences
			);
	}

	private String mappingAlias() {
		return Constants.MAPPING_TABLE_NAME + "_" + this.sourceColumnName;
	}
}
