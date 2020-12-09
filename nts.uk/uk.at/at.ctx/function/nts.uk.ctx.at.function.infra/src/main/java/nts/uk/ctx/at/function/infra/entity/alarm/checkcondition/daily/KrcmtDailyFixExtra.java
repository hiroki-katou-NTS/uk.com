package nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.daily;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@NoArgsConstructor
@Entity
@Table(name = "KRCMT_DAILY_FIX_EXTRA_PK")
public class KrcmtDailyFixExtra extends ContractUkJpaEntity implements Serializable  {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcmtDailyFixExtraPK krcmtDailyFixExtraPK;
	
	@ManyToOne
	@JoinColumn(name="DAILY_ALARM_CON_ID", referencedColumnName="DAILY_ALARM_CON_ID", insertable = false, updatable = false)
	public KrcmtDailyAlarmCondition dailyAlarmCondition;
	
	@Override
	protected Object getKey() {
		return krcmtDailyFixExtraPK;
	}

	public KrcmtDailyFixExtra(KrcmtDailyFixExtraPK krcmtDailyFixExtraPK) {
		super();
		this.krcmtDailyFixExtraPK = krcmtDailyFixExtraPK;
	}
	
	public static KrcmtDailyFixExtra toEntity(String dailyAlarmConID, String errorAlarmID) {
		return new KrcmtDailyFixExtra(new KrcmtDailyFixExtraPK(dailyAlarmConID, errorAlarmID));
	}
	
	public static List<KrcmtDailyFixExtra> toEntity(String dailyAlarmConID, List<String> errorAlarmCode) {
		List<KrcmtDailyFixExtra> data = new ArrayList<>();
		for(String errorAlarmID : errorAlarmCode ) {
			data.add(KrcmtDailyFixExtra.toEntity(dailyAlarmConID, errorAlarmID));
		}
		return data;
	}

}
