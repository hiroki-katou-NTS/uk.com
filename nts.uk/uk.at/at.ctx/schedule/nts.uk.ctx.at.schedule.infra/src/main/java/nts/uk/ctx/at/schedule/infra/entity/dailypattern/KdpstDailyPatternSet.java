package nts.uk.ctx.at.schedule.infra.entity.dailypattern;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.WorkCycle;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.WorkCycleCode;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.WorkCycleInfo;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.WorkCycleName;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KdpstDailyPatternSet.
 */
@Getter
@Setter
@Entity
@Table(name = "KSCMT_WORKING_CYCLE")
public class KdpstDailyPatternSet extends UkJpaEntity implements Serializable {

    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;	
	
    /** The kcsmtContCalendarSetPK. */
    @EmbeddedId
    public KdpstDailyPatternSetPK kdpstDailyPatternSetPK;


    @Basic(optional = false)
    @Column(name = "CONTRACT_CD")
    public String contractCd;
	
    /** The pattern name. */
    @Column(name = "NAME")
    public String patternName;
    
    /** The list calendar type. */
    @JoinColumns({@JoinColumn(name = "CID", referencedColumnName = "CID", insertable = true, updatable = true),
            @JoinColumn(name = "CD", referencedColumnName = "CD", insertable = true, updatable = true)})
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    public List<KdpstDailyPatternVal> listKdpstDailyPatternVal;

    /**
     * Instantiates a new kcsmt cont calendar set.
     */
    public KdpstDailyPatternSet() {
    	super();
    }

    public KdpstDailyPatternSet(KdpstDailyPatternSetPK kdpstDailyPatternSetPK) {
        this.kdpstDailyPatternSetPK = kdpstDailyPatternSetPK;
    }

    public KdpstDailyPatternSet(KdpstDailyPatternSetPK kdpstDailyPatternSetPK, String contractCd, String patternName) {
        this.kdpstDailyPatternSetPK = kdpstDailyPatternSetPK;
        this.contractCd = contractCd;
        this.patternName = patternName;
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
    public static WorkCycle toDomain(KdpstDailyPatternSet entity, List<KdpstDailyPatternVal> entityValues){
        List<WorkCycleInfo> infos = new ArrayList<WorkCycleInfo>();
        entityValues.stream().forEach(item -> {
            WorkCycleInfo info = WorkCycleInfo.create(
                    item.days,
                    new WorkInformation(item.workTypeSetCd,item.workingHoursCd)
            );
            infos.add(info);
        });
        WorkCycle result = WorkCycle.create(
                entity.kdpstDailyPatternSetPK.cid,
                entity.kdpstDailyPatternSetPK.patternCd,
                entity.patternName,
                infos
        );
        return result;
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
     */
    public static WorkCycle toDomainGet(KdpstDailyPatternSet entity, List<KdpstDailyPatternVal> entityValues){
        List<WorkCycleInfo> infos = new ArrayList<WorkCycleInfo>();
        entityValues.stream().forEach(item -> {
            WorkCycleInfo info = WorkCycleInfo.create(
                    item.days,
                    new WorkInformation(item.workTypeSetCd,item.workingHoursCd)
            );
            infos.add(info);
        });
        WorkCycle result = new WorkCycle(
                entity.kdpstDailyPatternSetPK.cid,
                new WorkCycleCode(entity.kdpstDailyPatternSetPK.patternCd),
                new WorkCycleName(entity.patternName),
                infos
        );
        return result;
    }

    public static KdpstDailyPatternSet toEntity(WorkCycle domain) {
        return new KdpstDailyPatternSet(
                new KdpstDailyPatternSetPK(domain.getCid(), domain.getCode().v()),
                AppContexts.user().contractCode(),
                domain.getName().v()
        );
    }

    public KdpstDailyPatternSet updateEntity(WorkCycle domain) {
        this.patternName = domain.getName().v();
        return this;
    }
    @Override
    protected Object getKey() {
        return this.kdpstDailyPatternSetPK;
    }

}
