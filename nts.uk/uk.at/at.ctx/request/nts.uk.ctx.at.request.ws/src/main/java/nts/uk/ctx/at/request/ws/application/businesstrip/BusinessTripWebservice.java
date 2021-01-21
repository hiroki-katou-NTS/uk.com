package nts.uk.ctx.at.request.ws.application.businesstrip;


import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.command.application.businesstrip.AddBusinessTripCommand;
import nts.uk.ctx.at.request.app.command.application.businesstrip.AddBusinessTripCommandHandler;
import nts.uk.ctx.at.request.app.command.application.businesstrip.UpdateBusinessTripCommand;
import nts.uk.ctx.at.request.app.command.application.businesstrip.UpdateBusinessTripCommandHandler;
import nts.uk.ctx.at.request.app.find.application.businesstrip.BusinessTripFinder;
import nts.uk.ctx.at.request.app.find.application.businesstrip.ParamStart;
import nts.uk.ctx.at.request.app.find.application.businesstrip.businesstripdto.*;
import nts.uk.ctx.at.request.app.find.application.gobackdirectly.ParamUpdate;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.ConfirmMsgOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

@Path("at/request/application/businesstrip")
@Produces("application/json")
public class BusinessTripWebservice extends WebService {

    @Inject
    private BusinessTripFinder businessTripFinder;

    @Inject
    private AddBusinessTripCommandHandler addBusinessTripCommandHandler;

    @Inject
    private UpdateBusinessTripCommandHandler updateBusinessTripCommandHandler;

    /**
     * 起動する
     * @param param
     * @return
     */
    @POST
    @Path("start")
    public DetailStartScreenInfoDto start(ParamStart param) {
        return this.businessTripFinder.initKAF008(param);
    }

    /**
     * 出張申請登録前エラーチェック
     * @param param
     * @return
     */
    @POST
    @Path("checkBeforeRegister")
    public List<ConfirmMsgOutput> checkBeforeRegister(CheckBeforeRegisterDto param) {
        return this.businessTripFinder.checkBeforeRegister(param);
    }

    /**
     * 出張申請（新規）登録処理
     * @param param
     * @return
     */
    @POST
    @Path("register")
    public ProcessResult register(AddBusinessTripCommand param) {
        return this.addBusinessTripCommandHandler.handle(param);
    }

    /**
     * 出張申請期間の勤務内容を取得する
     * @param param
     * @return
     */
    @POST
    @Path("changeAppDate")
    public DetailStartScreenInfoDto changeAppDate(ParamChangeDate param) {
        return this.businessTripFinder.updateAppDate(param.getBusinessTripInfoOutputDto(), param.getApplicationDto());
    }

    /**
     * 勤務種類コードを入力する
     * @param param
     * @return
     */
    @POST
    @Path("changeWorkTypeCode")
    public BusinessTripInfoOutputDto changeWorkTypeCode(ChangeWorkCodeParam param) {
        return this.businessTripFinder.changeWorkTypeCode(param);
    }

    /**
     * 就業時間帯コードを入力する
     * @param param
     * @return
     */
    @POST
    @Path("changeWorkTimeCode")
    public WorkTypeNameDto changeWorkTimeCode(ChangeWorkCodeParam param) {
        return this.businessTripFinder.changeWorkTimeCode(param);
    }

    /**
     * 出張申請画面初期（更新）
     * @param param
     * @return
     */
    @POST
    @Path("getDetailPC")
    public DetailScreenDto getDetailPC(ParamUpdate param) {
        return this.businessTripFinder.getDetailKAF008(param);
    }

    /**
     * 出張申請を更新登録で更新する
     * @param param
     * @return
     */
    @POST
    @Path("updateBusinessTrip")
    public ProcessResult updateBusinesstrip(UpdateBusinessTripCommand param) {
        return this.updateBusinessTripCommandHandler.handle(param);
    }

    /**
     * 勤務就業の選択を実行する
     * @param param
     * @return
     */
    @POST
    @Path("startKDL003")
    public boolean startKDL003(ParamStartKDL003 param) {
        return this.businessTripFinder.getFlagStartKDL003(param);
    }
}
