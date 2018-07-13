package nts.uk.ctx.sys.log.infra.entity.log.startpage;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.sys.log.infra.entity.logbasicinfo.SrcdtLogBasicInfo;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**　 @author Tindh - 起動記録*/

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "SRCDT_START_PAGE_LOG_INFO")
public class SrcdtStartPageLogInfo extends UkJpaEntity {

	@Id
	@Column(name = "OPERATION_ID")
	public String operationId;

	@Column(name = "PGID")
	@Basic(optional = false)
	public String programId;

	@Column(name = "SCREEN_ID")
	@Basic(optional = false)
	public String screenId;

	@Column(name = "QUERY_STRING")
	@Basic(optional = false)
	public String queryString;

	@OneToOne(fetch = FetchType.LAZY)
    @MapsId
	private SrcdtLogBasicInfo logBasic;

	@Override
	protected Object getKey() {
		return this.operationId;
	}
}
