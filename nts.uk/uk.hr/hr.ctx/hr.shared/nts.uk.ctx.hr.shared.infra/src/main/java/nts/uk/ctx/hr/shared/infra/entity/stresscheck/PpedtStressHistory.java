package nts.uk.ctx.hr.shared.infra.entity.stresscheck;

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
@Table(name = "PPEDT_STRESS_HIST")
public class PpedtStressHistory extends UkJpaEntity implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public PpedtStressHistoryPk ppedtStressHistoryPk;
	
	/**
	 * 会社ID
	 */
	@Column(name = "CID")
	public String companyID;

	/**
	 * 社員ID
	 */
	@Column(name = "SID")
	public String employeeID;

	/**
	 * 受診日
	 */
	@Column(name = "START_DATE")
	@Convert(converter = GeneralDateToDBConverter.class)
	public GeneralDate startDate;

	/**
	 * 受診履歴失効日
	 */
	@Column(name = "END_DATE")
	@Convert(converter = GeneralDateToDBConverter.class)
	public GeneralDate endDate;

	@Override
	protected Object getKey() {
		return ppedtStressHistoryPk;
	}
}
