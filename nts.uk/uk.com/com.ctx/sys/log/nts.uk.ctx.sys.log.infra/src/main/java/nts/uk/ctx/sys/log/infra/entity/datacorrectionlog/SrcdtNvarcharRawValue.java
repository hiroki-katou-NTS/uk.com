package nts.uk.ctx.sys.log.infra.entity.datacorrectionlog;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author HungTT
 *
 */

@Entity
@Table(name = "SRCDT_NVARCHAR_RAW_VALUE")
@NoArgsConstructor
@AllArgsConstructor
public class SrcdtNvarcharRawValue extends UkJpaEntity {

	@Id
	@Column(name = "ID")
	public String id;

	@Column(name = "VALUE")
	@Basic(optional = false)
	public String value;

	@OneToOne(mappedBy = "rawNvarcharValueBefore")
	public SrcdtDataCorrectionLog beforeLog;

	@OneToOne(mappedBy = "rawNvarcharValueAfter")
	public SrcdtDataCorrectionLog afterLog;

	@Override
	protected Object getKey() {
		return this.id;
	}
}
