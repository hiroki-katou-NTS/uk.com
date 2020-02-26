package nts.uk.ctx.at.shared.app.find.workrule.shiftmaster;

import java.awt.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author anhdt
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlreadySettingWorkplaceDto {
	private List<String> workplaceIds;
} 
