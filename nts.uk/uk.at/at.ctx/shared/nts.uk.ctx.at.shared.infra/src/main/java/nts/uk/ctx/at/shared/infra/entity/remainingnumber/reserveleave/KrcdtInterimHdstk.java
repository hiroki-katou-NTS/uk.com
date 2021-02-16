package nts.uk.ctx.at.shared.infra.entity.remainingnumber.reserveleave;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
/**
 * 暫定積立年休管理データ
 *
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
//@Table(name = "KRCDT_INTERIM_HDSTK")
@Table(name = "KRCMT_INTERIM_RESERVE_MNG")
public class KrcdtInterimHdstk extends UkJpaEntity{

	/**
	 * 暫定積立年休管理データID
	 */
	@Id
	@Column(name = "REMAIN_MNG_ID")
    public String remainMngId;
	protected Object getKey() {
		return remainMngId;
	}

//	/**
//	 * 社員ID
//	 */
//	@Column(name = "SID")
//	public String sid;
//
//	/**
//	 * 対象日
//	 */
//	@Column(name = "YMD")
//	public GeneralDate ymd;
//
//	/**
//	 * 作成元区分
//	 */
//	@Column(name = "CREATOR_ATR")
//	public int createAtr;
//
//	/**
//	 * 残数分類
//	 */
//	@Column(name = "REMAIN_ATR")
//	public int remainAtr;

	/**
	 * 使用日数
	 */
	@Column(name = "USED_DAYS")
	public double usedDays;

}
