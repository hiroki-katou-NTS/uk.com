package nts.uk.cnv.core.dom.conversionsql;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nemunoki.oruta.shr.tabledefinetype.DatabaseSpec;

/**
 * コンバートSQL
 * @author ai_muto
 *
 */
@AllArgsConstructor
@Getter
public class ConversionSQL {

	private InsertSentence insert;

	private List<SelectSentence> select;

	private FromSentence from;

	private List<WhereSentence> where;

	public String build(DatabaseSpec spec) {
		String whereString = (from.getBaseTable().isPresent() && where.size() > 0) ? WhereSentence.join(where) : "";
		return insert.sql(
				SelectSentence.join(select) + "\r\n" +
				from.sql(spec) +
				whereString
			);
	}
}
