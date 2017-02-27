package nts.uk.shr.infra.arc.task.persist;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import nts.arc.layer.infra.data.entity.type.LocalDateTimeToDBConverter;

@Entity
@Table(name="CISDT_ASYNC_TASK")
public class CisdtAsyncTask implements Serializable {
	
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "TASK_ID")
	public String taskId;
	
	@Column(name = "TASK_STS")
	public int taskSts;

	@Convert(converter = LocalDateTimeToDBConverter.class)
	@Column(name = "CREATED_AT")
    public LocalDateTime createdAt;

	@Convert(converter = LocalDateTimeToDBConverter.class)
	@Column(name = "STARTED_AT")
    public LocalDateTime startedAt;

	@Convert(converter = LocalDateTimeToDBConverter.class)
	@Column(name = "FINISHED_AT")
    public LocalDateTime finishedAt;
	
	@OneToOne(optional = true, cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
	public CisdtAsyncTaskAbort abort;
}
