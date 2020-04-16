package nts.uk.ctx.at.record.infra.entity.stamp.application;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.stamp.application.StampResultDisplay;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@Entity
@NoArgsConstructor
@Table(name="KRCCT_STAMP_FUNCTION")
public class KrccpStampFunction  extends ContractUkJpaEntity{
	
	@EmbeddedId
    public KrccpStampFunctionPk pk;
	
	/** 使用区分*/
	@Column(name = "RECORD_DISPLAY_ART")
	public int recordDisplayArt;
	
	@OneToMany(targetEntity = KrccpStampRecordDis.class, mappedBy = "krccpStampFunction", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    @JoinTable(name = "KRCCT_STAMP_RECORD_DIS")
	public List<KrccpStampRecordDis> lstRecordDis;
	
	@Override
	protected Object getKey() {
		return this.pk;
	}

	public KrccpStampFunction(KrccpStampFunctionPk pk, int recordDisplayArt, List<KrccpStampRecordDis> lstRecordDis) {
		super();
		this.pk = pk;
		this.recordDisplayArt = recordDisplayArt;
		this.lstRecordDis = lstRecordDis;
	}
	
	public KrccpStampFunction(KrccpStampFunctionPk pk, int recordDisplayArt) {
		super();
		this.pk = pk;
		this.recordDisplayArt = recordDisplayArt;
	}
	
	public StampResultDisplay toDomain(){
		return new StampResultDisplay(this.pk.companyId, EnumAdaptor.valueOf(this.recordDisplayArt, NotUseAtr.class), this.lstRecordDis.stream().map(mapper->mapper.toDomain()).collect(Collectors.toList()));
	}
	
	public static KrccpStampFunction toEntity(StampResultDisplay display){
		return new KrccpStampFunction(new KrccpStampFunctionPk(display.getCompanyId()), display.getUsrAtr().value, display.getLstDisplayItemId().stream().map(mapper->KrccpStampRecordDis.toEntity(mapper)).collect(Collectors.toList()));
	}
	
	public StampResultDisplay toDomainStamp(){
		return new StampResultDisplay(this.pk.companyId, EnumAdaptor.valueOf(this.recordDisplayArt, NotUseAtr.class));
	}
	
	public static KrccpStampFunction toEntityStamp(StampResultDisplay display){
		return new KrccpStampFunction(new KrccpStampFunctionPk(display.getCompanyId()), display.getUsrAtr().value);
	}
}
																			
									
