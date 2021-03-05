package nts.uk.cnv.dom.td.alteration;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import nts.arc.time.GeneralDateTime;
import nts.gul.text.IdentifierUtil;

/**
 * メタ情報
 */
@Getter
@EqualsAndHashCode
public class AlterationMetaData {
	String alterID;
	Feature feature;
	GeneralDateTime time;
	String userName;
	String comment;

	public AlterationMetaData(Feature feature, String userName, String comment) {
		this.alterID = IdentifierUtil.randomUniqueId();
		this.feature = feature;
		this.time = GeneralDateTime.now();
		this.userName = userName;
		this.comment = comment;
	}
}
