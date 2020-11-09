package nts.uk.ctx.at.request.infra.entity.application.overtime;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
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
@Data
public class KrqdtAppOvertimePK implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    @Column(name = "CID")
    private String cid;
    
    @Column(name = "APP_ID")
    private String appId;
}
