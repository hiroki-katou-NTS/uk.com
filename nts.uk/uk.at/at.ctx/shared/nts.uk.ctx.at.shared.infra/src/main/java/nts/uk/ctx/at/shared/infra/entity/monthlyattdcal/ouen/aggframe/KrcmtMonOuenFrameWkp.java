package nts.uk.ctx.at.shared.infra.entity.monthlyattdcal.ouen.aggframe;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.ouen.aggframe.AggregateFrameName;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.ouen.aggframe.AggregateFrameTargetWorkplace;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.ouen.aggframe.OuenAggregateFrameOfMonthly;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@NoArgsConstructor
@Table(name = "KRCMT_MON_OUEN_FRAME_WKP")
@AllArgsConstructor
public class KrcmtMonOuenFrameWkp extends UkJpaEntity implements Serializable {

	/***/
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KrcmtMonOuenFramePK pk;
	
	/** 集計枠名 */
	@Column(name = "FRAME_NAME")
	public String name;
	
	/** 職場ID */
	@Column(name = "WORKPLACE_ID")
	public String workplaceId;

	@Override
	protected Object getKey() {
		return pk;
	}
	
	public OuenAggregateFrameOfMonthly domain() {
		return OuenAggregateFrameOfMonthly.create(
				pk.frameNo, 
				AggregateFrameTargetWorkplace.create(workplaceId), 
				new AggregateFrameName(name));
	}
}
