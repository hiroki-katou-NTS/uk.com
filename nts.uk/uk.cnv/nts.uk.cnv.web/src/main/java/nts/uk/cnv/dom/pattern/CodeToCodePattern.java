package nts.uk.cnv.dom.pattern;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import nts.uk.cnv.dom.constants.Constants;
import nts.uk.cnv.dom.conversionsql.ColumnName;
import nts.uk.cnv.dom.conversionsql.ConversionSQL;
import nts.uk.cnv.dom.conversionsql.Join;
import nts.uk.cnv.dom.conversionsql.JoinAtr;
import nts.uk.cnv.dom.conversionsql.OnSentence;
import nts.uk.cnv.dom.conversionsql.SelectSentence;
import nts.uk.cnv.dom.conversionsql.TableName;
import nts.uk.cnv.dom.service.ConversionInfo;

/**
 * コード変換
 * @author ai_muto
 *
 * 変換元テーブル、変換元列名には変換元のコードの列を指定。
 * 変換テーブルをFrom句に追加する
 */
@Getter
public class CodeToCodePattern extends ConversionPattern  {

	/** 変換元 **/
	private Join sourceJoin;

	private String sourceColumnName;

	/** 変換種別 変換テーブルの抽出キー **/
	private String mappingType;

	public CodeToCodePattern(ConversionInfo info, Join sourceJoin, String sourceColumnName, String mappingType) {
		super(info);
		this.sourceJoin = sourceJoin;
		this.sourceColumnName = sourceColumnName;
		this.mappingType = mappingType;
	}

	@Override
	public ConversionSQL apply(ConversionSQL conversionSql) {
		conversionSql.getFrom().addJoin(sourceJoin);

		Join mappingTableJoin = this.mappingJoin();
		conversionSql.getFrom().addJoin(mappingTableJoin);

		conversionSql.getSelect().add(
				SelectSentence.createNotFormat(
					mappingTableJoin.tableName.getAlias(),
					Constants.MAPPING_OUT_COLUMN_NAME));

//		conversionSql.getWhere().add(new WhereSentence(
//				new ColumnName(mappingAlias(), Constants.MAPPING_TYPE_COLUMN_NAME),
//				RelationalOperator.Equal,
//				Optional.of(new ColumnExpression(
//						Optional.empty(),
//						"'" + this.mappingType + "'"
//					))
//			));

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
				new TableName(
					info.getTargetDatabaseName(),
					info.getTargetSchema(),
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
