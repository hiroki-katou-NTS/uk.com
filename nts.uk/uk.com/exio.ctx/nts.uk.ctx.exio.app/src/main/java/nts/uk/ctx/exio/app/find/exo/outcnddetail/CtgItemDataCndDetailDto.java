package nts.uk.ctx.exio.app.find.exo.outcnddetail;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.exio.dom.exo.outcnddetail.CtgItemDataCndDetail;

@NoArgsConstructor
public class CtgItemDataCndDetailDto {
	/**
	 * Dto screen D
	 */
	@Setter
	@Getter
	private List<CtgItemDataDto> ctgItemDataList;

	@Setter
	@Getter
	private OutCndDetailDto cndDetaiList;

	public static CtgItemDataCndDetailDto fromDomain(CtgItemDataCndDetail domain) {
		CtgItemDataCndDetailDto dto = new CtgItemDataCndDetailDto();

		dto.setCtgItemDataList(domain.getDataItemsDetail().stream().map(x -> {
			return CtgItemDataDto.fromDomain(x);
		}).collect(Collectors.toList()));

		dto.setCndDetaiList(OutCndDetailDto.fromDomain(domain.getDataCndDetail()));
		
/*		dto.setDetaiItemList(domain.getDataCndItemsDetail().stream().map(x -> {
			return OutCndDetailItemDto.fromDomain(x);
		}).collect(Collectors.toList()));*/

		return dto;
	}
}
