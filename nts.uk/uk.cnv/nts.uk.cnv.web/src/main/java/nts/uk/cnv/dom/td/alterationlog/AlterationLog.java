package nts.uk.cnv.dom.td.alterationlog;

import java.util.List;

import nts.uk.cnv.dom.td.alterationlog.content.AlterationContent;
import nts.uk.cnv.dom.td.version.TableDesignVersion;

/**
 * 変更記録
 */
public class AlterationLog {
	String logID;
	String tableID;
	TableDesignVersion version;
	List<AlterationContent> contents;
}
