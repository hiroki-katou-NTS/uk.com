package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;

import java.util.List;
import java.util.Optional;

/**
 * 賃金テーブル
 */
public interface WageTableRepository {

	public List<WageTable> getAllWageTable(String companyId);

	public Optional<WageTable> getWageTableById(String companyId, String code);

	public void add(WageTable domain);

	public void update(WageTable domain);

	public void remove(String companyId, String code);

}
