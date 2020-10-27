package nts.uk.ctx.at.shared.infra.entity.ot.zerotime;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
/**
 * 
 * @author phongtq
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class KshmtCalcDOvdPK implements Serializable{
	private static final long serialVersionUID = 1L;
	/**会社ID*/
	@Column(name = "CID")
	public String companyId;
}

