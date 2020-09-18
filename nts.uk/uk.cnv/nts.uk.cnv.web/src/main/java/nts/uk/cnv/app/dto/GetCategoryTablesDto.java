package nts.uk.cnv.app.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class GetCategoryTablesDto {
	List<String> tables;
}
