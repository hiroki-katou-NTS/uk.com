package nts.uk.ctx.hr.develop.app.careermgmt.careerpath.dto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import nts.uk.ctx.hr.develop.dom.careermgmt.careerpath.MasterRequirement;

@Getter
@Data
@AllArgsConstructor
public class MasterRequirementDto {

	public String masterType;

	public List<String> masterItemList;

	public static MasterRequirementDto fromDomain(Optional<MasterRequirement> masterRequirement) {
		if (!masterRequirement.isPresent()) {
			return null;
		}
		return new MasterRequirementDto(masterRequirement.get().getMasterType(), 
				masterRequirement.get().getMasterItemList().stream().map(c -> c.getMasterItem()).collect(Collectors.toList()));
	}

}
