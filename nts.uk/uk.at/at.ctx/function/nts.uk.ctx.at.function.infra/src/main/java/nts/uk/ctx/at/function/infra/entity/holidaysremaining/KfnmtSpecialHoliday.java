package nts.uk.ctx.at.function.infra.entity.holidaysremaining;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 出力する特別休暇
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KFNMT_SPECIAL_HOLIDAY")
public class KfnmtSpecialHoliday extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@EmbeddedId
	public KfnmtSpecialHolidayPk specialHolidayPk;

	@Override
	protected Object getKey() {
		return specialHolidayPk;
	}

}
