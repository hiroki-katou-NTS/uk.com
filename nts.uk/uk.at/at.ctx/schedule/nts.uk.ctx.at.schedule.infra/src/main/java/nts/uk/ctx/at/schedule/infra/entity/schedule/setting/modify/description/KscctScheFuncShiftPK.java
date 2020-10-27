package nts.uk.ctx.at.schedule.infra.entity.schedule.setting.modify.description;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KscctScheFuncShiftPK implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 機能NO */
	@Column(name = "FUNCTION_NO_SHIFT")
	public int functionNoShift;
}
