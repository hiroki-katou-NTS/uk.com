package nts.uk.ctx.at.request.infra.entity.holidayworkinstruct;

import java.io.Serializable;
import java.util.Date;

//import javax.persistence.Basic;
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
public class KrqdtInstructHdWorkPK implements Serializable {

	private static final long serialVersionUID = 1L;
	
    /**
     * 対象者
     */
    @Column(name = "TARGET_PERSON")
    private String targetPerson;
    

    /**
     * 指示日付
     */
    @Column(name = "INSTRUCT_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date instructDate;

}
