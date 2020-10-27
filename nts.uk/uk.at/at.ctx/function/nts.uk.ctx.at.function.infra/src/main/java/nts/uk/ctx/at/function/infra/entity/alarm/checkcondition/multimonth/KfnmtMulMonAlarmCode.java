package nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.multimonth;

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
@Table(name = "KFNMT_ALST_CHKMLT_UDKEY")
public class KfnmtMulMonAlarmCode extends ContractUkJpaEntity implements Serializable {


	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KfnmtMulMonAlarmCodePK kfnmtMulMonAlarmCodePK;
	
	@ManyToOne
	@JoinColumn(name="MUL_MON_ALARM_CON_ID", referencedColumnName="MUL_MON_ALARM_CON_ID", insertable = false, updatable = false)
	public KfnmtMulMonAlarmCond mulMonAlarmCheck;

	@Override
	protected Object getKey() {
		return kfnmtMulMonAlarmCodePK;
	}

	public KfnmtMulMonAlarmCode(KfnmtMulMonAlarmCodePK kfnmtMulMonAlarmCodePK) {
		super();
		this.kfnmtMulMonAlarmCodePK = kfnmtMulMonAlarmCodePK;
	}
	
	public static KfnmtMulMonAlarmCode toEntity(String mulMonAlarmCondID,String errorAlarmCheckID ) {
		return new KfnmtMulMonAlarmCode(
				new KfnmtMulMonAlarmCodePK(mulMonAlarmCondID,errorAlarmCheckID)
				);
	}
	
	public static List<KfnmtMulMonAlarmCode> toEntity(String mulMonAlarmCondID,List<String> listErrorAlarmCheckID ) {
		List<KfnmtMulMonAlarmCode> data = new ArrayList<>();
		for(String errorAlarmCode : listErrorAlarmCheckID) {
			data.add(KfnmtMulMonAlarmCode.toEntity(mulMonAlarmCondID, errorAlarmCode));
		}
		return data;
	}
}
