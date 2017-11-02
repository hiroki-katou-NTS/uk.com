/**
 * 
 */
package entity.person.personinfoctgdata;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author danpv
 *
 */
@Embeddable
public class PpemtPerInfoItemDataPK {

	@Column(name = "RECORD_ID")
	public String recordId;

	@Column(name = "PER_INFO_DEF_ID")
	public String perInfoDefId;
}
