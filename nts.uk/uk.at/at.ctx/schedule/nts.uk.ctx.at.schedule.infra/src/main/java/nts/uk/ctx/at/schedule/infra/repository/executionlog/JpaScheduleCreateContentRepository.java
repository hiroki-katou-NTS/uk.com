package nts.uk.ctx.at.schedule.infra.repository.executionlog;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreateContent;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreateContentRepository;
import nts.uk.ctx.at.schedule.infra.entity.executionlog.KscmtScCreateContent;

/**
 * The Class JpaScheduleCreateContentRepository.
 */
@Stateless
public class JpaScheduleCreateContentRepository extends JpaRepository implements ScheduleCreateContentRepository{

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreateContentRepository#findByExecutionId(java.lang.String)
	 */
	@Override
	public Optional<ScheduleCreateContent> findByExecutionId(String executionId) {
		return this.queryProxy()
				.find(executionId, KscmtScCreateContent.class)
				.map(entity -> this.toDomain(entity));
	}

	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the schedule create content
	 */
	private ScheduleCreateContent toDomain(KscmtScCreateContent entity) {
		return new ScheduleCreateContent(new JpaScheduleCreateContentGetMemento(entity));
	}

}
