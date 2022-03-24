package nts.uk.ctx.at.record.infra.entity.worklocation;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.gul.location.GeoCoordinate;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.dom.stampmanagement.workplace.RadiusAtr;
import nts.uk.ctx.at.record.dom.stampmanagement.workplace.StampMobilePossibleRange;
import nts.uk.ctx.at.record.dom.stampmanagement.workplace.WorkLocation;
import nts.uk.ctx.at.record.dom.stampmanagement.workplace.WorkLocationName;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timedifferencemanagement.RegionCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name = "KRCMT_WORK_LOCATION")
@AllArgsConstructor
@NoArgsConstructor

/**
 * 
 * @author hieult
 *
 */
public class KrcmtWorkLocation extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KwlmtWorkLocationPK kwlmtWorkLocationPK;

	/** 勤務場所名称 */
	@Column(name = "WK_LOCATION_NAME")
	public String workLocationName;
	
	/** 半径 */
	@Column(name = "RADIUS")
	public Integer radius;
	
	/** 緯度 */
	@Column(name = "LATITUDE")
	public Double latitude;
	
	/** 経度 */
	@Column(name = "LONGITUDE")
	public Double longitude;
	
	/** 地域コード */
	@Column(name = "REGIONAL_CD")
	public Integer regionalCd;
	
	@OneToOne(targetEntity = KrcmtWorkplacePossible.class, mappedBy = "krcmtWorkLocation", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinTable(name = "KRCMT_POSSIBLE_WKP")
	public KrcmtWorkplacePossible krcmtWorkplacePossible;
	
	@OneToMany(targetEntity = KrcmtIP4Address.class, mappedBy = "krcmtWorkLocation", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinTable(name = "KRCMT_IP4ADDRESS")
	public List<KrcmtIP4Address> krcmtIP4Address;

	@Override
	protected Object getKey() {
		return kwlmtWorkLocationPK;
	}
	
	public static KrcmtWorkLocation toEntity(WorkLocation workLocation) {
		
		return new KrcmtWorkLocation(new KwlmtWorkLocationPK(
											workLocation.getContractCode().v(), 
											workLocation.getWorkLocationCD().v()),
				workLocation.getWorkLocationName().v(),
				workLocation.getStampRange().map(s -> s.getRadius().value).orElse(null),
				workLocation.getStampRange().map(s -> s.getGeoCoordinate().getLatitude()).orElse(null),
				workLocation.getStampRange().map(s -> s.getGeoCoordinate().getLongitude()).orElse(null),
				workLocation.getRegionCode().map(m -> m.v()).orElse(null),

				workLocation.getWorkplace().isPresent() ? KrcmtWorkplacePossible.toEntiy(
						workLocation.getContractCode().v(),
						workLocation.getWorkLocationCD().v(),
						workLocation.getWorkplace().get()) : null,
				workLocation
						.getListIPAddress().stream().map(c -> KrcmtIP4Address
								.toEntity(workLocation.getContractCode().v(), workLocation.getWorkLocationCD().v(), c))
						.collect(Collectors.toList())
				);
	}
	
	public  WorkLocation toDomain() {
		return new WorkLocation(
				new ContractCode(this.kwlmtWorkLocationPK.contractCode),
				new WorkLocationCD(this.kwlmtWorkLocationPK.workLocationCD), 
				new WorkLocationName(this.workLocationName),
				Optional.ofNullable(radius == null || latitude == null || longitude == null ?
						null : new StampMobilePossibleRange(RadiusAtr.toEnum(radius), new GeoCoordinate(latitude, longitude))),
				this.krcmtIP4Address.stream().map(c->c.toDomain()).collect(Collectors.toList()),
				Optional.ofNullable(this.krcmtWorkplacePossible == null ? null : this.krcmtWorkplacePossible.toDomain()),
				this.regionalCd == null ? Optional.empty() : Optional.of(new RegionCode(this.regionalCd)));
	}
}
