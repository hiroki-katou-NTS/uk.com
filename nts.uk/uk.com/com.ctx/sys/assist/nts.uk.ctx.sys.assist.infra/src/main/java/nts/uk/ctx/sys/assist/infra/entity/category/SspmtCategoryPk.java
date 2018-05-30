package nts.uk.ctx.sys.assist.infra.entity.category;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class SspmtCategoryPk implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * ID
	 */
	@Basic(optional = false)
	@Column(name = "CATEGORY_ID")
	public String categoryId;
}
