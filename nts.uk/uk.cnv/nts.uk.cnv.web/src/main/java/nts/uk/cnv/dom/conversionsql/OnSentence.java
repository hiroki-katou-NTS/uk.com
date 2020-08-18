package nts.uk.cnv.dom.conversionsql;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;

/**
 * ON句
 * @author ai_muto
 *
 */
@AllArgsConstructor
public class OnSentence {
	/** 左辺 */
	private ColumnName left;

	/** 右辺 */
	private ColumnName right;
	
	private String sql() {
		return left.sql() + " = " + right.sql();
	}
	
	public static String join(List<OnSentence> sentences) {
		return " ON " + String.join(" AND" + "\r\n    ", sentences.stream().map(s -> s.sql()).collect(Collectors.toList()));
	}
}
