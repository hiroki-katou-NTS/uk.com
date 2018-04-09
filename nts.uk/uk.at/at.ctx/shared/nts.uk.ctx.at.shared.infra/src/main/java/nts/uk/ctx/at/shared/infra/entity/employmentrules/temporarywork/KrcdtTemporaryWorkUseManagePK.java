package nts.uk.ctx.at.shared.infra.entity.employmentrules.temporarywork;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KrcdtTemporaryWorkUseManagePK implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "CID")
	public String companyID;
}
