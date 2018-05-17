package nts.uk.ctx.at.function.dom.annualworkschedule.repository;

import java.util.Optional;

import nts.uk.ctx.at.function.dom.annualworkschedule.SetOutItemsWoSc;

import java.util.List;

/**
* 年間勤務表（36チェックリスト）の出力項目設定
*/
public interface SetOutItemsWoScRepository
{

    List<SetOutItemsWoSc> getAllSetOutItemsWoSc(String cid);

    Optional<SetOutItemsWoSc> getSetOutItemsWoScById(String cid, String cd);

    void add(SetOutItemsWoSc domain);

    void update(SetOutItemsWoSc domain);

    void remove(String cid, String cd);

}
