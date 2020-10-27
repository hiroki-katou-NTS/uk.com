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
@Table(name = "KFNMT_ALST_CHKDAY_UDKEY")
public class KrcmtDailyWkRecord extends ContractUkJpaEntity implements Serializable  {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcmtDailyWkRecordPK krcmtDailyWkRecordPK;
	
	@ManyToOne
	@JoinColumn(name="DAILY_ALARM_CON_ID", referencedColumnName="DAILY_ALARM_CON_ID", insertable = false, updatable = false)
	public KfnmtAlstChkdaydition dailyAlarmCondition;
	
	@Override
	protected Object getKey() {
		return krcmtDailyWkRecordPK;
	}

	public KrcmtDailyWkRecord(KrcmtDailyWkRecordPK krcmtDailyWkRecordPK) {
		super();
		this.krcmtDailyWkRecordPK = krcmtDailyWkRecordPK;
	}

	public static KrcmtDailyWkRecord toEntity(String dailyAlarmConID, String errorAlarmID) {
		return new KrcmtDailyWkRecord(new KrcmtDailyWkRecordPK(dailyAlarmConID, errorAlarmID));
	}
	
	public static List<KrcmtDailyWkRecord> toEntity(String dailyAlarmConID, List<String> errorAlarmCode) {
		List<KrcmtDailyWkRecord> data = new ArrayList<>();
		for(String errorAlarmID : errorAlarmCode ) {
			data.add(KrcmtDailyWkRecord.toEntity(dailyAlarmConID, errorAlarmID));
		}
		return data;
	}

}
