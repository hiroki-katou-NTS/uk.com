package nts.uk.ctx.sys.assist.dom.datarestoration.common;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.assist.dom.datarestoration.ServerPrepareMng;
import nts.uk.ctx.sys.assist.dom.datarestoration.ServerPrepareOperatingCondition;
import nts.uk.ctx.sys.assist.dom.tablelist.TableList;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@Stateless
public class ThresholdConfigurationCheck {

	private FileUtil fileUtil;
	
	@Inject
	private CompanyDeterminationProcess companyDeterminationProcess;
	
	public ServerPrepareMng checkFileConfiguration(ServerPrepareMng serverPrepareMng, List<TableList> tableList){
		boolean fileConfigError = false;
		if (tableList.size()>1){
			NotUseAtr survey = tableList.get(1).getSurveyPreservation();
			String csvStoragePath = fileUtil.getCsvStoragePath(serverPrepareMng.getFileId().get());
			File f = new File(csvStoragePath);
			if (f.exists()){
				List<String> listFileName = Arrays.asList(f.list());
				if (tableList.size() -1 == listFileName.size() -2){
					if (tableList.size() > 1){
						if(survey == NotUseAtr.NOT_USE) serverPrepareMng.setOperatingCondition(ServerPrepareOperatingCondition.CAN_NOT_SAVE_SURVEY);
						for(int i = 1; i <= tableList.size(); i++){
							String tableJapanName = tableList.get(i).getTableJapaneseName();
							if(!listFileName.contains(tableJapanName)){
								fileConfigError = true;
								break;
							}
						}
					}
				} else {
					fileConfigError = true;
				}
				
			} else {
				fileConfigError = true;
			}
			
		}
		if (fileConfigError){
			serverPrepareMng.setOperatingCondition(ServerPrepareOperatingCondition.FILE_CONFIG_ERROR);
		}
		return serverPrepareMng;
	}
}
