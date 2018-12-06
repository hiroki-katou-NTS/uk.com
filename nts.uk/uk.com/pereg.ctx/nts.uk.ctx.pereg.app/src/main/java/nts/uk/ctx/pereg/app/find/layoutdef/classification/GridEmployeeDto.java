package nts.uk.ctx.pereg.app.find.layoutdef.classification;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pereg.app.find.person.info.item.PerInfoItemDefForLayoutDto;

@Data
@AllArgsConstructor
public class GridEmployeeDto {
	private String categoryId;
	private GeneralDate baseDate;
	
	private List<PerInfoItemDefForLayoutDto> headDatas;
	private List<GridEmployeeInfoDto> bodyDatas;
}
