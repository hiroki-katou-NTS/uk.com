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
	private OutCndDetailDto cndDetai;

	public static CtgItemDataCndDetailDto fromDomain(CtgItemDataCndDetail domain) {
		CtgItemDataCndDetailDto dto = new CtgItemDataCndDetailDto();

		dto.setCtgItemDataList(domain.getCtgItemDataList().stream().map(x -> {
			return CtgItemDataDto.fromDomain(x);
		}).collect(Collectors.toList()));

		dto.setCndDetai(domain.getCndDetail().isPresent()
				? OutCndDetailDto.fromDomain(domain.getCndDetail().get()) : null);
		return dto;
	}
}
