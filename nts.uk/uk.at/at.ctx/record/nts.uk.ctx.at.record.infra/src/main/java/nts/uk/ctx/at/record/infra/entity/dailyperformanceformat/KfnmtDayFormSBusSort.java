package nts.uk.ctx.at.record.infra.entity.dailyperformanceformat;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
/**
 * 勤務種別で参照する場合の並び順（スマホ版）- 日別
 * @author anhdt
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="KFNMT_DAY_FORM_S_BUS_SORT")
public class KfnmtDayFormSBusSort extends ContractUkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KfnmtDayFormSBusSortPK kfnmtDayFormSBusSortPK;

	/** 順番 */
	@Column(name = "DISP_ORDER")
	public BigDecimal order;

	@Override
	protected Object getKey() {
		return this.kfnmtDayFormSBusSortPK;
	}
}
