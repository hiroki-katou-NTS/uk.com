package nts.uk.ctx.at.function.dom.alarmworkplace.service.executeauto;

import nts.arc.time.GeneralDateTime;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.function.dom.alarmworkplace.extractprocessstatus.AlarmListExtractProcessStatusWorkplace;
import nts.uk.ctx.at.function.dom.alarmworkplace.extractprocessstatus.AlarmListExtractProcessStatusWorkplaceRepository;
import nts.uk.ctx.at.function.dom.alarmworkplace.extractprocessstatus.ExtractState;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.就業機能.アラーム_職場別.アルゴリズム.アラームリスト自動実行処理.抽出処理状況を作成する
 * 抽出処理状況を作成する
 *
 * @author Le Huu Dat
 */
@Stateless
public class ExtractProcessStatusService {
    @Inject
    private AlarmListExtractProcessStatusWorkplaceRepository alarmListExtractProcessStatusWorkplaceRepo;

    public String create(String cid) {
        String id = IdentifierUtil.randomUniqueId();
        GeneralDateTime now = GeneralDateTime.now();
        AlarmListExtractProcessStatusWorkplace domain = new AlarmListExtractProcessStatusWorkplace(id, cid,
                now.toDate(), now.hours() * 60 + now.minutes(),
                null, null, null,
                ExtractState.PROCESSING);
        alarmListExtractProcessStatusWorkplaceRepo.add(domain);
        return domain.getId();
    }
}
