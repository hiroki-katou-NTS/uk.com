package nts.uk.ctx.at.request.infra.entity.application.overtime;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
/**
 * Refactor5
 * 
 * @author hoangnd
 *
 */

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KrqdtAppOvertimePK implements Serializable {
	
	public static final long serialVersionUID = 1L;
	
    @Column(name = "CID")
    public String cid;
    
    @Column(name = "APP_ID")
    public String appId;
}
