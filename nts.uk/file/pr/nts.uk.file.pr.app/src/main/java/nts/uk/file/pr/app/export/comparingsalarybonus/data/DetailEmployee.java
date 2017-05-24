package nts.uk.file.pr.app.export.comparingsalarybonus.data;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DetailEmployee {
	private String personID;
	private String SCD;
	private String personName;
	List<DataRowComparingSalaryBonus> lstData;
	List<DataRowComparingSalaryBonusDto> lstDataDto;

	public List<DataRowComparingSalaryBonusDto> convertDataRowComparingSalaryBonusDto(
			List<DataRowComparingSalaryBonus> lstData) {
		List<DataRowComparingSalaryBonusDto> lstDto = lstData.stream().map(c -> {
			DataRowComparingSalaryBonusDto dataRowDto = new DataRowComparingSalaryBonusDto();
			dataRowDto.setItemName(c.getItemName());
			if (c.getMonth1() < 0) {
				dataRowDto.setMonth1("");
			} else {
				dataRowDto.setMonth1(String.valueOf(c.getMonth1()));
			}
			
			if (c.getMonth2() < 0) {
				dataRowDto.setMonth2("");
			} else {
				dataRowDto.setMonth2(String.valueOf(c.getMonth2()));
			}
			
			if (c.getDifferentSalary() < 0) {
				dataRowDto.setDifferentSalary("");
			} else {
				dataRowDto.setDifferentSalary(String.valueOf(c.getDifferentSalary()));
			}
			dataRowDto.setRegistrationStatus1(c.getRegistrationStatus1());
			dataRowDto.setRegistrationStatus2(c.getRegistrationStatus2());
			dataRowDto.setReason(c.getReason());
			return  dataRowDto;
		}).collect(Collectors.toList());
		return lstDto;

	}
}
