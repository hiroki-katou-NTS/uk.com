package nts.uk.ctx.at.shared.infra.entity.remainingnumber.nursingcareleave.childcare.interimdata;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.interimdata.ChildTempCareData;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="KRCDT_CH_TEMP_CARE_DATA")
public class KrcdtChildTempCareData extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcdtChildTempCareDataPK id;

	@Column(name = "WORK_TYPE_CODE")
	public String workTypeCode;

	@Column(name = "USED_DAYS")
	public double usedDays;

	@Column(name = "USED_TIME")
	public int usedTime;

	@Column(name = "SCHE_RECD_ATR")
	public int scheRecdAtr;

	@Override
	protected Object getKey() {
		return this.id;
	}

	public ChildTempCareData toDomain() {
		return ChildTempCareData.createFromJavaType(this.id.sid, this.id.ymd, this.workTypeCode, this.usedDays,
				this.usedTime, this.scheRecdAtr);
	}

}