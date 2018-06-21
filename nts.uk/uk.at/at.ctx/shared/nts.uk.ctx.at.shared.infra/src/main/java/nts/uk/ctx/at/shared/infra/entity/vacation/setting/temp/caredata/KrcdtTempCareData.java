package nts.uk.ctx.at.shared.infra.entity.vacation.setting.temp.caredata;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;


/**
 * The persistent class for the KRCDT_TEMP_CARE_DATA database table.
 * 
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="KRCDT_TEMP_CARE_DATA")
public class KrcdtTempCareData extends UkJpaEntity implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@EmbeddedId
	private KrcdtTempCareDataPK id;

	/** The annlea use. */
	@Column(name="ANNLEA_USE")
	private double annleaUse;

	/** The sche recd atr. */
	@Column(name="SCHE_RECD_ATR")
	private int scheRecdAtr;

	/** The timeeal use priv goout. */
	@Column(name="TIMEEAL_USE_PRIV_GOOUT")
	private int timeealUsePrivGoout;

	/** The work type code. */
	@Column(name="WORK_TYPE_CODE")
	private String workTypeCode;

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.id;
	}

}