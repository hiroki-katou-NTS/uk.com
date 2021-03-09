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
	String featureId;
	GeneralDateTime time;
	String userName;
	String comment;

	public AlterationMetaData(String featureId, String userName, String comment) {
		this.alterID = IdentifierUtil.randomUniqueId();
		this.featureId = featureId;
		this.time = GeneralDateTime.now();
		this.userName = userName;
		this.comment = comment;
	}
}
