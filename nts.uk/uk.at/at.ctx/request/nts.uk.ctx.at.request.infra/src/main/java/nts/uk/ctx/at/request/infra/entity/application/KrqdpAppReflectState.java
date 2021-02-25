package nts.uk.ctx.at.request.infra.entity.application;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class KrqdpAppReflectState {
	
	@Column(name = "CID")
    private String companyID;
    
    @Column(name = "APP_ID")
    private String appID;
    
    @Column(name = "APP_DATE")
    private GeneralDate targetDate;
    
}
