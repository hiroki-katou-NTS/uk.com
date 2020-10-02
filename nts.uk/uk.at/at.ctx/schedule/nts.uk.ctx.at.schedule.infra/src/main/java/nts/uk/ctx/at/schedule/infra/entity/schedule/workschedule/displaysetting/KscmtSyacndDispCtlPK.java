package nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule.displaysetting;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 
 * @author HieuLt
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@EqualsAndHashCode
public class KscmtSyacndDispCtlPK implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 会社ID */
	@Basic(optional = false)
	@NotNull
	@Column(name = "CID")
	public String cid;

	/**
	 * 条件区分 0:保険加入状況 1:チーム 2:ランク 3:資格 4:免許区分
	 */
	@Basic(optional = false)
	@NotNull
	@Column(name = "CND_ATR")
	public int cndAtr;

}
