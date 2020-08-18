package nts.uk.cnv.dom.pattern;

import nts.uk.cnv.dom.conversionsql.ConversionSQL;
import nts.uk.cnv.dom.conversionsql.Join;
import nts.uk.cnv.dom.conversionsql.SelectSentence;
import nts.uk.cnv.dom.service.ConversionInfo;

/**
 * コードからIDへの変換
 * @author ai_muto
 * 
 * 変換元テーブル、変換元列名には変換元のコードの列を指定。
 * 変換テーブルをFrom句に追加する
 */
public class CodeToIdPattern extends ConversionPattern {

	private Join join;
	
	private String idColumnname;

	public CodeToIdPattern(ConversionInfo info, Join join, String idColumnname) {
		super(info);
		this.join = join;
		this.idColumnname = idColumnname;
	}

	@Override
	public ConversionSQL apply(ConversionSQL conversionSql) {
		conversionSql.getFrom().addJoin(join);
		conversionSql.getSelect().add(
				SelectSentence.createNotFormat(join.tableName.getAlias(), idColumnname));
		return conversionSql;
	}

}
