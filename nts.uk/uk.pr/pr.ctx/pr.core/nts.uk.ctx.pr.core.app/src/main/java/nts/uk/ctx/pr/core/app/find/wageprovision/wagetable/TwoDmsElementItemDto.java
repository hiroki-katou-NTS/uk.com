package nts.uk.ctx.pr.core.app.find.wageprovision.wagetable;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.ElementItem;

/**
 * 要素項目
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TwoDmsElementItemDto {
	// Merge domain 要素項目, 要素項目（マスタ）, 要素項目（数値）
	/**
	 * 要素項目（マスタ）.マスタコード
	 */
	private String masterCode;
	
	private String masterName;

	/**
	 * 要素項目(数値).枠番
	 */
	private Integer frameNumber;

	/**
	 * .要素項目(数値)当該枠下限
	 */
	private Integer frameLowerLimit;

	/**
	 * 要素項目(数値).当該枠上限
	 */
	private Integer frameUpperLimit;
	
	private List<ElementItemDto> listSecondDms;

	public static TwoDmsElementItemDto fromDomainToDto(ElementItem domain) {
		TwoDmsElementItemDto dto = new TwoDmsElementItemDto();
		dto.masterCode = domain.getMasterElementItem().map(i -> i.getMasterCode().v()).orElse(null);
		domain.getNumericElementItem().ifPresent(numericElementItem -> {
			dto.frameNumber = numericElementItem.getFrameNumber().v();
			dto.frameLowerLimit = numericElementItem.getFrameLowerLimit().v();
			dto.frameUpperLimit = numericElementItem.getFrameUpperLimit().v();
		});
		return dto;
	}
	
	public TwoDmsElementItemDto(ElementItemDto dto) {
		this.masterCode = dto.getMasterCode();
		this.masterName = dto.getMasterName();
		this.frameNumber = dto.getFrameNumber();
		this.frameLowerLimit = dto.getFrameLowerLimit();
		this.frameUpperLimit = dto.getFrameUpperLimit();
		this.listSecondDms = new ArrayList<>();
	}

}
