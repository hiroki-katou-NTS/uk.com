package nts.uk.ctx.at.record.infra.entity.workrecord.workrecord;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import nts.arc.time.GeneralDateTime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * UKDesign.データベース.ER図.就業.勤務実績.<<core>> 勤務実績.実績の状況管理.管理職場の就業確定.KRCDT_WORK_FIXED
 * @author chungnt
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCDT_WORK_FIXED")
public class KrcdtWorkFixed extends ContractUkJpaEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@EmbeddedId
	public KrcdtWorkFixedPk pk;
	
	/**
	 * 確定者
	 */
	@Basic(optional = true)
	@Column(name = "CONFIRM_SID")
	public String employeeId;
	
	/**
	 * 確定日時
	 */
	@Basic(optional = true)
	@Column(name = "CONFIRM_DATE_TIME")
	public GeneralDateTime confirm_date_time;
	

	@Override
	protected Object getKey() {
		return pk;
	}
	
}
