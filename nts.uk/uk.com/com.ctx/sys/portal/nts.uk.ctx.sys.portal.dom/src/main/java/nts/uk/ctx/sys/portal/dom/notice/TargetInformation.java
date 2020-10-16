package nts.uk.ctx.sys.portal.dom.notice;

import java.util.List;

import nts.arc.layer.dom.DomainObject;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.システム.ポータル.お知らせ.対象情報
 * @author DungDV
 *
 */
public class TargetInformation extends DomainObject {
	
	/** 対象社員ID */
	List<String> targetSIDs;
	
	/** 対象職場ID */
	List<String> targetWpids;
	
	/** 宛先区分 */
	DestinationClassification destination;
}