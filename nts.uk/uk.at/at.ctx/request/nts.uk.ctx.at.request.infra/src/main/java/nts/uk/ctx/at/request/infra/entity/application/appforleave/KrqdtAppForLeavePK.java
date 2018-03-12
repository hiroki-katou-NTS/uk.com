package nts.uk.ctx.at.request.infra.entity.application.appforleave;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class KrqdtAppForLeavePK implements Serializable{
	
	
	private static final long serialVersionUID = 1L;

	@Column(name = "CID")
    private String cid;
    
    @Column(name = "APP_ID")
    private String appId;

}
