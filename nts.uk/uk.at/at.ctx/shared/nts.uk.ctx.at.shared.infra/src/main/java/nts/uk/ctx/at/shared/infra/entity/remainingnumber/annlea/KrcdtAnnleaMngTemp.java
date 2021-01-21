package nts.uk.ctx.at.shared.infra.entity.remainingnumber.annlea;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TempAnnualLeaveManagement;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ManagementDays;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ScheduleRecordAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.TimeHoliday;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * エンティティ：暫定年休管理データ
 * @author shuichu_ishida
 */
@Entity
@Table(name = "KRCDT_ANNLEA_MNG_TEMP")
@NoArgsConstructor
public class KrcdtAnnleaMngTemp extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KrcdtAnnleaMngTempPK PK;
	
	/** 年休使用 */
	@Column(name = "ANNLEA_USE")
	public double annualLeaveUse;
	
	/** 時間年休使用 */
	@Column(name = "TIMEAL_USE")
	public int timeAnnualLeaveUse;
	
	/** 出勤前時間年休使用 */
	@Column(name = "TIMEAL_USE_BFR_WORK")
	public int timeAnnualLeaveUseBeforeWork;
	
	/** 退勤後時間年休使用 */
	@Column(name = "TIMEAL_USE_AFT_WORK")
	public int timeAnnualLeaveUseAfterWork;
	
	/** 出勤前2時間年休使用 */
	@Column(name = "TIMEAL_USE_BFR_WORK2")
	public int timeAnnualLeaveUseBeforeWork2;
	
	/** 退勤後2時間年休使用 */
	@Column(name = "TIMEAL_USE_AFT_WORK2")
	public int timeAnnualLeaveUseAfterWork2;
	
	/** 私用外出時間年休使用 */
	@Column(name = "TIMEAL_USE_PRIV_GOOUT")
	public int timeAnnualLeaveUsePrivateGoOut;
	
	/** 組合外出時間年休使用 */
	@Column(name = "TIMEAL_USE_UNION_GOOUT")
	public int timeAnnualLeaveUseUnionGoOut;

	/** 勤務種類コード */
	@Column(name = "WORK_TYPE_CODE")
	public String workTypeCode;
	
	/** 予定実績区分 */
	@Column(name = "SCHE_RECD_ATR")
	public int scheduleRecordAtr;
	
	/**
	 * キー取得
	 */
	@Override
	protected Object getKey() {
		return this.PK;
	}
	
	/**
	 * ドメインに変換
	 * @return 暫定年休管理データ
	 */
	public TempAnnualLeaveManagement toDomain(){
		
		return TempAnnualLeaveManagement.of(
				this.PK.employeeId,
				this.PK.ymd,
				new ManagementDays(this.annualLeaveUse),
				new TimeHoliday(this.timeAnnualLeaveUse),
				new TimeHoliday(this.timeAnnualLeaveUseBeforeWork),
				new TimeHoliday(this.timeAnnualLeaveUseAfterWork),
				new TimeHoliday(this.timeAnnualLeaveUseBeforeWork2),
				new TimeHoliday(this.timeAnnualLeaveUseAfterWork2),
				new TimeHoliday(this.timeAnnualLeaveUsePrivateGoOut),
				new TimeHoliday(this.timeAnnualLeaveUseUnionGoOut),
				new WorkTypeCode(this.workTypeCode),
				EnumAdaptor.valueOf(this.scheduleRecordAtr, ScheduleRecordAtr.class));
	}
	
	/**
	 * ドメインから変換　（for Insert）
	 * @param domain 暫定年休管理データ
	 */
	public void fromDomainForPersist(TempAnnualLeaveManagement domain){
		
		this.PK = new KrcdtAnnleaMngTempPK(
				domain.getEmployeeId(),
				domain.getYmd());
		this.fromDomainForUpdate(domain);
	}
	
	/**
	 * ドメインから変換　(for Update)
	 * @param domain 暫定年休管理データ
	 */
	public void fromDomainForUpdate(TempAnnualLeaveManagement domain){
		
		this.annualLeaveUse = domain.getAnnualLeaveUse().v();
		this.timeAnnualLeaveUse = domain.getTimeAnnualLeaveUse().v();
		this.timeAnnualLeaveUseBeforeWork = domain.getTimeAnnualLeaveUseBeforeWork().v();
		this.timeAnnualLeaveUseAfterWork = domain.getTimeAnnualLeaveUseAfterWork().v();
		this.timeAnnualLeaveUseBeforeWork2 = domain.getTimeAnnualLeaveUseBeforeWork2().v();
		this.timeAnnualLeaveUseAfterWork2 = domain.getTimeAnnualLeaveUseAfterWork2().v();
		this.timeAnnualLeaveUsePrivateGoOut = domain.getTimeAnnualLeaveUsePrivateGoOut().v();
		this.timeAnnualLeaveUseUnionGoOut = domain.getTimeAnnualLeaveUseUnionGoOut().v();
		this.workTypeCode = domain.getWorkType().v();
		this.scheduleRecordAtr = domain.getScheduleRecordAtr().value;
	}
}
