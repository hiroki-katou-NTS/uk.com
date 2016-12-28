package nts.uk.ctx.pr.core.infra.entity.personalinfo.employmentcontract;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.entity.type.LocalDateToDBConverter;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class PclmtPersonEmpContractPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "CCD")
	public String ccd;
	
	@Column(name = "PID")
	public String pId;
	
	@Column(name = "STR_D")
	@Convert(converter = LocalDateToDBConverter.class)
	public LocalDate strD;
}
