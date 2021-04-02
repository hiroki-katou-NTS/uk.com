package nts.uk.ctx.at.record.infra.entity.stamp.application;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.stamp.application.CommonSettingsStampInput;
import nts.uk.ctx.at.record.dom.stamp.application.StampResultDisplay;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="KRCMT_STAMP_FUNCTION")
public class KrcmtStampFunction  extends ContractUkJpaEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 会社ID */
	@Id
	@Column(name = "CID")
	public String cid;
	
	/** 使用区分*/
	@Column(name = "RECORD_DISPLAY_ART")
	public int recordDisplayArt;
	
	/**
	 * GoogleMap利用するか
	 */
	@Basic(optional = false)
	@Column(name = "GOOGLE_MAP_USE_ART")
	public int googleMapUseArt;

	/**
	 * マップ表示アドレス
	 */
	@Basic(optional = true)
	@Column(name = "MAP_ADDRESS")
	public String mapAddress;
	
	/**
	 * GoogleMap利用するか
	 */
	@Basic(optional = false)
	@Column(name = "SUPPORT_USE_ART")
	public int supportUseArt;
	
	@OneToMany(targetEntity = KrccpStampRecordDis.class, mappedBy = "krccpStampFunction", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinTable(name = "KRCMT_STAMP_RECORD_DIS")
	public List<KrccpStampRecordDis> lstRecordDis;
	
	@Override
	protected Object getKey() {
		return this.cid;
	}

	public StampResultDisplay toDomain(){
		return new StampResultDisplay(this.cid, EnumAdaptor.valueOf(this.recordDisplayArt, NotUseAtr.class), this.lstRecordDis.stream().map(mapper->mapper.toDomain()).collect(Collectors.toList()));
	}
	
	public StampResultDisplay toDomainStamp(){
		return new StampResultDisplay(this.cid, EnumAdaptor.valueOf(this.recordDisplayArt, NotUseAtr.class));
	}
	
	public void update(CommonSettingsStampInput domain, Optional<StampResultDisplay> display) {
		this.recordDisplayArt = display.isPresent() ? display.get().getUsrAtr().value : 0;
		this.googleMapUseArt = domain.isGooglemap() ? 1 : 0;
		domain.getMapAddres().ifPresent(c-> this.mapAddress = c.v());
		this.supportUseArt = domain.getSupportUseArt().value;
	}
}
																			
									
