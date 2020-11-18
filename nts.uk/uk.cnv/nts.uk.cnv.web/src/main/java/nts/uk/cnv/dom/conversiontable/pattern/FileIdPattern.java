package nts.uk.cnv.dom.conversiontable.pattern;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import nts.uk.cnv.dom.conversionsql.ColumnName;
import nts.uk.cnv.dom.conversionsql.ConversionSQL;
import nts.uk.cnv.dom.conversionsql.Join;
import nts.uk.cnv.dom.conversionsql.JoinAtr;
import nts.uk.cnv.dom.conversionsql.OnSentence;
import nts.uk.cnv.dom.conversionsql.SelectSentence;
import nts.uk.cnv.dom.conversionsql.TableName;
import nts.uk.cnv.dom.service.ConversionInfo;

/**
 * ファイルID変換
 * @author ai_muto
 */
@Getter
public class FileIdPattern extends ConversionPattern  {

	private static String MAPPING_TABLE_NAME = "SCVMT_MAPPING_FILE_ID";
	private static String MAPPING_IN_COLUMN_NAME = "FILE_PATH";
	private static String MAPPING_OUT_COLUMN_NAME = "FILE_ID";

	/** 変換元 **/
	private Join sourceJoin;

	private String sourceColumnName;

	public FileIdPattern(ConversionInfo info, Join sourceJoin, String sourceColumnName) {
		super(info);
		this.sourceJoin = sourceJoin;
		this.sourceColumnName = sourceColumnName;
	}

	@Override
	public ConversionSQL apply(ConversionSQL conversionSql) {
		conversionSql.getFrom().addJoin(sourceJoin);

		Join mappingTableJoin = this.mappingJoin();
		conversionSql.getFrom().addJoin(mappingTableJoin);

		conversionSql.getSelect().add(
				SelectSentence.createNotFormat(mappingTableJoin.tableName.getAlias(), MAPPING_OUT_COLUMN_NAME));

		return conversionSql;
	}

	private Join mappingJoin() {
		List<OnSentence> onSentences = new ArrayList<>();
		onSentences.add(new OnSentence(
				new ColumnName(this.sourceJoin.tableName.getAlias(), this.sourceColumnName),
				new ColumnName(mappingAlias(), MAPPING_IN_COLUMN_NAME),
				Optional.empty()
			));

		return new Join(
				new TableName(
					info.getSourceDatabaseName(),
					info.getSourceSchema(),
					MAPPING_TABLE_NAME,
					this.mappingAlias()
				),
				JoinAtr.OuterJoin,
				onSentences
			);
	}

	private String mappingAlias() {
		return MAPPING_TABLE_NAME + "_" + this.sourceColumnName;
	}
}
