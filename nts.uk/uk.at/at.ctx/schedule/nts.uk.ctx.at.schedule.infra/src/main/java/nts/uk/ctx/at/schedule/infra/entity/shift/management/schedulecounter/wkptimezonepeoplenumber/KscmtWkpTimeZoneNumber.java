package nts.uk.ctx.at.schedule.infra.entity.shift.management.schedulecounter.wkptimezonepeoplenumber;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.timezonepeople.WorkplaceCounterStartTime;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.timezonepeople.WorkplaceCounterTimeZonePeopleNumber;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCMT_WKP_TIME_ZONE") //TODO invalid name
public class KscmtWkpTimeZoneNumber extends ContractUkJpaEntity implements Serializable {

	/** 会社ID */
	@EmbeddedId
	@Column(name = "CID")
	public String companyId;

	@Column(name = "TIME_ZONE")
	public int timeZone;

	@Override
	protected Object getKey() {
		return this.companyId;
	}

	public static List<KscmtWkpTimeZoneNumber> toEntity(String companyId, WorkplaceCounterTimeZonePeopleNumber domain) {
		return domain.getTimeZoneList().stream().map(x -> {
//			KscmtWkpTimeZoneNumberPk pk = new KscmtWkpTimeZoneNumberPk(companyId);
			KscmtWkpTimeZoneNumber result = new KscmtWkpTimeZoneNumber(companyId,x.v());
			result.contractCd = AppContexts.user().contractCode();
			return result;
		}).collect(Collectors.toList());
	}

	public static WorkplaceCounterTimeZonePeopleNumber toDomain(List<KscmtWkpTimeZoneNumber> entities) {
		List<WorkplaceCounterStartTime> workplaceCounterStartTimes =  entities.stream().map(x ->{
			return new WorkplaceCounterStartTime(x.timeZone);
		}).collect(Collectors.toList());

		//TODO fix duplicate sau khi query
		return new WorkplaceCounterTimeZonePeopleNumber(workplaceCounterStartTimes);
	}
}
