package nts.uk.ctx.at.record.dom.reservation.bento;

import nts.arc.task.tran.AtomTask;

/**
 * 注文済みにする
 * @author Hoang Anh Tuan
 */
public class BentoMakeOrderService {

    public static AtomTask update(Require require, BentoReservation bentoReservation) {
        return AtomTask.of(() -> {
            require.update(bentoReservation);
        });
    }

    public static interface Require {
        void update(BentoReservation bentoReservation);
    }
}
