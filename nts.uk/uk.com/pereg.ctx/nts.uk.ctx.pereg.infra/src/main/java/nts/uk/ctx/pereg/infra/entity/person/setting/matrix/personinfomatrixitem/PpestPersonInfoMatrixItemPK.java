/**
 * 
 */
package nts.uk.ctx.pereg.infra.entity.person.setting.matrix.personinfomatrixitem;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * @author hieult
 *
 */
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class PpestPersonInfoMatrixItemPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Column(name = "PERSON_INFO_CATEGORY_ID")
	public String pInfoCategoryID;

	@NotNull
	@Column(name = "PERSON_INFO_ITEM_ID")
	public String pInfoDefiID;

}
