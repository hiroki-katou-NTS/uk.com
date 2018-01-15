package nts.uk.ctx.pereg.infra.entity.reghistory;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class PpedtEmployeeRegistrationHistoryPk implements Serializable {


	/**
	 * 登録者社員ID
	 */
	@Basic(optional = false)
	@Column(name = "REG_SID")
	public String registeredEmployeeID;

	private static final long serialVersionUID = 1L;

}
