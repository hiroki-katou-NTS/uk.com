package nts.uk.ctx.at.request.infra.entity.overtimeinstruct;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class KrqdtInstructOvertimePK implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Basic(optional = false)
    @Column(name = "CID")
    private String cid;
	

    @Column(name = "TARGET_PERSON")
    private String targetPerson;
    

    @Column(name = "INSTRUCT_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date instructDate;
	

}
