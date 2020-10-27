package nts.uk.ctx.at.schedule.infra.entity.shift.workpairpattern;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 
 * @author sonnh1
 *
 */
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KscmtPairGrpWkpPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "WKP_ID")
	public String workplaceId;

	@Column(name = "GROUP_NO")
	public int groupNo;
}
