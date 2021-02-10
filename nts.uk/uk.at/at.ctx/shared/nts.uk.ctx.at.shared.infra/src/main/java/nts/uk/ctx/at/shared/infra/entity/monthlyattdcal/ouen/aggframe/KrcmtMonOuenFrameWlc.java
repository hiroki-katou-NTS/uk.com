package nts.uk.ctx.at.shared.infra.entity.monthlyattdcal.ouen.aggframe;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.ouen.aggframe.AggregateFrameName;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.ouen.aggframe.AggregateFrameTargetWorkLocation;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.ouen.aggframe.OuenAggregateFrameOfMonthly;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Entity
@NoArgsConstructor
@Table(name = "KRCMT_MON_SUP_FRAME_WLC")
@AllArgsConstructor
public class KrcmtMonOuenFrameWlc extends ContractUkJpaEntity implements Serializable {

	/***/
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KrcmtMonOuenFramePK pk;
	
	/** 集計枠名 */
	@Column(name = "FRAME_NAME")
	public String name;
	
	/** 場所コード */
	@Column(name = "WORK_LOCATION_CD")
	public String workLocationCd;

	@Override
	protected Object getKey() {
		return pk;
	}
	
	public OuenAggregateFrameOfMonthly domain() {
		return OuenAggregateFrameOfMonthly.create(
				pk.frameNo, 
				AggregateFrameTargetWorkLocation.create(workLocationCd), 
				new AggregateFrameName(name));
	}
}
