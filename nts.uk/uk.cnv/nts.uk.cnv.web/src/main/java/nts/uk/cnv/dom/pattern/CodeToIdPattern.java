package nts.uk.cnv.dom.pattern;

import java.util.ArrayList;
import java.util.List;

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
 * コードからIDへの変換
 * @author ai_muto
 *
 * 変換元テーブル、変換元列名には変換元のコードの列を指定。
 * 変換テーブルをFrom句に追加する
 */
@Getter
public class CodeToIdPattern extends ConversionPattern {

	//TODO: 変換タイプを完成させる
	//TODO: 履歴あるテーブルどうしよう...
	public enum CodeToIdType{
		TO_CID("SCVMT_CONVERT_TO_CID", "CODE", "ID"),
		TO_SID("BSYMT_EMP_DTA_MNG_INFO", "SCD", "SID"),
		TO_JOB_ID("BSYMT_JOB_INFO", "JOB_CD", "JOB_ID");

		private final String tableName;
		private final String idColumnName;
		private final String codeColumnName;

		CodeToIdType(String tableName, String codeColumnName, String idColumnName){
			this.tableName = tableName;
			this.codeColumnName = codeColumnName;
			this.idColumnName = idColumnName;
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
	}

	/** 変換元 **/
	private Join sourceJoin;

	private String sourceColumnName;

	private CodeToIdType codeToIdType;

	public CodeToIdPattern(ConversionInfo info, Join join, String sourceColumnName, String codeToIdType) {
		super(info);
		this.sourceJoin = join;
		this.sourceColumnName = sourceColumnName;
		this.codeToIdType = CodeToIdType.valueOf(codeToIdType);
	}

	@Override
	public ConversionSQL apply(ConversionSQL conversionSql) {
		conversionSql.getFrom().addJoin(sourceJoin);

		Join idConvertJoin = this.idConvertJoin();
		conversionSql.getFrom().addJoin(idConvertJoin);

		conversionSql.getSelect().add(
				SelectSentence.createNotFormat(sourceJoin.tableName.getAlias(), this.codeToIdType.getIdColumnName()));

		//TODO: CODEだけじゃ特定できない（CIDとか履歴とか）からWhere条件が必要だが未実装
		//conversionSql.getWhere().add(new WhereSentence(
		//
		//	));

		return conversionSql;
	}

	private Join idConvertJoin() {
		List<OnSentence> onSentences = new ArrayList<>();
		onSentences.add(new OnSentence(
				new ColumnName(this.sourceJoin.tableName.getAlias(), this.sourceColumnName),
				new ColumnName(this.mappingAlias(), this.codeToIdType.getCodeColumnName())
			));

		return new Join(
				new TableName(
					info.getSourceDatabaseName(),
					info.getSourceSchema(),
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
