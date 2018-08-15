package nts.uk.ctx.at.shared.app.find.specialholiday.grantinformation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantTime;

@Value
public class GrantTimeDto {
	/** 固定付与日 */
	private FixGrantDateDto fixGrantDate;
	
	/** 特別休暇付与テーブル */
	private List<GrantDateTblDto> grantDateTbl;

	public static GrantTimeDto fromDomain(GrantTime grantTime) {
		if(grantTime == null) {
			return null;
		}
		
		FixGrantDateDto fixGrantDateDto = FixGrantDateDto.fromDomain(grantTime.getFixGrantDate() != null ? grantTime.getFixGrantDate() : null);
		
		List<GrantDateTblDto> grantDateTblDto = grantTime.getGrantDateTbl() != null ? (grantTime.getGrantDateTbl().stream()
				.map(x-> GrantDateTblDto.fromDomain(x))
				.collect(Collectors.toList())) : new ArrayList<>();
		
		return new GrantTimeDto(fixGrantDateDto, grantDateTblDto);
	}
}
