package nts.uk.ctx.at.shared.infra.entity.remainmana.absencerecruitment.interim;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
/**
 * 暫定振出管理データ
 * @author do_dt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_INTERIM_REC_MANA")
public class KrcmtInterimRecMana extends UkJpaEntity implements Serializable{
	/**	暫定振出管理データID */
	@Id
	@Column(name = "RECRUITMENT_MANA_ID")
	public String recruitmentManaId;
	/**	使用期限日 */
	@Column(name = "EXPIRATION_DAYS")
	public GeneralDate expirationDays;
	/**	発生日数 */
	@Column(name = "OCCURRENCE_DAYS")
	public Double occurrenceDays;
	/**	法定内外区分 */
	@Column(name = "STATUTORY_ATR")
	public int statutoryAtr;
	/**	未使用日数 */
	@Column(name = "UNUSED_DAYS")
	public Double unUsedDays;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return recruitmentManaId;
	}

}
