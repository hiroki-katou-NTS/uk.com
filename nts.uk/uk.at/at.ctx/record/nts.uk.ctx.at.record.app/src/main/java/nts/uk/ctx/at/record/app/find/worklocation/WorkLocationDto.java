package nts.uk.ctx.at.record.app.find.worklocation;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Value;
import nts.uk.ctx.at.record.app.command.worklocation.WorkplacePossibleCmd;
import nts.uk.ctx.at.record.dom.stampmanagement.workplace.WorkLocation;

@Value
/**
 * 
 * @author hieult
 *
 */
public class WorkLocationDto {
	
	/** 契約コード */
	private String contractCode;
		
	/** コード */
	private String workLocationCD;
	
	/** 名称 */
	private String workLocationName;
	
	/** 打刻範囲 . 半径*/
	private Integer radius;
	
	/** 打刻範囲.地理座標.緯度*/
	private Double latitude;
	
	/** 打刻範囲.地理座標.経度*/
	private Double longitude;
	
	/** IPアドレス一覧*/
	private List<Ipv4AddressDto>  listIPAddress;
	
	/** 職場*/
	private WorkplacePossibleCmd  listWorkplace;
	
	public static WorkLocationDto fromDomain (WorkLocation domain) {
		return new WorkLocationDto (
				domain.getContractCode().v(),
				domain.getWorkLocationCD().v(),
				domain.getWorkLocationName().v(),
				domain.getStampRange().map(x-> x.getRadius().value).orElse(null),
				domain.getStampRange().map(x-> .getGeoCoordinate().map(x -> x.getLatitude()).orElse(null)).orElse(null) ,
				domain.getStampRange().map(x-> .getGeoCoordinate().map(x -> x.getLongitude()).orElse(null)).orElse(null),
				domain.getListIPAddress().stream().map(c->new Ipv4AddressDto(c)).collect(Collectors.toList()),
				domain.getWorkplace().map(c->WorkplacePossibleCmd.toDto(c)).orElse(null));
	}
}
