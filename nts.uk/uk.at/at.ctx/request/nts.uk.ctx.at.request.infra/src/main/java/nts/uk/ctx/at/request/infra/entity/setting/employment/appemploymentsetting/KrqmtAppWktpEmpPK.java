package nts.uk.ctx.at.request.infra.entity.setting.employment.appemploymentsetting;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author loivt
 */
@Embeddable
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class KrqmtAppWktpEmpPK implements Serializable {

	private static final long serialVersionUID = 1L;
	
    @Column(name = "CID")
    private String cid;
	
    @Column(name = "EMPLOYMENT_CODE")
    private String employmentCode;
    
    @Column(name = "APP_TYPE")
    private int appType;
    
    @Column(name = "HOLIDAY_OR_PAUSE_TYPE")
    private int holidayOrPauseType;
    
    @Column(name = "WORK_TYPE_CODE")
    private String workTypeCode;

}
