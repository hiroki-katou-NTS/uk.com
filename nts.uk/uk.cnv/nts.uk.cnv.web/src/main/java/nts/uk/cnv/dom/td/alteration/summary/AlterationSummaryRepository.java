package nts.uk.cnv.dom.td.alteration.summary;

import java.util.List;

import nts.uk.cnv.dom.td.devstatus.DevelopmentProgress;
import nts.uk.cnv.dom.td.devstatus.DevelopmentStatus;

public interface AlterationSummaryRepository {

	/**
	 * 指定したFeatureに対するorutaをすべて取得する
	 * @param featureId
	 * @return
	 */
	List<AlterationSummary> getByFeature(String featureId);
	
	/**
	 * 指定したFeatureに対するorutaのうち指定した開発状況にいるorutaをすべて取得する
	 * @param featureId
	 * @param devStatus
	 * @return
	 */
	List<AlterationSummary> getByFeature(String featureId, DevelopmentStatus devStatus);
	
	/**
	 * 指定したFeatureに対するorutaのうち指定した開発進捗状況を満たすorutaをすべて取得する
	 * @param featureId
	 * @param devProgress
	 * @return
	 */
	List<AlterationSummary> getByFeature(String featureId, DevelopmentProgress devProgress);
	
	/**
	 * 指定したTableに対するorutaのうち指定した開発進捗状況を満たすorutaをすべて取得する
	 * @param alterId
	 * @param devProgress
	 * @return
	 */
	List<AlterationSummary> getByTable(String tableId, DevelopmentProgress devProgress);

	/**
	 * 指定したイベントに含まれるorutaのうち、指定した開発状況にいるorutaをすべて取得する
	 * @param eventId
	 * @param devStatus
	 * @return
	 */
	List<AlterationSummary> getByEvent(String eventId, DevelopmentStatus devStatus);

}
