package nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
/**
 * 
 * @author HieuLt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class KshmtHdComCmpPk implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/*会社ID*/
	@Column(name = "CID")
	public String companyId;

}
