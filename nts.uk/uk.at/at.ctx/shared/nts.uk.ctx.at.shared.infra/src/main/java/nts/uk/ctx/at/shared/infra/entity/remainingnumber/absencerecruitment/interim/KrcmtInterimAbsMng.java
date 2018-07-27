package nts.uk.ctx.at.shared.infra.entity.remainingnumber.absencerecruitment.interim;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
/**
 * 暫定振休管理データ
 * @author do_dt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_INTERIM_ABS_MNG")
public class KrcmtInterimAbsMng extends UkJpaEntity implements Serializable{

	/**暫定振休管理データID	 */
	@Id
	@Column(name = "ABSENCE_MNG_ID")
	public String absenceMngId;
	/** 必要日数 */
	@Column(name = "REQUIRED_DAYS")
	public Double requiredDays;
	/**	未相殺日数 */
	@Column(name = "UNOFFSET_DAYS")
	public Double unOffsetDay;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return absenceMngId;
	}

}
