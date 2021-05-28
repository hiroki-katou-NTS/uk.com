package nts.uk.cnv.core.dom.conversionsql;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nemunoki.oruta.shr.tabledefinetype.DatabaseSpec;

/**
 * ON句
 * @author ai_muto
 *
 */
@Getter
@AllArgsConstructor
public class OnSentence {
	/** 左辺 */
	private ColumnName left;

	/** 右辺 */
	private ColumnName right;

	/** 照合順序 */
	private Optional<String> collate;

	private String sql(DatabaseSpec spec) {
		return
			left.sql() + " = " + right.sql()
			+ (collate.isPresent()
				? " " + spec.collate()
				: "");
	}

	public static String join(List<OnSentence> sentences, DatabaseSpec spec) {
		return " ON " + String.join(" AND" + "\r\n    ", sentences.stream().map(s -> s.sql(spec)).collect(Collectors.toList()));
	}
}
