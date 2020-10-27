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
@Table(name = "KFNMT_ALST_CHKDAY_ERAL")
public class KrcmtDailyErrorCode extends ContractUkJpaEntity implements Serializable  {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	
	public KrcmtDailyErrorCodePK krcmtDailyErrorCodePK;
	
	@ManyToOne
	@JoinColumn(name="DAILY_ALARM_CON_ID", referencedColumnName="DAILY_ALARM_CON_ID", insertable = false, updatable = false)
	public KfnmtAlstChkdaydition dailyAlarmCondition;
	
	@Override
	protected Object getKey() {
		return krcmtDailyErrorCodePK;
	}

	public KrcmtDailyErrorCode(KrcmtDailyErrorCodePK krcmtDailyErrorCodePK) {
		super();
		this.krcmtDailyErrorCodePK = krcmtDailyErrorCodePK;
	}
	
	public static KrcmtDailyErrorCode toEntity(String dailyAlarmConID, String errorAlarmCode) {
		return new KrcmtDailyErrorCode(new KrcmtDailyErrorCodePK(dailyAlarmConID, errorAlarmCode));
	}
	
	public static List<KrcmtDailyErrorCode> toEntity(String dailyAlarmConID, List<String> listErrorAlarmCode) {
		List<KrcmtDailyErrorCode> data = new ArrayList<>();
		for(String errorAlarmCode : listErrorAlarmCode ) {
			data.add(KrcmtDailyErrorCode.toEntity(dailyAlarmConID, errorAlarmCode));
		}
		return data;
	}
	
}
