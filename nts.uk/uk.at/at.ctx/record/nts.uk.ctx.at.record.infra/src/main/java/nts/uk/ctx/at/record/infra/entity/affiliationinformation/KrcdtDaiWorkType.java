package nts.uk.ctx.at.record.infra.entity.affiliationinformation;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.affiliationinformation.WorkTypeOfDailyPerformance;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * @author nampt 日別実績の勤務種別
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCDT_DAI_WORKTYPE")
public class KrcdtDaiWorkType extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcdtDaiWorkTypePK krcdtDaiWorkTypePK;

	@Column(name = "WORKTYPE_CODE")
	public String workTypeCode;

	@Override
	protected Object getKey() {
		return this.krcdtDaiWorkTypePK;
	}

	public WorkTypeOfDailyPerformance toDomain() {
		WorkTypeOfDailyPerformance workTypeOfDailyPerformance = new WorkTypeOfDailyPerformance(
				this.krcdtDaiWorkTypePK.employeeId, this.krcdtDaiWorkTypePK.ymd, this.workTypeCode);
		return workTypeOfDailyPerformance;
	}

	public static KrcdtDaiWorkType toEntity(WorkTypeOfDailyPerformance workTypeOfDailyPerformance){
		return new KrcdtDaiWorkType(
				new KrcdtDaiWorkTypePK(workTypeOfDailyPerformance.getEmployeeId(), workTypeOfDailyPerformance.getDate()),
				workTypeOfDailyPerformance.getWorkTypeCode().v());
	}
}
