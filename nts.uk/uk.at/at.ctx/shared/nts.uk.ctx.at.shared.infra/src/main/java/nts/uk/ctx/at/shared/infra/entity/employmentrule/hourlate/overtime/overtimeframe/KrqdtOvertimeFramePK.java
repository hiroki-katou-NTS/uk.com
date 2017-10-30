package nts.uk.ctx.at.shared.infra.entity.employmentrule.hourlate.overtime.overtimeframe;

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
public class KrqdtOvertimeFramePK implements Serializable {

	private static final long serialVersionUID = 1L;
	
    @Column(name = "CID")
    private String cid;
    
    @Column(name = "OT_FRAME_NO")
    private int otFrameNo;
}

