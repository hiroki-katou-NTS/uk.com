package nts.uk.cnv.dom.conversionsql;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.cnv.dom.service.ConversionInfo;

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

	private String sql(ConversionInfo info) {
		return
			left.sql() + " = " + right.sql()
			+ (collate.isPresent()
				? " " + info.getDatebaseType().spec().collate()
				: "");
	}

	public static String join(List<OnSentence> sentences, ConversionInfo info) {
		return " ON " + String.join(" AND" + "\r\n    ", sentences.stream().map(s -> s.sql(info)).collect(Collectors.toList()));
	}
}
