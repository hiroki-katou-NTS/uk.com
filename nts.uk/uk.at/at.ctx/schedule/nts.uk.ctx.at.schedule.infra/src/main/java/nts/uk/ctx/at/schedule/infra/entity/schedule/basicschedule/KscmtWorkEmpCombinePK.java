package nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 
 * @author trungtran
 */
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class KscmtWorkEmpCombinePK implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column(name = "CD")
	public String code;

	@Column(name = "CID")
	public String companyId;
}
