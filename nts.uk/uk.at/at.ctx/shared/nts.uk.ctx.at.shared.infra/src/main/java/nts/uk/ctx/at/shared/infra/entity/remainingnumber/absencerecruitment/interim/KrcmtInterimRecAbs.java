package nts.uk.ctx.at.shared.infra.entity.remainingnumber.absencerecruitment.interim;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
/**
 * 暫定振出振休紐付け管理
 * @author do_dt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_INTERIM_REC_ABS")
public class KrcmtInterimRecAbs extends ContractUkJpaEntity implements Serializable{
	/**	 */
	@EmbeddedId
	public KrcmtInterimRecAbsPK recAbsPk;
	/**振休管理データ区分	 */
	@Column(name = "ABSENCE_MNG_ATR")
	public int absenceMngAtr;
	/**	振出管理データ区分 */
	@Column(name = "RECRUITMENT_MNG_ATR")
	public int recruitmentMngAtr;
	/**	使用日数 */
	@Column(name = "USE_DAYS")
	public Double useDays;
	/**	対象選択区分 */
	@Column(name = "SELECTED_ATR")
	public int selectedAtr;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return recAbsPk;
	}

}
