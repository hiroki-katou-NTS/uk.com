package nts.uk.cnv.dom.td.alteration;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * メタ情報
 */
@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class AlterationMetaData {
	String userName;
	String comment;

}
