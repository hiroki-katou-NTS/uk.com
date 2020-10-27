package nts.uk.ctx.at.shared.infra.entity.remainingnumber.absencerecruitment.interim;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
/**
 * 暫定振出管理データ
 * @author do_dt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_INTERIM_REC_MNG")
public class KrcmtInterimRecMng extends ContractUkJpaEntity implements Serializable{
	/**	暫定振出管理データID */
	@Id
	@Column(name = "RECRUITMENT_MNG_ID")
	public String recruitmentMngId;
	/**	使用期限日 */
	@Column(name = "EXPIRATION_DAYS")
	public GeneralDate expirationDate;
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
		return recruitmentMngId;
	}

}
