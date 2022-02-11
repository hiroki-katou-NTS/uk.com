package nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.timedifferencemanagement;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
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
@Table(name = "KRCMT_REGIONAL_TIME_DIFERENCE_MGT")
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
		return new RegionalTimeDifference(this.pk.code, this.name, this.timeDifference);
	}

	public KrcmtRegionalTimeDifferenceMgt toEntity(RegionalTimeDifference domain) {
		return new KrcmtRegionalTimeDifferenceMgt(
				new KrcmtRegionalTimeDifferenceMgtPk(AppContexts.user().contractCode(), domain.getCode()),
				domain.getName(), domain.getRegionalTimeDifference());
	}

}
