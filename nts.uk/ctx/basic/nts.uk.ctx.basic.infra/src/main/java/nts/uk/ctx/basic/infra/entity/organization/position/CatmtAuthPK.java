package nts.uk.ctx.basic.infra.entity.organization.position;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class CatmtAuthPK implements Serializable{
	private static final long serialVersionUID = 1L;

	/**会社コード*/
	@Basic(optional = false)	
	@Column(name = "CCD")
	public String companyCode;
		
	/**権限範囲区分*/
	@Basic(optional = false)
	@Column(name = "AUTH_SCOPE_ATR")
	public String authScopeAtr;
	
	/**権限コード*/
	@Basic(optional = false)
	@Column(name = "AUTHCD")
	public String authCode;
}
