package nts.uk.ctx.at.request.infra.entity.overtimeinstruct;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class KrqdtOvertimeInstructPK implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Basic(optional = false)
    @Column(name = "CID")
    private String cid;
	
    @Basic(optional = false)
    @Column(name = "APP_ID")
    private String appId;

}
