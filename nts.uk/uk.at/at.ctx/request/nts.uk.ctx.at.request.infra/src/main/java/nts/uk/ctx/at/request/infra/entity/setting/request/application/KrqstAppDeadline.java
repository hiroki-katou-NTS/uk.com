package nts.uk.ctx.at.request.infra.entity.setting.request.application;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "KRQST_APP_DEADLINE")
public class KrqstAppDeadline extends ContractUkJpaEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*主キー*/
	@EmbeddedId
	public KrqstAppDeadlinePK krqstAppDeadlinePK;
	/**
	 * 利用区分
	 */
	@Column(name = "USE_ATR")
	public int useAtr;	
	/**
	 * 締切基準
	 */
	@Column(name = "DEADLINE_CRITERIA")
	public int deadlineCriteria;
	/**
	 * 締切日数
	 */
	@Column(name = "DEADLINE")
	public int deadline;
	
	@ManyToOne
	@PrimaryKeyJoinColumns({
		@PrimaryKeyJoinColumn(name="CID",referencedColumnName="CID")
	})
	private KrqstApplicationSetting krqstApplicationSetting;
	
	@Override
	protected Object getKey() {
		return krqstAppDeadlinePK;
	}
	
}
