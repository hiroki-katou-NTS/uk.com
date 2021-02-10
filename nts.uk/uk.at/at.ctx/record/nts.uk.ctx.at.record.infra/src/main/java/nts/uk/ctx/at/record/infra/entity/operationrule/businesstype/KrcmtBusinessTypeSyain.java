/**
 * 5:33:49 PM Aug 24, 2017
 */
package nts.uk.ctx.at.record.infra.entity.operationrule.businesstype;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * @author hungnm
 *
 */
@Setter
@Getter
@Entity
@Table(name = "KRCMT_BUSINESS_TYPE_SYAIN")
//社員の勤務種別
public class KrcmtBusinessTypeSyain extends ContractUkJpaEntity implements Serializable  {

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcmtBusinessTypeSyainPK krcmtBusinessTypeSyainPK;

	@Override
	protected Object getKey() {
		return this.krcmtBusinessTypeSyainPK;
	}
	
}
