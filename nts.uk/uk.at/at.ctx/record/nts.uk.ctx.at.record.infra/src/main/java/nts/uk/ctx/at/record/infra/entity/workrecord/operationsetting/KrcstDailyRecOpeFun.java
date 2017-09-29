package nts.uk.ctx.at.record.infra.entity.workrecord.operationsetting;

import java.math.BigDecimal;

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
	public BigDecimal registerTotalTimeCheerAtr;
	
	@Column(name = "COMPLETED_DIS_ONE_MONTH_ATR")
	public BigDecimal completedDisOneMonthAtr;
	
	@Column(name = "DIS_CONFIRM_MESSAGE_ATR")
	public BigDecimal disConfirmMessageAtr;
	
	@Column(name = "USE_INITIAL_VALUE_SET_ATR")
	public BigDecimal useInitialValueSetAtr;
	
	@Column(name = "USE_WORK_DETAIL_ATR")
	public BigDecimal useWorkDetailAtr;
	
	@Column(name = "REGISTER_ACTUAL_EXCEED_ATR")
	public BigDecimal registerActualExceedAtr;
	
	@Column(name = "START_APP_SCREEN_ATR")
	public BigDecimal startAppScreenAtr;
	
	@Column(name = "CONFIRM_SUBMIT_APP_ATR")
	public BigDecimal confirmSubmitAppAtr;
	
	@Column(name = "CONFIRM_BY_YOURSELF_ATR")
	public BigDecimal confirmByYourselfAtr;
	
	@Column(name = "INITIAL_VALUE_SETTING_ATR")
	public BigDecimal initialValueSettingAtr;
	
	@Column(name = "CONFIRM_BY_SUPERVISOR_ATR")
	public BigDecimal confirmBySupervisorAtr;
	
	@Column(name = "YOURSELF_CONFIRM_WHEN_ERROR")
	public BigDecimal yourselfConfirmWhenError;
	
	@Column(name = "SUPERVISOR_CONFIRM_WHEN_ERROR")
	public BigDecimal supervisorConfirmWhenError;

	@Override
	protected Object getKey() {
		return this.cid;
	}
	
	public KrcstDailyRecOpeFun() {
		super();
	}
	
}
