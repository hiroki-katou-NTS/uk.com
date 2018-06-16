package nts.uk.ctx.sys.assist.infra.entity.deletedata;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class SspdtResultLogDeletionPK implements Serializable{
	 
	private static final long serialVersionUID = 1L;
	@Basic(optional=false)
	@Column(name = "SEQ_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "log_generator")
	@SequenceGenerator(name="log_generator", sequenceName = "log_seq", allocationSize=1)
    public int seqId;
}
