package nts.uk.ctx.at.schedule.infra.repository.executionlog;

import nts.gul.text.StringUtil;
import nts.uk.ctx.at.schedule.dom.executionlog.*;
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.MonthlyPatternCode;
import nts.uk.ctx.at.schedule.infra.entity.executionlog.KscdtScheExeContent;
import nts.uk.ctx.at.shared.dom.employmentrules.organizationmanagement.ConditionEmployee;

import java.util.Optional;

/**
 * The Class JpaExecutionContentGetMemento.
 */
public class JpaScheduleCreateContentGetMemento implements ScheduleCreateContentGetMemento {

	/** The Constant TRUE_VALUE. */
	public static final int TRUE_VALUE = 1;

	/** The entity. */
	private KscdtScheExeContent entity;

	/**
	 * Instantiates a new jpa execution content get memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaScheduleCreateContentGetMemento(KscdtScheExeContent entity) {
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.executionlog.ExecutionContentGetMemento#
	 * getCopyStartDate()
	 */
	@Override
	public SpecifyCreation getSpecifyCreation() {
		return new SpecifyCreation(
				CreationMethod.valueOf(this.entity.getCreationMethod()),
				Optional.ofNullable(this.entity.getCopyStartYmd()),
				Optional.ofNullable(this.entity.getReferenceMaster() == null?  null:
						ReferenceMaster.valueOf(this.entity.getReferenceMaster())),
				Optional.ofNullable(StringUtil.isNullOrEmpty(this.entity.getMonthlyPatternId(),false)? null:
						new MonthlyPatternCode(this.entity.getMonthlyPatternId()))

		);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.executionlog.ExecutionContentGetMemento#
	 * getCreateMethodAtr()
	 */
	@Override
	public Optional<RecreateCondition> getRecreateCondition() {
		return Optional.of(new RecreateCondition(
				this.entity.getReTargetAtr(),
				this.entity.getReOverwriteConfirmed(),
				this.entity.getReOverwriteRevised(),
				Optional.of(new ConditionEmployee(
						this.entity.getReTargetTransfer(),
						this.entity.getReTargetLeave(),
						this.entity.getReTargetShortWork(),
						this.entity.getReTargetLaborChange()
				))
		));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.executionlog.ExecutionContentGetMemento#
	 * getConfirm()
	 */
	@Override
	public Boolean getConfirm() {
		return this.entity.getBeConfirmed();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.executionlog.ExecutionContentGetMemento#
	 * getImplementAtr()
	 */
	@Override
	public ImplementAtr getCreationType() {
		return ImplementAtr.valueOf(this.entity.getCreationType());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreateContentGetMemento#
	 * getExecutionId()
	 */
	@Override
	public String getExecutionId() {
		return this.entity.getExeId();
	}

}
