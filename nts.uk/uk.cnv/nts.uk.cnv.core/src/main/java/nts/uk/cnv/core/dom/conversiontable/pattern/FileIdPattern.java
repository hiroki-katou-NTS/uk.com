package nts.uk.cnv.core.dom.conversiontable.pattern;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import nts.uk.cnv.core.dom.conversionsql.ColumnExpression;
import nts.uk.cnv.core.dom.conversionsql.ColumnName;
import nts.uk.cnv.core.dom.conversionsql.ConversionSQL;
import nts.uk.cnv.core.dom.conversionsql.Join;
import nts.uk.cnv.core.dom.conversionsql.JoinAtr;
import nts.uk.cnv.core.dom.conversionsql.OnSentence;
import nts.uk.cnv.core.dom.conversionsql.TableFullName;
import nts.uk.cnv.core.dom.conversiontable.ConversionInfo;

/**
 * ファイルID変換
 * @author ai_muto
 */
@Getter
public class FileIdPattern extends ConversionPattern  {

	private static String MAPPING_TABLE_NAME = "SCVMT_MAPPING_FILE_ID";
	private static String MAPPING_OUT_COLUMN_NAME = "FILE_ID";
	private static String MAPPING_FILE_TYPE_COLUMN_NAME = "FILE_TYPE";
	private static String MAPPING_KOJIN_ID_COLUMN_NAME = "kojin_id";

	private ConversionInfo info;

	/** 変換元 **/
	private Join sourceJoin;

	private String sourceColumnName;

	private ConversionFileType fileType;

	private String kojinIdColumnName;

	public FileIdPattern(
			ConversionInfo info,
			Join sourceJoin,
			String sourceColumnName,
			String fileType,
			String kojinIdColumnName) {
		this.info = info;
		this.sourceJoin = sourceJoin;
		this.sourceColumnName = sourceColumnName;
		this.fileType = ConversionFileType.parse(fileType);

		this.kojinIdColumnName = kojinIdColumnName;
	}

	@Override
	public ConversionSQL apply(ColumnName column, ConversionSQL conversionSql, boolean removeDuplicate) {
		conversionSql.addJoin(sourceJoin);

		Join mappingTableJoin = this.mappingJoin();
		conversionSql.addJoin(mappingTableJoin);
		conversionSql.add(
				column,
				new ColumnExpression(
						mappingTableJoin.tableName.getAlias(),
						MAPPING_OUT_COLUMN_NAME));
		if(removeDuplicate) {
			conversionSql.addGroupingColumn(column);
		}

		return conversionSql;
	}

	private Join mappingJoin() {
		List<OnSentence> onSentences = new ArrayList<>();
		onSentences.add(new OnSentence(
			new ColumnName(this.sourceJoin.tableName.getAlias(), this.kojinIdColumnName),
			new ColumnName(mappingAlias(), MAPPING_KOJIN_ID_COLUMN_NAME),
			Optional.empty()
		));
		onSentences.add(new OnSentence(
			new ColumnName(mappingAlias(), MAPPING_FILE_TYPE_COLUMN_NAME),
			new ColumnName("", "'" + this.fileType.getId() + "'"),
			Optional.empty()
		));

		return new Join(
				new TableFullName(
					info.getWorkDatabaseName(),
					info.getWorkSchema(),
					MAPPING_TABLE_NAME,
					this.mappingAlias()
				),
				JoinAtr.OuterJoin,
				onSentences
			);
	}

	private String mappingAlias() {
		return "fileId_" + this.sourceColumnName;
	}
}
