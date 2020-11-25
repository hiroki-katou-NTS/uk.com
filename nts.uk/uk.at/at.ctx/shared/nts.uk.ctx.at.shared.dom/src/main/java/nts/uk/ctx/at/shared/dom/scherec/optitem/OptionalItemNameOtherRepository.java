package nts.uk.ctx.at.shared.dom.scherec.optitem;

import java.util.List;
import java.util.Optional;

public interface OptionalItemNameOtherRepository {
	
	public Optional<OptionalItemNameOther> findByKey(String companyId, int no, String langueId);
	
	public List<OptionalItemNameOther> findAll(String companyId, String langueId);
	
	public void add(OptionalItemNameOther item);
	
	public void update(OptionalItemNameOther item);
	
	public void remove(String companyId, int no);

}
