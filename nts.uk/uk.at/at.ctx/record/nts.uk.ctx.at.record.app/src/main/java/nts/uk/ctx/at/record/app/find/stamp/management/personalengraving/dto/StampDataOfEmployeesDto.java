package nts.uk.ctx.at.record.app.find.stamp.management.personalengraving.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import lombok.Data;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampRecord;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.StampDataOfEmployees;

/**
 * @author anhdt
 *
 */
@Data
public class StampDataOfEmployeesDto {
	private String employeeId;
	private String date;
	private List<StampRecordDto> stampRecords = new ArrayList<>();
	private List<EmpStampCardDto> stamps = new ArrayList<>();
	
	public StampDataOfEmployeesDto (StampDataOfEmployees domain) {
		this.employeeId = domain.getEmployeeId();
		this.date = domain.getDate().toString();
		
		Map<String, Stamp> cardNumberStamp = domain.getListStamp()
				 .stream()
				 .filter(distinctByKey(s -> s.retriveCardNumber()))
				 .collect(Collectors.toMap(Stamp::retriveCardNumber, s -> s));

		for(StampRecord stampRecord : domain.getListStampRecord()) {
			Stamp stamp = cardNumberStamp.get(stampRecord.getStampNumber().v());
			this.stampRecords.add(new StampRecordDto(stampRecord, stamp));
		}
				
	}
	
	public static <T> Predicate<T> distinctByKey(
		    Function<? super T, ?> keyExtractor) {
		   
		    Map<Object, Boolean> seen = new ConcurrentHashMap<>(); 
		    return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null; 
		}
}
