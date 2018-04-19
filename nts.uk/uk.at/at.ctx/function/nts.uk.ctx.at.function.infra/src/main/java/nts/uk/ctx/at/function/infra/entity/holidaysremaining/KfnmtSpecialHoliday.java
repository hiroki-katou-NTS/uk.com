package nts.uk.ctx.at.function.infra.entity.holidaysremaining;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.dom.holidaysremaining.ExecutionCode;
import nts.uk.ctx.at.function.dom.holidaysremaining.SpecialHolidayOutput;
import nts.uk.ctx.at.function.dom.processexecution.LastExecDateTime;
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
	public KfnmtSpecialHolidayPk kfnmtSpecialHolidayPk;
	
	@Override
	protected Object getKey() {
		return kfnmtSpecialHolidayPk;
	}

	public static KfnmtSpecialHoliday toEntity(SpecialHolidayOutput domain) {
		return new KfnmtSpecialHoliday(
				new KfnmtSpecialHolidayPk(domain.getCode(), domain.getCompanyID(), domain.getHolidayCode()));
	}

	public SpecialHolidayOutput toDomain() {
		return new SpecialHolidayOutput(this.kfnmtSpecialHolidayPk.cid,
				this.kfnmtSpecialHolidayPk.cd,
				this.kfnmtSpecialHolidayPk.specialCd);
				
	}

}
