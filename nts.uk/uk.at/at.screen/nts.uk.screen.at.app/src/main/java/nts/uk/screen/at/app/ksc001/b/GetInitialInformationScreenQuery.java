package nts.uk.screen.at.app.ksc001.b;

import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * ScreenQuery: 初期情報を取得する
 */
@Stateless
public class GetInitialInformationScreenQuery {
    @Inject
    private ClosureRepository closureRepository;
}
