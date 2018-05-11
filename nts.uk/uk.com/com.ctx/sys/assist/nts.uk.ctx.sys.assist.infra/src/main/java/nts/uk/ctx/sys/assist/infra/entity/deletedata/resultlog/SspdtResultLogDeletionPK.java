package nts.uk.ctx.sys.assist.infra.entity.deletedata.resultlog;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class SspdtResultLogDeletionPK implements Serializable{
	 
	private static final long serialVersionUID = 1L;
	@NotNull
	@Column(name = "SEQ_ID")
    public int seqId;
}
