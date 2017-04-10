package nts.uk.pr.file.infra.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ReportPprmtPersonResiTaxPK {
	@Column(name="CCD")
	public String companyCode;
	@Column(name="PID")
	public String personId;
	@Column(name="Y_K")
	public int yearKey;
}
