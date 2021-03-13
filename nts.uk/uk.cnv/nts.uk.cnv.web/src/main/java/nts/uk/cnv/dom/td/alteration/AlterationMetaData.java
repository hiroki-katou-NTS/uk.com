package nts.uk.cnv.dom.td.alteration;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import nts.arc.time.GeneralDateTime;

/**
 * メタ情報
 */
@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class AlterationMetaData {
	String featureId;
	GeneralDateTime time;
	String userName;
	String comment;

	public AlterationMetaData(String featureId, String userName, String comment) {
		this.featureId = featureId;
		this.time = GeneralDateTime.now();
		this.userName = userName;
		this.comment = comment;
	}
}
