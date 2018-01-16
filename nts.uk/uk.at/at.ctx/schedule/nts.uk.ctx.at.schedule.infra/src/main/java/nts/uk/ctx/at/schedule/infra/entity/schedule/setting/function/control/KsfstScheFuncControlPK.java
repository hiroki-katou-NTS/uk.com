package nts.uk.ctx.at.schedule.infra.entity.schedule.setting.function.control;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 
 * @author TanLV
 *
 */
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KsfstScheFuncControlPK implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 会社ID */
	@Column(name = "CID")
	public String companyId;

}
