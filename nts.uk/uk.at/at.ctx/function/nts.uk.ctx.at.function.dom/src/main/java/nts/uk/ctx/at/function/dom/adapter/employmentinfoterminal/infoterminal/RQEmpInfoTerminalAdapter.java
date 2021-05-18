package nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal;

import java.util.Optional;

public interface RQEmpInfoTerminalAdapter {
	public Optional<String> getEmpInfoTerminalCode(String contractCode, String macAddr);
}
