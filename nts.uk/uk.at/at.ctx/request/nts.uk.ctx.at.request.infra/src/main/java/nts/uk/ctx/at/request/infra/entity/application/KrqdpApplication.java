package nts.uk.ctx.at.request.infra.entity.application;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@Embeddable
@AllArgsConstructor
public class KrqdpApplication {
	
	@Column(name = "CID")
    private String companyID;
    
    @Column(name = "APP_ID")
    private String appID;

}
