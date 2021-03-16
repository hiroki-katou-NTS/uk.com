package nts.uk.ctx.office.infra.entity.status;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class ActivityStatusEntityPK implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	// column 社員ID
	@NotNull
	@Column(name = "SID")
	private String sid;
}
