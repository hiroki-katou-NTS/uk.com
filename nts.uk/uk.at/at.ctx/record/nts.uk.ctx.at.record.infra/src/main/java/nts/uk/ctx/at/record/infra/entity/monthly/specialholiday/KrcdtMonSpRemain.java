package nts.uk.ctx.at.record.infra.entity.monthly.specialholiday;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata.SpecialHolidayRemainData;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="KRCDT_MON_SP_REMAIN")
public class KrcdtMonSpRemain extends UkJpaEntity implements Serializable{
	
	@EmbeddedId
	public KrcdtMonSpRemainPK pk;
	
	/**	締め処理状態 */
	@Column(name="CLOSURE_STATUS")
	public int closureStatus;
	/**	特別休暇月別残数データ．締め期間 開始年月日 */
	@Column(name="START_DATE")
	public GeneralDate closureStartDate;
	/**	特別休暇月別残数データ．締め期間 ．終了年月日*/
	@Column(name="END_DATE")
	public GeneralDate closureEndDate;
	/**	特別休暇月別残数データ．特別休暇．使用数．使用日数 */
	@Column(name="USED_DAYS")
	public double useDays;
	/**	特別休暇月別残数データ．特別休暇．使用数．使用日数．特別休暇使用日数付与前 */
	@Column(name="USED_DAYS_BEFORE")
	public double beforeUseDays;
	/**	特別休暇月別残数データ．特別休暇．使用数．使用日数． 特別休暇使用日数付与後*/
	@Column(name="USED_DAYS_AFTER")
	public Double afterUseDays;
	/**	特別休暇月別残数データ．特別休暇．使用数．使用時間．使用時間*/
	@Column(name="USED_MINUTES")
	public Integer useTimes;
	/**	特別休暇月別残数データ．特別休暇．使用数．使用時間． 使用時間付与前*/
	@Column(name="USED_MINUTES_BEFORE")
	public Integer beforeUseTimes;
	/**	特別休暇月別残数データ．特別休暇．使用数．使用時間 ．使用時間付与後*/
	@Column(name="USED_MINUTES_AFTER")
	public Integer afterUseTimes;
	/**	特別休暇月別残数データ．特別休暇．使用数．使用時間: 使用回数 */
	@Column(name="USED_TIMES")
	public Integer useNumber;
	/**	特別休暇月別残数データ．実特別休暇．使用数．使用日数 .実特別休暇使用日数*/
	@Column(name="FACT_USED_DAYS")
	public double factUseDays;
	/**	特別休暇月別残数データ．実特別休暇．使用数．使用日数. 使用日数付与前*/
	@Column(name="FACT_USED_DAYS_BEFORE")
	public double beforeFactUseDays;
	/**	特別休暇月別残数データ．実特別休暇．使用数．使用日数.使用日数付与後 */
	@Column(name="FACT_USED_DAYS_AFTER")
	public Double afterFactUseDays;
	/**	特別休暇月別残数データ．実特別休暇．使用数．使用時間.使用時間 */
	@Column(name="FACT_USED_MINUTES")
	public Integer factUsetimes;
	/**	特別休暇月別残数データ．実特別休暇．使用数．使用時間.使用時間付与前 */
	@Column(name="FACT_USED_MINUTES_BEFORE")
	public Integer beforeFactUseTimes;
	/**	特別休暇月別残数データ．実特別休暇．使用数．使用時間. 使用時間付与後*/
	@Column(name="FACT_USED_MINUTES_AFTER")
	public Integer afterFactUseTimes;
	/**特別休暇月別残数データ．実特別休暇．使用数．使用時間.使用回数	 */
	@Column(name="FACT_USED_TIMES")
	public Integer factUseNumber;
	/**	特別休暇月別残数データ．特別休暇．残数.日数 */
	@Column(name="REMAINING_DAYS")
	public double remainDays;
	/**	特別休暇月別残数データ．特別休暇．残数.時間 */
	@Column(name="REMAINING_MINUTES")
	public Integer remainTimes;
	/**	特別休暇月別残数データ．実特別休暇．残数. 日数*/
	@Column(name="FACT_REMAINING_DAYS")
	public double factRemainDays;
	/**特別休暇月別残数データ．実特別休暇．残数.時間	 */
	@Column(name="FACT_REMAINING_MINUTES")
	public Integer factRemainTimes;
	/**	特別休暇月別残数データ．特別休暇．残数付与前.日数 */
	@Column(name="REMAINING_DAYS_BEFORE")
	public double beforeRemainDays;
	/**	特別休暇月別残数データ．特別休暇．残数付与前.時間 */
	@Column(name="REMAINING_MINUTES_BEFORE")
	public Integer beforeRemainTimes;
	/**	特別休暇月別残数データ．実特別休暇．残数付与前.日数 */
	@Column(name="FACT_REMAINING_DAYS_BEFORE")
	public double beforeFactRemainDays;
	/**特別休暇月別残数データ．実特別休暇．残数付与前.時間	 */
	@Column(name="FACT_REMAINING_MINUTES_BEFORE")
	public Integer beforeFactRemainTimes;
	/**特別休暇月別残数データ．特別休暇．残数付与後.日数	 */
	@Column(name="REMAINING_DAYS_AFTER")
	public Double afterRemainDays;
	/**	特別休暇月別残数データ．特別休暇．残数付与後.時間 */
	@Column(name="REMAINING_MINUTES_AFTER")
	public Integer afterRemainTimes;
	/**	特別休暇月別残数データ．実特別休暇．残数付与後. 日数*/
	@Column(name="FACT_REMAINING_DAYS_AFTER")
	public Double afterFactRemainDays;
	/**	特別休暇月別残数データ．実特別休暇．残数付与後. 時間*/
	@Column(name="FACT_REMAINING_MINUTES_AFTER")
	public Integer afterFactRemainTimes;
	/**	特別休暇月別残数データ．特別休暇．未消化数．未消化日数.未消化日数 */
	@Column(name="NOT_USED_DAYS")
	public double notUseDays;
	/**	特別休暇月別残数データ．特別休暇．未消化数．未消化時間.未消化時間 */
	@Column(name="NOT_USED_MINUTES")
	public Integer notUseTime;
	/**	付与区分 */
	@Column(name="GRANT_ATR")
	public int grantAtr;
	/**	特別休暇月別残数データ．特別休暇付与情報.付与日数 */
	@Column(name="GRANT_DAYS")
	public Double grantDays;

	private static final long serialVersionUID = 1L;
	
	@Override
	protected Object getKey() {
		return this.pk;
	}

	/**
	 * ドメインから変換　（for Insert）
	 * @param domain 特別休暇月別残数データ
	 */
	// add 2018.8.22 shuichi_ishida
	public void fromDomainForPersist(SpecialHolidayRemainData domain){
		
		this.pk = new KrcdtMonSpRemainPK(
				domain.getSid(),
				domain.getYm().v(),
				domain.getClosureId(),
				domain.getClosureDate().getClosureDay().v(),
				(domain.getClosureDate().getLastDayOfMonth() ? 1 : 0),
				domain.getSpecialHolidayCd());
		this.fromDomainForUpdate(domain);
	}
	
	/**
	 * ドメインから変換　(for Update)
	 * @param domain 年休月別残数データ
	 */
	// add 2018.8.22 shuichi_ishida
	public void fromDomainForUpdate(SpecialHolidayRemainData domain){

		// Optional列の初期化
		this.afterUseDays = null;
		this.useTimes = null;
		this.beforeUseTimes = null;
		this.afterUseTimes = null;
		this.useNumber = null;
		this.afterFactUseDays = null;
		this.factUsetimes = null;
		this.beforeFactUseTimes = null;
		this.afterFactUseTimes = null;
		this.factUseNumber = null;
		this.remainTimes = null;
		this.factRemainTimes = null;
		this.beforeRemainTimes = null;
		this.beforeFactRemainTimes = null;
		this.afterFactRemainDays = null;
		this.afterFactRemainTimes = null;
		this.afterFactRemainDays = null;
		this.afterFactRemainTimes = null;
		this.notUseTime = null;
		this.grantDays = null;
		
		val specialLeave = domain.getSpecialLeave();
		val actualSpecial = domain.getActualSpecial();
		
		this.closureStatus = domain.getClosureStatus().value;
		this.closureStartDate = domain.getClosurePeriod().start();
		this.closureEndDate = domain.getClosurePeriod().end();
		
		// 特別休暇：使用数
		val specialUseNumber = specialLeave.getUseNumber();
		this.useDays = specialUseNumber.getUseDays().getUseDays().v();
		this.beforeUseDays = specialUseNumber.getUseDays().getBeforeUseGrantDays().v();
		if (specialUseNumber.getUseDays().getAfterUseGrantDays().isPresent()){
			this.afterUseDays = specialUseNumber.getUseDays().getAfterUseGrantDays().get().v();
		}
		if (specialUseNumber.getUseTimes().isPresent()){
			val specialUseTime = specialUseNumber.getUseTimes().get();
			this.useTimes = specialUseTime.getUseTimes().v();
			this.beforeUseTimes = specialUseTime.getBeforeUseGrantTimes().v();
			if (specialUseTime.getAfterUseGrantTimes().isPresent()){
				this.afterUseTimes = specialUseTime.getAfterUseGrantTimes().get().v();
			}
			this.useNumber = specialUseTime.getUseNumber().v();
		}
		
		// 実特別休暇：使用数
		val actualUseNumber = actualSpecial.getUseNumber();
		this.factUseDays = actualUseNumber.getUseDays().getUseDays().v();
		this.beforeFactUseDays = actualUseNumber.getUseDays().getBeforeUseGrantDays().v();
		if (actualUseNumber.getUseDays().getAfterUseGrantDays().isPresent()){
			this.afterFactUseDays = actualUseNumber.getUseDays().getAfterUseGrantDays().get().v();
		}
		if (actualUseNumber.getUseTimes().isPresent()){
			val actualUseTime = actualUseNumber.getUseTimes().get();
			this.factUsetimes = actualUseTime.getUseTimes().v();
			this.beforeFactUseTimes = actualUseTime.getBeforeUseGrantTimes().v();
			if (actualUseTime.getAfterUseGrantTimes().isPresent()){
				this.afterFactUseTimes = actualUseTime.getAfterUseGrantTimes().get().v();
			}
			this.factUseNumber = actualUseTime.getUseNumber().v();
		}
		
		// 特別休暇：残数
		val specialRemain = specialLeave.getRemain();
		this.remainDays = specialRemain.getDays().v();
		if (specialRemain.getTime().isPresent()){
			this.remainTimes = specialRemain.getTime().get().v();
		}
		
		// 実特別休暇：残数
		val actualRemain = actualSpecial.getRemain();
		this.factRemainDays = actualRemain.getDays().v();
		if (actualRemain.getTime().isPresent()){
			this.factRemainTimes = actualRemain.getTime().get().v();
		}
		
		// 特別休暇：残数付与前
		val specialRemainBefore = specialLeave.getBeforeRemainGrant();
		this.beforeRemainDays = specialRemainBefore.getDays().v();
		if (specialRemainBefore.getTime().isPresent()){
			this.beforeRemainTimes = specialRemainBefore.getTime().get().v();
		}
		
		// 実特別休暇：残数付与前
		val actualRemainBefore = actualSpecial.getBeforRemainGrant();
		this.beforeFactRemainDays = actualRemainBefore.getDays().v();
		if (actualRemainBefore.getTime().isPresent()){
			this.beforeFactRemainTimes = actualRemainBefore.getTime().get().v();
		}
		
		// 特別休暇：残数付与後
		if (specialLeave.getAfterRemainGrant().isPresent()){
			val specialRemainAfter = specialLeave.getAfterRemainGrant().get();
			this.afterRemainDays = specialRemainAfter.getDays().v();
			if (specialRemainAfter.getTime().isPresent()){
				this.afterRemainTimes = specialRemainAfter.getTime().get().v();
			}
		}
		
		// 実特別休暇：残数付与後
		if (actualSpecial.getAfterRemainGrant().isPresent()){
			val actualRemainAfter = actualSpecial.getAfterRemainGrant().get();
			this.afterFactRemainDays = actualRemainAfter.getDays().v();
			if (actualRemainAfter.getTime().isPresent()){
				this.afterFactRemainTimes = actualRemainAfter.getTime().get().v();
			}
		}
		
		// 特別休暇：未消化数
		val undegest = specialLeave.getUnDegestionNumber();
		this.notUseDays = undegest.getDays().v();
		if (undegest.getTimes().isPresent()){
			this.notUseTime = undegest.getTimes().get().v();
		}
		
		// 付与区分
		this.grantAtr = (domain.isGrantAtr() ? 1 : 0);
		
		// 特別休暇付与情報：付与日数
		if (domain.getGrantDays().isPresent()){
			this.grantDays = domain.getGrantDays().get().v();
		}
	}
}
