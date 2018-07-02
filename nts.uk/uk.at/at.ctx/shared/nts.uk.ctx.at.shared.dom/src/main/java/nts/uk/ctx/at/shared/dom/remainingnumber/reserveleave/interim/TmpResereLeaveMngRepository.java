package nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.interim;

import java.util.Optional;

public interface TmpResereLeaveMngRepository {
	/**
	 * get 暫定積立年休管理データ by id
	 * @param ResereMngId
	 * @return
	 */
	public Optional<TmpResereLeaveMng> getById(String resereMngId);
	/**
	 * delete 暫定積立年休管理データ by id
	 * @param resereMngId
	 */
	public void deleteById(String resereMngId);
	
	/**
	 * 登録および更新
	 * @param dataMng
	 */
	public void persistAndUpdate(TmpResereLeaveMng dataMng);
}
