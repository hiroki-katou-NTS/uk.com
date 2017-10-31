package nts.uk.ctx.at.shared.infra.entity.employmentrule.hourlate.overtime.overtimeframe;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 *
 * @author loivt
 */
@Entity
@Table(name = "KRQDT_OVERTIME_FRAME")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KrqdtOvertimeFrame extends UkJpaEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected KrqdtOvertimeFramePK krqdtOvertimeFramePK;
    
    @Column(name = "USE_ATR")
    private int useAtr;
    
    @Column(name = "TRANSFER_FRAME_NAME")
    private String transferFrameName;
    
    @Column(name = "OVERTIME_FRAME_NAME")
    private String overtimeFrameName;
    
	@Override
	protected Object getKey() {
		return krqdtOvertimeFramePK;
	}   
}

