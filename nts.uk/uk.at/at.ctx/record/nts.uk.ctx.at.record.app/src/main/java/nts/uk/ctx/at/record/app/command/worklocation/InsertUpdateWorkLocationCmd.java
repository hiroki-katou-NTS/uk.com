package nts.uk.ctx.at.record.app.command.worklocation;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.gul.location.GeoCoordinate;
import nts.uk.ctx.at.record.app.find.worklocation.Ipv4AddressDto;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.dom.stampmanagement.workplace.RadiusAtr;
import nts.uk.ctx.at.record.dom.stampmanagement.workplace.StampMobilePossibleRange;
import nts.uk.ctx.at.record.dom.stampmanagement.workplace.WorkLocation;
import nts.uk.ctx.at.record.dom.stampmanagement.workplace.WorkLocationName;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;
import nts.uk.shr.com.context.AppContexts;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InsertUpdateWorkLocationCmd {
	/** コード */
	private String workLocationCD;    

	/** 名称 */
	private String workLocationName; 
	
	/** 打刻範囲.半径 */
	private Integer radius;
	
	/** 打刻範囲.地理座標 .latitude */
	private Double latitude;
	/** 打刻範囲.地理座標 .longitude */
	private Double longitude;
	
	/** IPアドレス一覧*/
	private List<Ipv4AddressDto> listIPAddress;
	
	/** 職場*/
	private WorkplacePossibleCmd workplace;
	
	public WorkLocation toDomain() {
		return new WorkLocation(
				new ContractCode(AppContexts.user().contractCode()),
				new WorkLocationCD(this.workLocationCD), 
				new WorkLocationName(this.workLocationName), 
				new StampMobilePossibleRange(this.radius == null ? null : RadiusAtr.toEnum(this.radius),

						(this.latitude == null || this.longitude == null) ? null: new GeoCoordinate(this.latitude, this.longitude)),
				
				this.listIPAddress.stream().map(c->c.toDomain()).collect(Collectors.toList()),
				this.workplace == null ? Optional.empty() : Optional.of(this.workplace.toDomain()));
	}
	
	public static InsertUpdateWorkLocationCmd toDto(WorkLocation domain) {
		return new InsertUpdateWorkLocationCmd(
				domain.getWorkLocationCD().v(), 
				domain.getWorkLocationName().v(), 
				domain.getStampRange().getRadius() == null ? null : domain.getStampRange().getRadius().value,
						
				domain.getStampRange().getGeoCoordinate().map(x-> x.getLatitude()).orElse(null),
				domain.getStampRange().getGeoCoordinate().map(x-> x.getLongitude()).orElse(null),
				
				domain.getListIPAddress().stream().map(c-> new Ipv4AddressDto(c)).collect(Collectors.toList()),
				domain.getWorkplace().map(c-> WorkplacePossibleCmd.toDto(c)).orElse(null));
	}
}
