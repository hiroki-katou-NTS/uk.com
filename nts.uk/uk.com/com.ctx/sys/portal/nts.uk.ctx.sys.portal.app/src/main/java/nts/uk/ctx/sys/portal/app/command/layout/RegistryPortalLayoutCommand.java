package nts.uk.ctx.sys.portal.app.command.layout;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.sys.portal.app.command.placement.PortalPlacementCommand;

/**
 * @author LamDT
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistryPortalLayoutCommand {

	/** PortalLayoutCommand */
	private PortalLayoutCommand portalLayoutCommand;

	/** List PortalPlacementCommand */
	private List<PortalPlacementCommand> listPortalPlacementCommand;
	
}