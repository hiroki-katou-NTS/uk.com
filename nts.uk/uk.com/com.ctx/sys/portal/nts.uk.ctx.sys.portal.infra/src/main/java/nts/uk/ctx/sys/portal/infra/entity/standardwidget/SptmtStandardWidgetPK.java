package nts.uk.ctx.sys.portal.infra.entity.standardwidget;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class SptmtStandardWidgetPK implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotNull
	@Column(name = "TOPPAGE_PART_ID")
	public String toppagePartID;

}
