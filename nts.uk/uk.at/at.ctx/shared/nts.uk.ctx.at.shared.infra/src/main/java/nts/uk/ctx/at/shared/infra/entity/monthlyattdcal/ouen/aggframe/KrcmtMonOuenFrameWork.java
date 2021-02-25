package nts.uk.ctx.at.shared.infra.entity.monthlyattdcal.ouen.aggframe;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.ouen.aggframe.AggregateFrameName;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.ouen.aggframe.AggregateFrameTargetWork;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.ouen.aggframe.OuenAggregateFrameOfMonthly;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@NoArgsConstructor
@Table(name = "KRCMT_MON_SUP_FRAME_WORK")
@AllArgsConstructor
public class KrcmtMonOuenFrameWork extends UkJpaEntity implements Serializable {

	/***/
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KrcmtMonOuenFramePK pk;
	
	/** 集計枠名 */
	@Column(name = "FRAME_NAME")
	public String name;
	
	/** 作業グループコード */
	@Column(name = "WORK_GROUP_CD")
	public String groupCode;

	/** 作業CD1 */
	@Column(name = "WORK_CD1")
	public String workCd1;
	
	/** 作業CD2 */
	@Column(name = "WORK_CD2")
	public String workCd2;
	
	/** 作業CD3 */
	@Column(name = "WORK_CD3")
	public String workCd3;
	
	/** 作業CD4 */
	@Column(name = "WORK_CD4")
	public String workCd4;
	
	/** 作業CD5 */
	@Column(name = "WORK_CD5")
	public String workCd5;

	@Override
	protected Object getKey() {
		return pk;
	}
	
	public OuenAggregateFrameOfMonthly domain() {
		return OuenAggregateFrameOfMonthly.create(
				pk.frameNo, 
				AggregateFrameTargetWork.create(groupCode, workCd1, workCd2, workCd3, workCd4, workCd5), 
				new AggregateFrameName(name));
	}
}
