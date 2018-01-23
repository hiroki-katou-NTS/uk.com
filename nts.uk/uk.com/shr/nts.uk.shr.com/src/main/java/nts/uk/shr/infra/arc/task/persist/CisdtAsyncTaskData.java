package nts.uk.shr.infra.arc.task.persist;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CISDT_ASYNC_TASK_DATA")
@Cacheable(false)
public class CisdtAsyncTaskData implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	CisdtAsyncTaskDataKey pk;

	@Column(name = "VALUE")
	String value;
}
