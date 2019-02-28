package nts.uk.ctx.pereg.app.find.layoutdef.classification;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.Data;

@Data
public class GridLayoutPersonInfoClsDto {
	private String employeeId;
	private String personId;

	private List<LayoutPersonInfoClsDto> layoutDtos;

	public GridLayoutPersonInfoClsDto(String employeeId, String personId, List<LayoutPersonInfoClsDto> layout) {
		this.employeeId = employeeId;
		this.personId = personId;
		this.layoutDtos = Optional.ofNullable(layout).map(List::stream).orElseGet(Stream::empty)
				.collect(Collectors.toList());
	}
}
