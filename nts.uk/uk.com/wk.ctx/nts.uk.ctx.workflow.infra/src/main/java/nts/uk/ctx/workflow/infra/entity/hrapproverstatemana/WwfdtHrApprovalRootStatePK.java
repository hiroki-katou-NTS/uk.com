package nts.uk.ctx.workflow.infra.entity.hrapproverstatemana;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class WwfdtHrApprovalRootStatePK implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**ID*/
	@Column(name="ID")
	public String rootStateID;
}
