package nts.uk.ctx.at.schedule.infra.entity.dailypattern;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KdpstDailyPatternSet.
 */
@Setter
@Getter
@Entity
@Table(name = "KSCMT_DAILY_PATTERN_SET")
public class KdpstDailyPatternSet extends UkJpaEntity implements Serializable {

    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;	
	
    /** The kcsmtContCalendarSetPK. */
    @EmbeddedId
    private KdpstDailyPatternSetPK kdpstDailyPatternSetPK;
	
    /** The pattern name. */
    @Column(name = "PATTERN_NAME")
    private String patternName;
    
    /** The list calendar type. */
    @JoinColumns({@JoinColumn(name = "CID", referencedColumnName = "CID", insertable = true, updatable = true),
            @JoinColumn(name = "PATTERN_CD", referencedColumnName = "PATTERN_CD", insertable = true, updatable = true)})
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    public List<KdpstDailyPatternVal> listKdpstDailyPatternVal;
    
    /**
     * Instantiates a new kcsmt cont calendar set.
     */
    public KdpstDailyPatternSet() {
    	super();
    }
    
    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kdpstDailyPatternSetPK != null ? kdpstDailyPatternSetPK.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof KdpstDailyPatternSet)) {
            return false;
        }
        KdpstDailyPatternSet other = (KdpstDailyPatternSet) object;
        if ((this.kdpstDailyPatternSetPK == null && other.kdpstDailyPatternSetPK != null)
                || (this.kdpstDailyPatternSetPK != null && !this.kdpstDailyPatternSetPK.equals(
                        other.kdpstDailyPatternSetPK))) {
            return false;
        }
        return true;
    }
    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
     */
    @Override
    protected Object getKey() {
        return this.kdpstDailyPatternSetPK;
    }

}
