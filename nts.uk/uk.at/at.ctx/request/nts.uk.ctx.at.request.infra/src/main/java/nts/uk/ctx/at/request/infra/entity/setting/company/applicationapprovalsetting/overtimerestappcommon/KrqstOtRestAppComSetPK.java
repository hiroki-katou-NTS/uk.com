package nts.uk.ctx.at.request.infra.entity.setting.company.applicationapprovalsetting.overtimerestappcommon;

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
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KrqstOtRestAppComSetPK implements Serializable {

	private static final long serialVersionUID = 1L;
	
    @Column(name = "CID")
    private String cid;
	
    @Column(name = "APP_TYPE")
    private int appType;
    
}

