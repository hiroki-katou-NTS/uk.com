package nts.uk.ctx.exio.app.find.exo.categoryitemdata;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.exio.app.find.exo.outcnddetail.OutCndDetailItemDto;
import nts.uk.ctx.exio.dom.exo.categoryitemdata.CtgItemDataCndDetail;

@NoArgsConstructor
public class CtgItemDataCndDetailDto {
	/**
	 * Dto screen D
	 */
	@Setter
	@Getter
	private List<CtgItemDataTableDto> ctgItemDataList;

	@Setter
	@Getter
	private List<OutCndDetailItemDto> detaiItemList;

	public static CtgItemDataCndDetailDto fromDomain(CtgItemDataCndDetail domain) {
		CtgItemDataCndDetailDto dto = new CtgItemDataCndDetailDto();

		dto.setCtgItemDataList(domain.getDataItemsDetail().stream().map(x -> {
			return CtgItemDataTableDto.fromDomain(x);
		}).collect(Collectors.toList()));

		dto.setDetaiItemList(domain.getDataCndItemsDetail().stream().map(x -> {
			return OutCndDetailItemDto.fromDomain(x);
		}).collect(Collectors.toList()));

		return dto;
	}
}
