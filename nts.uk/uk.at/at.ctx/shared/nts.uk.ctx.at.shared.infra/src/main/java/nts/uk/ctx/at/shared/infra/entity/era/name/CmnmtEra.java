package nts.uk.ctx.at.shared.infra.entity.era.name;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.infra.data.entity.type.GeneralDateToDBConverter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;


/**
 * The persistent class for the CMNMT_ERA database table.
 * 
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="CMNMT_ERA")
public class CmnmtEra extends UkJpaEntity implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The hist id. */
	@Id
	@Column(name="HIST_ID")
	private String histId;

	/** The end D. */
	@Column(name="END_D")
	@Convert(converter = GeneralDateToDBConverter.class)
	private GeneralDate endD;

	/** The era mark. */
	@Column(name="ERA_MARK")
	private String eraMark;

	/** The era name. */
	@Column(name="ERA_NAME")
	private String eraName;

	/** The fix atr. */
	@Column(name="FIX_ATR")
	private BigDecimal fixAtr;
	
	/** The str D. */
	@Column(name="STR_D")
	@Convert(converter = GeneralDateToDBConverter.class)
	private GeneralDate strD;

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.histId;
	}

}