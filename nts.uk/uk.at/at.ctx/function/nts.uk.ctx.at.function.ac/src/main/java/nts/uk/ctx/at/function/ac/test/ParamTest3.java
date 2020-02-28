package nts.uk.ctx.at.function.ac.test;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ParamTest3 {
	
	String companyId;
	String historyId;
	List<String> listWorkplaceId;
	
}
