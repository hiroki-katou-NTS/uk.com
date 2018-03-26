package nts.uk.ctx.at.record.infra.repository.divergence.time;

import java.math.BigDecimal;
import java.util.List;

import nts.uk.ctx.at.record.dom.divergence.time.DivergenceReasonInputMethodSetMemento;
import nts.uk.ctx.at.record.dom.divergence.time.reason.DivergenceReasonSelect;
import nts.uk.ctx.at.record.infra.entity.divergence.time.KrcstDvgcTime;

/**
 * The Class JpaDivergenceReasonInputMethodRepositorySetMemento.
 */
public class JpaDivergenceReasonInputMethodRepositorySetMemento implements DivergenceReasonInputMethodSetMemento {

	/** The entities. */
	private KrcstDvgcTime entity;

	/**
	 * Instantiates a new jpa divergence time repository set memento.
	 */
	public JpaDivergenceReasonInputMethodRepositorySetMemento() {

	}

	/**
	 * Instantiates a new jpa divergence time repository set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaDivergenceReasonInputMethodRepositorySetMemento(KrcstDvgcTime entity) {
		this.entity = entity;
	}

	@Override
	public void setDivergenceTimeNo(int DivergenceTimeNo) {

		this.entity.getId().setNo(DivergenceTimeNo);
	}

	@Override
	public void setCompanyId(String companyId) {
		// No code

	}

	@Override
	public void setDivergenceReasonInputed(boolean divergenceReasonInputed) {

		this.entity.setReasonInputCanceled(divergenceReasonInputed ? new BigDecimal(1) : new BigDecimal(0));

	}

	@Override
	public void setDivergenceReasonSelected(boolean divergenceReasonSelected) {

		this.entity.setReasonSelectCanceled(divergenceReasonSelected ? new BigDecimal(1) : new BigDecimal(0));

	}

	@Override
	public void setReasons(List<DivergenceReasonSelect> reason) {
		// no code

	}

}
