package nts.uk.ctx.at.shared.infra.entity.remainingnumber.nursingcareleave.care.interimdata;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.care.interimdata.TempCareData;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "KRCDT_INTERIM_CARE")
public class KrcdtInterimCare extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcdtInterimCarePK id;

	@Column(name = "USED_DAYS")
	public double usedDays;

	@Column(name = "SCHE_RECD_ATR")
	public int scheRecdAtr;

	@Column(name = "USED_TIME")
	public int usedTime;

	@Column(name = "WORK_TYPE_CODE")
	public String workTypeCode;

	@Override
	protected Object getKey() {
		return this.id;
	}

	public TempCareData toDomain() {
		return TempCareData.createFromJavaType(this.id.sid, this.id.ymd, this.workTypeCode, this.usedDays,
				this.usedTime, this.scheRecdAtr);
	}

}