package nts.uk.ctx.exio.dom.qmm.setperiodcycle;

import java.util.Optional;
import java.util.List;

/**
 * 
 * @author thanh.tq 有効期間とサイクルの設定
 *
 */
public interface SetPeriodCycleRepository {

	List<SetPeriodCycle> getAllSetPeriodCycle();

	Optional<SetPeriodCycle> getSetPeriodCycleById(String salaryItemId);

	void add(SetPeriodCycle domain);

	void update(SetPeriodCycle domain);

	void remove(String salaryItemId);

}
