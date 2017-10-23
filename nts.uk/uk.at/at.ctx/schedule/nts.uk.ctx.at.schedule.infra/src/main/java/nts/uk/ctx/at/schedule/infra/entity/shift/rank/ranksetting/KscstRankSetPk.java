package nts.uk.ctx.at.schedule.infra.entity.shift.rank.ranksetting;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
/**
 * 
 * @author sonnh1
 *
 */
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KscstRankSetPk implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "RANK_CD")
	public String rankCode;

	@Column(name = "SID")
	public String sId;
}
