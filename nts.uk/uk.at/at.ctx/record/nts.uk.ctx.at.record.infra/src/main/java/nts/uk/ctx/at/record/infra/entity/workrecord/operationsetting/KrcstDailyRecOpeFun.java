package nts.uk.ctx.at.record.infra.entity.workrecord.operationsetting;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name = "KRCST_DAILY_REC_OPE_FUN")
public class KrcstDailyRecOpeFun extends UkJpaEntity {

	@Id
	@Column(name = "CID")
	public String cid;
	
	@Column(name = "REGISTER_TOTAL_TIME_CHEER_ATR")
	public int registerTotalTimeCheerAtr;
	
	@Column(name = "COMPLETED_DIS_ONE_MONTH_ATR")
	public int completedDisOneMonthAtr;
	
	@Column(name = "DIS_CONFIRM_MESSAGE_ATR")
	public int disConfirmMessageAtr;
	
	@Column(name = "USE_INITIAL_VALUE_SET_ATR")
	public int useInitialValueSetAtr;
	
	@Column(name = "USE_WORK_DETAIL_ATR")
	public int useWorkDetailAtr;
	
	@Column(name = "REGISTER_ACTUAL_EXCEED_ATR")
	public int registerActualExceedAtr;
	
	@Column(name = "START_APP_SCREEN_ATR")
	public int startAppScreenAtr;
	
	@Column(name = "CONFIRM_SUBMIT_APP_ATR")
	public int confirmSubmitAppAtr;
	
	@Column(name = "CONFIRM_BY_YOURSELF_ATR")
	public int confirmByYourselfAtr;
	
	@Column(name = "INITIAL_VALUE_SETTING_ATR")
	public int initialValueSettingAtr;
	
	@Column(name = "CONFIRM_BY_SUPERVISOR_ATR")
	public int confirmBySupervisorAtr;
	
	@Column(name = "YOURSELF_CONFIRM_WHEN_ERROR")
	public int yourselfConfirmWhenError;
	
	@Column(name = "SUPERVISOR_CONFIRM_WHEN_ERROR")
	public int supervisorConfirmWhenError;

	@Override
	protected Object getKey() {
		return this.cid;
	}
	
	public KrcstDailyRecOpeFun() {
		super();
	}
	
}
