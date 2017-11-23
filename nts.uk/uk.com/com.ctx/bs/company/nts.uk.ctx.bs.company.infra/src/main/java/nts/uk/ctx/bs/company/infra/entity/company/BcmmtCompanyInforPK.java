package nts.uk.ctx.bs.company.infra.entity.company;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class BcmmtCompanyInforPK implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 会社ID **/
	@Column(name = "CID")
	public String companyId;
	
	/** 会社コード */
	@Column(name = "CCD")
	public String companyCode;
	
	/** 契約コード */
	@Column(name ="CONTRACT_CD")
	public String contractCd;
}
