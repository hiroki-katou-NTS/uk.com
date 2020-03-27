package nts.uk.ctx.at.record.infra.entity.stamp.application;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.stamp.application.StampAttenDisplay;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Entity
@NoArgsConstructor
@Table(name="KRCCT_STAMP_RECORD_DIS")
public class KrccpStampRecordDis  extends ContractUkJpaEntity{
	
	@EmbeddedId
    public KrccpStampRecordDisPk pk;
	
	@ManyToOne
    @PrimaryKeyJoinColumns({
    	@PrimaryKeyJoinColumn(name = "CID", referencedColumnName = "CID")
    })
	public KrccpStampFunction krccpStampFunction;
	
	public static  KrccpStampRecordDis toEntity(StampAttenDisplay x){
		return new KrccpStampRecordDis(new KrccpStampRecordDisPk(x.getCompanyId(), x.getDisplayItemId()));
	}
	
	public StampAttenDisplay toDomain(){
		return new StampAttenDisplay(pk.companyId, pk.dAtdItemId);
	}
	
	@Override
	protected Object getKey() {
		return this.pk;
	}

	public KrccpStampRecordDis(KrccpStampRecordDisPk pk) {
		super();
		this.pk = pk;
	}
}
																			
