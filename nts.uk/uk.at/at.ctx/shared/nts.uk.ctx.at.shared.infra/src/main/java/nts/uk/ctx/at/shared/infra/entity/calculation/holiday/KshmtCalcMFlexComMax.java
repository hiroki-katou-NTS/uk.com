package nts.uk.ctx.at.shared.infra.entity.calculation.holiday;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;


/**
 * The persistent class for the KSHMT_CALC_M_FLEX_COM_MAX database table.
 * @author HoangNDH
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="KSHMT_CALC_M_FLEX_COM_MAX")
public class KshmtCalcMFlexComMax extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KshmtCalcMFlexComMaxPK kshmtCalcMFlexComMaxPK;

	/** 補填可能時間 */
	@Column(name="SUPPLEMTABLE_DAYS")
	private double supplementableDays;

	@Override
	protected Object getKey() {
		return kshmtCalcMFlexComMaxPK;
	}
}