package nts.uk.ctx.hr.develop.infra.entity.announcement.mandatoryretirement;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class JshmtRetireTermPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "HIST_ID")
	public String historyId;
	
	@Column(name = "COMMON_MASTER_ITEM_ID")
	public String empCommonMasterItemId;

}
