/**
 * 
 */
package nts.uk.ctx.pereg.infra.entity.person.personinfoctgdata;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * @author danpv
 *
 */
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class PpemtPerDataItemPK {

	@Column(name = "RECORD_ID")
	public String recordId;

	@Column(name = "PER_INFO_DEF_ID")
	public String perInfoDefId;
}
