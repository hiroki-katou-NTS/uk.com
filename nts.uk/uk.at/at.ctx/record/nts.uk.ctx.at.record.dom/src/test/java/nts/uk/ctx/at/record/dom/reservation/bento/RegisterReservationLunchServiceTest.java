package nts.uk.ctx.at.record.dom.reservation.bento;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.reservation.Helper;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenu;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.Achievements;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.BentoReservationSetting;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.CorrectionContent;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.OperationDistinction;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;
import nts.uk.shr.com.context.loginuser.SelectedLanguage;
import nts.uk.shr.com.context.loginuser.role.LoginUserRoles;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;

@RunWith(JMockit.class)
public class RegisterReservationLunchServiceTest {

    @Injectable
    RegisterReservationLunchService.Require require;

    @Mocked
    AppContexts appContexts;

    /**
     * Init Appcontext
     */
    @Before
    public void init(){
        new Expectations() {{
            AppContexts.user();
            result = new LoginUserContext() {
                public boolean hasLoggedIn() {
                    return false;
                }

                @Override
                public boolean isEmployee() {
                    return false;
                }

                @Override
                public String userId() {
                    return null;
                }

                @Override
                public String personId() {
                    return null;
                }

                @Override
                public String contractCode() {
                    return null;
                }

                @Override
                public String companyId() {
                    return "CID";
                }

                @Override
                public String companyCode() {
                    return null;
                }

                @Override
                public String employeeId() {
                    return null;
                }

                @Override
                public String employeeCode() {
                    return null;
                }

                @Override
                public LoginUserRoles roles() {
                    return null;
                }

                @Override
                public SelectedLanguage language() {
                    return null;
                }
            };
        }};
    }

    /**
     * 弁当予約設定を登録する TestCase 1
     * 1: get(会社ID) return null
     * 3: get(会社ID,’9999/12/31’) return null     *
     */
    @Test
    public void register_1() {
        // Input param
        OperationDistinction operationDistinction = Helper.Reservation.operationDistinction.DUMMY;
        Achievements achievements = Helper.Reservation.achievements.DUMMY;
        GeneralDate date = GeneralDate.max();
        CorrectionContent correctionContent = Helper.Reservation.correctionContent.DUMMY;

        // Mock up
        new Expectations() {{
            require.getReservationSettings("CID");
            result = null;

            require.getBentoMenu("CID",date);
            result = null;
        }};

        NtsAssert.atomTask(
                () -> RegisterReservationLunchService.register(
                        require, operationDistinction, achievements, correctionContent, null),
                any -> require.registerBentoMenu(any.get(),any.get()),
                any -> require.inSert(any.get())
                );
    }

    /**
     * 弁当予約設定を登録する TestCase 2
     * 1: get(会社ID) return not null
     * 3: get(会社ID,’9999/12/31’) return null
     */
    @Test
    public void register_2() {

        // Input param
        OperationDistinction operationDistinction = Helper.Reservation.operationDistinction.DUMMY;
        Achievements achievements = Helper.Reservation.achievements.DUMMY;
        GeneralDate date = GeneralDate.max();
        CorrectionContent correctionContent = Helper.Reservation.correctionContent.DUMMY;

        BentoReservationSetting reservationSetting =
                new BentoReservationSetting("CID",operationDistinction,correctionContent,achievements);

        new Expectations() {{
            require.getReservationSettings("CID");
            result = reservationSetting;

            require.getBentoMenu("CID",date);
            result = null;
        }};

        NtsAssert.atomTask(
                () -> RegisterReservationLunchService.register(
                        require, operationDistinction, achievements, correctionContent, null),
                any -> require.registerBentoMenu(any.get(),any.get()),
                any -> require.update(any.get())
        );
    }

    /**
     * 弁当予約設定を登録する TestCase 3
     * 1: get(会社ID) return not null
     * 3: get(会社ID,’9999/12/31’) return not null
     */
    @Test
    public void register_3() {
        // Input Param
        OperationDistinction operationDistinction = Helper.Reservation.operationDistinction.DUMMY;
        Achievements achievements = Helper.Reservation.achievements.DUMMY;
        GeneralDate date = GeneralDate.max();
        CorrectionContent correctionContent = Helper.Reservation.correctionContent.DUMMY;

        BentoReservationSetting reservationSetting =
                new BentoReservationSetting("CID",operationDistinction,correctionContent,achievements);

        BentoMenu menu = new BentoMenu(
                "historyId",
                Arrays.asList(Helper.Menu.Item.bentoReserveFrame(1, true, true)),
                Helper.ClosingTime.UNLIMITED);

        new Expectations() {{
            require.getReservationSettings("CID");
            result = reservationSetting;

            require.getBentoMenu("CID",date);
            result = menu;
        }};

        NtsAssert.atomTask(
                () -> RegisterReservationLunchService.register(
                        require, operationDistinction, achievements, correctionContent, null),
                any -> require.registerBentoMenu(any.get(),any.get()),
                any -> require.update(any.get())
        );
    }

    /**
     * 弁当予約設定を登録する TestCase 4
     * 1: get(会社ID) return null
     * 3: get(会社ID,’9999/12/31’) return not null
     */
    @Test
    public void register_4() {
        // Input Param
        OperationDistinction operationDistinction = Helper.Reservation.operationDistinction.DUMMY;
        Achievements achievements = Helper.Reservation.achievements.DUMMY;
        GeneralDate date = GeneralDate.max();
        CorrectionContent correctionContent = Helper.Reservation.correctionContent.DUMMY;

        BentoMenu menu = new BentoMenu(
                "historyId",
                Arrays.asList(Helper.Menu.Item.bentoReserveFrame(1, true, true)),
                Helper.ClosingTime.UNLIMITED);

        new Expectations() {{
            require.getReservationSettings("CID");
            result = null;

            require.getBentoMenu("CID",date);
            result = menu;
        }};

        NtsAssert.atomTask(
                () -> RegisterReservationLunchService.register(
                        require, operationDistinction, achievements, correctionContent, null),
                any -> require.registerBentoMenu(any.get(),any.get()),
                any -> require.inSert(any.get())
        );
    }
}
