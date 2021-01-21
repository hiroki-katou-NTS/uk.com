package nts.uk.ctx.at.request.app.find.application.businesstrip;

import lombok.Data;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;

import java.util.List;

@Data
public class ParamStart {

    //パラメータ.申請者List
    private List<String> applicantList;

    private List<String> dateLst;

    private AppDispInfoStartupDto appDispInfoStartupOutput;

}
