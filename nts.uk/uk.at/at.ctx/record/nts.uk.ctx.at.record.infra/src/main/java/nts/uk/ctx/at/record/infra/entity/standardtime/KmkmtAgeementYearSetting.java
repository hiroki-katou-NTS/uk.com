package nts.uk.ctx.at.record.infra.entity.standardtime;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_36AGR_YEAR")
public class KmkmtAgeementYearSetting extends ContractUkJpaEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KmkmtAgeementYearSettingPK kmkmtAgeementYearSettingPK;
	
	@Column(name ="ERROR_YEARLY")
	public int errorOneYear;
	
	@Column(name ="ALARM_YEARLY")
	public int alarmOneYear;
	
	@Override
	protected Object getKey() {
		return this.kmkmtAgeementYearSettingPK;
	}
}
