package nts.uk.ctx.at.request.dom.application.applist.service;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.request.dom.application.ApplicationType;

/**
 * refactor 4
 * UKDesign.UniversalK.就業.KAF_申請.CMM045_申請一覧・承認一覧.A:申請一覧画面.アルゴリズム.パラメータ.申請種類リスト
 * @author Doan Duy Hung
 *
 */
@Getter
public class ListOfAppTypes {
	
	private ApplicationType appType;
	
	private String appName;
	
	private boolean choice;
	
	private Optional<Integer> opProgramID;
	
	private Optional<ApplicationTypeDisplay> opApplicationTypeDisplay;
	
	private Optional<String> opString;
	
}
