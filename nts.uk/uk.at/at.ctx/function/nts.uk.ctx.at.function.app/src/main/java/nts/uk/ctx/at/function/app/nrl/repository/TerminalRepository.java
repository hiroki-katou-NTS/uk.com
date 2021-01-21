package nts.uk.ctx.at.function.app.nrl.repository;

import java.util.Optional;

/**
 * Terminal repository.
 * 
 * @author manhnd
 */
public interface TerminalRepository {

	/**
	 * Get terminal.
	 * @param nrlNo
	 * @param macAddress
	 * @return optional terminal
	 */
	Optional<Terminal> get(String nrlNo, String macAddress);
}
