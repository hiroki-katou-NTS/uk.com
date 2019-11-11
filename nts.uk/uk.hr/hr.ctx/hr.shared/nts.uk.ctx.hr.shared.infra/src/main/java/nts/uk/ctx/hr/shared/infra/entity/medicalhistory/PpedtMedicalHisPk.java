package nts.uk.ctx.hr.shared.infra.entity.medicalhistory;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class PpedtMedicalHisPk implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "HIST_ID")
	public String hisId;
}