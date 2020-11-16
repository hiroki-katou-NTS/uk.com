package nts.uk.ctx.at.function.app.command.alarmworkplace.alarmlist;

import lombok.val;
import nts.arc.layer.app.command.AsyncCommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.data.TaskDataSetter;
import nts.uk.ctx.at.function.dom.alarm.AlarmPatternCode;
import nts.uk.ctx.at.function.dom.alarmworkplace.AlarmPatternSettingWorkPlace;
import nts.uk.ctx.at.function.dom.alarmworkplace.AlarmPatternSettingWorkPlaceRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * UKDesign.UniversalK.就業.KAL_アラームリスト.KAL011_アラームリスト(職場別).D：進捗ダイアログ.アルゴリズム.アラームリストを出力する
 * アラームリストを出力する
 *
 * @author Le Huu Dat
 */
@Stateless
public class ExtractAlarmListWorkPlaceCommandHandler extends AsyncCommandHandler<ExtractAlarmListWorkPlaceCommand> {

    @Inject
    private AlarmPatternSettingWorkPlaceRepository alarmPatternSettingWorkPlaceRepo;

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    protected void handle(CommandHandlerContext<ExtractAlarmListWorkPlaceCommand> context) {
        val asyncContext = context.asAsync();
        String cid = AppContexts.user().companyId();
        ExtractAlarmListWorkPlaceCommand command = context.getCommand();
        TaskDataSetter dataSetter = asyncContext.getDataSetter();
        AtomicInteger counter = new AtomicInteger(0);

        // ログイン者IDの権限をチェック

    }

    /**
     * ログイン者IDの権限をチェック
     *
     * @param cid         会社ID
     * @param patternCode アラームパターンコード
     * @return 実行できるか権限
     */
    private boolean hasAuthorityToExecute(String cid, String patternCode) {
        //ドメインモデル「アラームリストパターン設定(職場別)」を取得
        Optional<AlarmPatternSettingWorkPlace> pattern = alarmPatternSettingWorkPlaceRepo.getBy(cid, new AlarmPatternCode(patternCode));


        return true;
    }
}