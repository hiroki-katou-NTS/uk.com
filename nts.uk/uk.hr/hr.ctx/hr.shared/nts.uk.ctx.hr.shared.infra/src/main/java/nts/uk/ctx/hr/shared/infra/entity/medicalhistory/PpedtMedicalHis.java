package nts.uk.ctx.hr.shared.infra.entity.medicalhistory;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.entity.type.GeneralDateToDBConverter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PPEDT_JYUSIN_HIST_HIST")
public class PpedtMedicalHis extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public PpedtMedicalHisPk ppedtMedicalHisPk;
	
	@Column(name = "CID")
	public String cId;

	@Column(name = "SID")
	public String sId;

	@Column(name = "START_DATE")
	@Convert(converter = GeneralDateToDBConverter.class)
	public GeneralDate startDate;

	@Column(name = "END_DATE")
	@Convert(converter = GeneralDateToDBConverter.class)
	public GeneralDate endDate;

	@Override
	protected Object getKey() {
		return ppedtMedicalHisPk;
	}
}