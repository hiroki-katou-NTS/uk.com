package nts.uk.ctx.at.function.dom.adapter.reservation.bento;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface BentoMenuAdaptor {

	public List<BentoMenuImport> getBentoMenu(String companyID, GeneralDate date);
}
