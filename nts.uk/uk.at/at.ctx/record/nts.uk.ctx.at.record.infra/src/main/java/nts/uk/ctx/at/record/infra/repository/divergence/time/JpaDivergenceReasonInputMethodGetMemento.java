package nts.uk.ctx.at.record.infra.repository.divergence.time;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceReasonInputMethodGetMemento;
import nts.uk.ctx.at.record.dom.divergence.time.reason.DivergenceReasonSelect;
import nts.uk.ctx.at.record.dom.divergence.time.reason.DivergenceReasonSelectGetMemento;
import nts.uk.ctx.at.record.dom.divergence.time.reason.DivergenceReasonSelectRepository;
import nts.uk.ctx.at.record.infra.entity.divergence.time.KrcmtDvgcTime;
import nts.uk.ctx.at.record.infra.repository.divergence.time.reason.JpaDivergenceReasonSelectGetMemento;
import nts.uk.ctx.at.record.infra.repository.divergence.time.reason.JpaDivergenceReasonSelectRepository;

/**
 * The Class JpaDivergenceReasonInputMethodRepositoryGetMemento.
 */
public class JpaDivergenceReasonInputMethodGetMemento implements DivergenceReasonInputMethodGetMemento {

	/** The entities. */
	private KrcmtDvgcTime entities;
	
	/**
	 * Instantiates a new jpa divergence reason input method repository get
	 * memento.
	 */
	public JpaDivergenceReasonInputMethodGetMemento() {

	}

	/**
	 * Instantiates a new jpa divergence reason input method repository get
	 * memento.
	 *
	 * @param entities
	 *            the entities
	 */
	public JpaDivergenceReasonInputMethodGetMemento(KrcmtDvgcTime entities) {

		this.entities = entities;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.divergence.time.DivergenceReasonInputMethodGetMemento#getDivergenceTimeNo()
	 */
	@Override
	public int getDivergenceTimeNo() {
		return entities.getId().getNo();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.divergence.time.DivergenceReasonInputMethodGetMemento#getCompanyId()
	 */
	@Override
	public String getCompanyId() {
		return entities.getId().getCid();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.divergence.time.DivergenceReasonInputMethodGetMemento#getDivergenceReasonInputed()
	 */
	@Override
	public boolean getDivergenceReasonInputed() {
		return entities.getDvgcReasonInputed().intValue() == 1;

	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.divergence.time.DivergenceReasonInputMethodGetMemento#getDivergenceReasonSelected()
	 */
	@Override
	public boolean getDivergenceReasonSelected() {
		return entities.getDvgcReasonSelected().intValue() == 1;

	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.divergence.time.DivergenceReasonInputMethodGetMemento#getReasons()
	 */
	@Override
	public List<DivergenceReasonSelect> getReasons() {
		
		if (CollectionUtil.isEmpty(entities.krcstDvgcReason)) return Collections.emptyList();
		
		return entities.krcstDvgcReason.stream()
								.map(x -> new DivergenceReasonSelect(new JpaDivergenceReasonSelectGetMemento(x)))
								.collect(Collectors.toList());
	}

}
