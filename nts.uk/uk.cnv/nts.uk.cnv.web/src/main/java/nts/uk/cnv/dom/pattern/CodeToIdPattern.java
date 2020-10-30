package nts.uk.cnv.dom.pattern;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import nts.uk.cnv.dom.conversionsql.ColumnExpression;
import nts.uk.cnv.dom.conversionsql.ColumnName;
import nts.uk.cnv.dom.conversionsql.ConversionSQL;
import nts.uk.cnv.dom.conversionsql.Join;
import nts.uk.cnv.dom.conversionsql.JoinAtr;
import nts.uk.cnv.dom.conversionsql.OnSentence;
import nts.uk.cnv.dom.conversionsql.RelationalOperator;
import nts.uk.cnv.dom.conversionsql.SelectSentence;
import nts.uk.cnv.dom.conversionsql.TableName;
import nts.uk.cnv.dom.conversionsql.WhereSentence;
import nts.uk.cnv.dom.service.ConversionInfo;

/**
 * コードからIDへの変換
 * @author ai_muto
 *
 * 変換元テーブル、変換元列名には変換元のコードの列を指定。
 * 変換テーブルをFrom句に追加する
 */
@Getter
public class CodeToIdPattern extends ConversionPattern {

	//TODO: 履歴あるテーブルどうしよう...
	public enum CodeToIdType{
		TO_CID("SCVMT_MAPPING_CODE_TO_CID", "CODE", "ID", ""),
		TO_SID("SCVMT_MAPPING_CODE_TO_SID", "SCD", "SID", "CCD"),
		TO_JOB_ID("SCVMT_MAPPING_CODE_TO_JOBID", "JOB_CD", "JOB_ID", "CCD");

		private final String tableName;
		private final String idColumnName;
		private final String codeColumnName;
		private final String ccd;

		CodeToIdType(String tableName, String codeColumnName, String idColumnName, String ccd){
			this.tableName = tableName;
			this.codeColumnName = codeColumnName;
			this.idColumnName = idColumnName;
			this.ccd = ccd;
		}

		public String getTableName() {
			return this.tableName;
		}
		public String getIdColumnName() {
			return this.idColumnName;
		}
		public String getCodeColumnName() {
			return this.codeColumnName;
		}
		public String getCcdColumnName() {
			return this.ccd;
		}
	}

	/** 変換元 **/
	private Join sourceJoin;

	private String sourceColumnName;

	private CodeToIdType codeToIdType;

	private Optional<String> sourceCcdColumnName;

	public CodeToIdPattern(ConversionInfo info, Join join, String sourceColumnName, String codeToIdType, String sourceCcdColumnName) {
		super(info);
		this.sourceJoin = join;
		this.sourceColumnName = sourceColumnName;
		this.codeToIdType = CodeToIdType.valueOf(codeToIdType);

		 this.sourceCcdColumnName = (sourceCcdColumnName == null || sourceCcdColumnName.isEmpty())
			? Optional.empty()
			: Optional.of(sourceCcdColumnName);
	}

	@Override
	public ConversionSQL apply(ConversionSQL conversionSql) {
		conversionSql.getFrom().addJoin(sourceJoin);

		Join idConvertJoin = this.idConvertJoin();
		conversionSql.getFrom().addJoin(idConvertJoin);

		conversionSql.getSelect().add(
				SelectSentence.createNotFormat(idConvertJoin.getTableName().getAlias(), this.codeToIdType.getIdColumnName()));

		if (this.sourceCcdColumnName.isPresent()) {
			conversionSql.getWhere().add( new WhereSentence(
					new ColumnName(idConvertJoin.getTableName().getAlias(), this.codeToIdType.getCcdColumnName()),
					RelationalOperator.Equal,
					Optional.of(new ColumnExpression(
							Optional.of(this.sourceJoin.getTableName().getAlias()),
							this.sourceCcdColumnName.get()
						))
				));
		}

		return conversionSql;
	}

	private Join idConvertJoin() {
		List<OnSentence> onSentences = new ArrayList<>();
		onSentences.add(new OnSentence(
				new ColumnName(this.sourceJoin.tableName.getAlias(), this.sourceColumnName),
				new ColumnName(this.mappingAlias(), this.codeToIdType.getCodeColumnName()),
				Optional.empty()
			));

		return new Join(
				new TableName(
					info.getTargetDatabaseName(),
					info.getTargetSchema(),
					this.codeToIdType.getTableName(),
					this.mappingAlias()
				),
				JoinAtr.OuterJoin,
				onSentences
			);
	}

	private String mappingAlias() {
		return this.codeToIdType.getTableName() + "_" + this.sourceColumnName;
	}
}
