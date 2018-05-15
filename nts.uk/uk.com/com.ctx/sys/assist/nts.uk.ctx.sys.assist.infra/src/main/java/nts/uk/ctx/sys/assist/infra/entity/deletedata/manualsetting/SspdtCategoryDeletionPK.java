package nts.uk.ctx.sys.assist.infra.entity.deletedata.manualsetting;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class SspdtCategoryDeletionPK implements Serializable{
	 
	private static final long serialVersionUID = 1L;
	@NotNull
	@Column(name = "DEL_ID")
    public String delId;
	
	@NotNull
	@Column(name = "CATEGORY_ID")
    public String categoryId;
}
