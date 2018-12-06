/**
 * 
 */
package nts.uk.ctx.pereg.infra.entity.person.setting.matrix.matrixdisplayset;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * @author hieult
 *
 */
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class PpestMatrixDisplaySetPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Column(name = "CID")
    public String companyID;
	
	@NotNull
	@Column(name = "USER_ID")
    public String userID;
}
