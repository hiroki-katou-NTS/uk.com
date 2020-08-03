package nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkSchedule;
import nts.uk.ctx.at.schedule.infra.entity.shift.schedulehorizontal.KscmtTotalEvalOrderItem;
import nts.uk.ctx.at.schedule.infra.entity.shift.schedulehorizontal.KscstHoriCalDaysSetItem;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.affiliationinfor.AffiliationInforOfDailyAttd;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
import nts.uk.shr.infra.data.entity.UkJpaEntity;



/**
 * 勤務予定の基本情報
 * @author HieuLt
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCDT_SCH_BASIC_INFO")
public class KscdtSchBasicInfo extends ContractUkJpaEntity {
	@EmbeddedId
	public KscdtSchBasicInfoPK pk;
	
	/** "予定確定区分 ---true:確定済み---false:未確定" **/								
	@Column(name = "DECISION_STATUS")
	public boolean confirmedATR;
	/** 雇用コード **/
	@Column(name = "EMP_CD")
	public String empCd;
	/** 職位ID **/
	@Column(name = "JOB_ID")
	public String jobId;
	/** 職場ID **/
	@Column(name = "WKP_ID")
	public String wkpId;
	/** 分類コード**/ 
	@Column(name = "CLS_CD ")
	public String clsCd;
	/** 勤務種別コード **/
	@Column(name = "BUSTYPE_CD")
	public String busTypeCd;
	/** 看護区分 **/
	@Column(name = "NURSE_LICENSE")
	public String nurseLicense;
	/** 勤務種類コード **/
	@Column(name = "WKTP_CD")
	public String wktpCd;
	/**就業時間帯コード**/
	@Column(name = "WKTM_CD")
	public String wktmCd;
	/** "直行区分---true:直行する---false:直行しない"**/
	@Column(name = "GO_STRAIGHT_ATR")
	public boolean goStraightAtr;
	/** "直帰区分---true:直帰する---false:直帰しない"**/
	@Column(name = "BACK_STRAIGHT_ATR")
	public int backStraightAtr;
	
	@OneToOne
	@JoinColumns({ @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
		@JoinColumn(name = "YMD", referencedColumnName = "YMD", insertable = false, updatable = false)
	})
	public KscdtSchTime kscdtSchTime;

	@OneToMany(targetEntity = KscdtSchEditState.class, mappedBy = "kscdtSchBasicInfo", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinTable(name = "KSCDT_SCH_EDIT_STATE")
	public List<KscdtSchEditState> editStates;

	@OneToMany(targetEntity = KscdtSchAtdLvwTime.class, mappedBy = "kscdtSchBasicInfo", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinTable(name = "KSCDT_SCH_ATD_LVW_TIME")
	public List<KscdtSchAtdLvwTime> atdLvwTimes;
	
	@OneToMany(targetEntity = KscdtSchShortTimeTs.class, mappedBy = "kscdtSchBasicInfo", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinTable(name = "KSCDT_SCH_SHORTTIME_TS")
	public List<KscdtSchShortTimeTs> schShortTimeTs;
	
	@OneToMany(targetEntity = KscdtSchBreakTs.class, mappedBy = "kscdtSchBasicInfo", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinTable(name = "KSCDT_SCH_BREAK_TS")
	public List<KscdtSchBreakTs> breakTs;
	
	
	
	public static KscdtSchBasicInfo toEntity(WorkSchedule workSchedule) {
		// 勤務予定.勤務情報 
		AffiliationInforOfDailyAttd workInfo = workSchedule.getAffInfo();
		
		// 勤務予定.勤務情報
		WorkInfoOfDailyAttendance workInfoOfDaily = workSchedule.getWorkInfo();
		
		// 勤務予定.勤務情報.勤務実績の勤務情報
		WorkInformation workInformation = workInfoOfDaily.getRecordInfo();
		
		
		KscdtSchBasicInfoPK basicInfoPK = new KscdtSchBasicInfoPK(workSchedule.getEmployeeID(), workSchedule.getYmd());
		
		// null đang đợi QA
		KscdtSchBasicInfo basicInfo = new KscdtSchBasicInfo(basicInfoPK, workSchedule.getConfirmedATR().value == 1 ? true : false, workInfo.getEmploymentCode().v(), workInfo.getJobTitleID(), workInfo.getWplID()
				, workInfo.getClsCode().v(),workInfo.getBonusPaySettingCode().v(), null, workInformation.getWorkTypeCode().v(), workInformation.getWorkTimeCode().v(), workInfoOfDaily.getGoStraightAtr().value == 1 ? true : false, 
				workInfoOfDaily.getBackStraightAtr().value);
		return basicInfo;
	}
	
	@Override
	protected Object getKey() {
		
		return this.pk;
	}

	public KscdtSchBasicInfo(KscdtSchBasicInfoPK pk, boolean confirmedATR, String empCd, String jobId, String wkpId,
			String clsCd, String busTypeCd, String nurseLicense, String wktpCd, String wktmCd, boolean goStraightAtr,
			int backStraightAtr) {
		super();
		this.pk = pk;
		this.confirmedATR = confirmedATR;
		this.empCd = empCd;
		this.jobId = jobId;
		this.wkpId = wkpId;
		this.clsCd = clsCd;
		this.busTypeCd = busTypeCd;
		this.nurseLicense = nurseLicense;
		this.wktpCd = wktpCd;
		this.wktmCd = wktmCd;
		this.goStraightAtr = goStraightAtr;
		this.backStraightAtr = backStraightAtr;
	}

}
