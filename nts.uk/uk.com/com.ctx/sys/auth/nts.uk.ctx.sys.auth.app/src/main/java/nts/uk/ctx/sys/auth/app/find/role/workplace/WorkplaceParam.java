package nts.uk.ctx.sys.auth.app.find.role.workplace;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkplaceParam implements Serializable {

	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The base date. */
	private String baseDate;
	
	/** The reference range. */
	private Integer referenceRange;
}
