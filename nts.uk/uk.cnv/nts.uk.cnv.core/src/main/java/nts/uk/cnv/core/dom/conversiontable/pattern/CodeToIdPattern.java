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
import nts.uk.cnv.core.dom.conversionsql.RelationalOperator;
import nts.uk.cnv.core.dom.conversionsql.TableFullName;
import nts.uk.cnv.core.dom.conversionsql.WhereSentence;
import nts.uk.cnv.core.dom.conversiontable.ConversionInfo;

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
		TO_CID("SCVMT_MAPPING_CODE_TO_CID", "会社CD", "CID", ""),
		TO_SID("SCVMT_MAPPING_CODE_TO_SID", "社員CD", "SID", "会社CD"),
		TO_JOB_ID("SCVMT_MAPPING_CODE_TO_JOBID", "職位CD", "JOB_ID", "会社CD");

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

	private ConversionInfo info;

	/** 変換元 **/
	private Join sourceJoin;

	private String sourceColumnName;

	private CodeToIdType codeToIdType;

	private Optional<String> sourceCcdColumnName;

	public CodeToIdPattern(ConversionInfo info, Join join, String sourceColumnName, String codeToIdType, String sourceCcdColumnName) {
		this.info = info;
		this.sourceJoin = join;
		this.sourceColumnName = sourceColumnName;
		this.codeToIdType = CodeToIdType.valueOf(codeToIdType);

		 this.sourceCcdColumnName = (sourceCcdColumnName == null || sourceCcdColumnName.isEmpty())
			? Optional.empty()
			: Optional.of(sourceCcdColumnName);
	}

	@Override
	public ConversionSQL apply(ColumnName columnName, ConversionSQL conversionSql, boolean removeDuplicate) {
		conversionSql.addJoin(sourceJoin);

		Join idConvertJoin = this.idConvertJoin();
		conversionSql.addJoin(idConvertJoin);

		conversionSql.add(
			columnName,
			new ColumnExpression(
				idConvertJoin.getTableName().getAlias(),
				this.codeToIdType.getIdColumnName()));
		if(removeDuplicate) {
			conversionSql.addGroupingColumn(columnName);
		}

		if (this.sourceCcdColumnName.isPresent()) {
			conversionSql.addWhere( new WhereSentence(
					new ColumnName(idConvertJoin.getTableName().getAlias(), this.codeToIdType.getCcdColumnName()),
					RelationalOperator.Equal,
					Optional.of(new ColumnExpression(
							this.sourceJoin.getTableName().getAlias(),
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
				new TableFullName(
					info.getWorkDatabaseName(),
					info.getWorkSchema(),
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
