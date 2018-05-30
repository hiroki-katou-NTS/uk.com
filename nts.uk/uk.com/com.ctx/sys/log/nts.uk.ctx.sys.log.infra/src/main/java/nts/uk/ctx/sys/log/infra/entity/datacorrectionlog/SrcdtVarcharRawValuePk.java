package nts.uk.ctx.sys.log.infra.entity.datacorrectionlog;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class SrcdtVarcharRawValuePk {
	
	@Column(name = "OPERATION_ID")
	@Basic(optional = false)
	String operationId;

	@Column(name = "USER_ID")
	@Basic(optional = false)
	String userId;

	@Column(name = "TARGET_DATA_TYPE")
	@Basic(optional = false)
	int targetDataType;

	@Column(name = "STRING_KEY")
	@Basic(optional = false)
	String stringKey;
	
	@Column(name = "VALUE")
	@Basic(optional = false)
	String value;
	
}
