package nts.uk.ctx.at.function.dom.annualworkschedule;

import java.util.Optional;
import java.util.List;

/**
* 年間勤務表（36チェックリスト）の出力項目設定
*/
public interface SetOutItemsWoScRepository
{

    List<SetOutItemsWoSc> getAllSetOutItemsWoSc();

    Optional<SetOutItemsWoSc> getSetOutItemsWoScById(String cid, int cd);

    void add(SetOutItemsWoSc domain);

    void update(SetOutItemsWoSc domain);

    void remove(String cid, int cd);

}
