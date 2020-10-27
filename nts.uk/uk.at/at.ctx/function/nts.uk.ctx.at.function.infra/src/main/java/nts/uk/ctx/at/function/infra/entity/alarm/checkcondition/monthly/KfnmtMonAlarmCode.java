package nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.monthly;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
//import nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.daily.KrcmtDailyAlarmCondition;
//import nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.daily.KrcmtDailyErrorCode;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@NoArgsConstructor
@Entity      
@Table(name = "KFNMT_MON_CHECK_PK")
public class KfnmtMonAlarmCode extends ContractUkJpaEntity implements Serializable {


	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KfnmtMonAlarmCodePK kfnmtMonAlarmCodePK;
	
	@ManyToOne
	@JoinColumn(name="MON_ALARM_CHECK_CON_ID", referencedColumnName="MON_ALARM_CHECK_CON_ID", insertable = false, updatable = false)
	public KfnmtMonAlarmCheckCon monthlyAlarmCheck;

	@Override
	protected Object getKey() {
		return kfnmtMonAlarmCodePK;
	}

	public KfnmtMonAlarmCode(KfnmtMonAlarmCodePK kfnmtMonAlarmCodePK) {
		super();
		this.kfnmtMonAlarmCodePK = kfnmtMonAlarmCodePK;
	}
	
	public static KfnmtMonAlarmCode toEntity(String monAlarmCheckConID,String errorAlarmCheckID ) {
		return new KfnmtMonAlarmCode(
				new KfnmtMonAlarmCodePK(monAlarmCheckConID,errorAlarmCheckID)
				);
	}
	
	public static List<KfnmtMonAlarmCode> toEntity(String monAlarmCheckConID,List<String> listErrorAlarmCheckID ) {
		List<KfnmtMonAlarmCode> data = new ArrayList<>();
		for(String errorAlarmCode : listErrorAlarmCheckID) {
			data.add(KfnmtMonAlarmCode.toEntity(monAlarmCheckConID, errorAlarmCode));
		}
		return data;
	}
}
