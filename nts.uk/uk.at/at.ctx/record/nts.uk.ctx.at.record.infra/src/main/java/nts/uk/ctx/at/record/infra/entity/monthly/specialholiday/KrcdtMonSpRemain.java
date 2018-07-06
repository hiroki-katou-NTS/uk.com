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
import nts.arc.time.GeneralDate;
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
	public double afterUseDays;
	/**	特別休暇月別残数データ．特別休暇．使用数．使用時間．使用時間*/
	@Column(name="USED_MINUTES")
	public int useTimes;
	/**	特別休暇月別残数データ．特別休暇．使用数．使用時間． 使用時間付与前*/
	@Column(name="USED_MINUTES_BEFORE")
	public int beforeUseTimes;
	/**	特別休暇月別残数データ．特別休暇．使用数．使用時間 ．使用時間付与後*/
	@Column(name="USED_MINUTES_AFTER")
	public int afterUseTimes;
	/**	特別休暇月別残数データ．特別休暇．使用数．使用時間: 使用回数 */
	@Column(name="USED_TIMES")
	public int useNumber;
	/**	特別休暇月別残数データ．実特別休暇．使用数．使用日数 .実特別休暇使用日数*/
	@Column(name="FACT_USED_DAYS")
	public double factUseDays;
	/**	特別休暇月別残数データ．実特別休暇．使用数．使用日数. 使用日数付与前*/
	@Column(name="FACT_USED_DAYS_BEFORE")
	public double beforeFactUseDays;
	/**	特別休暇月別残数データ．実特別休暇．使用数．使用日数.使用日数付与後 */
	@Column(name="FACT_USED_DAYS_AFTER")
	public double afterFactUseDays;
	/**	特別休暇月別残数データ．実特別休暇．使用数．使用時間.使用時間 */
	@Column(name="FACT_USED_MINUTES")
	public int factUsetimes;
	/**	特別休暇月別残数データ．実特別休暇．使用数．使用時間.使用時間付与前 */
	@Column(name="FACT_USED_MINUTES_BEFORE")
	public int beforeFactUseTimes;
	/**	特別休暇月別残数データ．実特別休暇．使用数．使用時間. 使用時間付与後*/
	@Column(name="FACT_USED_MINUTES_AFTER")
	public int afterFactUseTimes;
	/**特別休暇月別残数データ．実特別休暇．使用数．使用時間.使用回数	 */
	@Column(name="FACT_USED_TIMES")
	public int factUseNumber;
	/**	特別休暇月別残数データ．特別休暇．残数.日数 */
	@Column(name="REMAINING_DAYS")
	public double remainDays;
	/**	特別休暇月別残数データ．特別休暇．残数.時間 */
	@Column(name="REMAINING_MINUTES")
	public int remainTimes;
	/**	特別休暇月別残数データ．実特別休暇．残数. 日数*/
	@Column(name="FACT_REMAINING_DAYS")
	public double factRemainDays;
	/**特別休暇月別残数データ．実特別休暇．残数.時間	 */
	@Column(name="FACT_REMAINING_MINUTES")
	public int factRemainTimes;
	/**	特別休暇月別残数データ．特別休暇．残数付与前.日数 */
	@Column(name="REMAINING_DAYS_BEFORE")
	public double beforeRemainDays;
	/**	特別休暇月別残数データ．特別休暇．残数付与前.時間 */
	@Column(name="REMAINING_MINUTES_BEFORE")
	public int beforeRemainTimes;
	/**	特別休暇月別残数データ．実特別休暇．残数付与前.日数 */
	@Column(name="FACT_REMAINING_DAYS_BEFORE")
	public double beforeFactRemainDays;
	/**特別休暇月別残数データ．実特別休暇．残数付与前.時間	 */
	@Column(name="FACT_REMAINING_MINUTES_BEFORE")
	public int beforeFactRemainTimes;
	/**特別休暇月別残数データ．特別休暇．残数付与後.日数	 */
	@Column(name="REMAINING_DAYS_AFTER")
	public double afterRemainDays;
	/**	特別休暇月別残数データ．特別休暇．残数付与後.時間 */
	@Column(name="REMAINING_MINUTES_AFTER")
	public int afterRemainTimes;
	/**	特別休暇月別残数データ．実特別休暇．残数付与後. 日数*/
	@Column(name="FACT_REMAINING_DAYS_AFTER")
	public double afterFactRemainDays;
	/**	特別休暇月別残数データ．実特別休暇．残数付与後. 時間*/
	@Column(name="FACT_REMAINING_MINUTES_AFTER")
	public int afterFactRemainTimes;
	/**	特別休暇月別残数データ．特別休暇．未消化数．未消化日数.未消化日数 */
	@Column(name="NOT_USED_DAYS")
	public double notUseDays;
	/**	特別休暇月別残数データ．特別休暇．未消化数．未消化時間.未消化時間 */
	@Column(name="NOT_USED_MINUTES")
	public int notUseTime;
	/**	付与区分 */
	@Column(name="GRANT_ATR")
	public int grantAtr;
	/**	特別休暇月別残数データ．特別休暇付与情報.付与日数 */
	@Column(name="GRANT_DAYS")
	public double grantDays;

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return null;
	}

}
