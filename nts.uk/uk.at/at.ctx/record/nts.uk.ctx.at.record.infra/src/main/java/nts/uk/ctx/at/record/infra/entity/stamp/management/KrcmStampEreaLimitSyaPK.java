package nts.uk.ctx.at.record.infra.entity.stamp.management;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
/**
 * 
 * @author nws_vandv
 *
 */
public class KrcmStampEreaLimitSyaPK implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/** 社員ID */
	@NotNull
	@Column(name = "SID")
	private String sId;
}
