package nts.uk.pr.file.infra.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ReportQtxmtResidentialTaxPk implements Serializable{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	@Basic(optional = false)
	@Column(name ="CCD")
	public String companyCd;
	
	@Basic(optional = false)
	@Column(name ="RESI_TAX_CD")
	public String resiTaxCode;
}
