package nts.uk.ctx.at.record.app.find.monthly.nursingleave.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KDL051ProcessDto {
	private List<AggrResultOfChildCareNurseDto> aggrResultOfChildCareNurse;
	private InterimRemainDto interimRemain;
}
