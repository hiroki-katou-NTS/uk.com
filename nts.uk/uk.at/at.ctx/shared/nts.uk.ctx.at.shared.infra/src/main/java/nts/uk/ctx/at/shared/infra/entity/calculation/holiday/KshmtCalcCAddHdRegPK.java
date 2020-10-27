package nts.uk.ctx.at.shared.infra.entity.calculation.holiday;

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
public class KshmtCalcCAddHdRegPK  implements Serializable{
	private static final long serialVersionUID = 1L;
	/**会社ID*/
	@Column(name = "CID")
	public String companyId;
}
