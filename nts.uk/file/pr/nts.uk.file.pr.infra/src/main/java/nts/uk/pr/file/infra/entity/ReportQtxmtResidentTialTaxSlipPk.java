package nts.uk.pr.file.infra.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ReportQtxmtResidentTialTaxSlipPk {
	@Column(name="CCD")
	public String companyCode;
	
	@Column(name="RESI_TAX_CD")
	public String residentTaxCode;
	
	@Column(name="YM_K")
	public int yearMonth;
}
