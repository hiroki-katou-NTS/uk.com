package nts.uk.file.at.app.export.statement.stamp;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConditionListOfStampQueryDto {
	public String startDate;
	public String endDate;
	public List<String> lstEmployee;
	public int selectedIdProcessSelect;
	public boolean cardNumNotRegister;
}
