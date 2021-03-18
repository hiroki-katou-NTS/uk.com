package nts.uk.cnv.dom.td.alteration.summary;

import java.util.List;

import nts.uk.cnv.dom.td.devstatus.DevelopmentProgress;
import nts.uk.cnv.dom.td.devstatus.DevelopmentStatus;

public interface AlterationSummaryRepository {

	/**
	 * orutaをすべて取得する
	 * @param featureId
	 * @return
	 */
	List<AlterationSummary> getAll(String featureId);
	
	/**
	 * 指定した開発状況にいるorutaをすべて取得する
	 * @param featureId
	 * @param devStatus
	 * @return
	 */
	List<AlterationSummary> getAll(String featureId, DevelopmentStatus devStatus);
	
	/**
	 * 指定した開発状況に到達していないorutaをすべて取得する
	 * @param featureId
	 * @param devProgress
	 * @return
	 */
	List<AlterationSummary> getBefore(String featureId, DevelopmentProgress devProgress);
	
	/**
	 * 指定した開発状況を通過しているorutaをすべて取得する
	 * @param featureId
	 * @param devProgress
	 * @return
	 */
	List<AlterationSummary> getAfter(String featureId, DevelopmentProgress devProgress);
	
	
	/**
	 * 自身より過去のorutaのうち、指定した開発状況に達していないorutaを取得する
	 * @param alterId
	 * @param devProgress
	 * @return
	 */
	List<AlterationSummary> getOlder(String alterId, DevelopmentProgress devProgress);

}
