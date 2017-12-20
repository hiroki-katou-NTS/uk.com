package nts.uk.ctx.at.shared.infra.entity.employmentrule.hourlate.breaktime.breaktimeframe;

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
@Table(name = "KRQDT_BREAK_TIME_FRAME")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KrqdtBreaktimeFrame extends UkJpaEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected KrqdtBreaktimeFramePK krqdtBreaktimeFramePK;
    
    @Column(name = "USE_ATR")
    private int useAtr;
    
    @Column(name = "TRANSFER_FRAME_NAME")
    private String transferFrameName;
    
    @Column(name = "BREAK_TIME_FRAME_NAME")
    private String breaktimeFrameName;
    
	@Override
	protected Object getKey() {
		return krqdtBreaktimeFramePK;
	}   
}

