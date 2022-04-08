package nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.timedifferencemanagement;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timedifferencemanagement.RegionCode;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timedifferencemanagement.RegionName;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timedifferencemanagement.RegionalTime;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timedifferencemanagement.RegionalTimeDifference;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author chungnt
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCCT_REGIONAL_TIME_DIFERENCE_MGT")
public class KrcmtRegionalTimeDifferenceMgt extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcmtRegionalTimeDifferenceMgtPk pk;

	/**
	 * 名称
	 */
	@Basic(optional = true)
	@Column(name = "NAME")
	public String name;

	/**
	 * 時差
	 */
	@Basic(optional = true)
	@Column(name = "TIME_DIFFERENCE")
	public int timeDifference;

	@Override
	protected Object getKey() {
		return pk;
	}

	public RegionalTimeDifference toDomain() {
		return new RegionalTimeDifference(new RegionCode(this.pk.code), new RegionName(this.name), new RegionalTime(this.timeDifference));
	}

	public KrcmtRegionalTimeDifferenceMgt toEntity(RegionalTimeDifference domain) {
		return new KrcmtRegionalTimeDifferenceMgt(
				new KrcmtRegionalTimeDifferenceMgtPk(domain.getCode().v()),
				domain.getName().v(), domain.getRegionalTimeDifference().v());
	}

}
