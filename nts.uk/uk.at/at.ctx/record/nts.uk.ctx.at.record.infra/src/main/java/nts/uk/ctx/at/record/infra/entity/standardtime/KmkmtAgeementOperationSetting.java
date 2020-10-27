package nts.uk.ctx.at.record.infra.entity.standardtime;

import java.io.Serializable;

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
@Table(name="KSRMT_36AGR_OPERATION")
public class KmkmtAgeementOperationSetting extends ContractUkJpaEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
    public KmkmtAgeementOperationSettingPK kmkmtAgeementOperationSettingPK;
	
	@Column(name ="START_MONTH")
	public int startingMonth;

	@Column(name ="MCLOSE_DATE")
	public int closingDate;

	@Column(name ="APP_USE_ATR")
	public boolean appUseAtr;
	
	@Column(name ="ANNUAL_UNIT_ATR")
	public boolean annualUnitAtr;
	
	@Override
	protected Object getKey() {
		return this.kmkmtAgeementOperationSettingPK;
	}
}
